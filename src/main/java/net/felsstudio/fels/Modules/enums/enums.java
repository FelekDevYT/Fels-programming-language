package main.java.net.felsstudio.fels.Modules.enums;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.ArrayValue;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.Variables;

public class enums implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(1);

        map.set("create",args ->{
            final MapValue _enum = new MapValue(args[0].asInt());

            final ArrayValue _args1 = (ArrayValue) args[1];
            final ArrayValue _args2 = (ArrayValue) args[2];

            for(int i = 0;i < _args1.size();i++) {
                _enum.set(_args1.get(i),_args2.get(i));
            }

            return _enum;
        });

        Variables.define("enums",map);
    }
}
