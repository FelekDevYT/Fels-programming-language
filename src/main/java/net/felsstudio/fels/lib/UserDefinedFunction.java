package main.java.net.felsstudio.fels.lib;

import main.java.net.felsstudio.fels.exceptions.ArgumentsMismatchException;
import main.java.net.felsstudio.fels.parser.ast.Argument;
import main.java.net.felsstudio.fels.parser.ast.ReturnStatement;
import main.java.net.felsstudio.fels.parser.ast.Statement;

/**
 *
 * @author felek
 */
public class UserDefinedFunction implements Function {

    public final main.java.net.felsstudio.fels.parser.ast.Arguments arguments;
    public final Statement body;

    public UserDefinedFunction(main.java.net.felsstudio.fels.parser.ast.Arguments arguments, Statement body) {
        this.arguments = arguments;
        this.body = body;
    }

    public int getArgsCount() {
        return arguments.size();
    }

    public String getArgsName(int index) {
        if (index < 0 || index >= getArgsCount()) return "";
        return arguments.get(index).name();
    }

    @Override
    public Value execute(Value... values) {
        final int size = values.length;
        final int requiredArgsCount = arguments.getRequiredArgumentsCount();
        if (size < requiredArgsCount) {
            throw new ArgumentsMismatchException(String.format(
                    "Arguments count mismatch. Required %d, got %d", requiredArgsCount, size));
        }
        final int totalArgsCount = getArgsCount();
        if (size > totalArgsCount) {
            throw new ArgumentsMismatchException(String.format(
                    "Arguments count mismatch. Total %d, got %d", totalArgsCount, size));
        }

        try {
            ScopeHandler.push();
            for (int i = 0; i < size; i++) {
                ScopeHandler.defineVariableInCurrentScope(getArgsName(i), values[i]);
            }
            // Optional args if exists
            for (int i = size; i < totalArgsCount; i++) {
                final Argument arg = arguments.get(i);
                ScopeHandler.defineVariableInCurrentScope(arg.name(), arg.valueExpr().eval());
            }
            body.execute();
            return NumberValue.ZERO;
        } catch (ReturnStatement rt) {
            return rt.getResult();
        } finally {
            ScopeHandler.pop();
        }
    }

    @Override
    public String toString() {
        if (body instanceof ReturnStatement returnStmt) {
            return String.format("func%s = %s", arguments, returnStmt.expression);
        }
        return String.format("func%s %s", arguments, body);
    }
}