package main.java.net.felsstudio.fels.parser.visitors;

import main.java.net.felsstudio.fels.parser.ast.FunctionDefineStatement;
import main.java.net.felsstudio.fels.parser.visitors.AbstractVisitor;

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