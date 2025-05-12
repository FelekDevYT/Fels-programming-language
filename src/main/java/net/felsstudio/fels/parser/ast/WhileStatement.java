package net.felsstudio.fels.parser.ast;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class WhileStatement extends InterruptableNode implements Statement {
    
    public final Expression condition;
    public final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }
    
    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        while (condition.eval().asInt() != 0) {
            try {
                statement.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
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
        return "while " + condition + " " + statement;
    }
}
