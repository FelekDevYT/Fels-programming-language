package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.exceptions.OperationIsNotSupportedException;
import net.felsstudio.fels.lib.NumberValue;
import net.felsstudio.fels.lib.Types;
import net.felsstudio.fels.lib.Value;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class ConditionalExpression implements Expression {

    public enum Operator {
        EQUALS("=="),
        NOT_EQUALS("!="),

        LT("<"),
        LTEQ("<="),
        GT(">"),
        GTEQ(">="),

        AND("&&"),
        OR("||"),

        NULL_COALESCE("??");

        private final String name;

        Operator(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public final Expression expr1, expr2;
    public final Operator operation;

    public ConditionalExpression(Operator operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Value eval() throws IOException, InterruptedException {
        switch (operation) {
            case AND:
                return NumberValue.fromBoolean((expr1AsInt() != 0) && (expr2AsInt() != 0));
            case OR:
                return NumberValue.fromBoolean((expr1AsInt() != 0) || (expr2AsInt() != 0));

            case NULL_COALESCE:
                return nullCoalesce();

            default:
                return NumberValue.fromBoolean(evalAndCompare());
        }
    }

    private boolean evalAndCompare() throws IOException, InterruptedException {
        final Value value1 = expr1.eval();
        final Value value2 = expr2.eval();

        double number1, number2;
        if (value1.type() == Types.NUMBER) {
            number1 = value1.asNumber();
            number2 = value2.asNumber();
        } else {
            number1 = value1.compareTo(value2);
            number2 = 0;
        }

        switch (operation) {
            case EQUALS: return number1 == number2;
            case NOT_EQUALS: return number1 != number2;

            case LT: return number1 < number2;
            case LTEQ: return number1 <= number2;
            case GT: return number1 > number2;
            case GTEQ: return number1 >= number2;

            default:
                throw new OperationIsNotSupportedException(operation);
        }
    }

    private Value nullCoalesce() throws IOException, InterruptedException {
        Value value1;
        try {
            value1 = expr1.eval();
        } catch (NullPointerException | IOException | InterruptedException npe) {
            value1 = null;
        }
        if (value1 == null) {
            return expr2.eval();
        }
        return value1;
    }

    private int expr1AsInt() throws IOException, InterruptedException {
        return expr1.eval().asInt();
    }

    private int expr2AsInt() throws IOException, InterruptedException {
        return expr2.eval().asInt();
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
        return String.format("%s %s %s", expr1, operation.getName(), expr2);
    }
}
