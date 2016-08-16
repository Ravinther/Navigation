package com.vividsolutions.jts.algorithm.locate;

import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryCollectionIterator;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import java.util.Iterator;

public class SimplePointInAreaLocator implements PointOnGeometryLocator {
    public static int locate(Coordinate p, Geometry geom) {
        if (!geom.isEmpty() && containsPoint(p, geom)) {
            return 0;
        }
        return 2;
    }

    private static boolean containsPoint(Coordinate p, Geometry geom) {
        if (geom instanceof Polygon) {
            return containsPointInPolygon(p, (Polygon) geom);
        }
        if (geom instanceof GeometryCollection) {
            Iterator geomi = new GeometryCollectionIterator((GeometryCollection) geom);
            while (geomi.hasNext()) {
                Geometry g2 = (Geometry) geomi.next();
                if (g2 != geom && containsPoint(p, g2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsPointInPolygon(Coordinate p, Polygon poly) {
        if (poly.isEmpty() || !isPointInRing(p, (LinearRing) poly.getExteriorRing())) {
            return false;
        }
        for (int i = 0; i < poly.getNumInteriorRing(); i++) {
            if (isPointInRing(p, (LinearRing) poly.getInteriorRingN(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPointInRing(Coordinate p, LinearRing ring) {
        if (ring.getEnvelopeInternal().intersects(p)) {
            return CGAlgorithms.isPointInRing(p, ring.getCoordinates());
        }
        return false;
    }
}
