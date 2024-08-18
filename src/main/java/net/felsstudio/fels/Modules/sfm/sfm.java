package main.java.net.felsstudio.fels.Modules.sfm;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.Start.Starter;
import main.java.net.felsstudio.fels.exceptions.ArgumentsMismatchException;
import main.java.net.felsstudio.fels.exceptions.TypeException;
import main.java.net.felsstudio.fels.lib.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Map.entry;

public final class sfm implements Module {//Standard fels module

    @Override
    public Map<String, Function> functions() {
        return Map.ofEntries(
                entry("echo",new Function(){

                    @Override
                    public Value execute(Value[] args) {
                        for (Value arg : args) {
                            System.out.println(arg.asString());
                        }
                        System.out.println();
                        return NumberValue.ZERO;
                    }
                }),
                entry("const", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Variables.set(args[0].asString(),args[1]);
                        return NumberValue.ZERO;
                    }
                }),
                entry("array",new Function() {

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
                }),
                entry("default",new Function() {
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
                }),
                entry("thread", new Function() {
                    @Override
                    public Value execute(Value[] args) {
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

                        final Thread thread = new Thread(() -> {
                            try {
                                body.execute(params);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        thread.setUncaughtExceptionHandler(Starter::handleException);
                        thread.start();
                        return NumberValue.ZERO;
                    }
                }),
                entry("readln", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Scanner scan = new Scanner(System.in);
                        return new StringValue(scan.nextLine());
                    }
                }),
                entry("readNum", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Scanner scan = new Scanner(System.in);
                        return NumberValue.of(scan.nextInt());
                    }
                }),
                entry("splice", new Function() {
                    @Override
                    public Value execute(Value[] args) {
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
                    }
                }),
                entry("indexOf", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        String value = f[0].asString();
                        return NumberValue.of(value.indexOf(f[1].asString()));
                    }
                }),
                entry("lastIndexOf", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        final String input = args[0].asString();
                        final String what = args[1].asString();
                        if (args.length == 2) {
                            return NumberValue.of(input.lastIndexOf(what));
                        }
                        final int index = args[2].asInt();
                        return NumberValue.of(input.lastIndexOf(what, index));
                    }
                }),
                entry("replace", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        String value = f[0].asString();
                        return new StringValue(value.replace(f[1].asString(), f[2].asString()));
                    }
                }),
                entry("trim", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        return new StringValue(f[0].asString().trim());
                    }
                }),
                entry("getBytes", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        String value = f[0].asString();
                        byte[] array = value.getBytes();
                        ArrayValue arrayValue = new ArrayValue(array.length);
                        for (int i = 0; i < array.length; i++) {
                            arrayValue.set(i,NumberValue.of(array[i]));
                        }
                        return arrayValue;
                    }
                }),
                entry("split", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Arguments.checkOrOr(2, 3, args.length);

                        final String input = args[0].asString();
                        final String regex = args[1].asString();
                        final int limit = (args.length == 3) ? args[2].asInt() : 0;

                        final String[] parts = input.split(regex, limit);
                        return ArrayValue.of(parts);
                    }
                }),
                entry("toHexString", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Arguments.check(1, args.length);
                        long value;
                        if (args[0].type() == Types.NUMBER) {
                            value = ((NumberValue) args[0]).asLong();
                        } else {
                            value = (long) args[0].asNumber();
                        }
                        return new StringValue(Long.toHexString(value));
                    }
                }),
                entry("equals", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        if(Objects.equals(f[0],f[1])){
                            return NumberValue.ONE;
                        }else{
                            return NumberValue.ZERO;
                        }
                    }
                }),
                entry("concat", new Function() {
                    @Override
                    public Value execute(Value[] f){
                        StringBuilder sb = new StringBuilder();
                        for(Value v : f){
                            sb.append(v.asString());
                        }
                        return new StringValue(sb.toString());
                    }
                }),
                entry("equalsIgnoreCase", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        if (f[0].asString().equalsIgnoreCase(f[1].asString())) {
                            return NumberValue.ONE;
                        } else return NumberValue.ZERO;
                    }
                }),
                entry("compareTo", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        return NumberValue.of(f[0].asString().compareTo(f[1].asString()));
                    }
                }),
                entry("compareToIgnoreCase", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        return NumberValue.of(f[0].asString().compareToIgnoreCase(f[1].asString()));
                    }
                }),
                entry("isEmpty", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        if(f[0].asString().isEmpty()){
                            return NumberValue.ONE;
                        }else{
                            return NumberValue.ZERO;
                        }
                    }
                }),
                entry("toUpperCase", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        return new StringValue(f[0].asString().toUpperCase());
                    }
                }),
                entry("toLowerCase", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        return new StringValue(f[0].asString().toLowerCase());
                    }
                }),
                entry("substring", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        return new StringValue(f[0].asString().substring((int)f[1].asInt(),(int)f[2].asInt()));
                    }
                }),
                entry("length", new Function() {
                    @Override
                    public Value execute(Value[] args) {
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
                    }
                }),
                entry("quit", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        System.exit((int)f[0].asInt());
                        return NumberValue.ZERO;
                    }
                }),
                entry("replaceAll", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        if (f.length != 3) throw new ArgumentsMismatchException("Three arguments expected");

                        final String input = f[0].asString();
                        final String regex = f[1].asString();
                        final String replacement = f[2].asString();

                        return new StringValue(input.replaceAll(regex, replacement));
                    }
                }),
                entry("replaceFirst", new Function() {
                    @Override
                    public Value execute(Value[] f) {
                        if (f.length != 3) throw new ArgumentsMismatchException("Three arguments expected");

                        final String input = f[0].asString();
                        final String regex = f[1].asString();
                        final String replacement = f[2].asString();

                        return new StringValue(input.replaceFirst(regex, replacement));
                    }
                }),
                entry("sprintf", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        if (args.length < 1) throw new ArgumentsMismatchException("At least one argument expected");

                        final String format = args[0].asString();
                        final Object[] values = new Object[args.length - 1];
                        for (int i = 1; i < args.length; i++) {
                            values[i - 1] = (args[i].type() == Types.NUMBER) ? args[i].asInt() : args[i].asString();
                        }
                        return new StringValue(String.format(format, values));
                    }
                }),
                entry("arrayCombine", new Function() {
                    @Override
                    public Value execute(Value[] args) {
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
                    }
                }),
                entry("arrayKeyExists", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        if (args.length != 2) throw new ArgumentsMismatchException("Two arguments expected");
                        if (args[1].type() != Types.MAP) {
                            throw new TypeException("Map expected in second argument");
                        }
                        final MapValue map = ((MapValue) args[1]);
                        return NumberValue.fromBoolean(map.containsKey(args[0]));
                    }
                }),
                entry("arrayKeys", new Function() {
                    @Override
                    public Value execute(Value[] args) {
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
                    }
                }),
                entry("arrayValues", new Function() {
                    @Override
                    public Value execute(Value[] args) {
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
                    }
                }),
                entry("toChar", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Arguments.check(1, args.length);
                        return new StringValue(String.valueOf((char) args[0].asInt()));
                    }
                }),
                entry("charAt", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Arguments.check(2, args.length);
                        final String input = args[0].asString();
                        final int index = args[1].asInt();

                        return NumberValue.of((short)input.charAt(index));
                    }
                }),
                entry("num", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        return NumberValue.of(args[0].asNumber());
                    }
                }),
                entry("sleep", new Function() {
                    @Override
                    public Value execute(Value... f) {
                        try{
                            Thread.sleep((long) f[0].asNumber());
                            return NumberValue.of(1);
                        }catch(InterruptedException ie){
                            return NumberValue.of(0);
                        }
                    }
                }),
                entry("try", new Function() {
                    @Override
                    public Value execute(Value[] args) throws IOException {
                        Arguments.checkOrOr(1, 2, args.length);
                        try {
                            return ValueUtils.consumeFunction(args[0], 0).execute();
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
                    }
                })
        );
    }

    @Override
    public Map<String, Value> constants() {
        return Map.ofEntries(
          entry("__VERSION__",new StringValue(Information.FELS_VERSION)),
          entry("__AUTHOR__",new StringValue(Information.FELS_AUTHOR)),
          entry("__LOADERVER__",new StringValue(Information.LOADER_VERSION)),
          entry("__DATE__",new StringValue(Information.DATE))
        );
    }
}