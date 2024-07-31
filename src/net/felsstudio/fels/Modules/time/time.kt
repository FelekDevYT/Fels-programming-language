package net.felsstudio.fels.Modules.time;

import net.felsstudio.fels.lib.Functions;
import net.felsstudio.fels.lib.NumberValue;
import net.felsstudio.fels.lib.StringValue;
import net.felsstudio.fels.Modules.Module;

import java.util.Date;

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
