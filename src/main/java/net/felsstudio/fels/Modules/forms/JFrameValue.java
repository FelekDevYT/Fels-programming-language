package main.java.net.felsstudio.fels.Modules.forms;

import javax.swing.JFrame;

import static main.java.net.felsstudio.fels.lib.Converters.*;

public class JFrameValue extends ContainerValue {

    final JFrame frame;

    public JFrameValue(JFrame frame) {
        super(9, frame);
        this.frame = frame;
        init();
    }

    private void init() {
        set("dispose", voidToVoid(frame::dispose));
        set("getTitle", voidToString(frame::getTitle));
        set("getDefaultCloseOperation", voidToInt(frame::getDefaultCloseOperation));
        set("pack", voidToVoid(frame::pack));
        set("setAlwaysOnTop", booleanOptToVoid(frame::setAlwaysOnTop));
        set("setDefaultCloseOperation", intToVoid(frame::setDefaultCloseOperation));
        set("setLocationByPlatform", booleanOptToVoid(frame::setLocationByPlatform));
        set("setResizable", booleanOptToVoid(frame::setResizable));
        set("setTitle", stringToVoid(frame::setTitle));
    }
}