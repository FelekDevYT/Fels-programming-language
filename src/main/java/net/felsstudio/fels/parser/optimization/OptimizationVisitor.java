package net.felsstudio.fels.parser.optimization;

import net.felsstudio.fels.lib.Types;
import net.felsstudio.fels.lib.UserDefinedFunction;
import net.felsstudio.fels.lib.Value;

import net.felsstudio.fels.parser.ast.*;
import net.felsstudio.fels.parser.visitors.VisitorUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OptimizationVisitor<T> implements ResultVisitor<Node, T> {

    @Override
    public Node visit(ArrayExpression s, T t) throws IOException, InterruptedException {
        final List<Expression> elements = new ArrayList<>(s.elements.size());
        boolean changed = false;
        for (Expression expression : s.elements) {
            final Node node = expression.accept(this, t);
            if (node != expression) {
                changed = true;
            }
            elements.add((Expression) node);
        }
        if (changed) {
            return new ArrayExpression(elements);
        }
        return s;
    }

    @Override
    public Node visit(AssignmentExpression s, T t) throws IOException, InterruptedException {
        final Node exprNode = s.expression.accept(this, t);
        final Node targetNode = s.target.accept(this, t);
        if ( (exprNode != s.expression || targetNode != s.target) && (targetNode instanceof Accessible) ) {
            return new AssignmentExpression(s.operation, (Accessible) targetNode, (Expression) exprNode);
        }
        return s;
    }

    @Override
    public Node visit(BinaryExpression s, T t) throws IOException, InterruptedException {
        final Node expr1 = s.expr1.accept(this, t);
        final Node expr2 = s.expr2.accept(this, t);
        if (expr1 != s.expr1 || expr2 != s.expr2) {
            return new BinaryExpression(s.operation, (Expression) expr1, (Expression) expr2);
        }
        return s;
    }

    @Override
    public Node visit(BlockStatement s, T t) throws IOException, InterruptedException {
        boolean changed = false;
        final BlockStatement result = new BlockStatement();
        for (Statement statement : s.statements) {
            final Node node = statement.accept(this, t);
            if (node != statement) {
                changed = true;
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
        return s;
    }

    @Override
    public Node visit(BreakStatement s, T t) {
        return s;
    }

    @Override
    public Node visit(ClassDeclarationStatement s, T t) {
        return s;
    }
    
    @Override
    public Node visit(ConditionalExpression s, T t) throws IOException, InterruptedException {
        final Node expr1 = s.expr1.accept(this, t);
        final Node expr2 = s.expr2.accept(this, t);
        if (expr1 != s.expr1 || expr2 != s.expr2) {
            return new ConditionalExpression(s.operation, (Expression) expr1, (Expression) expr2);
        }
        return s;
    }

    @Override
    public Node visit(ContainerAccessExpression s, T t) throws IOException, InterruptedException {
        final Node root = s.root.accept(this, t);
        boolean changed = (root != s.root);

        final List<Expression> indices = new ArrayList<>(s.indices.size());
        for (Expression expression : s.indices) {
            final Node node = expression.accept(this, t);
            if (node != expression) {
                changed = true;
            }
            indices.add((Expression) node);
        }
        if (changed) {
            return new ContainerAccessExpression((Expression) root, indices);
        }
        return s;
    }

    @Override
    public Node visit(ContinueStatement s, T t) {
        return s;
    }

    @Override
    public Node visit(DoWhileStatement s, T t) throws IOException, InterruptedException {
        final Node condition = s.condition.accept(this, t);
        final Node statement = s.statement.accept(this, t);
        if (condition != s.condition || statement != s.statement) {
            return new DoWhileStatement((Expression) condition, consumeStatement(statement));
        }
        return s;
    }

    @Override
    public Node visit(DestructuringAssignmentStatement s, T t) throws IOException, InterruptedException {
        final Node expr = s.containerExpression.accept(this, t);
        if (expr != s.containerExpression) {
            return new DestructuringAssignmentStatement(s.variables, (Expression) expr);
        }
        return s;
    }

    @Override
    public Node visit(ForStatement s, T t) throws IOException, InterruptedException {
        final Node initialization = s.initialization.accept(this, t);
        final Node termination = s.termination.accept(this, t);
        final Node increment = s.increment.accept(this, t);
        final Node statement = s.statement.accept(this, t);
        if (initialization != s.initialization || termination != s.termination
                || increment != s.increment || statement != s.statement) {
            return new ForStatement(consumeStatement(initialization),
                    (Expression) termination, consumeStatement(increment), consumeStatement(statement));
        }
        return s;
    }

    @Override
    public Node visit(ForeachArrayStatement s, T t) throws IOException, InterruptedException {
        final Node container = s.container.accept(this, t);
        final Node body = s.body.accept(this, t);
        if (container != s.container || body != s.body) {
            return new ForeachArrayStatement(s.variable, (Expression) container, consumeStatement(body));
        }
        return s;
    }

    @Override
    public Node visit(ForeachMapStatement s, T t) throws IOException, InterruptedException {
        final Node container = s.container.accept(this, t);
        final Node body = s.body.accept(this, t);
        if (container != s.container || body != s.body) {
            return new ForeachMapStatement(s.key, s.value, (Expression) container, consumeStatement(body));
        }
        return s;
    }

    @Override
    public Node visit(FunctionDefineStatement s, T t) throws IOException, InterruptedException {
        final Arguments newArgs = new Arguments();
        boolean changed = visit(s.arguments, newArgs, t);

        final Node body = s.body.accept(this, t);
        if (changed || body != s.body) {
            return new FunctionDefineStatement(s.name, newArgs, consumeStatement(body));
        }
        return s;
    }

    @Override
    public Node visit(FunctionReferenceExpression s, T t) {
        return s;
    }

    @Override
    public Node visit(ExprStatement s, T t) throws IOException, InterruptedException {
        final Node expr = s.expr.accept(this, t);
        if (expr != s.expr) {
            return new ExprStatement((Expression) expr);
        }
        return s;
    }

    @Override
    public Node visit(FunctionalExpression s, T t) throws IOException, InterruptedException {
        final Node functionExpr = s.functionExpr.accept(this, t);
        final FunctionalExpression result = new FunctionalExpression((Expression) functionExpr);
        boolean changed = functionExpr != s.functionExpr;
        for (Expression argument : s.arguments) {
            final Node expr = argument.accept(this, t);
            if (expr != argument) {
                changed = true;
            }
            result.addArgument((Expression) expr);
        }
        if (changed) {
            return result;
        }
        return s;
    }

    @Override
    public Node visit(IfStatement s, T t) throws IOException, InterruptedException {
        final Node expression = s.expression.accept(this, t);
        final Node ifStatement = s.ifStatement.accept(this, t);
        final Node elseStatement;
        if (s.elseStatement != null) {
            elseStatement = s.elseStatement.accept(this, t);
        } else {
            elseStatement = null;
        }
        if (expression != s.expression || ifStatement != s.ifStatement || elseStatement != s.elseStatement) {
            return new IfStatement((Expression) expression, consumeStatement(ifStatement),
                    (elseStatement == null ? null : consumeStatement(elseStatement)) );
        }
        return s;
    }

    @Override
    public Node visit(ImportStatement s, T t) throws IOException, InterruptedException {
        final Node expression = s.expression.accept(this, t);
        if (expression != s.expression) {
            return new ImportStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(MapExpression s, T t) throws IOException, InterruptedException {
        final Map<Expression, Expression> elements = new HashMap<>(s.elements.size());
        boolean changed = false;
        for (Map.Entry<Expression, Expression> entry : s.elements.entrySet()) {
            final Node key = entry.getKey().accept(this, t);
            final Node value = entry.getValue().accept(this, t);
            if (key != entry.getKey() || value != entry.getValue()) {
                changed = true;
            }
            elements.put((Expression) key, (Expression) value);
        }
        if (changed) {
            return new MapExpression(elements);
        }
        return s;
    }

    @Override
    public Node visit(MatchExpression s, T t) throws IOException, InterruptedException {
        final Node expression = s.expression.accept(this, t);
        boolean changed = expression != s.expression;
        final List<MatchExpression.Pattern> patterns = new ArrayList<>(s.patterns.size());
        for (MatchExpression.Pattern pattern : s.patterns) {
            if (pattern instanceof MatchExpression.VariablePattern) {
                final String variable = ((MatchExpression.VariablePattern) pattern).variable;
                final VariableExpression expr = new VariableExpression(variable);
                final Node node = expr.accept(this, t);
                if ((node != expr) && VisitorUtils.isValue(node)) {
                    changed = true;
                    final Value value = ((ValueExpression) node).value;
                    final Expression optCondition = pattern.optCondition;
                    final Statement result = pattern.result;
                    pattern = new MatchExpression.ConstantPattern(value);
                    pattern.optCondition = optCondition;
                    pattern.result = result;
                }
            }
            
            if (pattern instanceof MatchExpression.TuplePattern tuple) {
                final List<Expression> newValues = new ArrayList<>(tuple.values.size());
                boolean valuesChanged = false;
                for (Expression value : tuple.values) {
                    final Node node = value.accept(this, t);
                    if (node != value) {
                        valuesChanged = true;
                        value = (Expression) node;
                    }
                    newValues.add(value);
                }
                if (valuesChanged) {
                    changed = true;
                    final Expression optCondition = pattern.optCondition;
                    final Statement result = pattern.result;
                    pattern = new MatchExpression.TuplePattern(newValues);
                    pattern.optCondition = optCondition;
                    pattern.result = result;
                }
            }

            final Node patternResult = pattern.result.accept(this, t);
            if (patternResult != pattern.result) {
                changed = true;
                pattern.result = consumeStatement(patternResult);
            }

            if (pattern.optCondition != null) {
                Node optCond = pattern.optCondition.accept(this, t);
                if (optCond != pattern.optCondition) {
                    changed = true;
                    pattern.optCondition = (Expression) optCond;
                }
            }

            patterns.add(pattern);
        }
        if (changed) {
            return new MatchExpression((Expression) expression, patterns);
        }
        return s;
    }
    
    @Override
    public Node visit(ObjectCreationExpression s, T t) throws IOException, InterruptedException {
        final List<Expression> args = new ArrayList<>();
        boolean changed = false;
        for (Expression argument : s.constructorArguments) {
            final Node expr = argument.accept(this, t);
            if (expr != argument) {
                changed = true;
            }
            args.add((Expression) expr);
        }
        
        if (changed) {
            return new ObjectCreationExpression(s.className, args);
        }
        return s;
    }

    @Override
    public Node visit(PrintStatement s, T t) throws IOException, InterruptedException {
        final Node expression = s.expression.accept(this, t);
        if (expression != s.expression) {
            return new PrintStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(PrintlnStatement s, T t) throws IOException, InterruptedException {
        final Node expression = s.expression.accept(this, t);
        if (expression != s.expression) {
            return new PrintlnStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(ReturnStatement s, T t) throws IOException, InterruptedException {
        final Node expression = s.expression.accept(this, t);
        if (expression != s.expression) {
            return new ReturnStatement((Expression) expression);
        }
        return s;
    }

    @Override
    public Node visit(TernaryExpression s, T t) throws IOException, InterruptedException {
        final Node condition = s.condition.accept(this, t);
        final Node trueExpr = s.trueExpr.accept(this, t);
        final Node falseExpr = s.falseExpr.accept(this, t);
        if (condition != s.condition || trueExpr != s.trueExpr || falseExpr != s.falseExpr) {
            return new TernaryExpression((Expression) condition, (Expression) trueExpr, (Expression) falseExpr);
        }
        return s;
    }

    @Override
    public Node visit(UnaryExpression s, T t) throws IOException, InterruptedException {
        final Node expr1 = s.expr1.accept(this, t);
        if (expr1 != s.expr1) {
            return new UnaryExpression(s.operation, (Expression) expr1);
        }
        return s;
    }

    @Override
    public Node visit(ValueExpression s, T t) throws IOException, InterruptedException {
        if ( (s.value.type() == Types.FUNCTION) && (s.value.raw() instanceof UserDefinedFunction function) ) {
            final UserDefinedFunction accepted = visit(function, t);
            if (accepted != function) {
                return new ValueExpression(accepted);
            }
        }
        return s;
    }

    @Override
    public Node visit(VariableExpression s, T t) {
        return s;
    }

    @Override
    public Node visit(WhileStatement s, T t) throws IOException, InterruptedException {
        final Node condition = s.condition.accept(this, t);
        final Node statement = s.statement.accept(this, t);
        if (condition != s.condition || statement != s.statement) {
            return new WhileStatement((Expression) condition, consumeStatement(statement));
        }
        return s;
    }

    @Override
    public Node visit(UsingStatement s, T t) throws IOException, InterruptedException {
        final Node expression = s.expression.accept(this, t);
        if (expression != s.expression) {
            return new UsingStatement((Expression) expression);
        }
        return s;
    }

    public UserDefinedFunction visit(UserDefinedFunction s, T t) throws IOException, InterruptedException {
        final Arguments newArgs = new Arguments();
        boolean changed = visit(s.arguments, newArgs, t);

        final Node body = s.body.accept(this, t);
        if (changed || body != s.body) {
            return new UserDefinedFunction(newArgs, consumeStatement(body));
        }
        return s;
    }



    protected boolean visit(final Arguments in, final Arguments out, T t) throws IOException, InterruptedException {
        boolean changed = false;
        for (Argument argument : in) {
            final Expression valueExpr = argument.getValueExpr();
            if (valueExpr == null) {
                out.addRequired(argument.getName());
            } else {
                final Node expr = valueExpr.accept(this, t);
                if (expr != valueExpr) {
                    changed = true;
                }
                out.addOptional(argument.getName(), (Expression) expr);
            }
        }
        return changed;
    }

    protected Statement consumeStatement(Node node) {
        if (node instanceof Statement) {
            return (Statement) node;
        }
        return new ExprStatement((Expression) node);
    }
}
