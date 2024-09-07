package main.java.net.felsstudio.fels.Modules.clipboard;

import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

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
    public void init() {
        MapValue map = new MapValue(4);

        map.set("get",args -> new StringValue(cp.get()));

        map.set("set",args->{
            cp.set(args[0].asString());
            return new StringValue(cp.get());
        });

        map.set("clear",args ->{
            cp.set(null);
            return NumberValue.ZERO;
        });

        map.set("add",args ->{
            cp.set(cp.get()+args[0].asString());
            return NumberValue.ZERO;
        });

        Variables.define("clipboard", map);
    }
}
