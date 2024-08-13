package main.java.net.felsstudio.fels.Modules.time;

import net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;
import java.util.*;

public class time implements Module {
    @Override
    public void init() {
    Functions.set("sleep", f ->{
        try{
            Thread.sleep((long) f[0].asNumber());
            return NumberValue.of(1);
        }catch(InterruptedException ie){
            return NumberValue.of(0);
        }
    });
    Functions.set("getTime",f ->{
        return new StringValue(new Date().toString());
    });
    Functions.set("getMillis",f ->{
        return NumberValue.of(new Date().getTime());
    });
    }
}