package net.felsstudio.fels.parser.ast

import net.felsstudio.fels.lib.*
import net.felsstudio.fels.lib.Function

/**
 *
 * @author felek
 */
class ValueExpression : Expression {
    @JvmField
    val value: Value

    constructor(value: Number?) {
        this.value = NumberValue.of(value)
    }

    constructor(value: String?) {
        this.value = StringValue(value)
    }

    constructor(value: Function?) {
        this.value = FunctionValue(value)
    }

    constructor(value: Value) {
        this.value = value
    }

    override fun eval(): Value {
        return value
    }

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, t: T): R? {
        return visitor.visit(this, t)
    }

    override fun toString(): String {
        if (value.type() == Types.STRING) {
            return "\"" + value.asString() + "\""
        }
        return value.toString()
    }
}
