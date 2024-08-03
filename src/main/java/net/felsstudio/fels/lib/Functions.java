package main.java.net.felsstudio.fels.lib;

import main.java.net.felsstudio.fels.exceptions.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author felek
 */
public final class Functions {

    private static final Map<String, Function> functions;
    static {
        functions = new HashMap<>();
    }
    
    public static Map<String, Function> getFunctions() {
        return functions;
    }
    
    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }
    
    public static Function get(String key) {
        if (!isExists(key)) throw new UnknownFunctionException(key);
        return functions.get(key);
    }
    
    public static void set(String key, Function function) {
        functions.put(key, function);
    }
}
