package main.java.net.felsstudio.fels.lib

/**
 *
 * @author felek
 */
interface Value : Comparable<Value?> {
    fun raw(): Any?

    fun asInt(): Int

    fun asNumber(): Double

    fun asString(): String?

    fun type(): Int
}
