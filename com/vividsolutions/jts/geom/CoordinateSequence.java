package com.vividsolutions.jts.geom;

public interface CoordinateSequence extends Cloneable {
    Object clone();

    Envelope expandEnvelope(Envelope envelope);

    Coordinate getCoordinate(int i);

    void getCoordinate(int i, Coordinate coordinate);

    double getX(int i);

    double getY(int i);

    int size();

    Coordinate[] toCoordinateArray();
}
