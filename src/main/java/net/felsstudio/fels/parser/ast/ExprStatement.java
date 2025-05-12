package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Value;

import java.io.IOException;

/**
 * Wrapper for expressions, which can be used as statements.
 * 
 * @author FelekDevYT,FelsStudio
 */
public final class ExprStatement extends InterruptableNode implements Expression, Statement {
    
    public final Expression expr;
    
    public ExprStatement(Expression function) {
        this.expr = function;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        expr.eval();
    }
    
    @Override
    public Value eval() throws IOException, InterruptedException {
        return expr.eval();
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
        return expr.toString();
    }
}
