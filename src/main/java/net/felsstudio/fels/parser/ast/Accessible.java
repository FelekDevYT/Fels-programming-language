package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.Value;

import java.io.IOException;

public interface Accessible extends main.java.net.felsstudio.fels.parser.ast.Node {

    Value get() throws IOException, InterruptedException;
    
    Value set(Value value) throws IOException, InterruptedException;
}
