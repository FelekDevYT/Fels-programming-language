package main.java.net.felsstudio.fels.parser

/**
 *
 * @author felek
 */
class Token(@JvmField val type: TokenType, @JvmField val text: String, @JvmField val row: Int, val col: Int) {
    fun position(): String {
        return "[$row $col]"
    }

    override fun toString(): String {
        return type.name + " " + position() + " " + text
    }
}
