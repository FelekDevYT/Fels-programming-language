package main.java.net.felsstudio.fels.parser;

public record Token(TokenType type, String text, Pos pos) {

    public String shortDescription() {
        return type().name() + " " + text;
    }

    @Override
    public String toString() {
        return type.name() + " " + pos().format() + " " + text;
    }
}