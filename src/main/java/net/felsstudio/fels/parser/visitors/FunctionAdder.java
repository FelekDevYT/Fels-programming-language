package net.felsstudio.fels.parser.visitors;


import net.felsstudio.fels.parser.ast.FunctionDefineStatement;

/**
 *
 * @author aNNiMON
 */
public final class FunctionAdder extends AbstractVisitor {

    @Override
    public void visit(FunctionDefineStatement s) {
        super.visit(s);
        s.execute();
    }
}
