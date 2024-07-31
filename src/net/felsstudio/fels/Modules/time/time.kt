package net.felsstudio.fels.Modules.time

import net.felsstudio.fels.Modules.Module
import net.felsstudio.fels.lib.Functions
import net.felsstudio.fels.lib.NumberValue
import net.felsstudio.fels.lib.StringValue
import net.felsstudio.fels.lib.Value
import java.util.*

class time : Module{

    override fun init() {
        Functions.set("sleep") { f: Array<Value> ->
            try {
                Thread.sleep(f[0].asNumber().toLong())
                return@set NumberValue.of(1)
            } catch (ie: InterruptedException) {
                return@set NumberValue.of(0)
            }
        }
        Functions.set("getTime") { f: Array<Value?>? ->
            StringValue(
                Date().toString()
            )
        }
        Functions.set("getMillis") { f: Array<Value?>? ->
            NumberValue.of(
                Date().time
            )
        }
    }

}