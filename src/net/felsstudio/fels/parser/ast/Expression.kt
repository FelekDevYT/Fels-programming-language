package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Value;

/**
 *
 * @author felek
 */
public interface Expression extends Node {
    
    Value eval();
}
