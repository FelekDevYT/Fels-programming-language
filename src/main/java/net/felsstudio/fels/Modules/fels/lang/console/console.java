package net.felsstudio.fels.Modules.fels.lang.console;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.MapValue;
import net.felsstudio.fels.lib.NumberValue;
import net.felsstudio.fels.lib.StringValue;
import net.felsstudio.fels.lib.Variables;

import java.util.Scanner;

public class console implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(10);
        map.set("writeLine",args ->{
            System.out.println(args[0].asString());
            return NumberValue.ZERO;
        });
        map.set("write",args ->{
            System.out.print(args[0].asString());
            return NumberValue.ZERO;
        });
        map.set("writeError",args ->{
            System.err.println(args[0].asString());
            return NumberValue.ZERO;
        });
        map.set("newLine",args ->{
            return new StringValue("\n");
        });
        map.set("readLine",args ->{
            Scanner s = new Scanner(System.in);
            String word = s.nextLine();
            s.close();
            return new StringValue(word);
        });
        map.set("readNum",args ->{
            Scanner s = new Scanner(System.in);
            int word = s.nextInt();
            s.close();
            return NumberValue.of(word);
        });

        Variables.define("console", map);
    }
}
