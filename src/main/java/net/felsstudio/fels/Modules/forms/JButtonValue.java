package main.java.net.felsstudio.fels.Modules.forms;

import main.java.net.felsstudio.fels.lib.*;

import javax.swing.JButton;

public class JButtonValue extends JComponentValue {

    final JButton button;

    public JButtonValue(JButton button) {
        super(2, button);
        this.button = button;
        init();
    }

    private void init() {
        set("onClick", new FunctionValue(this::addActionListener));
        set("addActionListener", new FunctionValue(this::addActionListener));
    }

    private Value addActionListener(Value... args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        button.addActionListener(e -> action.execute());
        return NumberValue.ZERO;
    }
}