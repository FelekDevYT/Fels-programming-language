package main.java.net.felsstudio.fels.Modules.ftypes;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.lib.Converters.*;

import java.util.Map;

import static java.util.Map.entry;

public final class ftypes implements Module {

    @Override
    public Map<String, Value> constants() {
        return Map.ofEntries(
                entry("OBJECT", NumberValue.of(Types.OBJECT)),
                entry("NUMBER", NumberValue.of(Types.NUMBER)),
                entry("STRING", NumberValue.of(Types.STRING)),
                entry("ARRAY", NumberValue.of(Types.ARRAY)),
                entry("MAP", NumberValue.of(Types.MAP)),
                entry("FUNCTION", NumberValue.of(Types.FUNCTION)),
                entry("CLASS",NumberValue.of(Types.CLASS))
        );
    }

    @Override
    public Map<String, Function> functions() {
        return Map.ofEntries(
                entry("typeOf", args -> NumberValue.of(args[0].type())),
                entry("toString", args -> new StringValue(args[0].asString())),
                entry("toNumber", args -> NumberValue.of(args[0].asNumber())),
                entry("getNameFromNumber",args -> new StringValue(Types.typeToString(args[0].asInt()))),

                entry("toByte", args -> NumberValue.of((byte)args[0].asInt())),
                entry("toShort", args -> NumberValue.of((short)args[0].asInt())),
                entry("toInt", args -> NumberValue.of(args[0].asInt())),
                entry("toLong", args -> NumberValue.of((long)args[0].asNumber())),
                entry("toFloat", args -> NumberValue.of((float)args[0].asNumber())),
                entry("toDouble", args -> NumberValue.of(args[0].asNumber()))
        );
    }
}