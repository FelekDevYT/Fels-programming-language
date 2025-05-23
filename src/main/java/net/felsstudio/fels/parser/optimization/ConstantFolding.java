package net.felsstudio.fels.parser.optimization;

import net.felsstudio.fels.exceptions.OperationIsNotSupportedException;
import net.felsstudio.fels.parser.ast.*;
import net.felsstudio.fels.parser.visitors.VisitorUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Performs constant folding optimization.
 */
public class ConstantFolding extends OptimizationVisitor<Void> implements Optimizable {

    private static final Set<String> OPERATORS = VisitorUtils.operators();

    private int binaryExpressionFoldingCount;
    private int conditionalExpressionFoldingCount;
    private int unaryExpressionFoldingCount;

    private final Set<String> overloadedOperators;

    public ConstantFolding() {
        overloadedOperators = new HashSet<>();
    }

    @Override
    public Node optimize(Node node) throws IOException, InterruptedException {
        return node.accept(this, null);
    }

    @Override
    public int optimizationsCount() {
        return binaryExpressionFoldingCount
                + conditionalExpressionFoldingCount
                + unaryExpressionFoldingCount;
    }

    @Override
    public String summaryInfo() {
        if (optimizationsCount() == 0) return "";
        final StringBuilder sb = new StringBuilder();
        if (binaryExpressionFoldingCount > 0) {
            sb.append("\nBinaryExpression foldings: ").append(binaryExpressionFoldingCount);
        }
        if (conditionalExpressionFoldingCount > 0) {
            sb.append("\nConditionalExpression foldings: ").append(conditionalExpressionFoldingCount);
        }
        if (unaryExpressionFoldingCount > 0) {
            sb.append("\nUnaryExpression foldings: ").append(unaryExpressionFoldingCount);
        }
        return sb.toString();
    }

    @Override
    public Node visit(BinaryExpression s, Void t) throws IOException, InterruptedException {
        if (overloadedOperators.contains(s.operation.toString())) {
            return super.visit(s, t);
        }
        if ( (s.expr1 instanceof ValueExpression) && (s.expr2 instanceof ValueExpression) ) {
            binaryExpressionFoldingCount++;
            try {
                return new ValueExpression(s.eval());
            } catch (OperationIsNotSupportedException op) {
                binaryExpressionFoldingCount--;
            }
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(ConditionalExpression s, Void t) throws IOException, InterruptedException {
        if (overloadedOperators.contains(s.operation.getName())) {
            return super.visit(s, t);
        }
        if ( (s.expr1 instanceof ValueExpression) && (s.expr2 instanceof ValueExpression) ) {
            conditionalExpressionFoldingCount++;
            try {
                return new ValueExpression(s.eval());
            } catch (OperationIsNotSupportedException op) {
                conditionalExpressionFoldingCount--;
            }
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(UnaryExpression s, Void t) throws IOException, InterruptedException {
        if (overloadedOperators.contains(s.operation.toString())) {
            return super.visit(s, t);
        }
        if (s.expr1 instanceof ValueExpression) {
            unaryExpressionFoldingCount++;
            try {
                return new ValueExpression(s.eval());
            } catch (OperationIsNotSupportedException op) {
                unaryExpressionFoldingCount--;
            }
        }
        return super.visit(s, t);
    }

    @Override
    public Node visit(FunctionDefineStatement s, Void t) throws IOException, InterruptedException {
        if (OPERATORS.contains(s.name)) {
            overloadedOperators.add(s.name);
        }
        return super.visit(s, t);
    }
}
