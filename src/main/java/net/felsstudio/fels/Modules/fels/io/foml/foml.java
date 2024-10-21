package main.java.net.felsstudio.fels.Modules.fels.io.foml;

import com.electronwill.nightconfig.core.file.FileConfig;
import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class foml implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(5);
        map.set("getValue",args ->{
            FileConfig conf = FileConfig.of(Paths.get(args[0].asString()));
            conf.load();
            return new StringValue(conf.get(args[1].asString()).toString());
        });
        map.set("setValue",args ->{
            FileConfig conf = FileConfig.of(Paths.get(args[0].asString()));
            conf.load();

            switch (args[1].type()) {
                case Types.NUMBER -> {
                    conf.set(args[1].asString(), conf.get(args[2].asString()));
                }
                case Types.STRING -> {
                    conf.set(args[1].asString(), args[1].asString());
                }
            }

            conf.save();
            return NumberValue.ZERO;
        });
        Variables.set("foml",map);
    }
}
