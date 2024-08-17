package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.exceptions.TypeException;
import main.java.net.felsstudio.fels.lib.*;
import main.java.net.felsstudio.fels.parser.ast.ArrayExpression;
import main.java.net.felsstudio.fels.parser.ast.ValueExpression;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author felek
 */
public final class UsingStatement extends InterruptableNode implements Statement {
    public final Collection<String> modules;

    public UsingStatement(Collection<String> modules) {
        this.modules = modules;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        for (String module : modules) {
            ModuleLoader.loadAndUse(module);
        }
    }

    public Map<String, Value> loadConstants() {
        final var result = new LinkedHashMap<String, Value>();
        for (String moduleName : modules) {
            final Module module = ModuleLoader.load(moduleName);
            result.putAll(module.constants());
        }
        return result;
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