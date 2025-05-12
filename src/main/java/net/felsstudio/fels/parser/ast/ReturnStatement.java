package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Value;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class ReturnStatement extends RuntimeException implements Statement {

    public final Expression expression;
    private Value result;
    
    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }
    
    public Value getResult() {
        return result;
    }
    
    @Override
    public void execute() throws IOException, InterruptedException {
        result = expression.eval();
        throw this;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) throws IOException, InterruptedException {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "return " + expression;
    }
}
