package main.java.net.felsstudio.fels.parser.visitors;

import main.java.net.felsstudio.fels.parser.ast.ArrayExpression;
import main.java.net.felsstudio.fels.parser.ast.Expression;
import main.java.net.felsstudio.fels.parser.ast.Statement;
import main.java.net.felsstudio.fels.parser.ast.UsingStatement;
import main.java.net.felsstudio.fels.parser.ast.ValueExpression;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ModuleDetector extends AbstractVisitor {

    private Set<String> modules;

    public ModuleDetector() {
        modules = new HashSet<>();
    }

    public Set<String> detect(Statement s) {
        s.accept(this);
        return modules;
    }

    @Override
    public void visit(UsingStatement st) throws IOException, InterruptedException {
        if (st.expression instanceof ArrayExpression) {
            ArrayExpression ae = (ArrayExpression) st.expression;
            for (Expression expr : ae.elements) {
                modules.add(expr.eval().asString());
            }
        }
        if (st.expression instanceof ValueExpression) {
            ValueExpression ve = (ValueExpression) st.expression;
            modules.add(ve.value.asString());
        }
        super.visit(st);
    }
}
