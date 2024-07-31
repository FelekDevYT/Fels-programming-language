package net.felsstudio.fels.parser;

/**
 *
 * @author felek
 */
public enum TokenType {

    NUMBER,
    HEX_NUMBER,
    WORD,
    TEXT,
    
    // keyword
    PRINT,
    PRINTLN,
    IF,
    ELSE,
    WHILE,
    FOR,
    DO,
    BREAK,
    CONTINUE,
    FUNC,
    RETURN,
    USING,
    MATCH,
    CASE,
    EXTRACT,
    IMPORT,
    PANIC,
    CLASS,
    NEW,
    
    PLUS, // +
    MINUS, // -
    STAR, // *
    SLASH, // /
    PERCENT,// %
    AT,//@
    
    EQ, // =
    EQEQ, // ==
    EXCL, // !
    EXCLEQ, // !=
    LTEQ, // <=
    LT, // <
    GT, // >
    GTEQ, // >=
    ATEQ, // @=
    QUESTIONQUESTION,
    
    PLUSEQ, // +=
    MINUSEQ, // -=
    STAREQ, // *=
    SLASHEQ, // /=
    PERCENTEQ, // %=
    AMPEQ, // &=
    CARETEQ, // ^=
    BAREQ, // |=
    COLONCOLONEQ, // ::=
    LTLTEQ, // <<=
    GTGTEQ, // >>=
    GTGTGTEQ, // >>>=
    PLUSPLUS,
    MINUSMINUS,
    
    LTLT, // <<
    GTGT, // >>
    GTGTGT, // >>>
    
    TILDE, // ~
    CARET, // ^
    BAR, // |
    BARBAR, // ||
    AMP, // &
    AMPAMP, // &&
    
    QUESTION, // ?
    COLON, // :
    COLONCOLON, // ::

    DOTDOT, // ..
    STARSTAR, // **
    QUESTIONCOLON, // ?:

    LPAREN, // (
    RPAREN, // )
    LBRACKET, // [
    RBRACKET, // ]
    LBRACE, // {
    RBRACE, // }
    COMMA, // ,
    DOT, // .
    CARETCARET,
    
    EOF
}
