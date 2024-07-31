package net.felsstudio.fels.Start

import net.felsstudio.fels.lib.CallStack
import net.felsstudio.fels.parser.Lexer
import net.felsstudio.fels.parser.Parser
import net.felsstudio.fels.parser.visitors.AssignValidator
import net.felsstudio.fels.parser.visitors.FunctionAdder
import net.felsstudio.fels.parser.visitors.VariablePrinter
import net.felsstudio.fels.utils.TimeMeasurement
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

object Starter {
    @JvmStatic
    @Throws(IOException::class)
    fun start(doShowVars: Boolean, doShowTokens: Boolean, doShowMe: Boolean, doShowAST: Boolean, file: String?) {
        val measurement = TimeMeasurement()
        measurement.start("Tokenize time")
        val input = String(Files.readAllBytes(Paths.get(file)), charset("UTF-8"))
        val tokens = Lexer(input).tokenize()
        measurement.stop("Tokenize time")
        if (doShowTokens) {
            for (token in tokens) {
                println(token)
            }
        }

        measurement.start("Parse time")
        val parser = Parser(tokens)
        val program = Parser(tokens).parse()
        println(program.toString())
        program.accept(FunctionAdder())
        measurement.stop("Parse time")
        if (doShowVars) {
            program.accept(VariablePrinter())
        }
        measurement.start("Execution time")
        program.accept(AssignValidator())
        program.execute()
        if (doShowMe) {
            measurement.stop("Execution time")
            println()
            println(measurement.summary(TimeUnit.MILLISECONDS, true))
        }
        if (parser.parseErrors.hasErrors()) {
            println(parser.parseErrors)
            return
        }
        val p = program
    }

    @JvmStatic
    fun handleException(thread: Thread, throwable: Throwable) {
        System.err.printf("%s in %s\n", throwable.message, thread.name)
        for (call in CallStack.calls) {
            System.err.printf("\tat %s\n", call)
        }
        throwable.printStackTrace()
    }
}
