package main.java.net.felsstudio.fels.Start

import java.io.IOException

/**
 * @author felek
 */
object Main {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size == 0) {
            Starter.start(false, true, true, false, "prog.fels")
        } else if (args.size == 1) {
            Starter.start(false, false, true, false, args[0])
        } else if (args.size == 2) {
            Starter.start(args[0].toBoolean(), false, true, false, args[1])
        } else if (args.size == 3) {
            Starter.start(args[0].toBoolean(), args[1].toBoolean(), true, false, args[2])
        } else if (args.size == 4) {
            Starter.start(args[0].toBoolean(), args[1].toBoolean(), args[2].toBoolean(), false, args[3])
        } else if (args.size == 5) {
            Starter.start(args[0].toBoolean(), args[1].toBoolean(), args[2].toBoolean(), false, args[4])
        } else if (args.size == 6) {
            Starter.start(args[0].toBoolean(), args[1].toBoolean(), args[2].toBoolean(), args[4].toBoolean(), args[5])
        }
    }
}
