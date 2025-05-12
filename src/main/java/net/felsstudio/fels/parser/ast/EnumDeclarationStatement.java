package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Value;

import java.io.IOException;
import java.util.Map;

public class EnumDeclarationStatement implements Statement {
    private final String enumName;
    private final Map<String, Value> constants;

    public EnumDeclarationStatement(String enumName, Map<String, Value> constants) {
        this.enumName = enumName;
        this.constants = constants;
    }

    @Override
    public void execute() {

    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) throws IOException, InterruptedException {
        return null;
    }
}