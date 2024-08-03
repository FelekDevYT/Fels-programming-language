package main.java.net.felsstudio.fels.Modules.system;

import net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

public class system implements Module {
    @Override
    public void init() {
        Functions.set("currentTimeMillis", f ->{
            return NumberValue.of(System.currentTimeMillis());
        });

        Functions.set("nanoTime", f ->{
            return NumberValue.of(System.nanoTime());
        });

        Functions.set("getUsedMemory",f -> {
            return NumberValue.of(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        });

        Functions.set("getTotalMemory",f ->{
            return NumberValue.of(Runtime.getRuntime().totalMemory());
        });

        Functions.set("getMaxMemory",f ->{
            return NumberValue.of(Runtime.getRuntime().maxMemory());
        });

        Functions.set("getFreeMemory",f ->{
            return NumberValue.of(Runtime.getRuntime().freeMemory());
        });

        Functions.set("availableProcessors",f ->{
            return NumberValue.of(Runtime.getRuntime().availableProcessors());
        });

        Functions.set("getProperty",f ->{
            switch (f[0].asString()){
                case "fels.version":return new StringValue(Information.FELS_VERSION);
                case "fels.creator":return new StringValue(Information.FELS_AUTHOR);
                case "date":return new StringValue(Information.DATE);
            }
            return NumberValue.of(-1);
        });

    }
}