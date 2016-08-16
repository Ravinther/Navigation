package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Coordinate;

public class EdgeIntersection implements Comparable {
    public Coordinate coord;
    public double dist;
    public int segmentIndex;

    public EdgeIntersection(Coordinate coord, int segmentIndex, double dist) {
        this.coord = new Coordinate(coord);
        this.segmentIndex = segmentIndex;
        this.dist = dist;
    }

    public int compareTo(Object obj) {
        EdgeIntersection other = (EdgeIntersection) obj;
        return compare(other.segmentIndex, other.dist);
    }

    public int compare(int segmentIndex, double dist) {
        if (this.segmentIndex < segmentIndex) {
            return -1;
        }
        if (this.segmentIndex > segmentIndex) {
            return 1;
        }
        if (this.dist < dist) {
            return -1;
        }
        if (this.dist > dist) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return this.coord + " seg # = " + this.segmentIndex + " dist = " + this.dist;
    }
}
