package main.java.net.felsstudio.fels.parser.ast;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public interface Statement extends Node {
    
    void execute() throws IOException, InterruptedException;
}
