package main.java.net.felsstudio.fels.parser.ast.statements;

import main.java.net.felsstudio.fels.exceptions.TypeException;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.parser.ast.expressions.ArrayExpression;
import main.java.net.felsstudio.fels.parser.ast.expressions.ValueExpression;
import net.felsstudio.fels.Modules.Module;

import java.lang.reflect.Method;

/**
 *
 * @author felek
 */
public final class UsingStatement extends InterruptableNode implements Statement {

    private static final String PACKAGE = "main.java.net.felsstudio.fels.Modules.%s.%s";
    private static final String INIT_CONSTANTS_METHOD = "initConstants";

    public final Expression expression;

    public UsingStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        final Value value = expression.eval();
        switch (value.type()) {
            case Types.ARRAY:
                for (Value module : ((ArrayValue) value)) {
                    loadModule(module.asString());
                }
                break;
            case Types.STRING:
                loadModule(value.asString());
                break;
            default:
                throw new TypeException("Array or string required");
        }
    }

    private void loadModule(String name) {
        try {
            final Module module = (Module) Class.forName(String.format(PACKAGE, name, name)).newInstance();
            module.init();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void loadConstants() {
        if (expression instanceof ArrayExpression) {
            ArrayExpression ae = (ArrayExpression) expression;
            for (Expression expr : ae.elements) {
                loadConstants(expr.eval().asString());
            }
        }
        if (expression instanceof ValueExpression) {
            ValueExpression ve = (ValueExpression) expression;
            loadConstants(ve.value.asString());
        }
    }

    private void loadConstants(String moduleName) {
        try {
            final Class<?> moduleClass = Class.forName(String.format(PACKAGE, moduleName, moduleName));
            final Method method = moduleClass.getMethod(INIT_CONSTANTS_METHOD);
            if (method != null) {
                method.invoke(this);
            }
        } catch (Exception ex) {
            // ignore
        }
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
        return "use " + expression;
    }
}