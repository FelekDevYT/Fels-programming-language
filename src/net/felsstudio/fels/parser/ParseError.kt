package net.felsstudio.fels.parser

class ParseError(val line: Int, val exception: Exception) {
    override fun toString(): String {
        return "ParseError on line " + line + ": " + exception.message
    }
}