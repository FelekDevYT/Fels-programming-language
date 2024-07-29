package net.felsstudio.fels.Modules.random;

import net.felsstudio.fels.lib.Functions;
import net.felsstudio.fels.lib.NumberValue;
import net.felsstudio.fels.Modules.Module;

public class random implements Module {

    private static int random(int min, int max){
        max-= min;
        return (int) (Math.random()* ++max) + min;
    }

    @Override
    public void init() {
        Functions.set("rand", f ->{
            int max = 100;
            max = (int) f[0].asNumber();
            return NumberValue.of(random(0, max));
        });
        Functions.set("random",f ->{
           int max = 0,min = 0;
           max = (int) f[0].asNumber();
           min = (int) f[1].asNumber();
           return NumberValue.of(random(min, max));
        });
    }
}
