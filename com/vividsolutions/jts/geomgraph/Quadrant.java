package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Coordinate;

public class Quadrant {
    public static int quadrant(double dx, double dy) {
        if (dx == 0.0d && dy == 0.0d) {
            throw new IllegalArgumentException("Cannot compute the quadrant for point ( " + dx + ", " + dy + " )");
        } else if (dx >= 0.0d) {
            if (dy >= 0.0d) {
                return 0;
            }
            return 3;
        } else if (dy >= 0.0d) {
            return 1;
        } else {
            return 2;
        }
    }

    public static int quadrant(Coordinate p0, Coordinate p1) {
        if (p1.f1295x == p0.f1295x && p1.f1296y == p0.f1296y) {
            throw new IllegalArgumentException("Cannot compute the quadrant for two identical points " + p0);
        } else if (p1.f1295x >= p0.f1295x) {
            if (p1.f1296y >= p0.f1296y) {
                return 0;
            }
            return 3;
        } else if (p1.f1296y >= p0.f1296y) {
            return 1;
        } else {
            return 2;
        }
    }
}
