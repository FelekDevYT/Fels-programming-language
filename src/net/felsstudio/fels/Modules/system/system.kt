package net.felsstudio.fels.Modules.system

import net.felsstudio.fels.Modules.Module
import net.felsstudio.fels.lib.*

class system : Module {

    override fun init() {
        Functions.set("currentTimeMillis") { f: Array<Value?>? ->
            NumberValue.of(
                System.currentTimeMillis()
            )
        }

        Functions.set("nanoTime") { f: Array<Value?>? ->
            NumberValue.of(
                System.nanoTime()
            )
        }

        Functions.set("getUsedMemory") { f: Array<Value?>? ->
            NumberValue.of(
                Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
            )
        }

        Functions.set("getTotalMemory") { f: Array<Value?>? ->
            NumberValue.of(
                Runtime.getRuntime().totalMemory()
            )
        }

        Functions.set("getMaxMemory") { f: Array<Value?>? ->
            NumberValue.of(
                Runtime.getRuntime().maxMemory()
            )
        }

        Functions.set("getFreeMemory") { f: Array<Value?>? ->
            NumberValue.of(
                Runtime.getRuntime().freeMemory()
            )
        }

        Functions.set("availableProcessors") { f: Array<Value?>? ->
            NumberValue.of(
                Runtime.getRuntime().availableProcessors()
            )
        }

        Functions.set("getProperty") { f: Array<Value> ->
            when (f[0].asString()) {
                "fels.version" -> return@set StringValue(Information.FELS_VERSION)
                "fels.creator" -> return@set StringValue(Information.FELS_AUTHOR)
                "date" -> return@set StringValue(Information.DATE)
            }
            NumberValue.of(-1)
        }
    }

}