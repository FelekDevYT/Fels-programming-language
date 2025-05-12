package net.felsstudio.fels.Modules.fels.lang.character;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.MapValue;
import net.felsstudio.fels.lib.NumberValue;
import net.felsstudio.fels.lib.Variables;

public class character implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(5);
        map.set("isLetter",args ->{
            if(Character.isLetter(args[0].asString().charAt(0))){
                return NumberValue.ONE;
            }
            return NumberValue.ZERO;
        });
        map.set("isDigit",args ->{
            if(Character.isDigit(args[0].asString().charAt(0))){
                return NumberValue.ONE;
            }
            return NumberValue.ZERO;
        });
        map.set("isLetterOrDigit",args ->{
            if(Character.isLetterOrDigit(args[0].asString().charAt(0))){
                return NumberValue.ONE;
            }
            return NumberValue.ZERO;
        });
        map.set("isAlphabetic",args ->{
            if(Character.isAlphabetic(args[0].asString().charAt(0))){
                return NumberValue.ONE;
            }
            return NumberValue.ZERO;
        });
        Variables.set("character",map);
    }
}
