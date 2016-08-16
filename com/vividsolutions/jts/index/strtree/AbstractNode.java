package com.vividsolutions.jts.index.strtree;

import com.vividsolutions.jts.util.Assert;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode implements Boundable, Serializable {
    private Object bounds;
    private ArrayList childBoundables;
    private int level;

    protected abstract Object computeBounds();

    public AbstractNode() {
        this.childBoundables = new ArrayList();
        this.bounds = null;
    }

    public AbstractNode(int level) {
        this.childBoundables = new ArrayList();
        this.bounds = null;
        this.level = level;
    }

    public List getChildBoundables() {
        return this.childBoundables;
    }

    public Object getBounds() {
        if (this.bounds == null) {
            this.bounds = computeBounds();
        }
        return this.bounds;
    }

    public boolean isEmpty() {
        return this.childBoundables.isEmpty();
    }

    public void addChildBoundable(Boundable childBoundable) {
        Assert.isTrue(this.bounds == null);
        this.childBoundables.add(childBoundable);
    }
}
