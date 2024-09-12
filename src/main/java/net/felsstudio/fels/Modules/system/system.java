package main.java.net.felsstudio.fels.Modules.system;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

import java.util.Map;

public class system implements Module {

    @Override
    public void init() {
        final MapValue map = new MapValue(10);

        map.set("currentTimeMillis",args ->{
            return NumberValue.of(System.currentTimeMillis());
        });
        map.set("nanoTime",args ->{
            return NumberValue.of(System.nanoTime());
        });
        map.set("getUsedMemory",args ->{
            return NumberValue.of(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        });
        map.set("getTotalMemory",args ->{
            return NumberValue.of(Runtime.getRuntime().totalMemory());
        });
        map.set("getMaxMemory",args ->{
            return NumberValue.of(Runtime.getRuntime().maxMemory());
        });
        map.set("getFreeMemory",args ->{
            return NumberValue.of(Runtime.getRuntime().freeMemory());
        });
        map.set("availableProcessors",args ->{
            return NumberValue.of(Runtime.getRuntime().availableProcessors());
        });
        map.set("getProperty", new Function() {
            @Override
            public Value execute(Value[] f) {
                switch (f[0].asString()){
                    case "fels.version":return new StringValue(Information.FELS_VERSION);
                    case "fels.creator":return new StringValue(Information.FELS_AUTHOR);
                    case "fels.loader.version":return new StringValue(Information.LOADER_VERSION);
                    case "date":return new StringValue(Information.DATE);
                }
                return NumberValue.of(-1);
            }
        });

        Variables.define("system", map);
    }
    
}