package net.felsstudio.fels.Modules.fels.grm.mBox;

import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.*;

import javax.swing.JOptionPane;

public class mBox implements Module {

    @Override
    public void init() {
        final MapValue map = new MapValue(7);

        map.set("showErrorMessage", args ->{
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.ERROR_MESSAGE);
            return NumberValue.ZERO;
        });

        map.set("showInformationMessage",args ->{
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.INFORMATION_MESSAGE);
            return NumberValue.ZERO;
        });

        map.set("showWarningMessage",args -> {
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.WARNING_MESSAGE);
            return NumberValue.ZERO;
        });

        map.set("showNoneMessage",args -> {
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.PLAIN_MESSAGE);
            return NumberValue.ZERO;
        });

        map.set("showInputDialog",args -> {
            return new StringValue(JOptionPane.showInputDialog(null,args[0], String.valueOf(args[1]),JOptionPane.QUESTION_MESSAGE));
        });

        Variables.define("mBox",map);
    }
}