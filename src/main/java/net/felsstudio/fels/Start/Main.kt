package main.java.net.felsstudio.fels.Start

import java.io.IOException
import java.util.*

/**
 * @author felek
 */
internal class vals {
    @JvmField
    var doShowVars: Boolean = false
    @JvmField
    var doShowTokens: Boolean = false
    @JvmField
    var doShowMe: Boolean = false
    @JvmField
    var doShowAst: Boolean = false
}

internal object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        /*if (args.size == 0) {
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
        }*/

        println("==========================================================")
        println("                    WELCOME IN FELS                       ")
        println("-run - run you script")
        println("-setArgs - set args")
        println("-help - about of FELS")
        println("-version - version of FELS")
        println("-cls - clear console")
        println("-exit - exit from program")
        println("==========================================================")

        val scan = Scanner(System.`in`)

        val v = vals()

        while (true) {
            val line = scan.nextLine().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            when (line[0]) {
                "-setArgs" -> {
                    if (line[1] == ":f") {
                        try {
                            val lines = Saver().read()
                            v.doShowVars = lines[0].toBoolean()
                            v.doShowTokens = lines[1].toBoolean()
                            v.doShowMe = lines[2].toBoolean()
                            v.doShowAst = lines[3].toBoolean()
                            continue
                        } catch (exc: IOException) {
                            exc.printStackTrace()
                        }
                    }
                    try {
                        v.doShowVars = line[1].toBoolean()
                        v.doShowTokens = line[2].toBoolean()
                        v.doShowMe = line[3].toBoolean()
                        v.doShowAst = line[4].toBoolean()
                    } catch (e: Exception) {
                        System.err.println("FELS [ERROR] " + e.message)
                    }
                }

                "-run" -> {
                    try {
                        Starter.start(v.doShowVars, v.doShowTokens, v.doShowMe, v.doShowAst, line[1])
                    } catch (e: Exception) {
                        System.err.println("FELS [ERROR] " + e.message)
                    }
                }

                "-help" -> {
                    println("FELS programming language V.\${Information.FELS_VERSION}")
                    println("Copyright \${Information.FELS_AUTHOR}")
                    println("FELS console V.0.1")
                }

                "-version" -> println("FELS programming language V.\${Information.FELS_VERSION}")
                "-exit" -> {
                    System.exit(0)
                }

                "-save" -> {
                    try {
                        val s = Saver()
                        s.save(v)
                    } catch (exc: Exception) {
                        exc.printStackTrace()
                    }
                }

                "-cls" -> {
                    print("\u001b[H\u001b[2J")
                    System.out.flush()
                }
            }
        }
    }
}
