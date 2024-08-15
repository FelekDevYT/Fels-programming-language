package main.java.net.felsstudio.fels.parser.ast.statements;

import main.java.net.felsstudio.fels.lib.*;

/**
 *
 * @author felek
 */
public final class ContinueStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        throw this;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "continue";
    }
}
