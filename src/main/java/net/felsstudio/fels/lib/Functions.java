package main.java.net.felsstudio.fels.lib;

import main.java.net.felsstudio.fels.exceptions.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author felek
 */
public final class Functions {
    private Functions() { }

    public static Map<String, Function> getFunctions() {
        return ScopeHandler.functions();
    }

    public static boolean isExists(String name) {
        return ScopeHandler.isFunctionExists(name);
    }

    public static Function get(String name) {
        return ScopeHandler.getFunction(name);
    }

    public static void set(String key, Function function) {
        ScopeHandler.setFunction(key, function);
    }

    public static void remove(String name) {
        ScopeHandler.removeFunction(name);
    }
}