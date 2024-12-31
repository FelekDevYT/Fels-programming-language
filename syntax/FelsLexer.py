from pygments.lexer import RegexLexer
from pygments.token import Keyword, Name, String, Number, Comment

class FelsLexer(RegexLexer):
    name = 'Fels'
    tokens = {
        'root': [
            (r'\b(class|func|println|if|else|while|for|return|import|using)\b', Keyword),
            (r'\b\d+\b', Number),
            (r'"([^"\\]*(\\.[^"\\]*)*)"', String),
            (r'//.*', Comment.Single),
            (r'/\*.*?\*/', Comment.Multiline),
            (r'\b[A-Za-z_][A-Za-z0-9_]*\b', Name),
        ]
    }