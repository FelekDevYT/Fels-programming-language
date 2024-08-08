package main.java.net.felsstudio.fels.parser

import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

object SourceLoader {
    @JvmStatic
    @Throws(IOException::class)
    fun readSource(name: String): String {
        var `is` = SourceLoader::class.java.getResourceAsStream("/$name")
        if (`is` != null) return readAndCloseStream(`is`)

        `is` = FileInputStream(name)
        return readAndCloseStream(`is`)
    }

    @Throws(IOException::class)
    fun readAndCloseStream(`is`: InputStream): String {
        val result = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var read: Int
        while ((`is`.read(buffer).also { read = it }) != -1) {
            result.write(buffer, 0, read)
        }
        `is`.close()
        return result.toString("UTF-8")
    }
}