package net.felsstudio.fels.Modules.fels.io.files;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class files implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(15);
        map.set("readAllLines",args ->{
            ArrayValue arr = null;
            List<String> array =  Files.readAllLines(Path.of(args[0].asString()));
            arr = new ArrayValue(ArrayValue.of(array.toArray(new String[array.size()])));
            return arr;
        });
        map.set("createFile",args ->{
            new File(args[0].asString()).createNewFile();
            return NumberValue.ZERO;
        });
        map.set("createDirectory",args ->{
            new File(args[0].asString()).mkdir();
            return NumberValue.ZERO;
        });
        map.set("readAllText",args ->{
            String array = Files.readString(Path.of(args[0].asString()));
            return new StringValue(array);
        });
        map.set("readAllBytes",args ->{
            ArrayValue arr = null;
            byte[] array =  Files.readAllBytes(Path.of(args[0].asString()));
            arr = new ArrayValue(ArrayValue.of(array));
            return arr;
        });
        map.set("writeAllText",args ->{
            try (PrintWriter out = new PrintWriter(args[0].asString())) {
                out.println(args[1].asString());
            }
            return NumberValue.ZERO;
        });
        map.set("writeAllLines",args ->{
            ArrayValue arr = (ArrayValue) args[1];
            try(PrintWriter out = new PrintWriter(args[0].asString())) {
                for(int i = 0;i < arr.size();i++) {
                    out.println(arr.get(i).asString());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return NumberValue.ZERO;
        });

        Variables.define("files",map);
    }
}
