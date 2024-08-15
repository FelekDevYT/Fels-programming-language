package main.java.net.felsstudio.fels.parser.visitors;

import main.java.net.felsstudio.fels.parser.ast.FunctionDefineStatement;
import main.java.net.felsstudio.fels.parser.ast.PerrorStatement;
import main.java.net.felsstudio.fels.parser.visitors.AbstractVisitor;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author felek
 */
public final class FunctionAdder extends AbstractVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }

    @Override
    public void visit(@Nullable PerrorStatement st) {

    }
}
