package main.java.net.felsstudio.fels.utils;

import main.java.net.felsstudio.fels.Console;
import main.java.net.felsstudio.fels.exceptions.StoppedException;
import main.java.net.felsstudio.fels.lib.CallStack;
import main.java.net.felsstudio.fels.outputsettings.ConsoleOutputSettings;
import main.java.net.felsstudio.fels.parser.Lexer;
import main.java.net.felsstudio.fels.parser.Parser;
import main.java.net.felsstudio.fels.parser.SourceLoader;
import main.java.net.felsstudio.fels.parser.Token;
import main.java.net.felsstudio.fels.parser.ast.Statement;
import main.java.net.felsstudio.fels.parser.visitors.FunctionAdder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class Sandbox {

    public static void main(String[] args) throws IOException {
        Console.useSettings(new ConsoleOutputSettings() {
            @Override
            public File fileInstance(String path) {
                return new File("tmp/" + path);
            }
        });
        final String input = SourceLoader.readAndCloseStream(System.in);
        dumpInputArguments(input, args);
        
        final List<Token> tokens = Lexer.tokenize(input);
        final Parser parser = new Parser(tokens);
        final Statement program = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            System.out.print(parser.getParseErrors());
            return;
        }
        
        program.accept(new FunctionAdder());
        
        try {
            program.execute();
        } catch (StoppedException ex) {
            // skip
        } catch (Exception ex) {
            // ownlang call stack to stdout
            System.out.format("%s in %s%n", ex.getMessage(), Thread.currentThread().getName());
            CallStack.getCalls().forEach(call -> System.out.format("\tat %s%n", call));
            // java stack trace to stderr
            Console.handleException(Thread.currentThread(), ex);
        }
    }

    private static void dumpInputArguments(String source, String[] args) {
        System.err.println();
        System.err.println();
        System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Arrays.stream(args).forEach(System.err::println);
        System.err.println();
        System.err.println(source);
    }
}
