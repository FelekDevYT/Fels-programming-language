package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.exceptions.TypeException;
import net.felsstudio.fels.lib.*;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class ForeachArrayStatement extends InterruptableNode implements Statement {
    
    public final String variable;
    public final Expression container;
    public final Statement body;

    public ForeachArrayStatement(String variable, Expression container, Statement body) {
        this.variable = variable;
        this.container = container;
        this.body = body;
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        super.interruptionCheck();
        final Value previousVariableValue = Variables.isExists(variable) ? Variables.get(variable) : null;

        final Value containerValue = container.eval();
        switch (containerValue.type()) {
            case Types.STRING:
                iterateString(containerValue.asString());
                break;
            case Types.ARRAY:
                iterateArray((ArrayValue) containerValue);
                break;
            case Types.MAP:
                iterateMap((MapValue) containerValue);
                break;
            default:
                throw new TypeException("Cannot iterate " + Types.typeToString(containerValue.type()));
        }

        // Restore variables
        if (previousVariableValue != null) {
            Variables.set(variable, previousVariableValue);
        } else {
            Variables.remove(variable);
        }
    }

    private void iterateString(String str) {
        for (char ch : str.toCharArray()) {
            Variables.set(variable, new StringValue(String.valueOf(ch)));
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void iterateArray(ArrayValue containerValue) {
        for (Value value : containerValue) {
            Variables.set(variable, value);
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement | IOException | InterruptedException cs) {
                // continue;
            }
        }
    }

    private void iterateMap(MapValue containerValue) {
        for (Map.Entry<Value, Value> entry : containerValue) {
            Variables.set(variable, new ArrayValue(new Value[] {
                    entry.getKey(),
                    entry.getValue()
            }));
            try {
                body.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement | IOException | InterruptedException cs) {
                // continue;
            }
        }
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
        return String.format("for %s : %s %s", variable, container, body);
    }
}
