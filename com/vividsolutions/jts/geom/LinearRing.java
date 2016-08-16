package com.vividsolutions.jts.geom;

public class LinearRing extends LineString {
    public LinearRing(CoordinateSequence points, GeometryFactory factory) {
        super(points, factory);
        validateConstruction();
    }

    private void validateConstruction() {
        if (!isEmpty() && !super.isClosed()) {
            throw new IllegalArgumentException("Points of LinearRing do not form a closed linestring");
        } else if (getCoordinateSequence().size() >= 1 && getCoordinateSequence().size() < 4) {
            throw new IllegalArgumentException("Invalid number of points in LinearRing (found " + getCoordinateSequence().size() + " - must be 0 or >= 4)");
        }
    }

    public int getBoundaryDimension() {
        return -1;
    }

    public boolean isClosed() {
        if (isEmpty()) {
            return true;
        }
        return super.isClosed();
    }
}
