package net.felsstudio.fels.parser.ast;

/**
 *
 * @author felek
 */
public interface Node {
    
    void accept(Visitor visitor);

    <R, T> R accept(ResultVisitor<R, T> visitor, T input);
}
