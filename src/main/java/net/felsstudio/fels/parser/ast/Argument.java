package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.*;

public record Argument(String name, Expression valueExpr) {

    public Argument(String name) {
        this(name, null);
    }

    @Override
    public String toString() {
        return name + (valueExpr == null ? "" : " = " + valueExpr);
    }
}
