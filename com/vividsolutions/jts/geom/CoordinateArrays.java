package com.vividsolutions.jts.geom;

public class CoordinateArrays {
    private static final Coordinate[] coordArrayType;

    static {
        coordArrayType = new Coordinate[0];
    }

    public static boolean hasRepeatedPoints(Coordinate[] coord) {
        for (int i = 1; i < coord.length; i++) {
            if (coord[i - 1].equals(coord[i])) {
                return true;
            }
        }
        return false;
    }

    public static Coordinate[] removeRepeatedPoints(Coordinate[] coord) {
        return !hasRepeatedPoints(coord) ? coord : new CoordinateList(coord, false).toCoordinateArray();
    }
}
