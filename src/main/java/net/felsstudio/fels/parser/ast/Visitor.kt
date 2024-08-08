package main.java.net.felsstudio.fels.parser.ast;

/**
 *
 * @author felek
 */
public interface Visitor {
    
    void visit(ArrayExpression s);
    void visit(AssignmentExpression s);
    void visit(BinaryExpression s);
    void visit(BlockStatement s);
    void visit(BreakStatement s);
    void visit(ConditionalExpression s);
    void visit(ContainerAccessExpression s);
    void visit(ContinueStatement s);
    void visit(DoWhileStatement s);
    void visit(DestructuringAssignmentStatement s);
    void visit(ForStatement s);
    void visit(ForeachArrayStatement s);
    void visit(ForeachMapStatement s);
    void visit(FunctionDefineStatement s);
    void visit(FunctionReferenceExpression e);
    void visit(ExprStatement s);
    void visit(FunctionalExpression s);
    void visit(IfStatement s);
    void visit(ImportStatement s);
    void visit(MapExpression s);
    void visit(MatchExpression s);
    void visit(PrintStatement s);
    void visit(PrintlnStatement s);
    void visit(ReturnStatement s);
    void visit(TernaryExpression s);
    void visit(UnaryExpression s);
    void visit(ValueExpression s);
    void visit(VariableExpression s);
    void visit(WhileStatement st);
    void visit(UsingStatement st);
    void visit(PanicStatement st);
    void visit(ClassDeclarationStatement st);
    void visit(ObjectCreationExpression st);
}
