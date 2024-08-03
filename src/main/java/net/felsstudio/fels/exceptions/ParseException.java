package main.java.net.felsstudio.fels.exceptions;

/**
 *
 * @author felek
 */
public final class ParseException extends RuntimeException {
    
    public ParseException() {
        super();
    }
    
    public ParseException(String string) {
        super(string);
    }
}