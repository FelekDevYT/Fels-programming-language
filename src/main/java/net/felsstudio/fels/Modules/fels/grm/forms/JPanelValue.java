package net.felsstudio.fels.Modules.fels.grm.forms;

import javax.swing.JPanel;

public class JPanelValue extends JComponentValue {

    final JPanel panel;

    public JPanelValue(JPanel panel) {
        super(0, panel);
        this.panel = panel;
        init();
    }

    private void init() {
    }
}