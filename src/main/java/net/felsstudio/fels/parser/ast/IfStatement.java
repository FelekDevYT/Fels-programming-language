package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.*;

/**
 *
 * @author felek
 */
public final class IfStatement implements Statement {

    public final Expression expression;
    public final Statement ifStatement, elseStatement;

    public IfStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        final int result = expression.eval().asInt();
        if (result != 0) {
            ifStatement.execute();
        } else if (elseStatement != null) {
            elseStatement.execute();
        }
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
        final StringBuilder result = new StringBuilder();
        result.append("if ").append(expression).append(' ').append(ifStatement);
        if (elseStatement != null) {
            result.append("\nelse ").append(elseStatement);
        }
        return result.toString();
    }
}
