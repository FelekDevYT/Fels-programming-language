package net.felsstudio.fels.parser.ast;

import java.io.IOException;

public class PanicStatement implements Statement{
    private final Expression expression;

    public PanicStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        throw new RuntimeException(expression.toString());
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) throws IOException, InterruptedException {
        return null;
    }
}
