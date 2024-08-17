package main.java.net.felsstudio.fels.parser;

public record Token(TokenType type, String text, int row, int col) {

    public String position() {
        return "[" + row + " " + col + "]";
    }

    @Override
    public String toString() {
        return type.name() + " " + position() + " " + text;
    }
}