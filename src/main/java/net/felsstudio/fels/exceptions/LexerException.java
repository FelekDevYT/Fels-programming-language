package main.java.net.felsstudio.fels.exceptions;

import main.java.net.felsstudio.fels.parser.Pos;

/**
 *
 * @author felek
 */
public final class LexerException extends RuntimeException {

    public LexerException(String message) {
        super(message);
    }

    public LexerException(Pos pos, String message) {
        super(pos.format() + " " + message);
    }
}