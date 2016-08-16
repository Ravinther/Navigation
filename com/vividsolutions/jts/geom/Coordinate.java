package com.vividsolutions.jts.geom;

import com.vividsolutions.jts.util.Assert;
import java.io.Serializable;

public class Coordinate implements Serializable, Cloneable, Comparable {
    public double f1295x;
    public double f1296y;
    public double f1297z;

    public Coordinate(double x, double y, double z) {
        this.f1295x = x;
        this.f1296y = y;
        this.f1297z = z;
    }

    public Coordinate() {
        this(0.0d, 0.0d);
    }

    public Coordinate(Coordinate c) {
        this(c.f1295x, c.f1296y, c.f1297z);
    }

    public Coordinate(double x, double y) {
        this(x, y, Double.NaN);
    }

    public boolean equals2D(Coordinate other) {
        if (this.f1295x == other.f1295x && this.f1296y == other.f1296y) {
            return true;
        }
        return false;
    }

    public boolean equals(Object other) {
        if (other instanceof Coordinate) {
            return equals2D((Coordinate) other);
        }
        return false;
    }

    public int compareTo(Object o) {
        Coordinate other = (Coordinate) o;
        if (this.f1295x < other.f1295x) {
            return -1;
        }
        if (this.f1295x > other.f1295x) {
            return 1;
        }
        if (this.f1296y < other.f1296y) {
            return -1;
        }
        if (this.f1296y > other.f1296y) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return "(" + this.f1295x + ", " + this.f1296y + ", " + this.f1297z + ")";
    }

    public Object clone() {
        try {
            return (Coordinate) super.clone();
        } catch (CloneNotSupportedException e) {
            Assert.shouldNeverReachHere("this shouldn't happen because this class is Cloneable");
            return null;
        }
    }

    public double distance(Coordinate p) {
        double dx = this.f1295x - p.f1295x;
        double dy = this.f1296y - p.f1296y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public int hashCode() {
        return ((hashCode(this.f1295x) + 629) * 37) + hashCode(this.f1296y);
    }

    public static int hashCode(double x) {
        long f = Double.doubleToLongBits(x);
        return (int) ((f >>> 32) ^ f);
    }
}
