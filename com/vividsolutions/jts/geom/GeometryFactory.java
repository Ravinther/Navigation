package com.vividsolutions.jts.geom;

import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;
import java.io.Serializable;

public class GeometryFactory implements Serializable {
    private int SRID;
    private CoordinateSequenceFactory coordinateSequenceFactory;
    private PrecisionModel precisionModel;

    public GeometryFactory(PrecisionModel precisionModel, int SRID, CoordinateSequenceFactory coordinateSequenceFactory) {
        this.precisionModel = precisionModel;
        this.coordinateSequenceFactory = coordinateSequenceFactory;
        this.SRID = SRID;
    }

    public GeometryFactory(PrecisionModel precisionModel, int SRID) {
        this(precisionModel, SRID, getDefaultCoordinateSequenceFactory());
    }

    public GeometryFactory() {
        this(new PrecisionModel(), 0);
    }

    private static CoordinateSequenceFactory getDefaultCoordinateSequenceFactory() {
        return CoordinateArraySequenceFactory.instance();
    }

    public PrecisionModel getPrecisionModel() {
        return this.precisionModel;
    }

    public Point createPoint(Coordinate coordinate) {
        CoordinateSequence create;
        if (coordinate != null) {
            create = getCoordinateSequenceFactory().create(new Coordinate[]{coordinate});
        } else {
            create = null;
        }
        return createPoint(create);
    }

    public Point createPoint(CoordinateSequence coordinates) {
        return new Point(coordinates, this);
    }

    public LinearRing createLinearRing(Coordinate[] coordinates) {
        return createLinearRing(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
    }

    public LinearRing createLinearRing(CoordinateSequence coordinates) {
        return new LinearRing(coordinates, this);
    }

    public Polygon createPolygon(LinearRing shell, LinearRing[] holes) {
        return new Polygon(shell, holes, this);
    }

    public Polygon createPolygon(LinearRing shell) {
        return createPolygon(shell, null);
    }

    public int getSRID() {
        return this.SRID;
    }

    public CoordinateSequenceFactory getCoordinateSequenceFactory() {
        return this.coordinateSequenceFactory;
    }
}
