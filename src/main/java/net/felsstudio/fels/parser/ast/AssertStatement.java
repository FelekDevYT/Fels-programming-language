package net.felsstudio.fels.parser.ast;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class AssertStatement extends InterruptableNode implements Statement{
    private final Expression expression;

    public AssertStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        int expr = expression.eval().asInt();
        boolean boolExpr;
        boolExpr = expr == 1;

        if(!boolExpr){
            throw new AssertionError("Assertion failed: "+expression);
        }
    }

    @Override
    public void accept(@Nullable Visitor visitor) {

    }

    @Override
    public <R, T> R accept(@Nullable ResultVisitor<R, T> visitor, T input) {
        return null;
    }
}