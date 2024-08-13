package main.java.net.felsstudio.fels.Start

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Saver {
    fun save(v: vals) {
        File("saver.fsf")
        try {
            BufferedWriter(FileWriter("saver.fsf")).use { bw ->
                bw.write(v.doShowVars.toString() + "\n")
                bw.write(v.doShowTokens.toString() + "\n")
                bw.write(v.doShowMe.toString() + "\n")
                bw.write(v.doShowAst.toString() + "\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun read(): Array<String> {
        val s = Files.readAllLines(Paths.get("saver.fsf")).toTypedArray<String>()
        return s
    }
}
