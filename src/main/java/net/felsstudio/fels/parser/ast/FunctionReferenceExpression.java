package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.FunctionValue;
import net.felsstudio.fels.lib.Functions;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class FunctionReferenceExpression extends InterruptableNode implements Expression {

    public final String name;

    public FunctionReferenceExpression(String name) {
        this.name = name;
    }

    @Override
    public FunctionValue eval() {
        super.interruptionCheck();
        return new FunctionValue(Functions.get(name));
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
        return "::" + name;
    }
}
