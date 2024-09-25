package main.java.net.felsstudio.fels.utils

import main.java.net.felsstudio.fels.lib.Information

object Preprocessor {
    fun preprocess(code: String): String {
        val macros: MutableMap<String, String> = HashMap()
        macros["Fels Version"] = "\"" + Information.FELS_VERSION + "\""
        val processedCode = StringBuilder()
        val lines = code.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            if (line.trim { it <= ' ' }.startsWith("#define")) {
                val parts = line.trim { it <= ' ' }.split("\\s+".toRegex(), limit = 3).toTypedArray()
                if (parts.size == 3) {
                    macros[parts[1]] = parts[2]
                }
            } else if (line.trim { it <= ' ' }.startsWith("#include")) {
                val parts = line.trim { it <= ' ' }.split("\\s+".toRegex(), limit = 2).toTypedArray()
                if (parts.size == 2) {
                    processedCode.append("import ").append(parts[1]).append("\n")
                }
            } else {
                var s = "";
                for ((key, value) in macros) {
                    s = line.replace(key, value)
                }
                processedCode.append(s).append("\n")
            }
        }
        return processedCode.toString()
    }
}