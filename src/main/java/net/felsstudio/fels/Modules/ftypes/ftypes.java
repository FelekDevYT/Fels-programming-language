package main.java.net.felsstudio.fels.Modules.ftypes;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.*;

public final class ftypes implements Module {

    @Override
    public void init() {
        final MapValue map = new MapValue(30);

        map.set("OBJECT", NumberValue.of(Types.OBJECT));
        map.set("NUMBER", NumberValue.of(Types.NUMBER));
        map.set("STRING", NumberValue.of(Types.STRING));
        map.set("ARRAY", NumberValue.of(Types.ARRAY));
        map.set("MAP", NumberValue.of(Types.MAP));
        map.set("FUNCTION", NumberValue.of(Types.FUNCTION));
        map.set("CLASS",NumberValue.of(Types.CLASS));

        map.set("typeToString",args -> new StringValue(Types.typeToString(args[0].asInt())));

        map.set("toByte", args -> NumberValue.of((byte)args[0].asInt()));
        map.set("toShort", args -> NumberValue.of((short)args[0].asInt()));
        map.set("toInt", args -> NumberValue.of(args[0].asInt()));
        map.set("toLong", args -> NumberValue.of((long)args[0].asNumber()));
        map.set("toFloat", args -> NumberValue.of((float)args[0].asNumber()));
        map.set("toDouble", args -> NumberValue.of(args[0].asNumber()));

        Variables.define("ftypes", map);
    }
}