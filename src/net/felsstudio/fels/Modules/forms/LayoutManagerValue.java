package net.felsstudio.fels.Modules.forms;

import net.felsstudio.fels.lib.*;

import java.awt.LayoutManager;

public class LayoutManagerValue extends MapValue {

    final LayoutManager layout;

    public LayoutManagerValue(LayoutManager layout) {
        super(0);
        this.layout = layout;
    }
}