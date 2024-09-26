package main.java.net.felsstudio.fels.Modules.fels.lang.memory;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.Start.Starter;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.parser.ast.ImportStatement;

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
                ScopeHandler.functions().clear();
                return NumberValue.ZERO;
            }
        });
        Functions.set("remFunc", new Function() {
            @Override
            public Value execute(Value[] args) {
                ScopeHandler.functions().remove(args[0].asString());
                return NumberValue.ZERO;
            }
        });
        Functions.set("putSFunc", new Function() {//putSFunc("function_name","return statement")
            @Override
            public Value execute(Value... args) {
                ScopeHandler.setFunction(args[0].asString(),fargs -> args[1]);
                return args[1];
            }
        });
        Functions.set("runFile", new Function() {
            @Override
            public Value execute(Value... args) throws IOException {
                Starter.start(false,false,false,false,true,args[0].asString());
                return NumberValue.ZERO;
            }
        });
    }
}
