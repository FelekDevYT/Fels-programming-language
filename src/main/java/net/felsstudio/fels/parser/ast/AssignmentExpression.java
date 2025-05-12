package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Value;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class AssignmentExpression extends net.felsstudio.fels.parser.ast.InterruptableNode implements net.felsstudio.fels.parser.ast.Expression, net.felsstudio.fels.parser.ast.Statement {

    public final Accessible target;
    public final net.felsstudio.fels.parser.ast.BinaryExpression.Operator operation;
    public final net.felsstudio.fels.parser.ast.Expression expression;
    
    public AssignmentExpression(BinaryExpression.Operator operation, Accessible target, Expression expr) {
        this.operation = operation;
        this.target = target;
        this.expression = expr;
    }
    
    @Override
    public void execute() throws IOException, InterruptedException {
        eval();
    }

    @Override
    public Value eval() throws IOException, InterruptedException {
        super.interruptionCheck();
        if (operation == null) {
            // Simple assignment
            return target.set(expression.eval());
        }
        final Expression expr1 = new ValueExpression(target.get());
        final Expression expr2 = new ValueExpression(expression.eval());
        return target.set(new BinaryExpression(operation, expr1, expr2).eval());
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
        final String op = (operation == null) ? "" : operation.toString();
        return String.format("%s %s= %s", target, op, expression);
    }
}
