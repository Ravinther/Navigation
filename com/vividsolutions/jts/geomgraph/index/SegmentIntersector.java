package com.vividsolutions.jts.geomgraph.index;

import com.vividsolutions.jts.algorithm.LineIntersector;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geomgraph.Edge;
import com.vividsolutions.jts.geomgraph.Node;
import java.util.Collection;

public class SegmentIntersector {
    private Collection[] bdyNodes;
    private boolean hasIntersection;
    private boolean hasProper;
    private boolean hasProperInterior;
    private boolean includeProper;
    private LineIntersector li;
    private int numIntersections;
    public int numTests;
    private Coordinate properIntersectionPoint;
    private boolean recordIsolated;

    public static boolean isAdjacentSegments(int i1, int i2) {
        return Math.abs(i1 - i2) == 1;
    }

    public SegmentIntersector(LineIntersector li, boolean includeProper, boolean recordIsolated) {
        this.hasIntersection = false;
        this.hasProper = false;
        this.hasProperInterior = false;
        this.properIntersectionPoint = null;
        this.numIntersections = 0;
        this.numTests = 0;
        this.li = li;
        this.includeProper = includeProper;
        this.recordIsolated = recordIsolated;
    }

    public void setBoundaryNodes(Collection bdyNodes0, Collection bdyNodes1) {
        this.bdyNodes = new Collection[2];
        this.bdyNodes[0] = bdyNodes0;
        this.bdyNodes[1] = bdyNodes1;
    }

    public boolean hasProperIntersection() {
        return this.hasProper;
    }

    public boolean hasProperInteriorIntersection() {
        return this.hasProperInterior;
    }

    private boolean isTrivialIntersection(Edge e0, int segIndex0, Edge e1, int segIndex1) {
        if (e0 == e1 && this.li.getIntersectionNum() == 1) {
            if (isAdjacentSegments(segIndex0, segIndex1)) {
                return true;
            }
            if (e0.isClosed()) {
                int maxSegIndex = e0.getNumPoints() - 1;
                if (segIndex0 == 0 && segIndex1 == maxSegIndex) {
                    return true;
                }
                if (segIndex1 == 0 && segIndex0 == maxSegIndex) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addIntersections(Edge e0, int segIndex0, Edge e1, int segIndex1) {
        if (e0 != e1 || segIndex0 != segIndex1) {
            this.numTests++;
            this.li.computeIntersection(e0.getCoordinates()[segIndex0], e0.getCoordinates()[segIndex0 + 1], e1.getCoordinates()[segIndex1], e1.getCoordinates()[segIndex1 + 1]);
            if (this.li.hasIntersection()) {
                if (this.recordIsolated) {
                    e0.setIsolated(false);
                    e1.setIsolated(false);
                }
                this.numIntersections++;
                if (!isTrivialIntersection(e0, segIndex0, e1, segIndex1)) {
                    this.hasIntersection = true;
                    if (this.includeProper || !this.li.isProper()) {
                        e0.addIntersections(this.li, segIndex0, 0);
                        e1.addIntersections(this.li, segIndex1, 1);
                    }
                    if (this.li.isProper()) {
                        this.properIntersectionPoint = (Coordinate) this.li.getIntersection(0).clone();
                        this.hasProper = true;
                        if (!isBoundaryPoint(this.li, this.bdyNodes)) {
                            this.hasProperInterior = true;
                        }
                    }
                }
            }
        }
    }

    private boolean isBoundaryPoint(LineIntersector li, Collection[] bdyNodes) {
        if (bdyNodes == null) {
            return false;
        }
        if (isBoundaryPoint(li, bdyNodes[0])) {
            return true;
        }
        if (isBoundaryPoint(li, bdyNodes[1])) {
            return true;
        }
        return false;
    }

    private boolean isBoundaryPoint(LineIntersector li, Collection bdyNodes) {
        for (Node node : bdyNodes) {
            if (li.isIntersection(node.getCoordinate())) {
                return true;
            }
        }
        return false;
    }
}
