package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.exceptions.PanicException;

public class PanicStatement implements Statement{
    public final Expression expression;

    public PanicStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        throw new PanicException(expression.eval().asString());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return null;
    }

    @Override
    public String toString() {
        return "PANIC STATEMENT";
    }
}
