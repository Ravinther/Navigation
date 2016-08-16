package com.vividsolutions.jts.geom;

public class MultiPolygon extends GeometryCollection {
    public int getDimension() {
        return 2;
    }

    public int getBoundaryDimension() {
        return 1;
    }

    public boolean equalsExact(Geometry other, double tolerance) {
        if (isEquivalentClass(other)) {
            return super.equalsExact(other, tolerance);
        }
        return false;
    }
}
