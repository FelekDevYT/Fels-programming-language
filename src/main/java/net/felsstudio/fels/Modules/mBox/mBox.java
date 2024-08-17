package main.java.net.felsstudio.fels.Modules.mBox;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;
import javax.swing.JOptionPane;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class mBox implements Module {

    @Override
    public Map<String, Value> constants() {
        return Map.of();
    }

    @Override
    public Map<String, Function> functions() {
        final var result = new LinkedHashMap<String, Function>(5);

        result.put("showErrorMessage", args ->{
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.ERROR_MESSAGE);
            return NumberValue.ZERO;
        });

        result.put("showInformationMessage",args ->{
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.INFORMATION_MESSAGE);
            return NumberValue.ZERO;
        });

        result.put("showWarningMessage",args -> {
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.WARNING_MESSAGE);
            return NumberValue.ZERO;
        });

        result.put("showNoneMessage",args -> {
            JOptionPane.showMessageDialog(null, args[0].asString(),args[1].asString(),JOptionPane.PLAIN_MESSAGE);
            return NumberValue.ZERO;
        });

        result.put("showInputDialog",args -> {
            return new StringValue(JOptionPane.showInputDialog(null,args[0], String.valueOf(args[1]),JOptionPane.QUESTION_MESSAGE));
        });

        return result;
    }
}