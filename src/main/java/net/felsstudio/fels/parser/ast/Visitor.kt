package main.java.net.felsstudio.fels.parser.ast

/**
 *
 * @author felek
 */
interface Visitor {
    fun visit(s: ArrayExpression?)
    fun visit(s: AssignmentExpression?)
    fun visit(s: BinaryExpression?)
    fun visit(s: BlockStatement?)
    fun visit(s: BreakStatement?)
    fun visit(s: ConditionalExpression?)
    fun visit(s: ContainerAccessExpression?)
    fun visit(s: ContinueStatement?)
    fun visit(s: DoWhileStatement?)
    fun visit(s: DestructuringAssignmentStatement?)
    fun visit(s: ForStatement?)
    fun visit(s: ForeachArrayStatement?)
    fun visit(s: ForeachMapStatement?)
    fun visit(s: FunctionDefineStatement?)
    fun visit(e: FunctionReferenceExpression?)
    fun visit(s: ExprStatement?)
    fun visit(s: FunctionalExpression?)
    fun visit(s: IfStatement?)
    fun visit(s: ImportStatement?)
    fun visit(s: MapExpression?)
    fun visit(s: MatchExpression?)
    fun visit(s: PrintStatement?)
    fun visit(s: PrintlnStatement?)
    fun visit(s: ReturnStatement?)
    fun visit(s: TernaryExpression?)
    fun visit(s: UnaryExpression?)
    fun visit(s: ValueExpression?)
    fun visit(s: VariableExpression?)
    fun visit(st: WhileStatement?)
    fun visit(st: UsingStatement?)
    fun visit(st: PanicStatement?)
    fun visit(st: ClassDeclarationStatement?)
    fun visit(st: ObjectCreationExpression?)
}
