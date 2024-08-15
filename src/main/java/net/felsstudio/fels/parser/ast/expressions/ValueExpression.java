package main.java.net.felsstudio.fels.parser.ast.expressions;

import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.parser.ast.interfaces.Expression;
import main.java.net.felsstudio.fels.parser.ast.interfaces.ResultVisitor;
import main.java.net.felsstudio.fels.parser.ast.interfaces.Visitor;

/**
 *
 * @author felek
 */
public final class ValueExpression implements Expression {

    public final Value value;

    public ValueExpression(Number value) {
        this.value = NumberValue.of(value);
    }

    public ValueExpression(String value) {
        this.value = new StringValue(value);
    }

    public ValueExpression(Function value) {
        this.value = new FunctionValue(value);
    }

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value eval() {
        return value;
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
        if (value.type() == Types.STRING) {
            return "\"" + value.asString() + "\"";
        }
        return value.toString();
    }
}
