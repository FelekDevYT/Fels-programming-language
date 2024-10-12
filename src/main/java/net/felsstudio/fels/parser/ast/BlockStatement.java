package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.Console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class BlockStatement extends InterruptableNode implements Statement {
    
    public final List<Statement> statements;

    public BlockStatement() {
        statements = new ArrayList<>();
    }
    
    public void add(Statement statement) {
        statements.add(statement);
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        for (Statement statement : statements) {
            statement.execute();
        }
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
        final StringBuilder result = new StringBuilder();
        for (Statement statement : statements) {
            result.append(statement.toString()).append(Console.newline());
        }
        return result.toString();
    }
}
