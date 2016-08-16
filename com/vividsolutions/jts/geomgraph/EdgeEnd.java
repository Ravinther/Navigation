package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.algorithm.BoundaryNodeRule;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.util.Assert;

public class EdgeEnd implements Comparable {
    private double dx;
    private double dy;
    protected Edge edge;
    protected Label label;
    private Node node;
    private Coordinate p0;
    private Coordinate p1;
    private int quadrant;

    protected EdgeEnd(Edge edge) {
        this.edge = edge;
    }

    public EdgeEnd(Edge edge, Coordinate p0, Coordinate p1, Label label) {
        this(edge);
        init(p0, p1);
        this.label = label;
    }

    protected void init(Coordinate p0, Coordinate p1) {
        this.p0 = p0;
        this.p1 = p1;
        this.dx = p1.f1295x - p0.f1295x;
        this.dy = p1.f1296y - p0.f1296y;
        this.quadrant = Quadrant.quadrant(this.dx, this.dy);
        boolean z = (this.dx == 0.0d && this.dy == 0.0d) ? false : true;
        Assert.isTrue(z, "EdgeEnd with identical endpoints found");
    }

    public Edge getEdge() {
        return this.edge;
    }

    public Label getLabel() {
        return this.label;
    }

    public Coordinate getCoordinate() {
        return this.p0;
    }

    public Coordinate getDirectedCoordinate() {
        return this.p1;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int compareTo(Object obj) {
        return compareDirection((EdgeEnd) obj);
    }

    public int compareDirection(EdgeEnd e) {
        if (this.dx == e.dx && this.dy == e.dy) {
            return 0;
        }
        if (this.quadrant > e.quadrant) {
            return 1;
        }
        if (this.quadrant < e.quadrant) {
            return -1;
        }
        return CGAlgorithms.computeOrientation(e.p0, e.p1, this.p1);
    }

    public void computeLabel(BoundaryNodeRule boundaryNodeRule) {
    }

    public String toString() {
        double angle = Math.atan2(this.dy, this.dx);
        String className = getClass().getName();
        return "  " + className.substring(className.lastIndexOf(46) + 1) + ": " + this.p0 + " - " + this.p1 + " " + this.quadrant + ":" + angle + "   " + this.label;
    }
}
