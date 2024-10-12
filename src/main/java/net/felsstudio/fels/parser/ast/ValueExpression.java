package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.Function;
import main.java.net.felsstudio.fels.lib.FunctionValue;
import main.java.net.felsstudio.fels.lib.NumberValue;
import main.java.net.felsstudio.fels.lib.StringValue;
import main.java.net.felsstudio.fels.lib.Types;
import main.java.net.felsstudio.fels.lib.Value;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class ValueExpression extends InterruptableNode implements Expression {
    
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
        super.interruptionCheck();
        return value;
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
        if (value.type() == Types.STRING) {
            return "\"" + value.asString() + "\"";
        }
        return value.toString();
    }
}
