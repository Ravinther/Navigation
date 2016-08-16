package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;

public class CentralEndpointIntersector {
    private Coordinate intPt;
    private Coordinate[] pts;

    public static Coordinate getIntersection(Coordinate p00, Coordinate p01, Coordinate p10, Coordinate p11) {
        return new CentralEndpointIntersector(p00, p01, p10, p11).getIntersection();
    }

    public CentralEndpointIntersector(Coordinate p00, Coordinate p01, Coordinate p10, Coordinate p11) {
        this.intPt = null;
        this.pts = new Coordinate[]{p00, p01, p10, p11};
        compute();
    }

    private void compute() {
        this.intPt = new Coordinate(findNearestPoint(average(this.pts), this.pts));
    }

    public Coordinate getIntersection() {
        return this.intPt;
    }

    private static Coordinate average(Coordinate[] pts) {
        Coordinate avg = new Coordinate();
        int n = pts.length;
        for (int i = 0; i < pts.length; i++) {
            avg.f1295x += pts[i].f1295x;
            avg.f1296y += pts[i].f1296y;
        }
        if (n > 0) {
            avg.f1295x /= (double) n;
            avg.f1296y /= (double) n;
        }
        return avg;
    }

    private Coordinate findNearestPoint(Coordinate p, Coordinate[] pts) {
        double minDist = Double.MAX_VALUE;
        Coordinate result = null;
        for (int i = 0; i < pts.length; i++) {
            double dist = p.distance(pts[i]);
            if (i == 0 || dist < minDist) {
                minDist = dist;
                result = pts[i];
            }
        }
        return result;
    }
}
