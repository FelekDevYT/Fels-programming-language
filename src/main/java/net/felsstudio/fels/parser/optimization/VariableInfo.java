package main.java.net.felsstudio.fels.parser.optimization;

import main.java.net.felsstudio.fels.lib.Value;

public final class VariableInfo {
    public Value value;
    int modifications;

    @Override
    public String toString() {
        return (value == null ? "?" : value) + " (" + modifications + " mods)";
    }
}