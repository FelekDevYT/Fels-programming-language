package main.java.net.felsstudio.fels.Modules.forms;

import main.java.net.felsstudio.fels.lib.FunctionValue;
import main.java.net.felsstudio.fels.lib.*;

import javax.swing.AbstractButton;

import java.io.IOException;

import static main.java.net.felsstudio.fels.lib.Converters.*;

public class AbstractButtonValue extends JComponentValue {

    final AbstractButton abstractButton;

    public AbstractButtonValue(int functionsCount, AbstractButton abstractButton) {
        super(functionsCount + 2, abstractButton);
        this.abstractButton = abstractButton;
        init();
    }

    private void init() {
        set("onClick", new FunctionValue(this::addActionListener));
        set("addActionListener", new FunctionValue(this::addActionListener));
        set("onChange", new FunctionValue(this::addChangeListener));
        set("addChangeListener", new FunctionValue(this::addChangeListener));
        set("doClick", intOptToVoid(abstractButton::doClick, abstractButton::doClick));
        set("getActionCommand", voidToString(abstractButton::getActionCommand));
        set("getDisplayedMnemonicIndex", voidToInt(abstractButton::getDisplayedMnemonicIndex));
        set("getHideActionText", voidToBoolean(abstractButton::getHideActionText));
        set("getHorizontalAlignment", voidToInt(abstractButton::getHorizontalAlignment));
        set("getHorizontalTextPosition", voidToInt(abstractButton::getHorizontalTextPosition));
        set("getIconTextGap", voidToInt(abstractButton::getIconTextGap));
        set("getText", voidToString(abstractButton::getText));
        set("getVerticalAlignment", voidToInt(abstractButton::getVerticalAlignment));
        set("getVerticalTextPosition", voidToInt(abstractButton::getVerticalTextPosition));
        set("isSelected", voidToBoolean(abstractButton::isSelected));
        set("setActionCommand", stringToVoid(abstractButton::setActionCommand));
        set("setHorizontalAlignment", intToVoid(abstractButton::setHorizontalAlignment));
        set("setHorizontalTextPosition", intToVoid(abstractButton::setHorizontalTextPosition));
        set("setSelected", booleanToVoid(abstractButton::setSelected));
        set("setText", stringToVoid(abstractButton::setText));
        set("setVerticalAlignment", intToVoid(abstractButton::setVerticalAlignment));
        set("setVerticalTextPosition", intToVoid(abstractButton::setVerticalTextPosition));
    }

    private Value addActionListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        abstractButton.addActionListener(e -> {
            try {
                action.execute();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return NumberValue.ZERO;
    }
    
    private Value addChangeListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        abstractButton.addChangeListener(e -> {
            try {
                action.execute();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return NumberValue.ZERO;
    }
}