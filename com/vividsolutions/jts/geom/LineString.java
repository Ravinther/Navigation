package com.vividsolutions.jts.geom;

public class LineString extends Geometry {
    protected CoordinateSequence points;

    public LineString(CoordinateSequence points, GeometryFactory factory) {
        super(factory);
        init(points);
    }

    private void init(CoordinateSequence points) {
        if (points == null) {
            points = getFactory().getCoordinateSequenceFactory().create(new Coordinate[0]);
        }
        if (points.size() == 1) {
            throw new IllegalArgumentException("Invalid number of points in LineString (found " + points.size() + " - must be 0 or >= 2)");
        }
        this.points = points;
    }

    public Coordinate[] getCoordinates() {
        return this.points.toCoordinateArray();
    }

    public CoordinateSequence getCoordinateSequence() {
        return this.points;
    }

    public Coordinate getCoordinateN(int n) {
        return this.points.getCoordinate(n);
    }

    public int getDimension() {
        return 1;
    }

    public int getBoundaryDimension() {
        if (isClosed()) {
            return -1;
        }
        return 0;
    }

    public boolean isEmpty() {
        return this.points.size() == 0;
    }

    public int getNumPoints() {
        return this.points.size();
    }

    public boolean isClosed() {
        if (isEmpty()) {
            return false;
        }
        return getCoordinateN(0).equals2D(getCoordinateN(getNumPoints() - 1));
    }

    protected Envelope computeEnvelopeInternal() {
        if (isEmpty()) {
            return new Envelope();
        }
        return this.points.expandEnvelope(new Envelope());
    }

    public boolean equalsExact(Geometry other, double tolerance) {
        if (!isEquivalentClass(other)) {
            return false;
        }
        LineString otherLineString = (LineString) other;
        if (this.points.size() != otherLineString.points.size()) {
            return false;
        }
        for (int i = 0; i < this.points.size(); i++) {
            if (!equal(this.points.getCoordinate(i), otherLineString.points.getCoordinate(i), tolerance)) {
                return false;
            }
        }
        return true;
    }

    public Object clone() {
        LineString ls = (LineString) super.clone();
        ls.points = (CoordinateSequence) this.points.clone();
        return ls;
    }

    protected boolean isEquivalentClass(Geometry other) {
        return other instanceof LineString;
    }

    protected int compareToSameClass(Object o) {
        LineString line = (LineString) o;
        int i = 0;
        int j = 0;
        while (i < this.points.size() && j < line.points.size()) {
            int comparison = this.points.getCoordinate(i).compareTo(line.points.getCoordinate(j));
            if (comparison != 0) {
                return comparison;
            }
            i++;
            j++;
        }
        if (i < this.points.size()) {
            return 1;
        }
        if (j < line.points.size()) {
            return -1;
        }
        return 0;
    }
}
