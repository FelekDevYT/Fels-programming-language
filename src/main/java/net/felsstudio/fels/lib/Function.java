package main.java.net.felsstudio.fels.lib;

import java.io.IOException;

/**
 *
 * @author felek
 */
public interface Function {

    Value execute(Value... args) throws IOException;

}