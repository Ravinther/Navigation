package com.vividsolutions.jts.geom;

import java.util.ArrayList;

public class CoordinateList extends ArrayList {
    private static final Coordinate[] coordArrayType;

    static {
        coordArrayType = new Coordinate[0];
    }

    public CoordinateList(Coordinate[] coord, boolean allowRepeated) {
        ensureCapacity(coord.length);
        add(coord, allowRepeated);
    }

    public boolean add(Coordinate[] coord, boolean allowRepeated, boolean direction) {
        int i;
        if (direction) {
            for (Coordinate add : coord) {
                add(add, allowRepeated);
            }
        } else {
            for (i = coord.length - 1; i >= 0; i--) {
                add(coord[i], allowRepeated);
            }
        }
        return true;
    }

    public boolean add(Coordinate[] coord, boolean allowRepeated) {
        add(coord, allowRepeated, true);
        return true;
    }

    public void add(Coordinate coord, boolean allowRepeated) {
        if (allowRepeated || size() < 1 || !((Coordinate) get(size() - 1)).equals2D(coord)) {
            super.add(coord);
        }
    }

    public Coordinate[] toCoordinateArray() {
        return (Coordinate[]) toArray(coordArrayType);
    }

    public Object clone() {
        CoordinateList clone = (CoordinateList) super.clone();
        for (int i = 0; i < size(); i++) {
            clone.add(i, ((Coordinate) get(i)).clone());
        }
        return clone;
    }
}
