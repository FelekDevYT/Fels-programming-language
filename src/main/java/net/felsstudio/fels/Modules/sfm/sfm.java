package main.java.net.felsstudio.fels.Modules.sfm;

import main.java.net.felsstudio.fels.Start.Starter;
import main.java.net.felsstudio.fels.exceptions.ArgumentsMismatchException;
import main.java.net.felsstudio.fels.exceptions.TypeException;
import net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class sfm implements Module {//Standard fels module

    @Override
    public void init() {
        Functions.set("echo", args -> {
        for (Value arg : args) {
        System.out.println(arg.asString());
    }
        System.out.println();
        return NumberValue.ZERO;
    });
        Functions.set("array", new Function() {

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
        Functions.set("default", new Function() {
            @Override
            public Value execute(Value... args) {
            Arguments.check(2, args.length);
            if (isEmpty(args[0])) {
                return args[1];
            }
            return args[0];
        }

            private boolean isEmpty(Value value) {
                if (value == null || value.raw() == null) {
                    return true;
                }
                switch (value.type()) {
                    case Types.NUMBER:
                    return (value.asInt() == 0);
                    case Types.STRING:
                    return (value.asString().isEmpty());
                    case Types.ARRAY:
                    return ((ArrayValue) value).size() == 0;
                    case Types.MAP:
                    return ((MapValue) value).size() == 0;
                    default:
                    return false;
                }
            }
        });
        Functions.set("thread", args ->{
        // Создаём новый поток и передаём параметры, если есть.
        // Функция может передаваться как напрямую, так и по имени
        if (args.length == 0) throw new ArgumentsMismatchException("At least one arg expected");

        Function body;
        if (args[0].type() == Types.FUNCTION) {
            body = ((FunctionValue) args[0]).getValue();
        } else {
            body = Functions.get(args[0].asString());
        }

        // Сдвигаем аргументы
        final Value[] params = new Value[args.length - 1];
        if (params.length > 0) {
            System.arraycopy(args, 1, params, 0, params.length);
        }

        final Thread thread = new Thread(() -> body.execute(params));
        thread.setUncaughtExceptionHandler(Starter::handleException);
        thread.start();
        return NumberValue.ZERO;
    });
        /*Functions.set("toString", f ->{
            return new StringValue(f[0].asString());
        });
        Functions.set("toNum", f ->{
            return NumberValue.of(f[0].asInt());
        });*/
        //DEPRECATED FUNCTIONS WITH CREATED FTYPES MODULE
        Functions.set("readln", f ->{
        Scanner scan = new Scanner(System.in);
        return new StringValue(scan.nextLine());
    });
        Functions.set("readNum",f ->{
        Scanner scan = new Scanner(System.in);
        return NumberValue.of(scan.nextInt());
    });
        ///DERECATED WITH CREATED TOKEN IN FELS LANGUAGE
        Functions.set("oldeach", new Function() {
            private static final int UNKNOWN = -1;

            @Override
            public Value execute(Value... args) {
            Arguments.check(2, args.length);
            final Value container = args[0];
            final Function consumer = ValueUtils.consumeFunction(args[1], 1);
            final int argsCount;
            if (consumer instanceof UserDefinedFunction) {
                argsCount = ((UserDefinedFunction) consumer).getArgsCount();
            } else {
                argsCount = UNKNOWN;
            }

            switch (container.type()) {
                case Types.STRING:
                final StringValue string = (StringValue) container;
                if (argsCount == 2) {
                    for (char ch : string.asString().toCharArray()) {
                        consumer.execute(new StringValue(String.valueOf(ch)), NumberValue.of(ch));
                    }
                } else {
                    for (char ch : string.asString().toCharArray()) {
                        consumer.execute(new StringValue(String.valueOf(ch)));
                    }
                }
                return string;

                case Types.ARRAY:
                final ArrayValue array = (ArrayValue) container;
                if (argsCount == 2) {
                    int index = 0;
                    for (Value element : array) {
                        consumer.execute(element, NumberValue.of(index++));
                    }
                } else {
                    for (Value element : array) {
                        consumer.execute(element);
                    }
                }
                return array;

                case Types.MAP:
                final MapValue map = (MapValue) container;
                for (Map.Entry<Value, Value> element : map) {
                consumer.execute(element.getKey(), element.getValue());
            }
                return map;

                default:
                throw new TypeException("Cannot iterate " + Types.typeToString(container.type()));
            }
        }
        });
        Functions.set("splice",args ->{
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

        Functions.set("indexOf",f ->{//string val
        String value = f[0].asString();
        return NumberValue.of(value.indexOf(f[1].asString()));
    });

        Functions.set("lastIndexOf", args->{
        final String input = args[0].asString();
        final String what = args[1].asString();
        if (args.length == 2) {
            return NumberValue.of(input.lastIndexOf(what));
        }
        final int index = args[2].asInt();
        return NumberValue.of(input.lastIndexOf(what, index));
    });

        Functions.set("replace", f->{
        String value = f[0].asString();
        return new StringValue(value.replace(f[1].asString(), f[2].asString()));
    });

    Functions.set("trim", f ->{
        return new StringValue(f[0].asString().trim());
    });

    Functions.set("getBytes", f ->{
        String value = f[0].asString();
        byte[] array = value.getBytes();
        ArrayValue arrayValue = new ArrayValue(array.length);
        for (int i = 0; i < array.length; i++) {
        arrayValue.set(i,NumberValue.of(array[i]));
    }
        return arrayValue;
    });

    Functions.set("split", args ->{
        Arguments.checkOrOr(2, 3, args.length);

        final String input = args[0].asString();
        final String regex = args[1].asString();
        final int limit = (args.length == 3) ? args[2].asInt() : 0;

        final String[] parts = input.split(regex, limit);
        return ArrayValue.of(parts);
    });

    Functions.set("filter", new Function() {
            @Override
            public Value execute(Value... args) {
            Arguments.check(2, args.length);
            if (args[1].type() != Types.FUNCTION) {
                throw new TypeException("Function expected in second argument");
            }

            final Value container = args[0];
            final Function consumer = ((FunctionValue) args[1]).getValue();
            if (container.type() == Types.ARRAY) {
                return filterArray((ArrayValue) container, consumer);
            }

            if (container.type() == Types.MAP) {
                return filterMap((MapValue) container, consumer);
            }

            throw new TypeException("Invalid first argument. Array or map expected");
        }

        private Value filterArray(ArrayValue array, Function predicate) {
                final int size = array.size();
                final List<Value> values = new ArrayList<Value>(size);
                for (Value value : array) {
                if (predicate.execute(value) != NumberValue.ZERO) {
                    values.add(value);
                }
            }
                final int newSize = values.size();
                return new ArrayValue(values.toArray(new Value[newSize]));
            }

            private Value filterMap(MapValue map, Function predicate) {
                final MapValue result = new MapValue(map.size());
                for (Map.Entry<Value, Value> element : map) {
                if (predicate.execute(element.getKey(), element.getValue()) != NumberValue.ZERO) {
                    result.set(element.getKey(), element.getValue());
                }
            }
                return result;
            }
        });
    Functions.set("toHexString",args -> {
        Arguments.check(1, args.length);
        long value;
        if (args[0].type() == Types.NUMBER) {
            value = ((NumberValue) args[0]).asLong();
        } else {
            value = (long) args[0].asNumber();
        }
        return new StringValue(Long.toHexString(value));
    });

    Functions.set("equals",f ->{
        if(Objects.equals(f[0],f[1])){
            return NumberValue.ONE;
        }else{
            return NumberValue.ZERO;
        }
    });

    Functions.set("concat", f -> {
        StringBuilder sb = new StringBuilder();
        for(Value v : f){
        sb.append(v.asString());
    }
        return new StringValue(sb.toString());
    });

    Functions.set("equalsIgnoreCase", f ->{
        if(f[0].asString().equalsIgnoreCase(f[1].asString())){
            return NumberValue.ONE;
        }else return NumberValue.ZERO;
    });

    Functions.set("compareTo", f ->{
        return NumberValue.of(f[0].asString().compareTo(f[1].asString()));
    });

    Functions.set("compareToIgnoreCase", f ->{
        return NumberValue.of(f[0].asString().compareToIgnoreCase(f[1].asString()));
    });

    Functions.set("isEmpty", f ->{
        if(f[0].asString().isEmpty()){
            return NumberValue.ONE;
        }else{
            return NumberValue.ZERO;
        }
    });

    Functions.set("toUpperCase", f ->{
        return new StringValue(f[0].asString().toUpperCase());
    });

        Functions.set("toLowerCase", f ->{
        return new StringValue(f[0].asString().toLowerCase());
    });

        //DEPRECATED WITH CREATED FUNCTION LENGTH
        Functions.set("StringLength",f ->{
        return NumberValue.of(f[0].asString().length());
    });

        Functions.set("substring",f ->{
        return new StringValue(f[0].asString().substring((int)f[1].asInt(),(int)f[2].asInt()));
    });

        Functions.set("length", args ->{
        if (args.length == 0) throw new RuntimeException("At least one arg expected");

        final Value val = args[0];
        int length;
        switch (val.type()) {
        case Types.ARRAY:
        length = ((ArrayValue) val).size();
        break;
        case Types.MAP:
        length = ((MapValue) val).size();
        break;
        case Types.STRING:
        length = ((StringValue) val).length();
        break;
        case Types.FUNCTION:
        final Function func = ((FunctionValue) val).getValue();
        if (func instanceof UserDefinedFunction) {
            length = ((UserDefinedFunction) func).getArgsCount();
        } else {
            length = 0;
        }
        break;
        default:
        length = 0;

    }
        return NumberValue.of(length);
    });

        Functions.set("getEmpty", f ->{
        return new StringValue("");
    });

        Functions.set("quit", f ->{
        System.exit((int)f[0].asInt());
        return null;
    });

        Functions.set("replaceAll", f ->{
        if (f.length != 3) throw new ArgumentsMismatchException("Three arguments expected");

        final String input = f[0].asString();
        final String regex = f[1].asString();
        final String replacement = f[2].asString();

        return new StringValue(input.replaceAll(regex, replacement));
    });
        Functions.set("replaceFirst", f ->{
        if (f.length != 3) throw new ArgumentsMismatchException("Three arguments expected");

        final String input = f[0].asString();
        final String regex = f[1].asString();
        final String replacement = f[2].asString();

        return new StringValue(input.replaceFirst(regex, replacement));
    });
        Functions.set("sprintf",args ->{
        if (args.length < 1) throw new ArgumentsMismatchException("At least one argument expected");

        final String format = args[0].asString();
        final Object[] values = new Object[args.length - 1];
        for (int i = 1; i < args.length; i++) {
        values[i - 1] = (args[i].type() == Types.NUMBER) ? args[i].asInt() : args[i].asString();
    }
        return new StringValue(String.format(format, values));
    });
        Functions.set("arrayCombine", args -> {
        if (args.length != 2) throw new ArgumentsMismatchException("Two arguments expected");
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
        Functions.set("arrayKeyExists",args -> {
        if (args.length != 2) throw new ArgumentsMismatchException("Two arguments expected");
        if (args[1].type() != Types.MAP) {
            throw new TypeException("Map expected in second argument");
        }
        final MapValue map = ((MapValue) args[1]);
        return NumberValue.fromBoolean(map.containsKey(args[0]));
    });
        Functions.set("arrayKeys",args ->{
        if (args.length != 1) throw new ArgumentsMismatchException("One argument expected");
        if (args[0].type() != Types.MAP) {
            throw new TypeException("Map expected in first argument");
        }
        final MapValue map = ((MapValue) args[0]);
        final List<Value> keys = new ArrayList<>(map.size());
        for (Map.Entry<Value, Value> entry : map) {
        keys.add(entry.getKey());
    }
        return new ArrayValue(keys);
    });
        Functions.set("arrayValues",args ->{
        if (args.length != 1) throw new ArgumentsMismatchException("One argument expected");
        if (args[0].type() != Types.MAP) {
            throw new TypeException("Map expected in first argument");
        }
        final MapValue map = ((MapValue) args[0]);
        final List<Value> values = new ArrayList<>(map.size());
        for (Map.Entry<Value, Value> entry : map) {
        values.add(entry.getValue());
    }
        return new ArrayValue(values);
    });
        Functions.set("chain",args ->{
        Arguments.checkAtLeast(2, args.length);

        Value result = args[0];
        for (int i = 1; i < args.length; i += 2) {
        final Value arg = args[i];
        if (arg.type() != Types.FUNCTION) {
            throw new TypeException(arg.toString() + " is not a function");
        }
        final Function function = ((FunctionValue) arg).getValue();
        result = function.execute(result, args[i+1]);
    }
        return result;
    });
        Functions.set("toChar",args ->{
        Arguments.check(1, args.length);
        return new StringValue(String.valueOf((char) args[0].asInt()));
    });
        Functions.set("charAt",args ->{
        Arguments.check(2, args.length);
        final String input = args[0].asString();
        final int index = args[1].asInt();

        return NumberValue.of((short)input.charAt(index));
    });
        Functions.set("arrayKeyExists",args ->{
        Arguments.check(2, args.length);
        if (args[1].type() != Types.MAP) {
            throw new TypeException("Map expected in second argument");
        }
        final MapValue map = ((MapValue) args[1]);
        return NumberValue.fromBoolean(map.containsKey(args[0]));
    });
        Functions.set("num",args -> {
        return NumberValue.of(args[0].asNumber());
    });
        Functions.set("sync",args ->{
        Arguments.check(1, args.length);
        if (args[0].type() != Types.FUNCTION) {
            throw new TypeException(args[0].toString() + " is not a function");
        }

        final BlockingQueue<Value> queue = new LinkedBlockingQueue<>(2);
        final Function synchronizer = (sArgs) -> {
        try {
            queue.put(sArgs[0]);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return NumberValue.ZERO;
    };
        final Function callback = ((FunctionValue) args[0]).getValue();
        callback.execute(new FunctionValue(synchronizer));

        try {
            return queue.take();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    });
        Functions.set("try",args ->{
        Arguments.checkOrOr(1, 2, args.length);
        if (args[0].type() != Types.FUNCTION) {
            throw new TypeException(args[0].toString() + " is not a function");
        }
        try {
            return ((FunctionValue) args[0]).getValue().execute();
        } catch (Exception ex) {
            if (args.length == 2) {
                switch (args[1].type()) {
                    case Types.FUNCTION:
                    final String message = ex.getMessage();
                    final Function catchFunction = ((FunctionValue) args[1]).getValue();
                    return catchFunction.execute(
                        new StringValue(ex.getClass().getName()),
                        new StringValue(message == null ? "" : message));
                    default:
                    return args[1];
                }
            }
            return NumberValue.MINUS_ONE;
        }
    });


        MapValue map = new MapValue(3);
        map.set("__VERSION__",new StringValue(Information.FELS_VERSION));
        map.set("__AUTOR__",new StringValue(Information.FELS_AUTHOR));
        map.set("__DATE__",new StringValue(Information.DATE));

    }

}