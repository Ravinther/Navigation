package com.vividsolutions.jts.geom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GeometryCollectionIterator implements Iterator {
    private boolean atStart;
    private int index;
    private int max;
    private Geometry parent;
    private GeometryCollectionIterator subcollectionIterator;

    public GeometryCollectionIterator(Geometry parent) {
        this.parent = parent;
        this.atStart = true;
        this.index = 0;
        this.max = parent.getNumGeometries();
    }

    public boolean hasNext() {
        if (this.atStart) {
            return true;
        }
        if (this.subcollectionIterator != null) {
            if (this.subcollectionIterator.hasNext()) {
                return true;
            }
            this.subcollectionIterator = null;
        }
        if (this.index >= this.max) {
            return false;
        }
        return true;
    }

    public Object next() {
        if (this.atStart) {
            this.atStart = false;
            return this.parent;
        }
        if (this.subcollectionIterator != null) {
            if (this.subcollectionIterator.hasNext()) {
                return this.subcollectionIterator.next();
            }
            this.subcollectionIterator = null;
        }
        if (this.index >= this.max) {
            throw new NoSuchElementException();
        }
        Geometry geometry = this.parent;
        int i = this.index;
        this.index = i + 1;
        Object obj = geometry.getGeometryN(i);
        if (!(obj instanceof GeometryCollection)) {
            return obj;
        }
        this.subcollectionIterator = new GeometryCollectionIterator((GeometryCollection) obj);
        return this.subcollectionIterator.next();
    }

    public void remove() {
        throw new UnsupportedOperationException(getClass().getName());
    }
}
