package net.felsstudio.fels.lib

import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

object CallStack {
    @get:Synchronized
    val calls: Deque<CallInfo> = ConcurrentLinkedDeque()

    @Synchronized
    fun clear() {
        calls.clear()
    }

    @JvmStatic
    @Synchronized
    fun enter(name: String?, function: Function) {
        calls.push(CallInfo(name, function))
    }

    @JvmStatic
    @Synchronized
    fun exit() {
        calls.pop()
    }

    class CallInfo(var name: String?, var function: Function) {
        override fun toString(): String {
            return String.format("%s: %s", name, function.toString().trim { it <= ' ' })
        }
    }
}
