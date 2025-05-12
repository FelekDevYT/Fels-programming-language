package net.felsstudio.fels.Modules.fels.lang.number;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.MapValue;
import net.felsstudio.fels.lib.NumberValue;
import net.felsstudio.fels.lib.Variables;

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
        Variables.set("number",map);
    }
}
