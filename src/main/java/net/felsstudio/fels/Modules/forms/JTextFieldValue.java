package main.java.net.felsstudio.fels.Modules.forms;

import main.java.net.felsstudio.fels.lib.*;

import javax.swing.*;

import java.io.IOException;

import static main.java.net.felsstudio.fels.lib.Converters.*;
import static main.java.net.felsstudio.fels.lib.ValueUtils.consumeFunction;

public class JTextFieldValue extends JTextComponentValue {

    private final JTextField textField;

    public JTextFieldValue(JTextField textField) {
        super(10, textField);
        this.textField = textField;
        init();
    }

    private void init() {
        set("onAction", new FunctionValue(this::addActionListener));
        set("addActionListener", new FunctionValue(this::addActionListener));
        set("getColumns", voidToInt(textField::getColumns));
        set("getHorizontalAlignment", voidToInt(textField::getHorizontalAlignment));
        set("getScrollOffset", voidToInt(textField::getScrollOffset));
        set("postActionEvent", voidToVoid(textField::postActionEvent));
        set("setActionCommand", stringToVoid(textField::setActionCommand));
        set("setColumns", intToVoid(textField::setColumns));
        set("setHorizontalAlignment", intToVoid(textField::setHorizontalAlignment));
        set("setScrollOffset", intToVoid(textField::setScrollOffset));
    }

    private Value addActionListener(Value... args) {
        Arguments.check(1, args.length);
        Function action = consumeFunction(args[0], 1);
        textField.addActionListener(e -> {
            try {
                action.execute();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        return NumberValue.ZERO;
    }
}