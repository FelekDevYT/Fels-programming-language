package main.java.net.felsstudio.fels.parser;

public record Token(TokenType type, String text, Pos pos) {

    @Override
    public String toString() {
        return type.name() + " " + pos().format() + " " + text;
    }
}