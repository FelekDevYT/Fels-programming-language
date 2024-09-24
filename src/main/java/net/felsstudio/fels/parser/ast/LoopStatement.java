package main.java.net.felsstudio.fels.parser.ast;

import org.jetbrains.annotations.Nullable;

public class LoopStatement implements Statement{

    private Statement body;

    public LoopStatement(Statement body) {
        this.body = body;
    }

    @Override
    public void execute() {
        while(true){
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
    }

    @Override
    public void accept(@Nullable Visitor visitor) {

    }

    @Override
    public <R, T> R accept(@Nullable ResultVisitor<R, T> visitor, T input) {
        return null;
    }

    @Override
    public String toString(){
        return "LoopStatement";
    }
}
