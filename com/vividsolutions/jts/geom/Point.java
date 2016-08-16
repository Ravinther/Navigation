package com.vividsolutions.jts.geom;

import com.vividsolutions.jts.util.Assert;

public class Point extends Geometry {
    private CoordinateSequence coordinates;

    public Point(CoordinateSequence coordinates, GeometryFactory factory) {
        super(factory);
        init(coordinates);
    }

    private void init(CoordinateSequence coordinates) {
        boolean z = true;
        if (coordinates == null) {
            coordinates = getFactory().getCoordinateSequenceFactory().create(new Coordinate[0]);
        }
        if (coordinates.size() > 1) {
            z = false;
        }
        Assert.isTrue(z);
        this.coordinates = coordinates;
    }

    public boolean isEmpty() {
        return getCoordinate() == null;
    }

    public int getDimension() {
        return 0;
    }

    public int getBoundaryDimension() {
        return -1;
    }

    public Coordinate getCoordinate() {
        return this.coordinates.size() != 0 ? this.coordinates.getCoordinate(0) : null;
    }

    protected Envelope computeEnvelopeInternal() {
        if (isEmpty()) {
            return new Envelope();
        }
        Envelope env = new Envelope();
        env.expandToInclude(this.coordinates.getX(0), this.coordinates.getY(0));
        return env;
    }

    public boolean equalsExact(Geometry other, double tolerance) {
        if (!isEquivalentClass(other)) {
            return false;
        }
        if (isEmpty() && other.isEmpty()) {
            return true;
        }
        if (isEmpty() == other.isEmpty()) {
            return equal(((Point) other).getCoordinate(), getCoordinate(), tolerance);
        }
        return false;
    }

    public Object clone() {
        Point p = (Point) super.clone();
        p.coordinates = (CoordinateSequence) this.coordinates.clone();
        return p;
    }

    protected int compareToSameClass(Object other) {
        return getCoordinate().compareTo(((Point) other).getCoordinate());
    }
}
