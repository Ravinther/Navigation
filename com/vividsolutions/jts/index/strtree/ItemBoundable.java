package com.vividsolutions.jts.index.strtree;

import java.io.Serializable;

public class ItemBoundable implements Boundable, Serializable {
    private Object bounds;
    private Object item;

    public ItemBoundable(Object bounds, Object item) {
        this.bounds = bounds;
        this.item = item;
    }

    public Object getBounds() {
        return this.bounds;
    }

    public Object getItem() {
        return this.item;
    }
}
