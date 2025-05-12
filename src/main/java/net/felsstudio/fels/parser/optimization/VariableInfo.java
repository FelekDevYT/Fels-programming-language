package net.felsstudio.fels.parser.optimization;

import net.felsstudio.fels.lib.Value;

public final class VariableInfo {
    public Value value;
    int modifications;

    @Override
    public String toString() {
        return (value == null ? "?" : value) + " (" + modifications + " mods)";
    }
}