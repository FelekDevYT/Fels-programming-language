package main.java.net.felsstudio.fels.Modules.fels.lang.console;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.NumberValue;
import main.java.net.felsstudio.fels.lib.StringValue;

public class console implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(4);
        map.set("writeLine",args ->{
            System.out.println(args[0].asString());
            return NumberValue.ZERO;
        });
        map.set("write",args ->{
            System.out.print(args[0].asString());
            return NumberValue.ZERO;
        });
        map.set("writeError",args ->{
            System.err.println(args[0].asString());
            return NumberValue.ZERO;
        });
        map.set("newLine",args ->{
            return new StringValue("\n");
        });
    }
}
