package main.java.net.felsstudio.fels.parser.ast.statements;

import org.jetbrains.annotations.Nullable;

public class PerrorStatement implements Statement {

    private final Expression expr;

    public PerrorStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public void execute() {
        System.err.println(expr.eval());
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
        return "perror " + expr.eval().toString();
    }
}
