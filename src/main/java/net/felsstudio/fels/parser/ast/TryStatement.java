package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.StringValue;
import net.felsstudio.fels.lib.Variables;

import java.io.IOException;

public class TryStatement implements Statement{
    private final Statement body;

    public TryStatement(Statement body) {
        this.body = body;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        try{
            body.execute();
        }catch (Exception exception){
            Variables.define("__ERRMSG__",new StringValue(exception.toString()));
        }
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) throws IOException, InterruptedException {
        return null;
    }
}
