package com.vividsolutions.jts.geom;

import java.io.Serializable;

public class Envelope implements Serializable {
    private double maxx;
    private double maxy;
    private double minx;
    private double miny;

    public int hashCode() {
        return ((((((Coordinate.hashCode(this.minx) + 629) * 37) + Coordinate.hashCode(this.maxx)) * 37) + Coordinate.hashCode(this.miny)) * 37) + Coordinate.hashCode(this.maxy);
    }

    public static boolean intersects(Coordinate p1, Coordinate p2, Coordinate q) {
        if (q.f1295x >= (p1.f1295x < p2.f1295x ? p1.f1295x : p2.f1295x)) {
            if (q.f1295x <= (p1.f1295x > p2.f1295x ? p1.f1295x : p2.f1295x)) {
                if (q.f1296y >= (p1.f1296y < p2.f1296y ? p1.f1296y : p2.f1296y)) {
                    if (q.f1296y <= (p1.f1296y > p2.f1296y ? p1.f1296y : p2.f1296y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean intersects(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
        double minq = Math.min(q1.f1295x, q2.f1295x);
        double maxq = Math.max(q1.f1295x, q2.f1295x);
        double minp = Math.min(p1.f1295x, p2.f1295x);
        double maxp = Math.max(p1.f1295x, p2.f1295x);
        if (minp > maxq) {
            return false;
        }
        if (maxp < minq) {
            return false;
        }
        minq = Math.min(q1.f1296y, q2.f1296y);
        maxq = Math.max(q1.f1296y, q2.f1296y);
        minp = Math.min(p1.f1296y, p2.f1296y);
        maxp = Math.max(p1.f1296y, p2.f1296y);
        if (minp > maxq) {
            return false;
        }
        if (maxp < minq) {
            return false;
        }
        return true;
    }

    public Envelope() {
        init();
    }

    public Envelope(Coordinate p1, Coordinate p2) {
        init(p1.f1295x, p2.f1295x, p1.f1296y, p2.f1296y);
    }

    public Envelope(Envelope env) {
        init(env);
    }

    public void init() {
        setToNull();
    }

    public void init(double x1, double x2, double y1, double y2) {
        if (x1 < x2) {
            this.minx = x1;
            this.maxx = x2;
        } else {
            this.minx = x2;
            this.maxx = x1;
        }
        if (y1 < y2) {
            this.miny = y1;
            this.maxy = y2;
            return;
        }
        this.miny = y2;
        this.maxy = y1;
    }

    public void init(Coordinate p1, Coordinate p2) {
        init(p1.f1295x, p2.f1295x, p1.f1296y, p2.f1296y);
    }

    public void init(Envelope env) {
        this.minx = env.minx;
        this.maxx = env.maxx;
        this.miny = env.miny;
        this.maxy = env.maxy;
    }

    public void setToNull() {
        this.minx = 0.0d;
        this.maxx = -1.0d;
        this.miny = 0.0d;
        this.maxy = -1.0d;
    }

    public boolean isNull() {
        return this.maxx < this.minx;
    }

    public double getMinX() {
        return this.minx;
    }

    public double getMaxX() {
        return this.maxx;
    }

    public double getMinY() {
        return this.miny;
    }

    public double getMaxY() {
        return this.maxy;
    }

    public void expandToInclude(Coordinate p) {
        expandToInclude(p.f1295x, p.f1296y);
    }

    public void expandToInclude(double x, double y) {
        if (isNull()) {
            this.minx = x;
            this.maxx = x;
            this.miny = y;
            this.maxy = y;
            return;
        }
        if (x < this.minx) {
            this.minx = x;
        }
        if (x > this.maxx) {
            this.maxx = x;
        }
        if (y < this.miny) {
            this.miny = y;
        }
        if (y > this.maxy) {
            this.maxy = y;
        }
    }

    public void expandToInclude(Envelope other) {
        if (!other.isNull()) {
            if (isNull()) {
                this.minx = other.getMinX();
                this.maxx = other.getMaxX();
                this.miny = other.getMinY();
                this.maxy = other.getMaxY();
                return;
            }
            if (other.minx < this.minx) {
                this.minx = other.minx;
            }
            if (other.maxx > this.maxx) {
                this.maxx = other.maxx;
            }
            if (other.miny < this.miny) {
                this.miny = other.miny;
            }
            if (other.maxy > this.maxy) {
                this.maxy = other.maxy;
            }
        }
    }

    public boolean intersects(Envelope other) {
        if (isNull() || other.isNull() || other.minx > this.maxx || other.maxx < this.minx || other.miny > this.maxy || other.maxy < this.miny) {
            return false;
        }
        return true;
    }

    public boolean intersects(Coordinate p) {
        return intersects(p.f1295x, p.f1296y);
    }

    public boolean intersects(double x, double y) {
        if (!isNull() && x <= this.maxx && x >= this.minx && y <= this.maxy && y >= this.miny) {
            return true;
        }
        return false;
    }

    public boolean contains(Envelope other) {
        return covers(other);
    }

    public boolean contains(Coordinate p) {
        return covers(p);
    }

    public boolean covers(double x, double y) {
        if (!isNull() && x >= this.minx && x <= this.maxx && y >= this.miny && y <= this.maxy) {
            return true;
        }
        return false;
    }

    public boolean covers(Coordinate p) {
        return covers(p.f1295x, p.f1296y);
    }

    public boolean covers(Envelope other) {
        if (isNull() || other.isNull() || other.getMinX() < this.minx || other.getMaxX() > this.maxx || other.getMinY() < this.miny || other.getMaxY() > this.maxy) {
            return false;
        }
        return true;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Envelope)) {
            return false;
        }
        Envelope otherEnvelope = (Envelope) other;
        if (isNull()) {
            return otherEnvelope.isNull();
        }
        if (this.maxx == otherEnvelope.getMaxX() && this.maxy == otherEnvelope.getMaxY() && this.minx == otherEnvelope.getMinX() && this.miny == otherEnvelope.getMinY()) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Env[" + this.minx + " : " + this.maxx + ", " + this.miny + " : " + this.maxy + "]";
    }
}
