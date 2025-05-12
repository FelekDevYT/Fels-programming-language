package net.felsstudio.fels.Modules.fels.lang.sfm;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.Start.Starter;
import net.felsstudio.fels.exceptions.ArgumentsMismatchException;
import net.felsstudio.fels.lib.*;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class sfm implements Module {
    @Override
    public void init() {
        Functions.set("echo",args ->{
            for (Value arg : args) {
                System.out.println(arg.asString());
            }
            System.out.println();
            return NumberValue.ZERO;
        });
        Functions.set("equals",args ->{
            if(args[0].equals(args[1])){
                return NumberValue.ONE;
            }
            return NumberValue.ZERO;
        });
        Functions.set("quit",args ->{
            System.exit(args[0].asInt());
            return NumberValue.ZERO;
        });
        Functions.set("length",args ->{
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
        Functions.set("sleep",args ->{
            Thread.sleep(1000);
            return NumberValue.ZERO;
        });
        Functions.set("thread",args ->{
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
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.setUncaughtExceptionHandler(Starter::handleException);
            thread.start();
            return NumberValue.ZERO;
        });
        Functions.set("sync",args ->{
            Arguments.check(1, args.length);

            final BlockingQueue<Value> queue = new LinkedBlockingQueue<>(2);
            final Function synchronizer = (sArgs) -> {
                try {
                    queue.put(sArgs[0]);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                return NumberValue.ZERO;
            };
            final Function callback = ValueUtils.consumeFunction(args[0], 0);
            callback.execute(new FunctionValue(synchronizer));

            try {
                return queue.take();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
        });
        Functions.set("try",args ->{
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
        });

        Functions.set("itoa", args -> new StringValue(args[0].asString()));
        Functions.set("atoi", args -> NumberValue.of(args[0].asNumber()));
        Functions.set("itoc", args -> new StringValue(String.valueOf((char)args[0].asInt())));
        Functions.set("typeOf", args -> NumberValue.of(args[0].type()));

        Functions.set("default",new Function() {
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
    }
}
