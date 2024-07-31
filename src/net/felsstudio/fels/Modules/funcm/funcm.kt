package net.felsstudio.fels.Modules.funcm;

import net.felsstudio.fels.exceptions.ArgumentsMismatchException;
import net.felsstudio.fels.exceptions.TypeException;
import net.felsstudio.fels.lib.*;
import net.felsstudio.fels.Modules.Module;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class funcm implements Module {
    @Override
    public void init() {
        Functions.set("map", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length < 2) throw new ArgumentsMismatchException("At least two args expected");

                final Value container = args[0];
                if (container.type() == Types.ARRAY) {
                    if (args[1].type() != Types.FUNCTION) {
                        throw new TypeException("Function expected in second arg");
                    }
                    final Function mapper = ((FunctionValue) args[1]).getValue();
                    return mapArray((ArrayValue) container, mapper);
                }

                if (container.type() == Types.MAP) {
                    if (args[1].type() != Types.FUNCTION) {
                        throw new TypeException("Function expected in second arg");
                    }
                    if (args[2].type() != Types.FUNCTION) {
                        throw new TypeException("Function expected in third arg");
                    }
                    final Function keyMapper = ((FunctionValue) args[1]).getValue();
                    final Function valueMapper = ((FunctionValue) args[2]).getValue();
                    return mapMap((MapValue) container, keyMapper, valueMapper);
                }

                throw new TypeException("Invalid first argument. Array or map exprected");
            }

            private Value mapArray(ArrayValue array, Function mapper) {
                final int size = array.size();
                final ArrayValue result = new ArrayValue(size);
                for (int i = 0; i < size; i++) {
                    result.set(i, mapper.execute(array.get(i)));
                }
                return result;
            }

            private Value mapMap(MapValue map, Function keyMapper, Function valueMapper) {
                final MapValue result = new MapValue(map.size());
                for (Map.Entry<Value, Value> element : map) {
                    final Value newKey = keyMapper.execute(element.getKey());
                    final Value newValue = valueMapper.execute(element.getValue());
                    result.set(newKey, newValue);
                }
                return result;
            }
        });

        Functions.set("filter", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length < 2) throw new ArgumentsMismatchException("At least two args expected");

                if (args[1].type() != Types.FUNCTION) {
                    throw new TypeException("Function expected in second arg");
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

        Functions.set("reduce", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 3) throw new ArgumentsMismatchException("Three args expected");

                if (args[2].type() != Types.FUNCTION) {
                    throw new TypeException("Function expected in third arg");
                }
                final Value container = args[0];
                final Value identity = args[1];
                final Function accumulator = ((FunctionValue) args[2]).getValue();
                if (container.type() == Types.ARRAY) {
                    Value result = identity;
                    final ArrayValue array = (ArrayValue) container;
                    for (Value element : array) {
                        result = accumulator.execute(result, element);
                    }
                    return result;
                }
                if (container.type() == Types.MAP) {
                    Value result = identity;
                    final MapValue map = (MapValue) container;
                    for (Map.Entry<Value, Value> element : map) {
                        result = accumulator.execute(result, element.getKey(), element.getValue());
                    }
                    return result;
                }
                throw new TypeException("Invalid first argument. Array or map exprected");
            }
        });

        Functions.set("combine", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length < 1) throw new ArgumentsMismatchException("At least one arg expected");

                Function result = null;
                for (Value arg : args) {
                    if (arg.type() != Types.FUNCTION) {
                        throw new TypeException(arg.toString() + " is not a function");
                    }
                    final Function current = result;
                    final Function next = ((FunctionValue) arg).getValue();
                    result = fArgs -> {
                        if (current == null) return next.execute(fArgs);
                        return next.execute(current.execute(fArgs));
                    };
                }

                return new FunctionValue(result);
            }
        });

        Functions.set("flatmap", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length < 2) throw new ArgumentsMismatchException("At least two arguments expected");

                if (args[0].type() != Types.ARRAY) {
                    throw new TypeException("Array expected in first argument");
                }
                if (args[1].type() != Types.FUNCTION) {
                    throw new TypeException("Function expected in second argument");
                }

                final Function mapper = ValueUtils.consumeFunction(args[1], 1);
                return flatMapArray((ArrayValue) args[0], mapper);
            }

            private Value flatMapArray(ArrayValue array, Function mapper) {
                final List<Value> values = new ArrayList<>();
                final int size = array.size();
                for (int i = 0; i < size; i++) {
                    final Value inner = mapper.execute(array.get(i));
                    if (inner.type() != Types.ARRAY) {
                        throw new TypeException("Array expected " + inner);
                    }
                    for (Value value : (ArrayValue) inner) {
                        values.add(value);
                    }
                }
                return new ArrayValue(values);
            }
        });

        Functions.set("sortBy",f ->{
            if (f.length != 2) throw new ArgumentsMismatchException("Two arguments expected");

            if (f[0].type() != Types.ARRAY) {
                throw new TypeException("Array expected in first argument");
            }
            if (f[1].type() != Types.FUNCTION) {
                throw new TypeException("Function expected in second argument");
            }

            final Value[] elements = ((ArrayValue) f[0]).getCopyElements();
            final Function function = ((FunctionValue) f[1]).getValue();
            Arrays.sort(elements, (o1, o2) -> function.execute(o1).compareTo(function.execute(o2)));
            return new ArrayValue(elements);
        });
        Functions.set("sort",f ->{
            if (f.length < 1) throw new ArgumentsMismatchException("At least one argument expected");
            if (f[0].type() != Types.ARRAY) {
                throw new TypeException("Array expected in first argument");
            }
            final Value[] elements = ((ArrayValue) f[0]).getCopyElements();

            switch (f.length) {
                case 1:
                    Arrays.sort(elements);
                    break;
                case 2:
                    if (f[1].type() != Types.FUNCTION) {
                        throw new TypeException("Function expected in second argument");
                    }
                    final Function comparator = ((FunctionValue) f[1]).getValue();
                    Arrays.sort(elements, (o1, o2) -> (int) comparator.execute(o1, o2).asNumber());
                    break;
                default:
                    throw new ArgumentsMismatchException("Wrong number of arguments");
            }

            return new ArrayValue(elements);
        });

    }
}
