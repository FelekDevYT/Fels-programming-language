package main.java.net.felsstudio.fels.parser.ast.interfaces

/**
 *
 * @author felek
 */
interface Node {
    fun accept(visitor: Visitor?)

    fun <R, T> accept(visitor: ResultVisitor<R, T>?, input: T): R
}
