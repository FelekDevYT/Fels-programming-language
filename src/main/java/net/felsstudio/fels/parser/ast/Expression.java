package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.Value;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public interface Expression extends Node {
    
    Value eval() throws IOException, InterruptedException;
}
