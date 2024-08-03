package main.java.net.felsstudio.fels.Modules.mBox;

import net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;
import javax.swing.JOptionPane;

public class mBox implements Module {
    @Override
    public void init() {
        Functions.set("showErrorMessage", args ->{
        JOptionPane.showMessageDialog(null,args[0], String.valueOf(args[1]),JOptionPane.ERROR_MESSAGE);
        return NumberValue.ZERO;
    });
        Functions.set("showInformationMessage",args ->{
        JOptionPane.showMessageDialog(null,args[0], String.valueOf(args[1]),JOptionPane.INFORMATION_MESSAGE);
        return NumberValue.ZERO;
    });
        Functions.set("showQuestionMessage",args ->{
        JOptionPane.showMessageDialog(null,args[0], String.valueOf(args[1]),JOptionPane.QUESTION_MESSAGE);
        return NumberValue.ZERO;
    });
        Functions.set("showWarningMessage",args -> {
        JOptionPane.showMessageDialog(null,args[0], String.valueOf(args[1]),JOptionPane.WARNING_MESSAGE);
        return NumberValue.ZERO;
    });
        Functions.set("showPlanMessage",args -> {
        JOptionPane.showMessageDialog(null,args[0], String.valueOf(args[1]),JOptionPane.PLAIN_MESSAGE);
        return NumberValue.ZERO;
    });
        Functions.set("showInputDialog",args -> {
        return new StringValue(JOptionPane.showInputDialog(null,args[0], String.valueOf(args[1]),JOptionPane.QUESTION_MESSAGE));
    });
    }
}