package main.java.net.felsstudio.fels.parser.ast;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class DoWhileStatement extends InterruptableNode implements Statement {
    
    public final Expression condition;
    public final Statement statement;

    public DoWhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }
    
    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        do {
            try {
                statement.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while (condition.eval().asInt() != 0);
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
        return "do " + statement + " while " + condition;
    }
}
