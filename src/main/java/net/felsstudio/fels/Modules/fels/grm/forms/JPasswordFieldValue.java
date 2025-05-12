package net.felsstudio.fels.Modules.fels.grm.forms;

import net.felsstudio.fels.lib.*;

import javax.swing.*;

import java.io.IOException;

import static net.felsstudio.fels.lib.Converters.*;
import static net.felsstudio.fels.lib.Converters.intToVoid;
import static net.felsstudio.fels.lib.ValueUtils.consumeFunction;

public class JPasswordFieldValue extends JTextComponentValue {
    
    private final JPasswordField passwordField;
    
    public JPasswordFieldValue(JPasswordField passwordField) {
        super(10,passwordField);
        this.passwordField = passwordField;
    }

    private void init() {
        set("onAction", new FunctionValue(this::addActionListener));
        set("addActionListener", new FunctionValue(this::addActionListener));
        set("getColumns", voidToInt(passwordField::getColumns));
        set("getHorizontalAlignment", voidToInt(passwordField::getHorizontalAlignment));
        set("getScrollOffset", voidToInt(passwordField::getScrollOffset));
        set("postActionEvent", voidToVoid(passwordField::postActionEvent));
        set("setActionCommand", stringToVoid(passwordField::setActionCommand));
        set("setColumns", intToVoid(passwordField::setColumns));
        set("setHorizontalAlignment", intToVoid(passwordField::setHorizontalAlignment));
        set("setScrollOffset", intToVoid(passwordField::setScrollOffset));
    }

    private Value addActionListener(Value... args) {
        Arguments.check(1, args.length);
        Function action = consumeFunction(args[0], 1);
        passwordField.addActionListener(e -> {
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
