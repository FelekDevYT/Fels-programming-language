package main.java.net.felsstudio.fels.parser;

public record ParseError(Exception exception, Pos pos) {

    @Override
    public String toString() {
        return "ParseError on line " + pos.row() + ": " + exception;
    }
}