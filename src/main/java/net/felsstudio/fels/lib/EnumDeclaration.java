package net.felsstudio.fels.lib;

import java.util.Map;

public class EnumDeclaration {
    private final String name;
    private final Map<String, Value> constants;

    public EnumDeclaration(String name, Map<String, Value> constants) {
        this.name = name;
        this.constants = constants;
    }

    public Value getConstant(String constantName) {
        return constants.get(constantName);
    }
}