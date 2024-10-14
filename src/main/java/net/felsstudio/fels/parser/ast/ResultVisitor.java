package main.java.net.felsstudio.fels.parser.ast;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 * @param <R> the result type
 * @param <T> the type if the input
 */
public interface ResultVisitor<R, T> {
    
    R visit(ArrayExpression s, T t) throws IOException, InterruptedException;
    R visit(AssignmentExpression s, T t) throws IOException, InterruptedException;
    R visit(BinaryExpression s, T t) throws IOException, InterruptedException;
    R visit(BlockStatement s, T t) throws IOException, InterruptedException;
    R visit(BreakStatement s, T t);
    R visit(ClassDeclarationStatement s, T t) throws IOException, InterruptedException;
    R visit(ConditionalExpression s, T t) throws IOException, InterruptedException;
    R visit(ContainerAccessExpression s, T t) throws IOException, InterruptedException;
    R visit(ContinueStatement s, T t);
    R visit(DoWhileStatement s, T t) throws IOException, InterruptedException;
    R visit(DestructuringAssignmentStatement s, T t) throws IOException, InterruptedException;
    R visit(ForStatement s, T t) throws IOException, InterruptedException;
    R visit(ForeachArrayStatement s, T t) throws IOException, InterruptedException;
    R visit(ForeachMapStatement s, T t) throws IOException, InterruptedException;
    R visit(FunctionDefineStatement s, T t) throws IOException, InterruptedException;
    R visit(FunctionReferenceExpression s, T t);
    R visit(ExprStatement s, T t) throws IOException, InterruptedException;
    R visit(FunctionalExpression s, T t) throws IOException, InterruptedException;
    R visit(IfStatement s, T t) throws IOException, InterruptedException;
    R visit(ImportStatement s, T t) throws IOException, InterruptedException;
    R visit(MapExpression s, T t) throws IOException, InterruptedException;
    R visit(MatchExpression s, T t) throws IOException, InterruptedException;
    R visit(ObjectCreationExpression s, T t) throws IOException, InterruptedException;
    R visit(PrintStatement s, T t) throws IOException, InterruptedException;
    R visit(PrintlnStatement s, T t) throws IOException, InterruptedException;
    R visit(ReturnStatement s, T t) throws IOException, InterruptedException;
    R visit(TernaryExpression s, T t) throws IOException, InterruptedException;
    R visit(UnaryExpression s, T t) throws IOException, InterruptedException;
    R visit(ValueExpression s, T t) throws IOException, InterruptedException;
    R visit(VariableExpression s, T t);
    R visit(WhileStatement s, T t) throws IOException, InterruptedException;
    R visit(UsingStatement s, T t) throws IOException, InterruptedException;
}
