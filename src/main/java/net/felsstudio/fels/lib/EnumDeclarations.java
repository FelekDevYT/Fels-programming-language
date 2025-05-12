package net.felsstudio.fels.lib;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EnumDeclarations {
    private static final Map<String, EnumDeclaration> declarations = new ConcurrentHashMap<>();

    public static void set(String name, EnumDeclaration enumDecl) {
        declarations.put(name, enumDecl);
    }

    public static EnumDeclaration get(String name) {
        return declarations.get(name);
    }

    public static void clear() {
        declarations.clear();
    }
}