package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryCollectionIterator;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import java.util.Iterator;

public class PointLocator {
    private BoundaryNodeRule boundaryRule;
    private boolean isIn;
    private int numBoundaries;

    public PointLocator() {
        this.boundaryRule = BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE;
    }

    public int locate(Coordinate p, Geometry geom) {
        if (geom.isEmpty()) {
            return 2;
        }
        if (geom instanceof LineString) {
            return locate(p, (LineString) geom);
        }
        if (geom instanceof Polygon) {
            return locate(p, (Polygon) geom);
        }
        this.isIn = false;
        this.numBoundaries = 0;
        computeLocation(p, geom);
        if (this.boundaryRule.isInBoundary(this.numBoundaries)) {
            return 1;
        }
        if (this.numBoundaries > 0 || this.isIn) {
            return 0;
        }
        return 2;
    }

    private void computeLocation(Coordinate p, Geometry geom) {
        if (geom instanceof Point) {
            updateLocationInfo(locate(p, (Point) geom));
        }
        if (geom instanceof LineString) {
            updateLocationInfo(locate(p, (LineString) geom));
        } else if (geom instanceof Polygon) {
            updateLocationInfo(locate(p, (Polygon) geom));
        } else if (geom instanceof MultiLineString) {
            MultiLineString ml = (MultiLineString) geom;
            for (i = 0; i < ml.getNumGeometries(); i++) {
                updateLocationInfo(locate(p, (LineString) ml.getGeometryN(i)));
            }
        } else if (geom instanceof MultiPolygon) {
            MultiPolygon mpoly = (MultiPolygon) geom;
            for (i = 0; i < mpoly.getNumGeometries(); i++) {
                updateLocationInfo(locate(p, (Polygon) mpoly.getGeometryN(i)));
            }
        } else if (geom instanceof GeometryCollection) {
            Iterator geomi = new GeometryCollectionIterator((GeometryCollection) geom);
            while (geomi.hasNext()) {
                Geometry g2 = (Geometry) geomi.next();
                if (g2 != geom) {
                    computeLocation(p, g2);
                }
            }
        }
    }

    private void updateLocationInfo(int loc) {
        if (loc == 0) {
            this.isIn = true;
        }
        if (loc == 1) {
            this.numBoundaries++;
        }
    }

    private int locate(Coordinate p, Point pt) {
        if (pt.getCoordinate().equals2D(p)) {
            return 0;
        }
        return 2;
    }

    private int locate(Coordinate p, LineString l) {
        if (!l.getEnvelopeInternal().intersects(p)) {
            return 2;
        }
        Coordinate[] pt = l.getCoordinates();
        if (!l.isClosed() && (p.equals(pt[0]) || p.equals(pt[pt.length - 1]))) {
            return 1;
        }
        if (CGAlgorithms.isOnLine(p, pt)) {
            return 0;
        }
        return 2;
    }

    private int locateInPolygonRing(Coordinate p, LinearRing ring) {
        if (ring.getEnvelopeInternal().intersects(p)) {
            return CGAlgorithms.locatePointInRing(p, ring.getCoordinates());
        }
        return 2;
    }

    private int locate(Coordinate p, Polygon poly) {
        if (poly.isEmpty()) {
            return 2;
        }
        int shellLoc = locateInPolygonRing(p, (LinearRing) poly.getExteriorRing());
        if (shellLoc == 2) {
            return 2;
        }
        if (shellLoc == 1) {
            return 1;
        }
        for (int i = 0; i < poly.getNumInteriorRing(); i++) {
            int holeLoc = locateInPolygonRing(p, (LinearRing) poly.getInteriorRingN(i));
            if (holeLoc == 0) {
                return 2;
            }
            if (holeLoc == 1) {
                return 1;
            }
        }
        return 0;
    }
}
