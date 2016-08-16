package com.vividsolutions.jts.operation.predicate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class RectangleContains {
    private Envelope rectEnv;

    public static boolean contains(Polygon rectangle, Geometry b) {
        return new RectangleContains(rectangle).contains(b);
    }

    public RectangleContains(Polygon rectangle) {
        this.rectEnv = rectangle.getEnvelopeInternal();
    }

    public boolean contains(Geometry geom) {
        if (this.rectEnv.contains(geom.getEnvelopeInternal()) && !isContainedInBoundary(geom)) {
            return true;
        }
        return false;
    }

    private boolean isContainedInBoundary(Geometry geom) {
        if (geom instanceof Polygon) {
            return false;
        }
        if (geom instanceof Point) {
            return isPointContainedInBoundary((Point) geom);
        }
        if (geom instanceof LineString) {
            return isLineStringContainedInBoundary((LineString) geom);
        }
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            if (!isContainedInBoundary(geom.getGeometryN(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isPointContainedInBoundary(Point point) {
        return isPointContainedInBoundary(point.getCoordinate());
    }

    private boolean isPointContainedInBoundary(Coordinate pt) {
        return pt.f1295x == this.rectEnv.getMinX() || pt.f1295x == this.rectEnv.getMaxX() || pt.f1296y == this.rectEnv.getMinY() || pt.f1296y == this.rectEnv.getMaxY();
    }

    private boolean isLineStringContainedInBoundary(LineString line) {
        CoordinateSequence seq = line.getCoordinateSequence();
        Coordinate p0 = new Coordinate();
        Coordinate p1 = new Coordinate();
        for (int i = 0; i < seq.size() - 1; i++) {
            seq.getCoordinate(i, p0);
            seq.getCoordinate(i + 1, p1);
            if (!isLineSegmentContainedInBoundary(p0, p1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLineSegmentContainedInBoundary(Coordinate p0, Coordinate p1) {
        if (p0.equals(p1)) {
            return isPointContainedInBoundary(p0);
        }
        if (p0.f1295x == p1.f1295x) {
            if (p0.f1295x == this.rectEnv.getMinX() || p0.f1295x == this.rectEnv.getMaxX()) {
                return true;
            }
        } else if (p0.f1296y == p1.f1296y) {
            if (p0.f1296y == this.rectEnv.getMinY()) {
                return true;
            }
            if (p0.f1296y == this.rectEnv.getMaxY()) {
                return true;
            }
        }
        return false;
    }
}
