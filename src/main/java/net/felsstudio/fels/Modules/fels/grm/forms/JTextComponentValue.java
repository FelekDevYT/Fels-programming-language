package net.felsstudio.fels.Modules.fels.grm.forms;

import net.felsstudio.fels.lib.*;

import static net.felsstudio.fels.lib.Converters.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.io.IOException;

public class JTextComponentValue extends JComponentValue {

    private final JTextComponent textComponent;

    public JTextComponentValue(int functionsCount, JTextComponent textComponent) {
        super(functionsCount + 21, textComponent);
        this.textComponent = textComponent;
        init();
    }

    private void init() {
        set("addCaretListener", this::addCaretListener);
        set("addDocumentListener", this::addDocumentListener);
        set("copy", voidToVoid(textComponent::copy));
        set("cut", voidToVoid(textComponent::cut));
        set("getCaretPosition", voidToInt(textComponent::getCaretPosition));
        set("getDragEnabled", voidToBoolean(textComponent::getDragEnabled));
        set("getSelectedText", voidToString(textComponent::getSelectedText));
        set("getSelectionStart", voidToInt(textComponent::getSelectionStart));
        set("getSelectionEnd", voidToInt(textComponent::getSelectionEnd));
        set("getText", voidToString(textComponent::getText));
        set("isEditable", voidToBoolean(textComponent::isEditable));
        set("moveCaretPosition", intToVoid(textComponent::moveCaretPosition));
        set("paste", voidToVoid(textComponent::paste));
        set("replaceSelection", stringToVoid(textComponent::replaceSelection));
        set("select", int2ToVoid(textComponent::select));
        set("selectAll", voidToVoid(textComponent::selectAll));
        set("setCaretPosition", intToVoid(textComponent::setCaretPosition));
        set("setDragEnabled", booleanToVoid(textComponent::setDragEnabled));
        set("setEditable", booleanToVoid(textComponent::setEditable));
        set("setSelectionStart", intToVoid(textComponent::setSelectionStart));
        set("setSelectionEnd", intToVoid(textComponent::setSelectionEnd));
        set("setText", stringToVoid(textComponent::setText));
    }
    
    private Value addCaretListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        textComponent.addCaretListener((CaretEvent e) -> {
            final MapValue map = new MapValue(2);
            map.set("getDot", NumberValue.of(e.getDot()));
            map.set("getMark", NumberValue.of(e.getMark()));
            try {
                action.execute(map);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        return NumberValue.ZERO;
    }
    
    private Value addDocumentListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    handleDocumentEvent(DocumentEvent.EventType.INSERT, e);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    handleDocumentEvent(DocumentEvent.EventType.REMOVE, e);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    handleDocumentEvent(DocumentEvent.EventType.CHANGE, e);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            
            private void handleDocumentEvent(DocumentEvent.EventType type, final DocumentEvent e) throws IOException, InterruptedException {
                final MapValue map = new MapValue(3);
                map.set("getLength", NumberValue.of(e.getLength()));
                map.set("getOffset", NumberValue.of(e.getOffset()));
                map.set("getType", new StringValue(e.getType().toString()));
                action.execute(new StringValue(type.toString()), map);
            }
        });
        return NumberValue.ZERO;
    }
}