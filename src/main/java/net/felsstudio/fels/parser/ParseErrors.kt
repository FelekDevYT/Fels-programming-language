package main.java.net.felsstudio.fels.parser

class ParseErrors : Iterable<ParseError?> {
    private val errors: MutableList<ParseError> = ArrayList()

    fun clear() {
        errors.clear()
    }

    fun add(ex: Exception, line: Int) {
        errors.add(ParseError(line, ex))
    }

    fun hasErrors(): Boolean {
        return !errors.isEmpty()
    }

    override fun iterator(): MutableIterator<ParseError> {
        return errors.iterator()
    }

    override fun toString(): String {
        val result = StringBuilder()
        for (error in errors) {
            result.append(error).append(System.lineSeparator())
        }
        return result.toString()
    }
}