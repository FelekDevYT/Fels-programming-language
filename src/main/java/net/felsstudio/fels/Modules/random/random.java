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
    public void init() {
        final MapValue map = new MapValue(10);

        map.set("random", new Function() {
            @Override
            public Value execute(Value[] args) {
                return NumberValue.of(random(args[0].asInt(), args[1].asInt()));
            }
        });
        map.set("rand", new Function() {
            @Override
            public Value execute(Value[] args) {
                int max = args[0].asInt();
                return NumberValue.of(random(0, max));
            }
        });
                map.set("rnd", new Function() {
            @Override
            public Value execute(Value[] args) {
                return NumberValue.of(random(0,Integer.MAX_VALUE));
            }
        });
    }
}