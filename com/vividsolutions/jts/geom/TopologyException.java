package com.vividsolutions.jts.geom;

public class TopologyException extends RuntimeException {
    private Coordinate pt;

    private static String msgWithCoord(String msg, Coordinate pt) {
        if (pt != null) {
            return msg + " [ " + pt + " ]";
        }
        return msg;
    }

    public TopologyException(String msg, Coordinate pt) {
        super(msgWithCoord(msg, pt));
        this.pt = null;
        this.pt = new Coordinate(pt);
    }
}
