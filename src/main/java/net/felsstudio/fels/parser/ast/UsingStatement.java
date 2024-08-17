package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.exceptions.TypeException;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.parser.ast.ArrayExpression;
import main.java.net.felsstudio.fels.parser.ast.ValueExpression;
import net.felsstudio.fels.Modules.Module;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author felek
 */
public final class UsingStatement extends InterruptableNode implements Statement {

    private static final String PACKAGE = "main.java.net.felsstudio.fels.Modules.%s.%s";
    private static final String INIT_CONSTANTS_METHOD = "initConstants";

    public final Collection<String> modules;

    public UsingStatement(Collection<String> modules) {
        this.modules = modules;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        for (String module : modules) {
            loadModule(module);
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
        for (String module : modules) {
            loadConstants(module);
        }
    }

    private void loadConstants(String moduleName) {
        try {
            final Class<?> moduleClass = Class.forName(String.format(PACKAGE, moduleName, moduleName));
            final Method method = moduleClass.getMethod(INIT_CONSTANTS_METHOD);
            method.invoke(this);
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
        return "using " + String.join(", ", modules);
    }
}