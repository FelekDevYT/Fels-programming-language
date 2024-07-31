package net.felsstudio.fels.Modules.random

import net.felsstudio.fels.Modules.Module
import net.felsstudio.fels.lib.Functions
import net.felsstudio.fels.lib.NumberValue
import net.felsstudio.fels.lib.Value

public class random : Module{

    private fun random(min: Int, max: Int): Int {
        var max = max
        max -= min
        return (Math.random() * ++max).toInt() + min
    }

    override fun init() {
        Functions.set("rand") { f: Array<Value> ->
            var max = 100
            max = f[0].asNumber().toInt()
            NumberValue.of(random(0, max))
        }
        Functions.set("random") { f: Array<Value> ->
            var max = 0
            var min = 0
            max = f[0].asNumber().toInt()
            min = f[1].asNumber().toInt()
            NumberValue.of(random(min, max))
        }
    }

}