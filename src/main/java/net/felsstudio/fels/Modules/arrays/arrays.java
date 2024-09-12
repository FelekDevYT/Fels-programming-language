package main.java.net.felsstudio.fels.Modules.arrays;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.exceptions.ArgumentsMismatchException;
import main.java.net.felsstudio.fels.exceptions.TypeException;
import main.java.net.felsstudio.fels.lib.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class arrays implements Module {
    @Override
    public void init() {
        final MapValue map =new MapValue(15);

        map.set("array",new Function() {

            @Override
            public Value execute(Value... args) {
                return createArray(args, 0);
            }

            private ArrayValue createArray(Value[] args, int index) {
                final int size = (int) args[index].asInt();
                final int last = args.length - 1;
                ArrayValue array = new ArrayValue(size);
                if (index == last) {
                    for (int i = 0; i < size; i++) {
                        array.set(i, NumberValue.ZERO);
                    }
                } else if (index < last) {
                    for (int i = 0; i < size; i++) {
                        array.set(i, createArray(args, index + 1));
                    }
                }
                return array;
            }
        });

        map.set("join",args -> {
            Arguments.checkRange(1, 4, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new TypeException("Array expected in first argument");
            }

            final ArrayValue array = (ArrayValue) args[0];
            return switch (args.length) {
                case 1 -> ArrayValue.joinToString(array, "", "", "");
                case 2 -> ArrayValue.joinToString(array, args[1].asString(), "", "");
                case 3 -> ArrayValue.joinToString(array, args[1].asString(), args[2].asString(), args[2].asString());
                case 4 -> ArrayValue.joinToString(array, args[1].asString(), args[2].asString(), args[3].asString());
                default -> throw new ArgumentsMismatchException("Wrong number of arguments");
            };
        });

        map.set("sort",args ->{
            Arguments.checkAtLeast(1, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new TypeException("Array expected in first argument");
            }
            final Value[] elements = ((ArrayValue) args[0]).getCopyElements();

            switch (args.length) {
                case 1 -> Arrays.sort(elements);
                case 2 -> {
                    final Function comparator = ValueUtils.consumeFunction(args[1], 1);
                    Arrays.sort(elements, (o1, o2) -> {
                        try {
                            return comparator.execute(o1, o2).asInt();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                default -> throw new ArgumentsMismatchException("Wrong number of arguments");
            }

            return new ArrayValue(elements);
        });

        map.set("arrayCombine",args ->{
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new TypeException("Array expected in first argument");
            }
            if (args[1].type() != Types.ARRAY) {
                throw new TypeException("Array expected in second argument");
            }

            final ArrayValue keys = ((ArrayValue) args[0]);
            final ArrayValue values = ((ArrayValue) args[1]);
            final int length = Math.min(keys.size(), values.size());

            final MapValue result = new MapValue(length);
            for (int i = 0; i < length; i++) {
                result.set(keys.get(i), values.get(i));
            }
            return result;
        });

        map.set("arrayKeyExists",args ->{
            Arguments.check(2, args.length);
            if (args[1].type() != Types.MAP) {
                throw new TypeException("Map expected in second argument");
            }
            final MapValue map1 = ((MapValue) args[1]);
            return NumberValue.fromBoolean(map1.containsKey(args[0]));
        });

        map.set("arrayKeys",args ->{
            Arguments.check(1, args.length);
            if (args[0].type() != Types.MAP) {
                throw new TypeException("Map expected in first argument");
            }
            final MapValue map1 = ((MapValue) args[0]);
            final List<Value> keys = new ArrayList<>(map1.size());
            for (Map.Entry<Value, Value> entry : map1) {
                keys.add(entry.getKey());
            }
            return new ArrayValue(keys);
        });

        map.set("arrayValues",args ->{
            Arguments.check(1, args.length);
            if (args[0].type() != Types.MAP) {
                throw new TypeException("Map expected in first argument");
            }
            final MapValue map1 = ((MapValue) args[0]);
            final List<Value> values = new ArrayList<>(map1.size());
            for (Map.Entry<Value, Value> entry : map1) {
                values.add(entry.getValue());
            }
            return new ArrayValue(values);
        });

        map.set("arraySplice",args ->{
            Arguments.checkRange(2, 4, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new TypeException("Array expected at first argument");
            }
            final Value[] input = ((ArrayValue) args[0]).getCopyElements();
            final int size = input.length;

            int start = args[1].asInt();
            if (start < 0) start = size - Math.abs(start);
            start = Math.max(0, Math.min(size, start));

            final int deleteCount = (args.length >= 3)
                    ? Math.max(0, Math.min(size - start, args[2].asInt())) // [0..size - start)
                    : (size - start);

            final Value[] additions;
            if (args.length != 4) {
                additions = new Value[0];
            } else if (args[3].type() == Types.ARRAY) {
                additions = ((ArrayValue) args[3]).getCopyElements();
            } else {
                throw new TypeException("Array expected at fourth argument");
            }

            Value[] result = new Value[start + (size - start - deleteCount) + additions.length];
            System.arraycopy(input, 0, result, 0, start); // [0, start)
            System.arraycopy(additions, 0, result, start, additions.length); // insert new
            System.arraycopy(input, start + deleteCount, result, start + additions.length, size - start - deleteCount);
            return new ArrayValue(result);
        });

        map.set("range", new Function() {

            @Override
            public Value execute(Value[] args) {
                Arguments.checkRange(1, 3, args.length);
                return switch (args.length) {
                    case 2 -> RangeValue.of(getLong(args[0]), getLong(args[1]), 1);
                    case 3 -> RangeValue.of(getLong(args[0]), getLong(args[1]), getLong(args[2]));
                    default -> RangeValue.of(0, getLong(args[0]), 1);
                };
            }

            private static long getLong(Value v) {
                if (v.type() == Types.NUMBER) {
                    return ((NumberValue) v).asLong();
                }
                return v.asInt();
            }

            private static class RangeValue extends ArrayValue {

                public static ArrayValue of(long from, long to, long step) {
                    boolean isInvalid = false;
                    isInvalid = isInvalid || (step == 0);
                    isInvalid = isInvalid || ((step > 0) && (from >= to));
                    isInvalid = isInvalid || ((step < 0) && (to >= from));
                    if (isInvalid) return new ArrayValue(0);
                    return new RangeValue(from, to, step);
                }

                private final long from, to, step;
                private final int size;

                public RangeValue(long from, long to, long step) {
                    super(new Value[0]);
                    this.from = from;
                    this.to = to;
                    this.step = step;

                    final long base = (from < to)
                            ? Math.subtractExact(to, from)
                            : Math.subtractExact(from, to);
                    final long absStep = (step < 0) ? -step : step;
                    final long longSize = (base / absStep + (base % absStep == 0 ? 0 : 1));
                    this.size = longSize > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) longSize;
                }

                @Override
                public Value[] getCopyElements() {
                    final Value[] result = new Value[size];
                    int i = 0;
                    if (isIntegerRange()) {
                        final int toInt = (int) to;
                        final int stepInt = (int) step;
                        for (int value = (int) from; value < toInt; value += stepInt) {
                            result[i++] = NumberValue.of(value);
                        }
                    } else {
                        for (long value = from; value < to; value += step) {
                            result[i++] = NumberValue.of(value);
                        }
                    }
                    return result;
                }

                private boolean isIntegerRange() {
                    if (to > 0) {
                        return (from > Integer.MIN_VALUE && to < Integer.MAX_VALUE);
                    } else {
                        return (to > Integer.MIN_VALUE && from < Integer.MAX_VALUE);
                    }
                }

                @Override
                public int size() {
                    return size;
                }

                @Override
                public Value get(int index) {
                    long value = from + index * step;
                    if (isIntegerRange()) {
                        return NumberValue.of((int) (value));
                    } else {
                        return NumberValue.of(value);
                    }
                }

                @Override
                public void set(int index, Value value) {
                    // not implemented
                }

                @Override
                public Object raw() {
                    return getCopyElements();
                }

                @Override
                public String asString() {
                    if (size == 0) return "[]";

                    final StringBuilder sb = new StringBuilder();
                    sb.append('[').append(from);
                    for (long value = from + step; value < to; value += step) {
                        sb.append(", ").append(value);
                    }
                    sb.append(']');
                    return sb.toString();
                }

                @Override
                public Iterator<Value> iterator() {
                    if (isIntegerRange()) {
                        final int toInt = (int) to;
                        final int stepInt = (int) step;
                        return new Iterator<>() {

                            int value = (int) from;

                            @Override
                            public boolean hasNext() {
                                return (stepInt > 0) ? (value < toInt) : (value > toInt);
                            }

                            @Override
                            public Value next() {
                                final int result = value;
                                value += stepInt;
                                return NumberValue.of(result);
                            }

                            @Override
                            public void remove() {
                            }
                        };
                    }
                    return new Iterator<Value>() {

                        long value = from;

                        @Override
                        public boolean hasNext() {
                            return (step > 0) ? (value < to) : (value > to);
                        }

                        @Override
                        public Value next() {
                            final long result = value;
                            value += step;
                            return NumberValue.of(result);
                        }

                        @Override
                        public void remove() {
                        }
                    };
                }

                @Override
                public int hashCode() {
                    int hash = 5;
                    hash = 59 * hash + (int) (this.from ^ (this.from >>> 32));
                    hash = 59 * hash + (int) (this.to ^ (this.to >>> 32));
                    hash = 59 * hash + (int) (this.step ^ (this.step >>> 32));
                    return hash;
                }

                @Override
                public boolean equals(Object obj) {
                    if (this == obj) return true;
                    if (obj == null) return false;
                    if (getClass() != obj.getClass())
                        return false;
                    final RangeValue other = (RangeValue) obj;
                    if (this.from != other.from) return false;
                    if (this.to != other.to) return false;
                    if (this.step != other.step) return false;
                    return true;
                }

                @Override
                public int compareTo(Value o) {
                    if (o.type() == Types.ARRAY) {
                        final int lengthCompare = Integer.compare(size(), ((ArrayValue) o).size());
                        if (lengthCompare != 0) return lengthCompare;

                        if (o instanceof RangeValue o2) {
                            int compareResult;
                            compareResult = Long.compare(this.from, o2.from);
                            if (compareResult != 0) return compareResult;
                            compareResult = Long.compare(this.to, o2.to);
                            if (compareResult != 0) return compareResult;
                        }
                    }
                    return asString().compareTo(o.asString());
                }

                @Override
                public String toString() {
                    if (step == 1) {
                        return String.format("range(%d, %d)", from, to);
                    }
                    return String.format("range(%d, %d, %d)", from, to, step);
                }
            }
        });

        map.set("stringFromBytes",args ->{
            Arguments.checkOrOr(1, 2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new TypeException("Array expected at first argument");
            }
            final byte[] bytes = ValueUtils.toByteArray((ArrayValue) args[0]);
            final String charset = (args.length == 2) ? args[1].asString() : "UTF-8";
            try {
                return new StringValue(new String(bytes, charset));
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException(uee);
            }
        });

        Variables.define("arrays",map);
    }
}
