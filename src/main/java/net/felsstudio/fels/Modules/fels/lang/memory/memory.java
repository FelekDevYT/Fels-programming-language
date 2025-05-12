package net.felsstudio.fels.Modules.fels.lang.memory;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.Start.Starter;
import net.felsstudio.fels.lib.*;
import net.felsstudio.fels.parser.ast.ImportStatement;

import java.io.IOException;
import java.util.Map;

public class memory implements Module {

    @Override
    public void init() {
        Functions.set("remVar", new Function() {
            @Override
            public Value execute(Value[] args) {
                Variables.remove(args[0].asString());
                return NumberValue.ZERO;
            }
        });
        Functions.set("defineVar", new Function() {
            @Override
            public Value execute(Value[] args) {
                Variables.define(args[0].asString(),args[1]);
                return NumberValue.ZERO;
            }
        });
        Functions.set("isVarExists", new Function() {
            @Override
            public Value execute(Value[] args) {
                if(Variables.isExists(args[0].asString())){
                    return NumberValue.ONE;
                }else{
                    return NumberValue.ZERO;
                }
            }
        });
        Functions.set("getVarValue", new Function() {
            @Override
            public Value execute(Value[] args) {
                return new StringValue(Variables.get(args[0].asString()).asString());
            }
        });
        Functions.set("remFuncs", new Function() {
            @Override
            public Value execute(Value[] args) {
                Functions.clear();
                return NumberValue.ZERO;
            }
        });
        Functions.set("remFunc", new Function() {
            @Override
            public Value execute(Value[] args) {
                Functions.functions.remove(args[0].asString());
                return NumberValue.ZERO;
            }
        });
        Functions.set("putSFunc", new Function() {//putSFunc("function_name","return statement")
            @Override
            public Value execute(Value... args) {
                Functions.set(args[0].asString(),fargs -> args[1]);
                return args[1];
            }
        });
        Functions.set("runFile", new Function() {
            @Override
            public Value execute(Value... args) throws IOException {
                boolean b1 = Boolean.parseBoolean(args[0].asString());
                boolean b2 = Boolean.parseBoolean(args[1].asString());
                boolean b3 = Boolean.parseBoolean(args[2].asString());
                boolean b4 = Boolean.parseBoolean(args[3].asString());
                boolean b5 = Boolean.parseBoolean(args[4].asString());
                boolean b6 = Boolean.parseBoolean(args[5].asString());
                Starter.start(b1,b2,b3,b4,b5,b6,args[0].asString());
                return NumberValue.ZERO;
            }
        });
    }
}
