package net.felsstudio.fels.parser.ast

import net.felsstudio.fels.lib.Value

interface Accessible {
    fun get(): Value

    fun set(value: Value): Value
}
