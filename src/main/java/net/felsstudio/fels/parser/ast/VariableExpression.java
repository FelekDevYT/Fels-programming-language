package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.exceptions.VariableDoesNotExistsException;
import main.java.net.felsstudio.fels.lib.Value;
import main.java.net.felsstudio.fels.lib.Variables;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class VariableExpression extends InterruptableNode implements Expression, Accessible {
    
    public final String name;
    
    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        super.interruptionCheck();
        return get();
    }
    
    @Override
    public Value get() {
        if (!Variables.isExists(name)) throw new VariableDoesNotExistsException(name);
        return Variables.get(name);
    }

    @Override
    public Value set(Value value) {
        Variables.set(name, value);
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
        return name;
    }
}
