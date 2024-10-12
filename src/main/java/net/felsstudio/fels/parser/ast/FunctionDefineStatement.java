package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.Functions;
import main.java.net.felsstudio.fels.lib.UserDefinedFunction;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class FunctionDefineStatement implements Statement {
    
    public final String name;
    public final Arguments arguments;
    public final Statement body;
    
    public FunctionDefineStatement(String name, Arguments arguments, Statement body) {
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void execute() {
        Functions.set(name, new UserDefinedFunction(arguments, body));
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
        if (body instanceof ReturnStatement) {
            return String.format("def %s%s = %s", name, arguments, ((ReturnStatement)body).expression);
        }
        return String.format("def %s%s %s", name, arguments, body);
    }
}
