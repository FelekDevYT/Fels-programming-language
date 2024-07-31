package net.felsstudio.fels.Start;

import net.felsstudio.fels.lib.CallStack;
import net.felsstudio.fels.utils.TimeMeasurement;
import net.felsstudio.fels.parser.Lexer;
import net.felsstudio.fels.parser.Parser;
import net.felsstudio.fels.parser.Token;
import net.felsstudio.fels.parser.ast.Statement;
import net.felsstudio.fels.parser.visitors.AssignValidator;
import net.felsstudio.fels.parser.visitors.FunctionAdder;
import net.felsstudio.fels.parser.visitors.VariablePrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Starter {

    public static void start(boolean doShowVars,boolean doShowTokens,boolean doShowMe,boolean doShowAST,String file) throws IOException {
        final TimeMeasurement measurement = new TimeMeasurement();
        measurement.start("Tokenize time");
        final String input = new String( Files.readAllBytes(Paths.get(file)), "UTF-8");
        final List<Token> tokens = new Lexer(input).tokenize();
        measurement.stop("Tokenize time");
        if(doShowTokens){
            for (Token token : tokens) {
                System.out.println(token);
            }
        }

        measurement.start("Parse time");
        final Parser parser = new Parser(tokens);
        final Statement program = new Parser(tokens).parse();
        System.out.println(program.toString());
        program.accept(new FunctionAdder());
        measurement.stop("Parse time");
        if(doShowVars){
            program.accept(new VariablePrinter());
        }
        measurement.start("Execution time");
        program.accept(new AssignValidator());
        program.execute();
        if(doShowMe){
            measurement.stop("Execution time");
            System.out.println();
            System.out.println(measurement.summary(TimeUnit.MILLISECONDS, true));
        }
        if (parser.getParseErrors().hasErrors()) {
            System.out.println(parser.getParseErrors());
            return;
        }
        final Statement p;
        p = program;
    }

    public static void handleException(Thread thread, Throwable throwable) {
        System.err.printf("%s in %s\n", throwable.getMessage(), thread.getName());
        for (CallStack.CallInfo call : CallStack.getCalls()) {
            System.err.printf("\tat %s\n", call);
        }
        throwable.printStackTrace();
    }

}
