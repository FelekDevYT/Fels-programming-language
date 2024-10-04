package main.java.net.felsstudio.fels.Modules.fels.lang.character;

import main.java.net.felsstudio.fels.lib.Arguments;
import main.java.net.felsstudio.fels.lib.NumberValue;
import main.java.net.felsstudio.fels.lib.Value;

public final class CharacterClass {

    public static Value _isAlphabetic(Value[] args) {
        Arguments.check(1, args.length);
        char current = (char) args[0].asInt();
        boolean result = java.lang.Character.isAlphabetic(current);
        if (result)
            return NumberValue.ONE;
        else
            return NumberValue.ZERO;
    }

    public static Value _isDigit(Value[] args) {
        Arguments.check(1, args.length);
        char current = (char) args[0].asInt();
        boolean result = java.lang.Character.isDigit(current);
        if (result)
            return NumberValue.ONE;
        else
            return NumberValue.ZERO;
    }

    public static Value _isLetterOrDigit(Value[] args) {
        Arguments.check(1, args.length);
        char current = (char) args[0].asInt();
        boolean result = java.lang.Character.isLetterOrDigit(current);
        if (result)
            return NumberValue.ONE;
        else
            return NumberValue.ZERO;
    }

    public static Value _isLetter(Value[] args) {
        Arguments.check(1, args.length);
        char current = (char) args[0].asInt();
        boolean result = java.lang.Character.isLetter(current);
        if (result)
            return NumberValue.ONE;
        else
            return NumberValue.ZERO;
    }




}