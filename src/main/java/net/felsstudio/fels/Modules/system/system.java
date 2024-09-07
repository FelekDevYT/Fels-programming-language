package main.java.net.felsstudio.fels.Modules.system;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

import java.util.Map;

import static java.util.Map.entry;

/*public class system implements Module {

    @Override
    public Map<String, Value> constants() {
        return Map.of();
    }

    @Override
    public Map<String, Function> functions() {
        return Map.ofEntries(
                entry("currentTimeMillis",args ->{
                    return NumberValue.of(System.currentTimeMillis());
                }),
                entry("nanoTime",args ->{
                    return NumberValue.of(System.nanoTime());
                }),
                entry("getUsedMemory",args ->{
                    return NumberValue.of(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
                }),
                entry("getTotalMemory",args ->{
                    return NumberValue.of(Runtime.getRuntime().totalMemory());
                }),
                entry("getMaxMemory",args ->{
                    return NumberValue.of(Runtime.getRuntime().maxMemory());
                }),
                entry("getFreeMemory",args ->{
                    return NumberValue.of(Runtime.getRuntime().freeMemory());
                }),
                entry("availableProcessors",args ->{
                    return NumberValue.of(Runtime.getRuntime().availableProcessors());
                }),
                entry("getProperty", new Function() {
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
                })
        );
    }

}*/