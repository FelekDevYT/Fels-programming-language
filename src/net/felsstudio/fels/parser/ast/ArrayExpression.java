package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.*;

import java.util.List;

/**
 *
 * @author felek
 */
public final class ArrayExpression extends InterruptableNode implements Expression {
    
    public final List<Expression> elements;

    public ArrayExpression(List<Expression> arguments) {
        this.elements = arguments;
    }
    
    @Override
    public Value eval() {
        super.interruptionCheck();
        final int size = elements.size();
        final ArrayValue array = new ArrayValue(size);
        for (int i = 0; i < size; i++) {
            array.set(i, elements.get(i).eval());
        }
        return array;
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
        return elements.toString();
    }
}
