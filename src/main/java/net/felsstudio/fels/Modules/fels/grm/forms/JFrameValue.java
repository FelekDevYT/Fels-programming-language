package net.felsstudio.fels.Modules.fels.grm.forms;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import net.felsstudio.fels.lib.Arguments;
import net.felsstudio.fels.lib.Function;
import net.felsstudio.fels.lib.NumberValue;
import net.felsstudio.fels.lib.Value;

import javax.swing.JFrame;

import java.io.IOException;

import static net.felsstudio.fels.lib.Converters.*;

public class JFrameValue extends ContainerValue {

    final JFrame frame;

    public JFrameValue(JFrame frame) {
        super(9, frame);
        this.frame = frame;
        init();
    }

    private void init() {
        set("installTheme",new setTheme());
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

    private static class setTheme implements Function {
        @Override
        public Value execute(Value... args) throws IOException, InterruptedException {
            Arguments.check(1, args.length);
            switch (args[0].asString()) {
                case "intellij": {
                    LafManager.setTheme(new IntelliJTheme());
                    break;
                }
                case "dracula": {
                    LafManager.setTheme(new DarculaTheme());
                    break;
                }
            }
            return NumberValue.ZERO;
        }
    }
}