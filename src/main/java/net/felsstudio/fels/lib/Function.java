package net.felsstudio.fels.lib;

import java.io.IOException;

/**
 *
 * @author aNNiMON
 */
public interface Function {

    Value execute(Value... args) throws IOException, InterruptedException;
}
