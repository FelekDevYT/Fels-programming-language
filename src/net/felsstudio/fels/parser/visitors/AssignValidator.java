package net.felsstudio.fels.parser.visitors;

import net.felsstudio.fels.lib.Variables;
import net.felsstudio.fels.parser.ast.AssignmentExpression;
import net.felsstudio.fels.parser.ast.VariableExpression;

/**
 *
 * @author felek
 */
public final class AssignValidator extends AbstractVisitor {

    @Override
    public void visit(AssignmentExpression s) {
        super.visit(s);
        if (s.target instanceof VariableExpression) {
            final String variable = ((VariableExpression) s.target).name;
            if (Variables.isExists(variable)) {
                throw new RuntimeException("Cannot assign value to constant");
            }
        }
    }
}
