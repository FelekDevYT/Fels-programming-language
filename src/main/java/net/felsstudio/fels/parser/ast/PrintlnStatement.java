package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.Console;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class PrintlnStatement extends InterruptableNode implements Statement {
    
    public final Expression expression;

    public PrintlnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        Console.println(expression.eval().asString());
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
        return "println " + expression;
    }
}
