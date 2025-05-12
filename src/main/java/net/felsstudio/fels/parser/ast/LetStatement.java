package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Variables;

import java.io.IOException;

public class LetStatement implements Statement{
    private final Expression varname;
    private final Expression value;

    public LetStatement(Expression varname, Expression value) {
        this.varname = varname;
        this.value = value;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        Variables.define(varname.eval().asString(),value.eval());
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) throws IOException, InterruptedException {
        return null;
    }
}
