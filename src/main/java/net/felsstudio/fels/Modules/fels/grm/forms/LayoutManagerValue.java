package net.felsstudio.fels.Modules.fels.grm.forms;

import net.felsstudio.fels.lib.MapValue;

import java.awt.LayoutManager;

public class LayoutManagerValue extends MapValue {

    final LayoutManager layout;

    public LayoutManagerValue(LayoutManager layout) {
        super(0);
        this.layout = layout;
    }
}