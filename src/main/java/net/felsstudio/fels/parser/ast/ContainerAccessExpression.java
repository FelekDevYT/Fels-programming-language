package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.exceptions.TypeException;
import net.felsstudio.fels.lib.*;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class ContainerAccessExpression implements Expression, Accessible {
    
    public final Expression root;
    public final List<Expression> indices;
    private final boolean rootIsVariable;

    public ContainerAccessExpression(String variable, List<Expression> indices) {
        this(new VariableExpression(variable), indices);
    }

    public ContainerAccessExpression(Expression root, List<Expression> indices) {
        rootIsVariable = root instanceof VariableExpression;
        this.root = root;
        this.indices = indices;
    }

    public boolean rootIsVariable() {
        return rootIsVariable;
    }

    public Expression getRoot() {
        return root;
    }

    @Override
    public Value eval() throws IOException, InterruptedException {
        return get();
    }
    
    @Override
    public Value get() throws IOException, InterruptedException {
        final Value container = getContainer();
        final Value lastIndex = lastIndex();
        switch (container.type()) {
            case Types.ARRAY:
                return ((ArrayValue) container).get(lastIndex);

            case Types.MAP:
                return ((MapValue) container).get(lastIndex);

            case Types.STRING:
                return ((StringValue) container).access(lastIndex);
                
            case Types.CLASS:
                return ((ClassInstanceValue) container).access(lastIndex);
                
            default:
                throw new TypeException("Array or map expected. Got " + Types.typeToString(container.type()));
        }
    }

    @Override
    public Value set(Value value) throws IOException, InterruptedException {
        final Value container = getContainer();
        final Value lastIndex = lastIndex();
        switch (container.type()) {
            case Types.ARRAY:
                final int arrayIndex = lastIndex.asInt();
                ((ArrayValue) container).set(arrayIndex, value);
                return value;

            case Types.MAP:
                ((MapValue) container).set(lastIndex, value);
                return value;
                
            case Types.CLASS:
                ((ClassInstanceValue) container).set(lastIndex, value);
                return value;
                
            default:
                throw new TypeException("Array or map expected. Got " + container.type());
        }
    }
    
    public Value getContainer() throws IOException, InterruptedException {
        Value container = root.eval();
        final int last = indices.size() - 1;
        for (int i = 0; i < last; i++) {
            final Value index = index(i);
            switch (container.type()) {
                case Types.ARRAY:
                    final int arrayIndex = index.asInt();
                    container = ((ArrayValue) container).get(arrayIndex);
                    break;
                    
                case Types.MAP:
                    container = ((MapValue) container).get(index);
                    break;
                    
                default:
                    throw new TypeException("Array or map expected");
            }
        }
        return container;
    }
    
    public Value lastIndex() throws IOException, InterruptedException {
        return index(indices.size() - 1);
    }
    
    private Value index(int index) throws IOException, InterruptedException {
        return indices.get(index).eval();
    }
    
    public MapValue consumeMap(Value value) {
        if (value.type() != Types.MAP) {
            throw new TypeException("Map expected");
        }
        return (MapValue) value;
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
        return root.toString() + indices;
    }
}
