package main.java.net.felsstudio.fels.exceptions;

import main.java.net.felsstudio.fels.parser.Pos;
import main.java.net.felsstudio.fels.parser.Range;

/**
 *
 * @author felek
 */
public final class ParseException extends RuntimeException {

    private final Range range;

    public ParseException(String message) {
        this(message, Range.ZERO);
    }

    public ParseException(String message, Pos pos) {
        this(message, pos, pos);
    }

    public ParseException(String message, Pos start, Pos end) {
        this(message, new Range(start, end));
    }

    public ParseException(String message, Range range) {
        super(message);
        this.range = range;
    }

    public Range getRange() {
        return range;
    }
}