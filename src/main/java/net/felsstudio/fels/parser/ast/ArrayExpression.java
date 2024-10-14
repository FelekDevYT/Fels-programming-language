package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.ArrayValue;
import main.java.net.felsstudio.fels.lib.Value;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class ArrayExpression implements Expression {
    
    public final List<Expression> elements;

    public ArrayExpression(List<Expression> arguments) {
        this.elements = arguments;
    }
    
    @Override
    public Value eval() throws IOException, InterruptedException {
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
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) throws IOException, InterruptedException {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
