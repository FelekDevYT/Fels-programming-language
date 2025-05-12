package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.Console;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class PrintStatement extends InterruptableNode implements Statement {
    
    public final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        Console.print(expression.eval().asString());
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
        return "print " + expression;
    }
}
