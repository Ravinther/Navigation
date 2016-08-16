package com.sygic.aura.views.popup;

import android.graphics.drawable.Drawable;

public class ActionItem {
    private int actionId;
    private Drawable icon;
    private boolean sticky;
    private String title;

    public ActionItem(int actionId, String title, Drawable icon) {
        this.actionId = -1;
        this.title = title;
        this.icon = icon;
        this.actionId = actionId;
    }

    public ActionItem() {
        this(-1, null, null);
    }

    public String getTitle() {
        return this.title;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public int getActionId() {
        return this.actionId;
    }

    public boolean isSticky() {
        return this.sticky;
    }
}
