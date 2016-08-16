package com.vividsolutions.jts.geom.impl;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Envelope;
import java.io.Serializable;

public class CoordinateArraySequence implements CoordinateSequence, Serializable {
    private Coordinate[] coordinates;
    private int dimension;

    public CoordinateArraySequence(Coordinate[] coordinates) {
        this(coordinates, 3);
    }

    public CoordinateArraySequence(Coordinate[] coordinates, int dimension) {
        this.dimension = 3;
        this.coordinates = coordinates;
        this.dimension = dimension;
        if (coordinates == null) {
            this.coordinates = new Coordinate[0];
        }
    }

    public Coordinate getCoordinate(int i) {
        return this.coordinates[i];
    }

    public void getCoordinate(int index, Coordinate coord) {
        coord.f1295x = this.coordinates[index].f1295x;
        coord.f1296y = this.coordinates[index].f1296y;
        coord.f1297z = this.coordinates[index].f1297z;
    }

    public double getX(int index) {
        return this.coordinates[index].f1295x;
    }

    public double getY(int index) {
        return this.coordinates[index].f1296y;
    }

    public Object clone() {
        Coordinate[] cloneCoordinates = new Coordinate[size()];
        for (int i = 0; i < this.coordinates.length; i++) {
            cloneCoordinates[i] = (Coordinate) this.coordinates[i].clone();
        }
        return new CoordinateArraySequence(cloneCoordinates);
    }

    public int size() {
        return this.coordinates.length;
    }

    public Coordinate[] toCoordinateArray() {
        return this.coordinates;
    }

    public Envelope expandEnvelope(Envelope env) {
        for (Coordinate expandToInclude : this.coordinates) {
            env.expandToInclude(expandToInclude);
        }
        return env;
    }

    public String toString() {
        if (this.coordinates.length <= 0) {
            return "()";
        }
        StringBuffer strBuf = new StringBuffer(this.coordinates.length * 17);
        strBuf.append('(');
        strBuf.append(this.coordinates[0]);
        for (int i = 1; i < this.coordinates.length; i++) {
            strBuf.append(", ");
            strBuf.append(this.coordinates[i]);
        }
        strBuf.append(')');
        return strBuf.toString();
    }
}
