package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.lib.Value;

/**
 *
 * @author felek
 */
public interface Expression extends Node {
    
    Value eval();
}
