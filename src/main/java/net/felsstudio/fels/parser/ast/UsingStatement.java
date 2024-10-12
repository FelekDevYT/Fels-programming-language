package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.exceptions.TypeException;
import main.java.net.felsstudio.fels.lib.ArrayValue;
import main.java.net.felsstudio.fels.lib.Types;
import main.java.net.felsstudio.fels.lib.Value;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public final class UsingStatement extends InterruptableNode implements Statement {

    private static final String PACKAGE = "main.java.net.felsstudio.fels.Modules.%s.%s.%s.%s";
    private static final String INIT_CONSTANTS_METHOD = "initConstants";

    public final Expression expression;

    public UsingStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() throws RuntimeException, IOException, InterruptedException {
        super.interruptionCheck();
        final Value value = expression.eval();
        final String[] parts = value.asString().split("\\.");
        if (Files.exists(Paths.get(value.asString().substring(1,value.asString().length()-1)))) {
            new ImportStatement(expression).execute();
            return;
        }

        String page = parts[1];
        String name = parts[2];

        loadModule(page,name);
    }

    private void loadModule(String page,String name) {
        try {
            final Module module = (Module) Class.forName(String.format(PACKAGE, "fels",page, name,name))
                    .getDeclaredConstructor()
                    .newInstance();
            module.init();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to load module " + name, ex);
        }
    }

    public void loadConstants() throws IOException, InterruptedException {
        if (expression instanceof ArrayExpression ae) {
            for (Expression expr : ae.elements) {
                loadConstants(expr.eval().asString());
            }
        }
        if (expression instanceof ValueExpression ve) {
            loadConstants(ve.value.asString());
        }
    }

    private TypeException typeException(Value value) {
        return new TypeException("Array or string required in 'use' statement, " +
                "got " + Types.typeToString(value.type()) + " " + value);
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

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return null;
    }

    @Override
    public String toString() {
        return "using " + expression;
    }
}