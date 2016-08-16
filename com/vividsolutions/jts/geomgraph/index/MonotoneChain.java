package com.vividsolutions.jts.geomgraph.index;

public class MonotoneChain {
    int chainIndex;
    MonotoneChainEdge mce;

    public MonotoneChain(MonotoneChainEdge mce, int chainIndex) {
        this.mce = mce;
        this.chainIndex = chainIndex;
    }

    public void computeIntersections(MonotoneChain mc, SegmentIntersector si) {
        this.mce.computeIntersectsForChain(this.chainIndex, mc.mce, mc.chainIndex, si);
    }
}
