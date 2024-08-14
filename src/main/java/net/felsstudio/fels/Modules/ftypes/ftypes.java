package main.java.net.felsstudio.fels.Modules.ftypes;

import net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.lib.Converters.*;

public final class ftypes implements Module {

    @Override
    public void init() {
        Variables.set("OBJECT", NumberValue.of(Types.OBJECT));
        Variables.set("NUMBER", NumberValue.of(Types.NUMBER));
        Variables.set("STRING", NumberValue.of(Types.STRING));
        Variables.set("ARRAY", NumberValue.of(Types.ARRAY));
        Variables.set("MAP", NumberValue.of(Types.MAP));
        Variables.set("FUNCTION", NumberValue.of(Types.FUNCTION));

        Functions.set("typeOf", args -> NumberValue.of(args[0].type()));
        Functions.set("toString", args -> new StringValue(args[0].asString()));
        Functions.set("toNumber", args -> NumberValue.of(args[0].asNumber()));
        Functions.set("getNameFromNumber",args -> new StringValue(Types.typeToString(args[0].asInt())));

        Functions.set("toByte", args -> NumberValue.of((byte)args[0].asInt()));
        Functions.set("toShort", args -> NumberValue.of((short)args[0].asInt()));
        Functions.set("toInt", args -> NumberValue.of(args[0].asInt()));
        Functions.set("toLong", args -> NumberValue.of((long)args[0].asNumber()));
        Functions.set("toFloat", args -> NumberValue.of((float)args[0].asNumber()));
        Functions.set("toDouble", args -> NumberValue.of(args[0].asNumber()));
    }
}