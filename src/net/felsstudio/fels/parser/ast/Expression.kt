package net.felsstudio.fels.parser.ast

import net.felsstudio.fels.lib.Value

/**
 *
 * @author felek
 */
interface Expression : Node {
    fun eval(): Value
}
