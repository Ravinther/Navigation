package com.vividsolutions.jts.geom;

public class MultiLineString extends GeometryCollection {
    public int getDimension() {
        return 1;
    }

    public int getBoundaryDimension() {
        if (isClosed()) {
            return -1;
        }
        return 0;
    }

    public boolean isClosed() {
        if (isEmpty()) {
            return false;
        }
        for (Geometry geometry : this.geometries) {
            if (!((LineString) geometry).isClosed()) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsExact(Geometry other, double tolerance) {
        if (isEquivalentClass(other)) {
            return super.equalsExact(other, tolerance);
        }
        return false;
    }
}
