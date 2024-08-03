package main.java.net.felsstudio.fels.Modules.forms;

import main.java.net.felsstudio.fels.lib.*;

import static main.java.net.felsstudio.fels.lib.Converters.*;
import static main.java.net.felsstudio.fels.lib.ValueUtils.consumeFunction;

import javax.swing.JTextField;

public class JTextFieldValue extends JComponentValue {

    private final JTextField textField;

    public JTextFieldValue(JTextField textField) {
        super(16, textField);
        this.textField = textField;
        init();
    }

    private void init() {
        set("onAction", new FunctionValue(this::addActionListener));
        set("addActionListener", new FunctionValue(this::addActionListener));
        set("getCaretPosition", voidToInt(textField::getCaretPosition));
        set("getColumns", voidToInt(textField::getColumns));
        set("getHorizontalAlignment", voidToInt(textField::getHorizontalAlignment));
        set("getSelectionEnd", voidToInt(textField::getSelectionEnd));
        set("getSelectionStart", voidToInt(textField::getSelectionStart));
        set("getScrollOffset", voidToInt(textField::getScrollOffset));
        set("getText", voidToString(textField::getText));
        set("setCaretPosition", intToVoid(textField::setCaretPosition));
        set("setColumns", intToVoid(textField::setColumns));
        set("setHorizontalAlignment", intToVoid(textField::setHorizontalAlignment));
        set("setScrollOffset", intToVoid(textField::setScrollOffset));
        set("setSelectionEnd", intToVoid(textField::setSelectionEnd));
        set("setSelectionStart", intToVoid(textField::setSelectionStart));
        set("setText", stringToVoid(textField::setText));
    }

    private Value addActionListener(Value... args) {
        Arguments.check(1, args.length);
        Function action = consumeFunction(args[0], 1);
        textField.addActionListener(e -> action.execute());
        return NumberValue.ZERO;
    }
}