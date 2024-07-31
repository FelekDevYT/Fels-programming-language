package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.Value;

public interface Accessible {

    Value get();
    
    Value set(Value value);
}
