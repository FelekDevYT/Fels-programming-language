package main.java.net.felsstudio.fels.parser.ast.interfaces

import main.java.net.felsstudio.fels.lib.Value

/**
 *
 * @author felek
 */
interface Expression : Node {
    fun eval(): Value?
}
