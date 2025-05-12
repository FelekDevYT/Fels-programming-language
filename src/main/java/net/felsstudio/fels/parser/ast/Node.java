package net.felsstudio.fels.parser.ast;

import java.io.IOException;

/**
 *
 * @author FelekDevYT,FelsStudio
 */
public interface Node {
    
    void accept(Visitor visitor);

    <R, T> R accept(ResultVisitor<R, T> visitor, T input) throws IOException, InterruptedException;
}
