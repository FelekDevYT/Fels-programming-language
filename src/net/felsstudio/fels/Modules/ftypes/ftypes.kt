package net.felsstudio.fels.Modules.ftypes

import net.felsstudio.fels.Modules.Module
import net.felsstudio.fels.lib.*

class ftypes : Module {
    override fun init() {
        Variables.set("OBJECT", NumberValue.of(Types.OBJECT))
        Variables.set("NUMBER", NumberValue.of(Types.NUMBER))
        Variables.set("STRING", NumberValue.of(Types.STRING))
        Variables.set("ARRAY", NumberValue.of(Types.ARRAY))
        Variables.set("MAP", NumberValue.of(Types.MAP))
        Variables.set("FUNCTION", NumberValue.of(Types.FUNCTION))

        Functions.set("typeOf") { args: Array<Value> ->
            NumberValue.of(
                args[0].type()
            )
        }
        Functions.set("toString") { args: Array<Value> ->
            StringValue(
                args[0].asString()
            )
        }
        Functions.set("toNumber") { args: Array<Value> ->
            NumberValue.of(
                args[0].asNumber()
            )
        }

        Functions.set("toByte") { args: Array<Value> ->
            NumberValue.of(
                args[0].asInt().toByte().toInt()
            )
        }
        Functions.set("toShort") { args: Array<Value> ->
            NumberValue.of(
                args[0].asInt().toShort().toInt()
            )
        }
        Functions.set("toInt") { args: Array<Value> ->
            NumberValue.of(
                args[0].asInt()
            )
        }
        Functions.set("toLong") { args: Array<Value> ->
            NumberValue.of(
                args[0].asNumber().toLong()
            )
        }
        Functions.set("toFloat") { args: Array<Value> ->
            NumberValue.of(
                args[0].asNumber().toFloat()
            )
        }
        Functions.set("toDouble") { args: Array<Value> ->
            NumberValue.of(
                args[0].asNumber()
            )
        }
    }
}