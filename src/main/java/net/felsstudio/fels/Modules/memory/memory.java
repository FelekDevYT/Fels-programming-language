package main.java.net.felsstudio.fels.Modules.memory;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.Start.Starter;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.parser.ast.ImportStatement;

import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

public class memory implements Module {
    @Override
    public Map<String, Value> constants() {
        return Map.of();
    }

    @Override
    public Map<String, Function> functions() {
        return Map.ofEntries(
                entry("remVar", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Variables.remove(args[0].asString());
                        return NumberValue.ZERO;
                    }
                }),
                entry("defineVar", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        Variables.set(args[0].asString(),args[1]);
                        return NumberValue.ZERO;
                    }
                }),
                entry("isVarExists", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        if(Variables.isExists(args[0].asString())){
                            return NumberValue.ONE;
                        }else{
                            return NumberValue.ZERO;
                        }
                    }
                }),
                entry("getVarValue", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        return new StringValue(Variables.get(args[0].asString()).asString());
                    }
                }),
                entry("remFuncs", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        ScopeHandler.functions().clear();
                        return NumberValue.ZERO;
                    }
                }),
                entry("remFunc", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        ScopeHandler.functions().remove(args[0].asString());
                        return NumberValue.ZERO;
                    }
                }),
                entry("setSFunc", new Function() {//setSFunc("function_name","return statement")
                    @Override
                    public Value execute(Value... args) {
                        ScopeHandler.setFunction(args[0].asString(),fargs -> args[1]);
                        return args[1];
                    }
                }),
                entry("runFile", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        Starter.start(false,false,false,false,args[0].asString());
                        return NumberValue.ZERO;
                    }
                })
        );
    }
}
