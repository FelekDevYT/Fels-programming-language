package main.java.net.felsstudio.fels.Modules.fels.lang.character;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.MapValue;
import main.java.net.felsstudio.fels.lib.Variables;

public class character implements Module {
    @Override
    public void init() {
        final MapValue characterFunctions = new MapValue(4);
        characterFunctions.set("isAlphabetic", CharacterClass::_isAlphabetic);
        characterFunctions.set("isDigit", CharacterClass::_isDigit);
        characterFunctions.set("isLetter", CharacterClass::_isLetter);
        characterFunctions.set("isLetterOrDigit", CharacterClass::_isLetterOrDigit);
        Variables.define("Character", characterFunctions);
    }
}
