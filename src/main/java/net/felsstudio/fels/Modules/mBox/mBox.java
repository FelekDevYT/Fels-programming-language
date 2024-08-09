package main.java.net.felsstudio.fels.Modules.mBox;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;
import javax.swing.JOptionPane;
import java.util.Optional;

public class mBox implements Module {
    @Override
    public void init() {
    Functions.set("showErrorMessage", args ->{
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(args[0].asString());
        dialog.setHeaderText(null);
        dialog.setContentText(args[1].asString());
        dialog.showAndWait();
        return NumberValue.ZERO;
    });
    Functions.set("showInformationMessage",args ->{
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(args[0].asString());
        dialog.setHeaderText(null);
        dialog.setContentText(args[1].asString());
        dialog.showAndWait();
            return NumberValue.ZERO;
        });
    Functions.set("showWarningMessage",args -> {
        Alert dialog = new Alert(Alert.AlertType.WARNING);
        dialog.setTitle(args[0].asString());
        dialog.setHeaderText(null);
        dialog.setContentText(args[1].asString());
        dialog.showAndWait();
        return NumberValue.ZERO;
    });
    Functions.set("showNoneMessage",args -> {
        Alert dialog = new Alert(Alert.AlertType.NONE);
        dialog.setTitle(args[0].asString());
        dialog.setHeaderText(null);
        dialog.setContentText(args[1].asString());
        dialog.showAndWait();
        return NumberValue.ZERO;
    });
    Functions.set("showInputDialog",args -> {
        return new StringValue(JOptionPane.showInputDialog(null,args[0], String.valueOf(args[1]),JOptionPane.QUESTION_MESSAGE));
    });
    }
}