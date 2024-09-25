package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.Functions;
import main.java.net.felsstudio.fels.lib.UserDefinedFunction;
import org.jetbrains.annotations.Nullable;

public class MacroFunctionStatement implements Statement{
    public final String name;
    public final Arguments arguments;
    public final Statement body;

    public MacroFunctionStatement(String name, Arguments arguments, Statement body) {
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void execute() {
        if(body instanceof ReturnStatement){

        }
        Functions.set(name,new UserDefinedFunction(arguments, body));
    }

    @Override
    public void accept(@Nullable Visitor visitor) {

    }

    @Override
    public <R, T> R accept(@Nullable ResultVisitor<R, T> visitor, T input) {
        return null;
    }

    @Override
    public String toString() {
        return "Macro func "+name;
    }
}
