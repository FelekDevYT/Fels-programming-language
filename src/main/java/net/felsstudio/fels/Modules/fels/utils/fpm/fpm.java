package main.java.net.felsstudio.fels.Modules.fels.utils.fpm;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fpm.LibManager;

import java.util.*;

public class fpm implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(12);

        map.set("getName",args ->{
            return new StringValue(LibManager.getLibName(args[0].asString()));
        });
        map.set("getDescription",args ->{
            return new StringValue(LibManager.getLibDescription(args[0].asString()));
        });
        map.set("getAuthor",args ->{
            return new StringValue(LibManager.getLibAuthor(args[0].asString()));
        });
        map.set("getVersion",args ->{
            return new StringValue(LibManager.getLibVersion(args[0].asString()));
        });
        map.set("getName",args ->{
            List<String> l = Arrays.asList(LibManager.getLibDeps(args[0].asString()));
            ArrayValue arr = new ArrayValue(1024);
            for (int i = 0; i < l.size(); i++) {
                arr.set(i,new StringValue(l.get(i)));
            }
            return arr;
        });
        map.set("loadLib",arggs ->{
            LibManager.loadLib(arggs[0].asString());
            return NumberValue.ZERO;
        });

        Variables.define("fpm", map);
    }
}
