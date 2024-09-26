package main.java.net.felsstudio.fels.Modules.fels.grm.forms;

import javax.swing.JButton;

public class JButtonValue extends AbstractButtonValue {

    final JButton jButton;

    public JButtonValue(JButton jButton) {
        super(0, jButton);
        this.jButton = jButton;
        init();
    }

    private void init() {
    }
}