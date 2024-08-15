package main.java.net.felsstudio.fels.parser.ast.statements;

import main.java.net.felsstudio.fels.exceptions.PanicException;
import main.java.net.felsstudio.fels.lib.*;

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
