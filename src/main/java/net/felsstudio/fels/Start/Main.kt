package main.java.net.felsstudio.fels.Start

import main.java.net.felsstudio.fels.lib.Information
import java.io.*
import java.util.*
import kotlin.system.exitProcess

/**
 * @author felek
 */
class vals {
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

        println("=========================================================================================")
        println("                    WELCOME IN FELS                       ")
        println("setAS <DOSHOWVARS> <DOSHOWTOKENS> <DOSHOWME> <SOSHOWAST> - set auto start setting")
        println("debugger <PROJECT_NAME> - open debug setting of <PROJECT_NAME> project")
        println("run <NAME> - run you project")
        println("new <NAME> <VERSION> <AUTHOR> - create new project")
        println("setArgs <DOSHOWVARS> <DOSHOWTOKENS> <DOSHOWME> <SOSHOWAST> - set args")
        println("help - about of FELS")
        println("version - version of FELS")
        println("cls - clear console")
        println("exit - exit from program")
        println("=========================================================================================")

        val scan = Scanner(System.`in`)

        val v = vals()

        if(File("as.fsf").exists()){
            BufferedReader(FileReader(File("as.fsf"))).use { br ->
                v.doShowVars = br.readLine().toString().toBoolean()
                v.doShowTokens = br.readLine().toString().toBoolean()
                v.doShowMe = br.readLine().toString().toBoolean()
                v.doShowAst = br.readLine().toString().toBoolean()
            }
            println("Settings up automatically from as.fsf file")
        }

        while (true) {
            print("FELS v${Information.FELS_VERSION}>>>")
            val line = scan.nextLine().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            when (line[0]) {
                "debugger" ->{
                    println("This feature in development")
                    TODO("CREATE THIS DEBUGGER!!!")
                }

                "setAS" -> {
                    try {
                        File("as.fsf")

                        BufferedWriter(FileWriter("as.fsf")).use { bw ->
                            bw.write(line[1] + "\n")
                            bw.write(line[2] + "\n")
                            bw.write(line[3] + "\n")
                            bw.write(line[4] + "\n")
                        }
                    } catch (e: Exception) {
                        System.err.println("FELS [ERROR] " + e.message)
                    }
                    println("FELS [ SUCCESSFUL ] setting up auto setting")
                }

                "run" ->{
                    try {
                        Starter.start(v.doShowVars, v.doShowTokens, v.doShowMe, v.doShowAst, line[1]+"\\src\\main.fels")
                    } catch (e: Exception) {
                        System.err.println("FELS [ERROR] " + e.message)
                    }
                }

                "setArgs" -> {
                    if (line[1] == ":f") {
                        try {
                            val lines = Saver().read()
                            v.doShowVars = lines[0].toBoolean()
                            v.doShowTokens = lines[1].toBoolean()
                            v.doShowMe = lines[2].toBoolean()
                            v.doShowAst = lines[3].toBoolean()
                            println("FELS [ SUCCESSFUL ] setting up setting")
                            continue
                        } catch (exc: IOException) {
                            System.err.println("FELS [ERROR] " + exc.message)
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
                    println("FELS [ SUCCESSFUL ] setting up setting")
                }

                "help" -> {
                    println("FELS programming language V.${Information.FELS_VERSION}")
                    println("Copyright ${Information.FELS_AUTHOR}")
                    println("FELS console V.0.1")
                }

                "version" -> println("FELS programming language V.${Information.FELS_VERSION}")
                "exit" -> {
                    println("FELS [ SUCCESSFUL ] exiting from console")
                    exitProcess(0)
                }

                "save" -> {
                    try {
                        val s = Saver()
                        s.save(v)
                        println("FELS [ SUCCESSFUL ] run setting save to saver.fsf")
                    } catch (exc: Exception) {
                        exc.printStackTrace()
                    }
                }

                "cls" -> {
                    println("\u001b[H\u001b[2J")
                    System.out.flush()
                }

                "new" ->{
                    var p = Project(line[1],line[2],line[3])
                    p.createProject()
                    println("FELS [ SUCCESSFUL ] setting up project")
                }
            }
        }
    }
}
