package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTWriter;
import com.vividsolutions.jts.util.Assert;
import java.lang.reflect.Array;

public abstract class LineIntersector {
    protected Coordinate[][] inputLines;
    protected Coordinate[] intPt;
    protected boolean isProper;
    protected Coordinate pa;
    protected Coordinate pb;
    protected PrecisionModel precisionModel;
    protected int result;

    protected abstract int computeIntersect(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4);

    public abstract void computeIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3);

    public static double computeEdgeDistance(Coordinate p, Coordinate p0, Coordinate p1) {
        double dist;
        double dx = Math.abs(p1.f1295x - p0.f1295x);
        double dy = Math.abs(p1.f1296y - p0.f1296y);
        if (p.equals(p0)) {
            dist = 0.0d;
        } else if (!p.equals(p1)) {
            double pdx = Math.abs(p.f1295x - p0.f1295x);
            double pdy = Math.abs(p.f1296y - p0.f1296y);
            if (dx > dy) {
                dist = pdx;
            } else {
                dist = pdy;
            }
            if (dist == 0.0d && !p.equals(p0)) {
                dist = Math.max(pdx, pdy);
            }
        } else if (dx > dy) {
            dist = dx;
        } else {
            dist = dy;
        }
        boolean z = dist != 0.0d || p.equals(p0);
        Assert.isTrue(z, "Bad distance calculation");
        return dist;
    }

    public LineIntersector() {
        this.inputLines = (Coordinate[][]) Array.newInstance(Coordinate.class, new int[]{2, 2});
        this.intPt = new Coordinate[2];
        this.precisionModel = null;
        this.intPt[0] = new Coordinate();
        this.intPt[1] = new Coordinate();
        this.pa = this.intPt[0];
        this.pb = this.intPt[1];
        this.result = 0;
    }

    public void setPrecisionModel(PrecisionModel precisionModel) {
        this.precisionModel = precisionModel;
    }

    protected boolean isCollinear() {
        return this.result == 2;
    }

    public void computeIntersection(Coordinate p1, Coordinate p2, Coordinate p3, Coordinate p4) {
        this.inputLines[0][0] = p1;
        this.inputLines[0][1] = p2;
        this.inputLines[1][0] = p3;
        this.inputLines[1][1] = p4;
        this.result = computeIntersect(p1, p2, p3, p4);
    }

    public String toString() {
        return WKTWriter.toLineString(this.inputLines[0][0], this.inputLines[0][1]) + " - " + WKTWriter.toLineString(this.inputLines[1][0], this.inputLines[1][1]) + getTopologySummary();
    }

    private String getTopologySummary() {
        StringBuffer catBuf = new StringBuffer();
        if (isEndPoint()) {
            catBuf.append(" endpoint");
        }
        if (this.isProper) {
            catBuf.append(" proper");
        }
        if (isCollinear()) {
            catBuf.append(" collinear");
        }
        return catBuf.toString();
    }

    protected boolean isEndPoint() {
        return hasIntersection() && !this.isProper;
    }

    public boolean hasIntersection() {
        return this.result != 0;
    }

    public int getIntersectionNum() {
        return this.result;
    }

    public Coordinate getIntersection(int intIndex) {
        return this.intPt[intIndex];
    }

    public boolean isIntersection(Coordinate pt) {
        for (int i = 0; i < this.result; i++) {
            if (this.intPt[i].equals2D(pt)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProper() {
        return hasIntersection() && this.isProper;
    }

    public double getEdgeDistance(int segmentIndex, int intIndex) {
        return computeEdgeDistance(this.intPt[intIndex], this.inputLines[segmentIndex][0], this.inputLines[segmentIndex][1]);
    }
}
