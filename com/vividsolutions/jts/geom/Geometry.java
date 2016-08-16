package com.vividsolutions.jts.geom;

import com.vividsolutions.jts.io.WKTWriter;
import com.vividsolutions.jts.operation.predicate.RectangleContains;
import com.vividsolutions.jts.operation.relate.RelateOp;
import com.vividsolutions.jts.util.Assert;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class Geometry implements Serializable, Cloneable, Comparable {
    private static final GeometryComponentFilter geometryChangedFilter;
    private static Class[] sortedClasses;
    protected int SRID;
    protected Envelope envelope;
    protected final GeometryFactory factory;
    private Object userData;

    /* renamed from: com.vividsolutions.jts.geom.Geometry.1 */
    static class C18041 implements GeometryComponentFilter {
        C18041() {
        }
    }

    protected abstract int compareToSameClass(Object obj);

    protected abstract Envelope computeEnvelopeInternal();

    public abstract boolean equalsExact(Geometry geometry, double d);

    public abstract int getBoundaryDimension();

    public abstract int getDimension();

    public abstract boolean isEmpty();

    static {
        geometryChangedFilter = new C18041();
    }

    public Geometry(GeometryFactory factory) {
        this.userData = null;
        this.factory = factory;
        this.SRID = factory.getSRID();
    }

    protected static boolean hasNonEmptyElements(Geometry[] geometries) {
        for (Geometry isEmpty : geometries) {
            if (!isEmpty.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    protected static boolean hasNullElements(Object[] array) {
        for (Object obj : array) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    public GeometryFactory getFactory() {
        return this.factory;
    }

    public int getNumGeometries() {
        return 1;
    }

    public Geometry getGeometryN(int n) {
        return this;
    }

    public PrecisionModel getPrecisionModel() {
        return this.factory.getPrecisionModel();
    }

    public boolean isRectangle() {
        return false;
    }

    public Envelope getEnvelopeInternal() {
        if (this.envelope == null) {
            this.envelope = computeEnvelopeInternal();
        }
        return new Envelope(this.envelope);
    }

    public boolean contains(Geometry g) {
        if (!getEnvelopeInternal().contains(g.getEnvelopeInternal())) {
            return false;
        }
        if (isRectangle()) {
            return RectangleContains.contains((Polygon) this, g);
        }
        return relate(g).isContains();
    }

    public IntersectionMatrix relate(Geometry g) {
        checkNotGeometryCollection(this);
        checkNotGeometryCollection(g);
        return RelateOp.relate(this, g);
    }

    public boolean equals(Object o) {
        if (o instanceof Geometry) {
            return equalsExact((Geometry) o);
        }
        return false;
    }

    public int hashCode() {
        return getEnvelopeInternal().hashCode();
    }

    public String toString() {
        return toText();
    }

    public String toText() {
        return new WKTWriter().write(this);
    }

    public boolean equalsExact(Geometry other) {
        return equalsExact(other, 0.0d);
    }

    public Object clone() {
        try {
            Geometry clone = (Geometry) super.clone();
            if (clone.envelope == null) {
                return clone;
            }
            clone.envelope = new Envelope(clone.envelope);
            return clone;
        } catch (CloneNotSupportedException e) {
            Assert.shouldNeverReachHere();
            return null;
        }
    }

    public int compareTo(Object o) {
        Geometry other = (Geometry) o;
        if (getClassSortIndex() != other.getClassSortIndex()) {
            return getClassSortIndex() - other.getClassSortIndex();
        }
        if (isEmpty() && other.isEmpty()) {
            return 0;
        }
        if (isEmpty()) {
            return -1;
        }
        if (other.isEmpty()) {
            return 1;
        }
        return compareToSameClass(o);
    }

    protected boolean isEquivalentClass(Geometry other) {
        return getClass().getName().equals(other.getClass().getName());
    }

    protected void checkNotGeometryCollection(Geometry g) {
        if (g.getClass().getName().equals("com.vividsolutions.jts.geom.GeometryCollection")) {
            throw new IllegalArgumentException("This method does not support GeometryCollection arguments");
        }
    }

    protected int compare(Collection a, Collection b) {
        Iterator i = a.iterator();
        Iterator j = b.iterator();
        while (i.hasNext() && j.hasNext()) {
            int comparison = ((Comparable) i.next()).compareTo((Comparable) j.next());
            if (comparison != 0) {
                return comparison;
            }
        }
        if (i.hasNext()) {
            return 1;
        }
        if (j.hasNext()) {
            return -1;
        }
        return 0;
    }

    protected boolean equal(Coordinate a, Coordinate b, double tolerance) {
        if (tolerance == 0.0d) {
            return a.equals(b);
        }
        return a.distance(b) <= tolerance;
    }

    private int getClassSortIndex() {
        if (sortedClasses == null) {
            initSortedClasses();
        }
        for (int i = 0; i < sortedClasses.length; i++) {
            if (sortedClasses[i].isInstance(this)) {
                return i;
            }
        }
        Assert.shouldNeverReachHere("Class not supported: " + getClass());
        return -1;
    }

    private static void initSortedClasses() {
        sortedClasses = new Class[]{Point.class, MultiPoint.class, LineString.class, LinearRing.class, MultiLineString.class, Polygon.class, MultiPolygon.class, GeometryCollection.class};
    }
}
