package main.java.net.felsstudio.fels.Modules;

import main.java.net.felsstudio.fels.lib.Function;
import main.java.net.felsstudio.fels.lib.Value;

import java.util.Map;

public interface Module {

    Map<String, Value> constants();

    Map<String, Function> functions();
}