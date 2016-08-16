package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.algorithm.LineIntersector;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.IntersectionMatrix;
import com.vividsolutions.jts.geomgraph.index.MonotoneChainEdge;

public class Edge extends GraphComponent {
    private Depth depth;
    private int depthDelta;
    EdgeIntersectionList eiList;
    private boolean isIsolated;
    private MonotoneChainEdge mce;
    private String name;
    Coordinate[] pts;

    public static void updateIM(Label label, IntersectionMatrix im) {
        im.setAtLeastIfValid(label.getLocation(0, 0), label.getLocation(1, 0), 1);
        if (label.isArea()) {
            im.setAtLeastIfValid(label.getLocation(0, 1), label.getLocation(1, 1), 2);
            im.setAtLeastIfValid(label.getLocation(0, 2), label.getLocation(1, 2), 2);
        }
    }

    public Edge(Coordinate[] pts, Label label) {
        this.eiList = new EdgeIntersectionList(this);
        this.isIsolated = true;
        this.depth = new Depth();
        this.depthDelta = 0;
        this.pts = pts;
        this.label = label;
    }

    public int getNumPoints() {
        return this.pts.length;
    }

    public Coordinate[] getCoordinates() {
        return this.pts;
    }

    public Coordinate getCoordinate(int i) {
        return this.pts[i];
    }

    public Coordinate getCoordinate() {
        if (this.pts.length > 0) {
            return this.pts[0];
        }
        return null;
    }

    public EdgeIntersectionList getEdgeIntersectionList() {
        return this.eiList;
    }

    public MonotoneChainEdge getMonotoneChainEdge() {
        if (this.mce == null) {
            this.mce = new MonotoneChainEdge(this);
        }
        return this.mce;
    }

    public boolean isClosed() {
        return this.pts[0].equals(this.pts[this.pts.length - 1]);
    }

    public void setIsolated(boolean isIsolated) {
        this.isIsolated = isIsolated;
    }

    public boolean isIsolated() {
        return this.isIsolated;
    }

    public void addIntersections(LineIntersector li, int segmentIndex, int geomIndex) {
        for (int i = 0; i < li.getIntersectionNum(); i++) {
            addIntersection(li, segmentIndex, geomIndex, i);
        }
    }

    public void addIntersection(LineIntersector li, int segmentIndex, int geomIndex, int intIndex) {
        Coordinate intPt = new Coordinate(li.getIntersection(intIndex));
        int normalizedSegmentIndex = segmentIndex;
        double dist = li.getEdgeDistance(geomIndex, intIndex);
        int nextSegIndex = normalizedSegmentIndex + 1;
        if (nextSegIndex < this.pts.length && intPt.equals2D(this.pts[nextSegIndex])) {
            normalizedSegmentIndex = nextSegIndex;
            dist = 0.0d;
        }
        EdgeIntersection ei = this.eiList.add(intPt, normalizedSegmentIndex, dist);
    }

    public void computeIM(IntersectionMatrix im) {
        updateIM(this.label, im);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) o;
        if (this.pts.length != e.pts.length) {
            return false;
        }
        boolean isEqualForward = true;
        boolean isEqualReverse = true;
        int iRev = this.pts.length;
        for (int i = 0; i < this.pts.length; i++) {
            if (!this.pts[i].equals2D(e.pts[i])) {
                isEqualForward = false;
            }
            iRev--;
            if (!this.pts[i].equals2D(e.pts[iRev])) {
                isEqualReverse = false;
            }
            if (!isEqualForward && !isEqualReverse) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("edge " + this.name + ": ");
        buf.append("LINESTRING (");
        for (int i = 0; i < this.pts.length; i++) {
            if (i > 0) {
                buf.append(",");
            }
            buf.append(this.pts[i].f1295x + " " + this.pts[i].f1296y);
        }
        buf.append(")  " + this.label + " " + this.depthDelta);
        return buf.toString();
    }
}
