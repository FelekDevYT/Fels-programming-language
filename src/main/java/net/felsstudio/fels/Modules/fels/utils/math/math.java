package main.java.net.felsstudio.fels.Modules.fels.utils.math;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;
import java.lang.Math;

public final class math implements Module {

    private static final DoubleFunction<NumberValue> doubleToNumber = NumberValue::of;

    @Override
    public void init() {
        final MapValue map = new MapValue(45);
        
        map.set("PI",NumberValue.of(Math.PI));
        map.set("E",NumberValue.of(Math.E));

        map.set("abs", math::abs);
        map.set("acos", functionConvert(Math::acos));
        map.set("asin", functionConvert(Math::asin));
        map.set("atan", functionConvert(Math::atan));
        map.set("atan2", biFunctionConvert(Math::atan2));
        map.set("cbrt", functionConvert(Math::cbrt));
        map.set("ceil", functionConvert(Math::ceil));
        map.set("copySign", math::copySign);
        map.set("cos", functionConvert(Math::cos));
        map.set("cosh", functionConvert(Math::cosh));
        map.set("exp", functionConvert(Math::exp));
        map.set("expm1", functionConvert(Math::expm1));
        map.set("floor", functionConvert(Math::floor));
        map.set("getExponent", math::getExponent);
        map.set("hypot", biFunctionConvert(Math::hypot));
        map.set("IEEEremainder", biFunctionConvert(Math::IEEEremainder));
        map.set("log", functionConvert(Math::log));
        map.set("log1p", functionConvert(Math::log1p));
        map.set("log10", functionConvert(Math::log10));
        map.set("max", math::max);
        map.set("min", math::min);
        map.set("nextAfter", math::nextAfter);
        map.set("nextUp", functionConvert(Math::nextUp, Math::nextUp));
        map.set("nextDown", functionConvert(Math::nextDown, Math::nextDown));
        map.set("pow", biFunctionConvert(Math::pow));
        map.set("rint", functionConvert(Math::rint));
        map.set("round", math::round);
        map.set("signum", functionConvert(Math::signum, Math::signum));
        map.set("sin", functionConvert(Math::sin));
        map.set("sinh", functionConvert(Math::sinh));
        map.set("sqrt", functionConvert(Math::sqrt));
        map.set("tan", functionConvert(Math::tan));
        map.set("tanh", functionConvert(Math::tanh));
        map.set("toDegrees", functionConvert(Math::toDegrees));
        map.set("toRadians", functionConvert(Math::toRadians));
        map.set("ulp", functionConvert(Math::ulp, Math::ulp));

        Variables.define("math",map);
    }

    private static Value abs(Value[] args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return NumberValue.of(Math.abs((double) raw));
        }
        if (raw instanceof Float) {
            return NumberValue.of(Math.abs((float) raw));
        }
        if (raw instanceof Long) {
            return NumberValue.of(Math.abs((long) raw));
        }
        return NumberValue.of(Math.abs(args[0].asInt()));
    }

    private static Value copySign(Value[] args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return NumberValue.of(Math.copySign((float) raw, ((NumberValue) args[1]).asFloat()));
        }
        return NumberValue.of(Math.copySign(args[0].asNumber(), args[1].asNumber()));
    }

    private static Value getExponent(Value[] args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return NumberValue.of(Math.getExponent((float) raw));
        }
        return NumberValue.of(Math.getExponent(args[0].asNumber()));
    }

    private static Value max(Value[] args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return NumberValue.of(Math.max((double) raw, args[1].asNumber()));
        }
        if (raw instanceof Float) {
            return NumberValue.of(Math.max((float) raw, ((NumberValue) args[1]).asFloat()));
        }
        if (raw instanceof Long) {
            return NumberValue.of(Math.max((long) raw, ((NumberValue) args[1]).asLong()));
        }
        return NumberValue.of(Math.max(args[0].asInt(), args[1].asInt()));
    }

    private static Value min(Value[] args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Double) {
            return NumberValue.of(Math.min((double) raw, args[1].asNumber()));
        }
        if (raw instanceof Float) {
            return NumberValue.of(Math.min((float) raw, ((NumberValue) args[1]).asFloat()));
        }
        if (raw instanceof Long) {
            return NumberValue.of(Math.min((long) raw, ((NumberValue) args[1]).asLong()));
        }
        return NumberValue.of(Math.min(args[0].asInt(), args[1].asInt()));
    }

    private static Value nextAfter(Value[] args) {
        Arguments.check(2, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return NumberValue.of(Math.nextAfter((float) raw, args[1].asNumber()));
        }
        return NumberValue.of(Math.nextAfter(args[0].asNumber(), args[1].asNumber()));
    }

    private static Value round(Value[] args) {
        Arguments.check(1, args.length);
        final Object raw = args[0].raw();
        if (raw instanceof Float) {
            return NumberValue.of(Math.round((float) raw));
        }
        return NumberValue.of(Math.round(args[0].asNumber()));
    }


    private static Function functionConvert(DoubleUnaryOperator op) {
        return args -> {
            Arguments.check(1, args.length);
            return doubleToNumber.apply(op.applyAsDouble(args[0].asNumber()));
        };
    }

    private static Function functionConvert(DoubleUnaryOperator opDouble, UnaryOperator<Float> opFloat) {
        return args -> {
            Arguments.check(1, args.length);
            final Object raw = args[0].raw();
            if (raw instanceof Float) {
                return NumberValue.of(opFloat.apply((float) raw));
            }
            return NumberValue.of(opDouble.applyAsDouble(args[0].asNumber()));
        };
    }

    private static Function biFunctionConvert(DoubleBinaryOperator op) {
        return args -> {
            Arguments.check(2, args.length);
            return doubleToNumber.apply(op.applyAsDouble(args[0].asNumber(), args[1].asNumber()));
        };
    }
}