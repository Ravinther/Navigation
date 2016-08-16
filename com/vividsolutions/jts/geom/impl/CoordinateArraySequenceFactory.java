package com.vividsolutions.jts.geom.impl;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequenceFactory;
import java.io.Serializable;

public final class CoordinateArraySequenceFactory implements CoordinateSequenceFactory, Serializable {
    private static CoordinateArraySequenceFactory instanceObject;

    static {
        instanceObject = new CoordinateArraySequenceFactory();
    }

    private CoordinateArraySequenceFactory() {
    }

    public static CoordinateArraySequenceFactory instance() {
        return instanceObject;
    }

    public CoordinateSequence create(Coordinate[] coordinates) {
        return new CoordinateArraySequence(coordinates);
    }
}
