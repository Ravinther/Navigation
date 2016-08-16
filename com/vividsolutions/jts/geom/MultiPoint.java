package com.vividsolutions.jts.geom;

public class MultiPoint extends GeometryCollection {
    public int getDimension() {
        return 0;
    }

    public int getBoundaryDimension() {
        return -1;
    }

    public boolean equalsExact(Geometry other, double tolerance) {
        if (isEquivalentClass(other)) {
            return super.equalsExact(other, tolerance);
        }
        return false;
    }
}
