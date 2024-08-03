package main.java.net.felsstudio.fels.Modules.forms;

import javax.swing.*;

import main.java.net.felsstudio.fels.lib.*;

import static main.java.net.felsstudio.fels.lib.Converters.*;

public class JTextAreaValue extends JComponentValue{

    private final JTextArea textArea;

    public JTextAreaValue(JTextArea textArea) {
        super(11, textArea);
        this.textArea = textArea;
    }

    private void init(){
        set("getCaretPosition", voidToInt(textArea::getCaretPosition));
        set("getColumns", voidToInt(textArea::getColumns));
        set("getSelectionEnd", voidToInt(textArea::getSelectionEnd));
        set("getSelectionStart", voidToInt(textArea::getSelectionStart));
        set("getText", voidToString(textArea::getText));
        set("setCaretPosition", intToVoid(textArea::setCaretPosition));
        set("setColumns", intToVoid(textArea::setColumns));
        set("setSelectionEnd", intToVoid(textArea::setSelectionEnd));
        set("setSelectionStart", intToVoid(textArea::setSelectionStart));
        set("setText", stringToVoid(textArea::setText));
        set("append",stringToVoid(textArea::append));
    }
}
