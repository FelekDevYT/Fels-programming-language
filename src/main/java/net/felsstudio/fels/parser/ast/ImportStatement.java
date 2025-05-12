package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.exceptions.ParseException;
import net.felsstudio.fels.parser.Lexer;
import net.felsstudio.fels.parser.Parser;
import net.felsstudio.fels.parser.SourceLoader;
import net.felsstudio.fels.parser.Token;
import net.felsstudio.fels.parser.visitors.FunctionAdder;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public final class ImportStatement extends InterruptableNode implements Statement {

    public final Expression expression;
    
    public ImportStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        super.interruptionCheck();
        try {
            final Statement program = loadProgram(expression.eval().asString());
            if (program != null) {
                program.accept(new FunctionAdder());
                program.execute();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Statement loadProgram(String path) throws IOException {
        final String input = SourceLoader.readSource(path);
        final List<Token> tokens = Lexer.tokenize(input);
        final Parser parser = new Parser(tokens);
        final Statement program = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            throw new ParseException(parser.getParseErrors().toString());
        }
        return program;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) throws IOException, InterruptedException {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "include " + expression;
    }
}
