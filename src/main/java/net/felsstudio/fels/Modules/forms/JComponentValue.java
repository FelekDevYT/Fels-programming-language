package main.java.net.felsstudio.fels.Modules.forms;

import javax.swing.JComponent;

import main.java.net.felsstudio.fels.lib.*;

import static main.java.net.felsstudio.fels.lib.Converters.stringToVoid;
import static main.java.net.felsstudio.fels.lib.Converters.voidToString;

public abstract class JComponentValue extends ContainerValue {

    final JComponent jComponent;

    public JComponentValue(int functionsCount, JComponent jComponent) {
        super(functionsCount + 2, jComponent);
        this.jComponent = jComponent;
        init();
    }

    private void init() {
        set("getToolTipText", voidToString(jComponent::getToolTipText));
        set("setToolTipText", stringToVoid(jComponent::setToolTipText));
    }
}
