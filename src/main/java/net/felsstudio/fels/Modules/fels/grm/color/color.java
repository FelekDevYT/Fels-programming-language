package main.java.net.felsstudio.fels.Modules.fels.grm.color;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.StringValue;
import main.java.net.felsstudio.fels.lib.Variables;

public class color implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(3);

        map.set("reset",args -> new StringValue("\u001b[10m"));
        map.set("red",args -> new StringValue("\u001b[31m"));
        map.set("green",args -> new StringValue("\u001b[32m"));
        map.set("blue",args -> new StringValue("\u001b[34m"));
        map.set("white",args -> new StringValue("\u001b[37m"));
        map.set("black",args -> new StringValue("\u001b[40m"));
        map.set("purple",args -> new StringValue("\u001b[35m"));
        map.set("yellow",args -> new StringValue("\u001b[33m"));
        map.set("cyan",args -> new StringValue("\u001b[36m"));

        Variables.define("color", map);
    }
}