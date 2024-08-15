package main.java.net.felsstudio.fels.Start

import java.io.File
import java.io.IOException
import java.util.*

object Debugger {
    @Throws(IOException::class)
    fun debug(p: Project, v: vals) {
        val s = Scanner(System.`in`)

        while (true) {
            val line = s.nextLine().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            when (line[0]) {
                "run" -> {
                    Starter.start(v.doShowVars, v.doShowTokens, v.doShowMe, v.doShowAst, p.name + "\\src\\main.fels")
                }

                "exit" -> {
                    break
                }

                "createFile" ->{
                    p.createFile(line[1])
                }

                "createDir" ->{
                    p.createDir(line[1])
                }

                "dir" ->{
                    var dp = File(p.name);
                    var content = dp.list()
                    for(line in content){
                        println(line)
                    }
                }

                "saveConf" ->{
                    p.saveConf()
                }

                "setName" -> {
                    p.name = line[1]
                }

                "getName" -> {
                    println(p.name)
                }

                "setAuthor" ->{
                    p.creator = line[1]
                }

                "getAuthor" ->{
                    println(p.creator)
                }

                "setVersion" ->{
                    p.version = line[1]
                }

                "getVersion" ->{
                    println(p.version)
                }
            }
        }
    }
}
