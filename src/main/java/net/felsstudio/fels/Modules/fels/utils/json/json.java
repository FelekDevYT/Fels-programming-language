package net.felsstudio.fels.Modules.fels.utils.json;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.MapValue;
import net.felsstudio.fels.lib.Variables;

public class json implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(2);
        map.set("encode",args ->{
            return new json_encode().execute(args);
        });
        map.set("decode",args ->{
            return new json_decode().execute(args);
        });
        Variables.define("json",map);
    }
}
