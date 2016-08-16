package com.vividsolutions.jts.geomgraph.index;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geomgraph.Edge;

public class MonotoneChainEdge {
    Edge f1298e;
    Envelope env1;
    Envelope env2;
    Coordinate[] pts;
    int[] startIndex;

    public MonotoneChainEdge(Edge e) {
        this.env1 = new Envelope();
        this.env2 = new Envelope();
        this.f1298e = e;
        this.pts = e.getCoordinates();
        this.startIndex = new MonotoneChainIndexer().getChainStartIndices(this.pts);
    }

    public int[] getStartIndexes() {
        return this.startIndex;
    }

    public double getMinX(int chainIndex) {
        double x1 = this.pts[this.startIndex[chainIndex]].f1295x;
        double x2 = this.pts[this.startIndex[chainIndex + 1]].f1295x;
        return x1 < x2 ? x1 : x2;
    }

    public double getMaxX(int chainIndex) {
        double x1 = this.pts[this.startIndex[chainIndex]].f1295x;
        double x2 = this.pts[this.startIndex[chainIndex + 1]].f1295x;
        return x1 > x2 ? x1 : x2;
    }

    public void computeIntersectsForChain(int chainIndex0, MonotoneChainEdge mce, int chainIndex1, SegmentIntersector si) {
        computeIntersectsForChain(this.startIndex[chainIndex0], this.startIndex[chainIndex0 + 1], mce, mce.startIndex[chainIndex1], mce.startIndex[chainIndex1 + 1], si);
    }

    private void computeIntersectsForChain(int start0, int end0, MonotoneChainEdge mce, int start1, int end1, SegmentIntersector ei) {
        Coordinate p00 = this.pts[start0];
        Coordinate p01 = this.pts[end0];
        Coordinate p10 = mce.pts[start1];
        Coordinate p11 = mce.pts[end1];
        if (end0 - start0 == 1 && end1 - start1 == 1) {
            ei.addIntersections(this.f1298e, start0, mce.f1298e, start1);
            return;
        }
        this.env1.init(p00, p01);
        this.env2.init(p10, p11);
        if (this.env1.intersects(this.env2)) {
            int mid0 = (start0 + end0) / 2;
            int mid1 = (start1 + end1) / 2;
            if (start0 < mid0) {
                if (start1 < mid1) {
                    computeIntersectsForChain(start0, mid0, mce, start1, mid1, ei);
                }
                if (mid1 < end1) {
                    computeIntersectsForChain(start0, mid0, mce, mid1, end1, ei);
                }
            }
            if (mid0 < end0) {
                if (start1 < mid1) {
                    computeIntersectsForChain(mid0, end0, mce, start1, mid1, ei);
                }
                if (mid1 < end1) {
                    computeIntersectsForChain(mid0, end0, mce, mid1, end1, ei);
                }
            }
        }
    }
}
