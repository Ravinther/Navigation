package com.vividsolutions.jts.geom;

import java.util.Arrays;
import java.util.TreeSet;

public class GeometryCollection extends Geometry {
    protected Geometry[] geometries;

    public boolean isEmpty() {
        for (Geometry isEmpty : this.geometries) {
            if (!isEmpty.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getDimension() {
        int dimension = -1;
        for (Geometry dimension2 : this.geometries) {
            dimension = Math.max(dimension, dimension2.getDimension());
        }
        return dimension;
    }

    public int getBoundaryDimension() {
        int dimension = -1;
        for (Geometry boundaryDimension : this.geometries) {
            dimension = Math.max(dimension, boundaryDimension.getBoundaryDimension());
        }
        return dimension;
    }

    public int getNumGeometries() {
        return this.geometries.length;
    }

    public Geometry getGeometryN(int n) {
        return this.geometries[n];
    }

    public boolean equalsExact(Geometry other, double tolerance) {
        if (!isEquivalentClass(other)) {
            return false;
        }
        GeometryCollection otherCollection = (GeometryCollection) other;
        if (this.geometries.length != otherCollection.geometries.length) {
            return false;
        }
        for (int i = 0; i < this.geometries.length; i++) {
            if (!this.geometries[i].equalsExact(otherCollection.geometries[i], tolerance)) {
                return false;
            }
        }
        return true;
    }

    public Object clone() {
        GeometryCollection gc = (GeometryCollection) super.clone();
        gc.geometries = new Geometry[this.geometries.length];
        for (int i = 0; i < this.geometries.length; i++) {
            gc.geometries[i] = (Geometry) this.geometries[i].clone();
        }
        return gc;
    }

    protected Envelope computeEnvelopeInternal() {
        Envelope envelope = new Envelope();
        for (Geometry envelopeInternal : this.geometries) {
            envelope.expandToInclude(envelopeInternal.getEnvelopeInternal());
        }
        return envelope;
    }

    protected int compareToSameClass(Object o) {
        return compare(new TreeSet(Arrays.asList(this.geometries)), new TreeSet(Arrays.asList(((GeometryCollection) o).geometries)));
    }
}
