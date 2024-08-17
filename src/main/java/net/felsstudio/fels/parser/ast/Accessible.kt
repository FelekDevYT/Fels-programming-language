package main.java.net.felsstudio.fels.parser.ast

import main.java.net.felsstudio.fels.lib.Value

interface Accessible {
    fun get(): Value?

    fun set(value: Value?): Value?
}
