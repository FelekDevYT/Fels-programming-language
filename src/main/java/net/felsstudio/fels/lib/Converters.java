package main.java.net.felsstudio.fels.lib;

import java.util.function.Predicate;

import static main.java.net.felsstudio.fels.lib.ValueUtils.getFloatNumber;

public final class Converters {

    public interface VoidToVoidFunction {
        void apply();
    }

    public interface VoidToBooleanFunction {
        boolean apply();
    }

    public interface VoidToIntFunction {
        int apply();
    }

    public interface VoidToFloatFunction {
        float apply();
    }

    public interface VoidToDoubleFunction {
        double apply();
    }

    public interface VoidToStringFunction {
        String apply();
    }

    public interface VoidToEnumFunction<E extends Enum<E>> {
        Enum<E> apply();
    }

    public interface BooleanToVoidFunction {
        void apply(boolean b);
    }

    public interface IntToVoidFunction {
        void apply(int i);
    }

    public static FunctionValue stringToBoolean(Predicate<String> f) {
        return new FunctionValue(args -> {
            Arguments.check(1, args.length);
            return NumberValue.fromBoolean(f.test(args[0].asString()));
        });
    }

    public interface Int4ToVoidFunction {
        void apply(int i1, int i2, int i3, int i4);
    }

    public interface FloatToVoidFunction {
        void apply(float f);
    }

    public interface Float4ToVoidFunction {
        void apply(float f1, float f2, float f3, float f4);
    }

    public interface DoubleToVoidFunction {
        void apply(double d);
    }

    public interface Double2ToVoidFunction {
        void apply(double d1, double d2);
    }

    public interface Double4ToVoidFunction {
        void apply(double d1, double d2, double d3, double d4);
    }

    public interface StringToVoidFunction {
        void apply(String s);
    }


    public static FunctionValue voidToVoid(VoidToVoidFunction f) {
        return new FunctionValue(args -> {
            f.apply();
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue voidToBoolean(VoidToBooleanFunction f) {
        return new FunctionValue(args -> NumberValue.fromBoolean(f.apply()));
    }

    public static FunctionValue voidToInt(VoidToIntFunction f) {
        return new FunctionValue(args -> NumberValue.of(f.apply()));
    }

    public static FunctionValue voidToFloat(VoidToFloatFunction f) {
        return new FunctionValue(args -> NumberValue.of(f.apply()));
    }

    public static FunctionValue voidToDouble(VoidToDoubleFunction f) {
        return new FunctionValue(args -> NumberValue.of(f.apply()));
    }

    public static FunctionValue voidToString(VoidToStringFunction f) {
        return new FunctionValue(args -> new StringValue(f.apply()));
    }

    public static <E extends Enum<E>> FunctionValue enumOrdinal(VoidToEnumFunction<E> f) {
        return new FunctionValue(args -> NumberValue.of(f.apply().ordinal()));
    }

    public static FunctionValue booleanToVoid(BooleanToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asInt() != 0);
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue booleanOptToVoid(BooleanToVoidFunction f) {
        return booleanOptToVoid(f, true);
    }
    public static FunctionValue booleanOptToVoid(BooleanToVoidFunction f, final boolean def) {
        return new FunctionValue(args -> {
            Arguments.checkOrOr(0, 1, args.length);
            f.apply( (args.length == 1) ? (args[0].asInt() != 0) : def );
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue intToVoid(IntToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asInt());
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue int4ToVoid(Int4ToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(4, args.length);
            f.apply(args[0].asInt(),
                    args[1].asInt(),
                    args[2].asInt(),
                    args[3].asInt());
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue floatToVoid(FloatToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(1, args.length);
            f.apply(getFloatNumber(args[0]));
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue float4ToVoid(Float4ToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(4, args.length);
            f.apply(getFloatNumber(args[0]),
                    getFloatNumber(args[1]),
                    getFloatNumber(args[2]),
                    getFloatNumber(args[3]));
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue doubleToVoid(DoubleToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asNumber());
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue double2ToVoid(Double2ToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(2, args.length);
            f.apply(args[0].asNumber(), args[1].asNumber());
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue double4ToVoid(Double4ToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(4, args.length);
            f.apply(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber());
            return NumberValue.ZERO;
        });
    }

    public static FunctionValue stringToVoid(StringToVoidFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asString());
            return NumberValue.ZERO;
        });
    }
}