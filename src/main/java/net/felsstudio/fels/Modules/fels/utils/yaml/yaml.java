package main.java.net.felsstudio.fels.Modules.fels.utils.yaml;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.Variables;

public class yaml implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(2);
        map.set("encode",args -> {
            return new yaml_encode().execute(args);
        });
        map.set("decode",args -> {
            return new yaml_decode().execute(args);
        });
        Variables.define("yaml", map);
    }
}
