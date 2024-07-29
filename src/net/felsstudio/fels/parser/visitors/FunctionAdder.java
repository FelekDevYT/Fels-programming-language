package net.felsstudio.fels.parser.visitors;

import net.felsstudio.fels.parser.ast.FunctionDefineStatement;

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
}
