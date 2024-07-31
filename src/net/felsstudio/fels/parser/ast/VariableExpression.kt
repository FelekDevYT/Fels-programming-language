package net.felsstudio.fels.parser.ast

import net.felsstudio.fels.exceptions.VariableDoesNotExistsException
import net.felsstudio.fels.lib.Value
import net.felsstudio.fels.lib.Variables

/**
 *
 * @author felek
 */
class VariableExpression(@JvmField val name: String) : Expression, Accessible {
    override fun eval(): Value {
        return get()
    }

    override fun get(): Value {
        if (!Variables.isExists(name)) throw VariableDoesNotExistsException(name)
        return Variables.get(name)
    }

    override fun set(value: Value): Value {
        Variables.set(name, value)
        return value
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R? {
        return visitor.visit(this, t)
    }

    override fun toString(): String {
        return name
    }
}
