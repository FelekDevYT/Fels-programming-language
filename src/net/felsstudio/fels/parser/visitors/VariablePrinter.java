package net.felsstudio.fels.parser.visitors;

import net.felsstudio.fels.parser.ast.AssignmentExpression;
import net.felsstudio.fels.parser.ast.ContainerAccessExpression;
import net.felsstudio.fels.parser.ast.VariableExpression;

/**
 *
 * @author felek
 */
public final class VariablePrinter extends AbstractVisitor {

    @Override
    public void visit(AssignmentExpression s) {
        super.visit(s);
        System.out.println(s.target);
    }

    @Override
    public void visit(VariableExpression s) {
        super.visit(s);
        System.out.println(s.name);
    }
}
