package main.java.net.felsstudio.fels.parser

/**
 *
 * @author felek
 */
enum class TokenType {
    NUMBER,
    HEX_NUMBER,
    WORD,
    DECIMAL_NUMBER,
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
    PERROR,
    LOOP,
    RANGE,
    PASS,

    PLUS,  // +
    MINUS,  // -
    STAR,  // *
    SLASH,  // /
    PERCENT,  // %
    AT,  //@
    DOTCOMMA,

    EQ,  // =
    EQEQ,  // ==
    EXCL,  // !
    EXCLEQ,  // !=
    LTEQ,  // <=
    LT,  // <
    GT,  // >
    GTEQ,  // >=
    ATEQ,  // @=
    QUESTIONQUESTION,

    PLUSEQ,  // +=
    MINUSEQ,  // -=
    STAREQ,  // *=
    SLASHEQ,  // /=
    PERCENTEQ,  // %=
    AMPEQ,  // &=
    CARETEQ,  // ^=
    BAREQ,  // |=
    COLONCOLONEQ,  // ::=
    LTLTEQ,  // <<=
    GTGTEQ,  // >>=
    GTGTGTEQ,  // >>>=
    PLUSPLUS,
    MINUSMINUS,

    LTLT,  // <<
    GTGT,  // >>
    GTGTGT,  // >>>

    TILDE,  // ~
    CARET,  // ^
    BAR,  // |
    BARBAR,  // ||
    AMP,  // &
    AMPAMP,  // &&

    QUESTION,  // ?
    COLON,  // :
    COLONCOLON,  // ::

    DOTDOT,  // ..
    STARSTAR,  // **
    QUESTIONCOLON,  // ?:

    LPAREN,  // (
    RPAREN,  // )
    LBRACKET,  // [
    RBRACKET,  // ]
    LBRACE,  // {
    RBRACE,  // }
    COMMA,  // ,
    DOT,  // .
    CARETCARET,
    GRID,//#

    EOF;
}
