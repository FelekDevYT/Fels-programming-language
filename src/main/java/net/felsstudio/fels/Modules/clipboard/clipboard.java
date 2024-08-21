package main.java.net.felsstudio.fels.Modules.clipboard;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.Function;
import main.java.net.felsstudio.fels.lib.NumberValue;
import main.java.net.felsstudio.fels.lib.StringValue;
import main.java.net.felsstudio.fels.lib.Value;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

public class clipboard implements Module {

    private class cp{
        static String get(){
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            DataFlavor flavor = DataFlavor.stringFlavor;
            if (clipboard.isDataFlavorAvailable(flavor)) {
                String text = null;
                try {
                    text = (String) clipboard.getData(flavor);
                } catch (UnsupportedFlavorException e) {
                    throw new RuntimeException("RuntimeException: ");
                } catch (IOException e) {
                    throw new RuntimeException("IOException");
                }
                return text;
            }
            return null;
        }

        static void set(String text){
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(text);
            clipboard.setContents(stringSelection, null);
        }
    }

    @Override
    public Map<String, Value> constants() {
        return Map.of();
    }

    @Override
    public Map<String, Function> functions() {
        return Map.ofEntries(
                entry("getClipboard", new Function() {
                    @Override
                    public Value execute(Value... args) {
                        return new StringValue(cp.get());
                    }
                }),
                entry("setClipboard", new Function() {
                    @Override
                    public Value execute(Value... args) {
                        cp.set(args[0].asString());
                        return new StringValue(args[0].asString());
                    }
                }),
                entry("clearClipboard", new Function() {
                    @Override
                    public Value execute(Value... args) {
                        cp.set(null);
                        return NumberValue.ZERO;
                    }
                }),
                entry("add", new Function() {
                    @Override
                    public Value execute(Value... args) {
                        cp.set(cp.get()+args[0].asString());
                        return NumberValue.ZERO;
                    }
                })
        );
    }
}
