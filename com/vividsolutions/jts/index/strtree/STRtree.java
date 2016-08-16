package com.vividsolutions.jts.index.strtree;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.util.Assert;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class STRtree extends AbstractSTRtree implements Serializable {
    private static IntersectsOp intersectsOp;
    private static Comparator xComparator;
    private static Comparator yComparator;

    /* renamed from: com.vividsolutions.jts.index.strtree.STRtree.1 */
    static class C18051 implements Comparator {
        C18051() {
        }

        public int compare(Object o1, Object o2) {
            return AbstractSTRtree.compareDoubles(STRtree.centreX((Envelope) ((Boundable) o1).getBounds()), STRtree.centreX((Envelope) ((Boundable) o2).getBounds()));
        }
    }

    /* renamed from: com.vividsolutions.jts.index.strtree.STRtree.2 */
    static class C18062 implements Comparator {
        C18062() {
        }

        public int compare(Object o1, Object o2) {
            return AbstractSTRtree.compareDoubles(STRtree.centreY((Envelope) ((Boundable) o1).getBounds()), STRtree.centreY((Envelope) ((Boundable) o2).getBounds()));
        }
    }

    /* renamed from: com.vividsolutions.jts.index.strtree.STRtree.3 */
    static class C18073 implements IntersectsOp {
        C18073() {
        }

        public boolean intersects(Object aBounds, Object bBounds) {
            return ((Envelope) aBounds).intersects((Envelope) bBounds);
        }
    }

    private static final class STRtreeNode extends AbstractNode {
        private STRtreeNode(int level) {
            super(level);
        }

        protected Object computeBounds() {
            Envelope bounds = null;
            for (Boundable childBoundable : getChildBoundables()) {
                if (bounds == null) {
                    bounds = new Envelope((Envelope) childBoundable.getBounds());
                } else {
                    bounds.expandToInclude((Envelope) childBoundable.getBounds());
                }
            }
            return bounds;
        }
    }

    static {
        xComparator = new C18051();
        yComparator = new C18062();
        intersectsOp = new C18073();
    }

    private static double centreX(Envelope e) {
        return avg(e.getMinX(), e.getMaxX());
    }

    private static double centreY(Envelope e) {
        return avg(e.getMinY(), e.getMaxY());
    }

    private static double avg(double a, double b) {
        return (a + b) / 2.0d;
    }

    protected List createParentBoundables(List childBoundables, int newLevel) {
        Assert.isTrue(!childBoundables.isEmpty());
        int minLeafCount = (int) Math.ceil(((double) childBoundables.size()) / ((double) getNodeCapacity()));
        ArrayList sortedChildBoundables = new ArrayList(childBoundables);
        Collections.sort(sortedChildBoundables, xComparator);
        return createParentBoundablesFromVerticalSlices(verticalSlices(sortedChildBoundables, (int) Math.ceil(Math.sqrt((double) minLeafCount))), newLevel);
    }

    private List createParentBoundablesFromVerticalSlices(List[] verticalSlices, int newLevel) {
        Assert.isTrue(verticalSlices.length > 0);
        List parentBoundables = new ArrayList();
        for (List createParentBoundablesFromVerticalSlice : verticalSlices) {
            parentBoundables.addAll(createParentBoundablesFromVerticalSlice(createParentBoundablesFromVerticalSlice, newLevel));
        }
        return parentBoundables;
    }

    protected List createParentBoundablesFromVerticalSlice(List childBoundables, int newLevel) {
        return super.createParentBoundables(childBoundables, newLevel);
    }

    protected List[] verticalSlices(List childBoundables, int sliceCount) {
        int sliceCapacity = (int) Math.ceil(((double) childBoundables.size()) / ((double) sliceCount));
        List[] slices = new List[sliceCount];
        Iterator i = childBoundables.iterator();
        for (int j = 0; j < sliceCount; j++) {
            slices[j] = new ArrayList();
            int boundablesAddedToSlice = 0;
            while (i.hasNext() && boundablesAddedToSlice < sliceCapacity) {
                slices[j].add((Boundable) i.next());
                boundablesAddedToSlice++;
            }
        }
        return slices;
    }

    public STRtree() {
        this(10);
    }

    public STRtree(int nodeCapacity) {
        super(nodeCapacity);
    }

    protected AbstractNode createNode(int level) {
        return new STRtreeNode(null);
    }

    protected IntersectsOp getIntersectsOp() {
        return intersectsOp;
    }

    public void insert(Envelope itemEnv, Object item) {
        if (!itemEnv.isNull()) {
            super.insert(itemEnv, item);
        }
    }

    public List query(Envelope searchEnv) {
        return super.query(searchEnv);
    }

    protected Comparator getComparator() {
        return yComparator;
    }
}
