package main.java.net.felsstudio.fels.parser.visitors;

import main.java.net.felsstudio.fels.parser.ast.AssignmentExpression;
import main.java.net.felsstudio.fels.parser.ast.PerrorStatement;
import main.java.net.felsstudio.fels.parser.ast.VariableExpression;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public void visit(@Nullable PerrorStatement st) {

    }
}
