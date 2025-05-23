package net.felsstudio.fels.Modules.fels.lang.system;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.*;
import net.felsstudio.fels.utils.CmdExecuter;

import java.util.Date;
import java.util.Map;

public class system implements Module {

    @Override
    public void init() {
        final MapValue map = new MapValue(12);

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
        map.set("execute",args ->{
            CmdExecuter.execute(args[0].asString());
            return NumberValue.ZERO;
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
        map.set("exit",args ->{
            System.exit(args[0].asInt());
            return NumberValue.of(args[0].asInt());
        });

        Variables.define("system", map);
    }
    
}