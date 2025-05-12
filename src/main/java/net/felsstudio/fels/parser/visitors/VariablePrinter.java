package net.felsstudio.fels.parser.visitors;

import net.felsstudio.fels.Console;

import net.felsstudio.fels.parser.ast.AssignmentExpression;
import net.felsstudio.fels.parser.ast.VariableExpression;

/**
 *
 * @author aNNiMON
 */
public final class VariablePrinter extends AbstractVisitor {

    @Override
    public void visit(AssignmentExpression s) {
        super.visit(s);
        Console.println(s.target);
    }

    @Override
    public void visit(VariableExpression s) {
        super.visit(s);
        Console.println(s.name);
    }
}
