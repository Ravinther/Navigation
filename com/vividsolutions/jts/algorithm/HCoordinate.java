package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;

public class HCoordinate {
    public static Coordinate intersection(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) throws NotRepresentableException {
        double px = p1.f1296y - p2.f1296y;
        double py = p2.f1295x - p1.f1295x;
        double pw = (p1.f1295x * p2.f1296y) - (p2.f1295x * p1.f1296y);
        double qx = q1.f1296y - q2.f1296y;
        double qy = q2.f1295x - q1.f1295x;
        double qw = (q1.f1295x * q2.f1296y) - (q2.f1295x * q1.f1296y);
        double w = (px * qy) - (qx * py);
        double xInt = ((py * qw) - (qy * pw)) / w;
        double yInt = ((qx * pw) - (px * qw)) / w;
        if (!Double.isNaN(xInt) && !Double.isInfinite(xInt) && !Double.isNaN(yInt) && !Double.isInfinite(yInt)) {
            return new Coordinate(xInt, yInt);
        }
        throw new NotRepresentableException();
    }
}
