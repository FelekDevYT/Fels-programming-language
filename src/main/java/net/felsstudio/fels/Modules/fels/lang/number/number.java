package main.java.net.felsstudio.fels.Modules.fels.lang.number;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.NumberValue;

public class number implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(5);
        map.set("min",args ->{
            return NumberValue.of(Double.min(args[0].asNumber(),args[1].asNumber()));
        });
        map.set("max",args ->{
            return NumberValue.of(Double.max(args[0].asNumber(),args[1].asNumber()));
        });
        map.set("MAX_VALUE",args -> {
            return NumberValue.of(Double.MAX_VALUE);
        });
        map.set("MIN_VALUE",args -> {
            return NumberValue.of(Double.MIN_VALUE);
        });
    }
}
