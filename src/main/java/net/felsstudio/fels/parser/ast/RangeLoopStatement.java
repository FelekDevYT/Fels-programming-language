package main.java.net.felsstudio.fels.parser.ast;

import com.sun.source.tree.StatementTree;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.nimbus.State;

public class RangeLoopStatement implements Statement {
    private final Statement body;
    private final Expression condition;

    public RangeLoopStatement(Statement body, Expression condition) {
        this.body = body;
        this.condition = condition;
    }

    @Override
    public void execute() {
        for(int i = 0;i != condition.eval().asInt();i++){
            try{
                body.execute();
            }catch(BreakStatement bs){
                break;
            }catch(ContinueStatement cs){
                //continue passing
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
    public String toString() {
        return "range loop statement";
    }
}
