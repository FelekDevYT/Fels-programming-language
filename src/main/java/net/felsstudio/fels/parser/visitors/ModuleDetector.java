package net.felsstudio.fels.parser.visitors;

import net.felsstudio.fels.parser.ast.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ModuleDetector extends AbstractVisitor {

    private final Set<String> modules;

    public ModuleDetector() {
        modules = new HashSet<>();
    }

    public Set<String> detect(Statement s) {
        s.accept(this);
        return modules;
    }

    @Override
    public void visit(UsingStatement st) throws IOException, InterruptedException {
        if (st.expression instanceof ArrayExpression ae) {
            for (Expression expr : ae.elements) {
                modules.add(expr.eval().asString());
            }
        }
        if (st.expression instanceof ValueExpression ve) {
            modules.add(ve.value.asString());
        }
        super.visit(st);
    }
}
