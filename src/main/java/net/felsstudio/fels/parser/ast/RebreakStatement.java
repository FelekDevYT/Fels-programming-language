package main.java.net.felsstudio.fels.parser.ast;

import org.jetbrains.annotations.Nullable;

public final class RebreakStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        throw this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "rebreak";
    }
}