package main.java.net.felsstudio.fels.Modules.random;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

import java.util.Map;

import static java.util.Map.entry;

public class random implements Module {

    private static int random(int min, int max){
        max-= min;
        return (int) (Math.random()* ++max) + min;
    }

    @Override
    public Map<String, Value> constants() {
        return Map.of();
    }

    @Override
    public Map<String, Function> functions() {
        return Map.ofEntries(
                entry("random", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        return NumberValue.of(random(args[0].asInt(), args[1].asInt()));
                    }
                }),
                entry("rand", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        int max = args[0].asInt();
                        return NumberValue.of(random(0, max));
                    }
                }),
                entry("rnd", new Function() {
                    @Override
                    public Value execute(Value[] args) {
                        return NumberValue.of(random(0,Integer.MAX_VALUE));
                    }
                })
        );
    }
}