package main.java.net.felsstudio.fels.Modules.forms;

import main.java.net.felsstudio.fels.lib.FunctionValue;
import main.java.net.felsstudio.fels.lib.*;

import static main.java.net.felsstudio.fels.lib.Converters.*;
import javax.swing.JProgressBar;
import java.io.IOException;

public class JProgressBarValue extends JComponentValue {

    final JProgressBar progressBar;

    public JProgressBarValue(JProgressBar progressBar) {
        super(19, progressBar);
        this.progressBar = progressBar;
        init();
    }

    private void init() {
        set("onChange", new FunctionValue(this::addChangeListener));
        set("addChangeListener", new FunctionValue(this::addChangeListener));
        set("getMinimum", voidToInt(progressBar::getMinimum));
        set("getMaximum", voidToInt(progressBar::getMaximum));
        set("getOrientation", voidToInt(progressBar::getOrientation));
        set("getValue", voidToInt(progressBar::getValue));
        set("isBorderPainted", voidToBoolean(progressBar::isBorderPainted));
        set("isIndeterminate", voidToBoolean(progressBar::isIndeterminate));
        set("isStringPainted", voidToBoolean(progressBar::isStringPainted));
        set("getString", voidToString(progressBar::getString));
        set("getPercentComplete", voidToDouble(progressBar::getPercentComplete));
        
        set("setMinimum", intToVoid(progressBar::setMinimum));
        set("setMaximum", intToVoid(progressBar::setMaximum));
        set("setBorderPainted", booleanToVoid(progressBar::setBorderPainted));
        set("setIndeterminate", booleanToVoid(progressBar::setIndeterminate));
        set("setOrientation", intToVoid(progressBar::setOrientation));
        set("setStringPainted", booleanToVoid(progressBar::setStringPainted));
        set("setString", stringToVoid(progressBar::setString));
        set("setValue", intToVoid(progressBar::setValue));
    }
    
    private Value addChangeListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        progressBar.addChangeListener(e -> {
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