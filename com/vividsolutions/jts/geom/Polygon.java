package com.vividsolutions.jts.geom;

public class Polygon extends Geometry {
    protected LinearRing[] holes;
    protected LinearRing shell;

    public Polygon(LinearRing shell, LinearRing[] holes, GeometryFactory factory) {
        super(factory);
        this.shell = null;
        if (shell == null) {
            shell = getFactory().createLinearRing((CoordinateSequence) null);
        }
        if (holes == null) {
            holes = new LinearRing[0];
        }
        if (Geometry.hasNullElements(holes)) {
            throw new IllegalArgumentException("holes must not contain null elements");
        } else if (shell.isEmpty() && Geometry.hasNonEmptyElements(holes)) {
            throw new IllegalArgumentException("shell is empty but holes are not");
        } else {
            this.shell = shell;
            this.holes = holes;
        }
    }

    public int getDimension() {
        return 2;
    }

    public int getBoundaryDimension() {
        return 1;
    }

    public boolean isEmpty() {
        return this.shell.isEmpty();
    }

    public boolean isRectangle() {
        if (getNumInteriorRing() != 0) {
            return false;
        }
        if (this.shell == null) {
            return false;
        }
        if (this.shell.getNumPoints() != 5) {
            return false;
        }
        int i;
        CoordinateSequence seq = this.shell.getCoordinateSequence();
        Envelope env = getEnvelopeInternal();
        for (i = 0; i < 5; i++) {
            double x = seq.getX(i);
            if (x != env.getMinX() && x != env.getMaxX()) {
                return false;
            }
            double y = seq.getY(i);
            if (y != env.getMinY() && y != env.getMaxY()) {
                return false;
            }
        }
        double prevX = seq.getX(0);
        double prevY = seq.getY(0);
        for (i = 1; i <= 4; i++) {
            x = seq.getX(i);
            y = seq.getY(i);
            if ((x != prevX) == (y != prevY)) {
                return false;
            }
            prevX = x;
            prevY = y;
        }
        return true;
    }

    public LineString getExteriorRing() {
        return this.shell;
    }

    public int getNumInteriorRing() {
        return this.holes.length;
    }

    public LineString getInteriorRingN(int n) {
        return this.holes[n];
    }

    protected Envelope computeEnvelopeInternal() {
        return this.shell.getEnvelopeInternal();
    }

    public boolean equalsExact(Geometry other, double tolerance) {
        if (!isEquivalentClass(other)) {
            return false;
        }
        Polygon otherPolygon = (Polygon) other;
        if (!this.shell.equalsExact(otherPolygon.shell, tolerance) || this.holes.length != otherPolygon.holes.length) {
            return false;
        }
        for (int i = 0; i < this.holes.length; i++) {
            if (!this.holes[i].equalsExact(otherPolygon.holes[i], tolerance)) {
                return false;
            }
        }
        return true;
    }

    public Object clone() {
        Polygon poly = (Polygon) super.clone();
        poly.shell = (LinearRing) this.shell.clone();
        poly.holes = new LinearRing[this.holes.length];
        for (int i = 0; i < this.holes.length; i++) {
            poly.holes[i] = (LinearRing) this.holes[i].clone();
        }
        return poly;
    }

    protected int compareToSameClass(Object o) {
        return this.shell.compareToSameClass(((Polygon) o).shell);
    }
}
