package com.vividsolutions.jts.index.strtree;

import com.vividsolutions.jts.util.Assert;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractSTRtree implements Serializable {
    private boolean built;
    private ArrayList itemBoundables;
    private int nodeCapacity;
    protected AbstractNode root;

    protected interface IntersectsOp {
        boolean intersects(Object obj, Object obj2);
    }

    protected abstract AbstractNode createNode(int i);

    protected abstract Comparator getComparator();

    protected abstract IntersectsOp getIntersectsOp();

    public AbstractSTRtree(int nodeCapacity) {
        boolean z = true;
        this.built = false;
        this.itemBoundables = new ArrayList();
        if (nodeCapacity <= 1) {
            z = false;
        }
        Assert.isTrue(z, "Node capacity must be greater than 1");
        this.nodeCapacity = nodeCapacity;
    }

    public void build() {
        if (!this.built) {
            this.root = this.itemBoundables.isEmpty() ? createNode(0) : createHigherLevels(this.itemBoundables, -1);
            this.itemBoundables = null;
            this.built = true;
        }
    }

    protected List createParentBoundables(List childBoundables, int newLevel) {
        Assert.isTrue(!childBoundables.isEmpty());
        ArrayList parentBoundables = new ArrayList();
        parentBoundables.add(createNode(newLevel));
        ArrayList sortedChildBoundables = new ArrayList(childBoundables);
        Collections.sort(sortedChildBoundables, getComparator());
        Iterator i = sortedChildBoundables.iterator();
        while (i.hasNext()) {
            Boundable childBoundable = (Boundable) i.next();
            if (lastNode(parentBoundables).getChildBoundables().size() == getNodeCapacity()) {
                parentBoundables.add(createNode(newLevel));
            }
            lastNode(parentBoundables).addChildBoundable(childBoundable);
        }
        return parentBoundables;
    }

    protected AbstractNode lastNode(List nodes) {
        return (AbstractNode) nodes.get(nodes.size() - 1);
    }

    protected static int compareDoubles(double a, double b) {
        if (a > b) {
            return 1;
        }
        return a < b ? -1 : 0;
    }

    private AbstractNode createHigherLevels(List boundablesOfALevel, int level) {
        boolean z;
        if (boundablesOfALevel.isEmpty()) {
            z = false;
        } else {
            z = true;
        }
        Assert.isTrue(z);
        List parentBoundables = createParentBoundables(boundablesOfALevel, level + 1);
        if (parentBoundables.size() == 1) {
            return (AbstractNode) parentBoundables.get(0);
        }
        return createHigherLevels(parentBoundables, level + 1);
    }

    public int getNodeCapacity() {
        return this.nodeCapacity;
    }

    public boolean isEmpty() {
        if (this.built) {
            return this.root.isEmpty();
        }
        return this.itemBoundables.isEmpty();
    }

    protected void insert(Object bounds, Object item) {
        Assert.isTrue(!this.built, "Cannot insert items into an STR packed R-tree after it has been built.");
        this.itemBoundables.add(new ItemBoundable(bounds, item));
    }

    protected List query(Object searchBounds) {
        build();
        ArrayList matches = new ArrayList();
        if (!isEmpty() && getIntersectsOp().intersects(this.root.getBounds(), searchBounds)) {
            query(searchBounds, this.root, matches);
        }
        return matches;
    }

    private void query(Object searchBounds, AbstractNode node, List matches) {
        List childBoundables = node.getChildBoundables();
        for (int i = 0; i < childBoundables.size(); i++) {
            Boundable childBoundable = (Boundable) childBoundables.get(i);
            if (getIntersectsOp().intersects(childBoundable.getBounds(), searchBounds)) {
                if (childBoundable instanceof AbstractNode) {
                    query(searchBounds, (AbstractNode) childBoundable, matches);
                } else if (childBoundable instanceof ItemBoundable) {
                    matches.add(((ItemBoundable) childBoundable).getItem());
                } else {
                    Assert.shouldNeverReachHere();
                }
            }
        }
    }
}
