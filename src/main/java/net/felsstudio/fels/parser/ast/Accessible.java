package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Value;

import java.io.IOException;

public interface Accessible extends net.felsstudio.fels.parser.ast.Node {

    Value get() throws IOException, InterruptedException;
    
    Value set(Value value) throws IOException, InterruptedException;
}
