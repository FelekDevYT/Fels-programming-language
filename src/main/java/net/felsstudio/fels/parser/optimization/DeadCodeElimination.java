package main.java.net.felsstudio.fels.parser.optimization;

import main.java.net.felsstudio.fels.lib.Types;
import main.java.net.felsstudio.fels.parser.ast.AssignmentExpression;
import main.java.net.felsstudio.fels.parser.ast.BlockStatement;
import main.java.net.felsstudio.fels.parser.ast.ExprStatement;
import main.java.net.felsstudio.fels.parser.ast.Expression;
import main.java.net.felsstudio.fels.parser.ast.IfStatement;
import main.java.net.felsstudio.fels.parser.ast.Node;
import main.java.net.felsstudio.fels.parser.ast.Statement;
import main.java.net.felsstudio.fels.parser.ast.TernaryExpression;
import main.java.net.felsstudio.fels.parser.ast.ValueExpression;
import main.java.net.felsstudio.fels.parser.ast.VariableExpression;
import main.java.net.felsstudio.fels.parser.ast.WhileStatement;
import main.java.net.felsstudio.fels.parser.visitors.VisitorUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Performs dead code elimination.
 */
public class DeadCodeElimination extends OptimizationVisitor<Map<String, VariableInfo>> implements Optimizable {

    private int ifStatementEliminatedCount;
    private int ternaryExpressionEliminatedCount;
    private int whileStatementEliminatedCount;
    private int assignmentExpressionEliminatedCount;

    @Override
    public Node optimize(Node node) throws IOException, InterruptedException {
        final Map<String, VariableInfo> variableInfos = VariablesGrabber.getInfo(node);
        return node.accept(this, variableInfos);
    }

    @Override
    public int optimizationsCount() {
        return ifStatementEliminatedCount + ternaryExpressionEliminatedCount
                + whileStatementEliminatedCount + assignmentExpressionEliminatedCount;
    }

    @Override
    public String summaryInfo() {
        if (optimizationsCount() == 0) return "";
        final StringBuilder sb = new StringBuilder();
        if (ifStatementEliminatedCount > 0) {
            sb.append("\nEliminated IfStatement: ").append(ifStatementEliminatedCount);
        }
        if (ternaryExpressionEliminatedCount > 0) {
            sb.append("\nEliminated TernaryExpression: ").append(ternaryExpressionEliminatedCount);
        }
        if (whileStatementEliminatedCount > 0) {
            sb.append("\nEliminated WhileStatement: ").append(whileStatementEliminatedCount);
        }
        if (whileStatementEliminatedCount > 0) {
            sb.append("\nEliminated AssignmentExpression: ").append(assignmentExpressionEliminatedCount);
        }
        return sb.toString();
    }

    @Override
    public Node visit(IfStatement s, Map<String, VariableInfo> t) throws IOException, InterruptedException {
        if (VisitorUtils.isValue(s.expression)) {
            ifStatementEliminatedCount++;
            // true statement
            if (s.expression.eval().asInt() != 0) {
                return s.ifStatement;
            }
            // false statement
            if (s.elseStatement != null) {
                return s.elseStatement;
            }
            return new ExprStatement(s.expression);
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(TernaryExpression s, Map<String, VariableInfo> t) throws IOException, InterruptedException {
        if (VisitorUtils.isValue(s.condition)) {
            ternaryExpressionEliminatedCount++;
            if (s.condition.eval().asInt() != 0) {
                return s.trueExpr;
            }
            return s.falseExpr;
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(WhileStatement s, Map<String, VariableInfo> t) throws IOException, InterruptedException {
        if (VisitorUtils.isValueAsInt(s.condition, 0)) {
            whileStatementEliminatedCount++;
            return new ExprStatement(s.condition);
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(AssignmentExpression s, Map<String, VariableInfo> t) throws IOException, InterruptedException {
        if (!VisitorUtils.isVariable((Node)s.target)) return super.visit(s, t);

        final String variableName = ((VariableExpression) s.target).name;
        if (!t.containsKey(variableName)) return super.visit(s, t);

        final VariableInfo info = t.get(((VariableExpression) s.target).name);
        if (info.modifications != 1 || info.value == null) {
            return super.visit(s, t);
        }
        
        switch (info.value.type()) {
            case Types.NUMBER:
            case Types.STRING:
                assignmentExpressionEliminatedCount++;
                return new ValueExpression(info.value);
            default:
                return super.visit(s, t);
        }
    }

    @Override
    public Node visit(BlockStatement s, Map<String, VariableInfo> t) throws IOException, InterruptedException {
        final BlockStatement result = new BlockStatement();
        boolean changed = false;
        for (Statement statement : s.statements) {
            final Node node = statement.accept(this, t);
            if (node != statement) {
                changed = true;
            }
            if (node instanceof ExprStatement
                    && VisitorUtils.isConstantValue( ((ExprStatement) node).expr )) {
                changed = true;
                continue;
            }

            if (node instanceof Statement) {
                result.add((Statement) node);
            } else if (node instanceof Expression) {
                result.add(new ExprStatement((Expression) node));
            }
        }
        if (changed) {
            return result;
        }
        return super.visit(s, t);
    }
}
