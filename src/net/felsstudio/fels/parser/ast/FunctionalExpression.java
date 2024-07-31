package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.exceptions.UnknownFunctionException;
import net.felsstudio.fels.exceptions.VariableDoesNotExistsException;
import net.felsstudio.fels.lib.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author felek
 */
public final class FunctionalExpression implements Expression, Statement {
    
    public final Expression functionExpr;
    public final List<Expression> arguments;
    
    public FunctionalExpression(Expression functionExpr) {
        this.functionExpr = functionExpr;
        arguments = new ArrayList<>();
    }
    
    public void addArgument(Expression arg) {
        arguments.add(arg);
    }
    
    @Override
    public void execute() {
        eval();
    }
    
    @Override
    public Value eval() {
        final int size = arguments.size();
        final Value[] values = new Value[size];
        for (int i = 0; i < size; i++) {
            values[i] = arguments.get(i).eval();
        }
        final Function f = consumeFunction(functionExpr);
        CallStack.enter(functionExpr.toString(), f);
        final Value result = f.execute(values);
        CallStack.exit();
        return result;
    }
    
    private Function consumeFunction(Expression expr) {
        try {
            final Value value = expr.eval();
            if (value.type() == Types.FUNCTION) {
                return ((FunctionValue) value).getValue();
            }
            return getFunction(value.asString());
        } catch (VariableDoesNotExistsException ex) {
            return getFunction(ex.variable);
        }
    }
    
    private Function getFunction(String key) {
        if (Functions.isExists(key)) return Functions.get(key);
        if (Variables.isExists(key)) {
            final Value variable = Variables.get(key);
            if (variable.type() == Types.FUNCTION) {
                return ((FunctionValue)variable).getValue();
            }
        }
        throw new UnknownFunctionException(key);
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
        final StringBuilder sb = new StringBuilder();
        if (functionExpr instanceof ValueExpression && ((ValueExpression)functionExpr).value.type() == Types.STRING) {
            sb.append(((ValueExpression)functionExpr).value.asString()).append('(');
        } else {
            sb.append(functionExpr).append('(');
        }
        final Iterator<Expression> it = arguments.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(", ").append(it.next());
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
