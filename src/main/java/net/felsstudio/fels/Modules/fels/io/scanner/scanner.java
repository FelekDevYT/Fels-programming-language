package main.java.net.felsstudio.fels.Modules.fels.io.scanner;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.NumberValue;
import main.java.net.felsstudio.fels.lib.StringValue;
import main.java.net.felsstudio.fels.lib.Variables;

public class scanner implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(5);

        map.set("readLine",args ->{
            java.util.Scanner scan = new java.util.Scanner(System.in);
            return new StringValue(scan.nextLine());
        });

        map.set("readNumber",args ->{
            java.util.Scanner scan = new java.util.Scanner(System.in);
            return NumberValue.of(scan.nextDouble());
        });

        map.set("readBool",args ->{
            java.util.Scanner scan = new java.util.Scanner(System.in);
            return scan.nextLine().equals("true")?NumberValue.ONE:NumberValue.ZERO;
        });

        map.set("readChar",args ->{
            java.util.Scanner scan = new java.util.Scanner(System.in);
            return new StringValue(String.valueOf(scan.nextLine().charAt(0)));
        });

        Variables.define("scanner", map);
    }
}
