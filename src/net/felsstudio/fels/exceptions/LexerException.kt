package net.felsstudio.fels.exceptions

/**
 *
 * @author felek
 */
class LexerException : RuntimeException {
    constructor(message: String?) : super(message)

    constructor(row: Int, col: Int, message: String) : super("[$row:$col] $message")
}