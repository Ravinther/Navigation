package com.sygic.aura.showcase.targets;

import android.support.v7.widget.Toolbar;
import android.view.View;

public class ToolbarItemTarget extends ToolbarTarget {
    public ToolbarItemTarget(Toolbar toolbar, int itemId) {
        super(getToolbarItem(toolbar, itemId));
    }

    private static View getToolbarItem(Toolbar toolbar, int itemId) {
        if (toolbar == null) {
            return null;
        }
        return toolbar.findViewById(itemId);
    }
}
