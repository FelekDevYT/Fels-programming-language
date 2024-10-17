package main.java.net.felsstudio.fels.Modules.fels.io.stream;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.ArrayValue;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.NumberValue;
import main.java.net.felsstudio.fels.lib.Variables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class stream implements Module {
    private String file = null;
    @Override
    public void init() {
        final MapValue map = new MapValue(6);
        map.set("file",args ->{
            file = args[0].asString();
            return NumberValue.ZERO;
        });
        map.set("readLines",args ->{
            List lines = new ArrayList();
            if(file != null){
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    lines.add(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new ArrayValue(lines);
        });
        Variables.define("io",map);
    }
}
