package main.java.net.felsstudio.fels.parser.ast.expressions;

import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.parser.ast.interfaces.Expression;
import main.java.net.felsstudio.fels.parser.ast.interfaces.ResultVisitor;
import main.java.net.felsstudio.fels.parser.ast.interfaces.Visitor;

/**
 *
 * @author felek
 */
public final class FunctionReferenceExpression implements Expression {

    public final String name;

    public FunctionReferenceExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
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
