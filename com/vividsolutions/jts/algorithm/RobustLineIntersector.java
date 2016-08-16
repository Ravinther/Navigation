package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

public class RobustLineIntersector extends LineIntersector {
    public void computeIntersection(Coordinate p, Coordinate p1, Coordinate p2) {
        this.isProper = false;
        if (Envelope.intersects(p1, p2, p) && CGAlgorithms.orientationIndex(p1, p2, p) == 0 && CGAlgorithms.orientationIndex(p2, p1, p) == 0) {
            this.isProper = true;
            if (p.equals(p1) || p.equals(p2)) {
                this.isProper = false;
            }
            this.result = 1;
            return;
        }
        this.result = 0;
    }

    protected int computeIntersect(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
        this.isProper = false;
        if (!Envelope.intersects(p1, p2, q1, q2)) {
            return 0;
        }
        int Pq1 = CGAlgorithms.orientationIndex(p1, p2, q1);
        int Pq2 = CGAlgorithms.orientationIndex(p1, p2, q2);
        if (Pq1 > 0 && Pq2 > 0) {
            return 0;
        }
        if (Pq1 < 0 && Pq2 < 0) {
            return 0;
        }
        int Qp1 = CGAlgorithms.orientationIndex(q1, q2, p1);
        int Qp2 = CGAlgorithms.orientationIndex(q1, q2, p2);
        if (Qp1 > 0 && Qp2 > 0) {
            return 0;
        }
        if (Qp1 < 0 && Qp2 < 0) {
            return 0;
        }
        boolean collinear;
        if (Pq1 == 0 && Pq2 == 0 && Qp1 == 0 && Qp2 == 0) {
            collinear = true;
        } else {
            collinear = false;
        }
        if (collinear) {
            return computeCollinearIntersection(p1, p2, q1, q2);
        }
        if (Pq1 == 0 || Pq2 == 0 || Qp1 == 0 || Qp2 == 0) {
            this.isProper = false;
            if (p1.equals2D(q1) || p1.equals2D(q2)) {
                this.intPt[0] = p1;
            } else if (p2.equals2D(q1) || p2.equals2D(q2)) {
                this.intPt[0] = p2;
            } else if (Pq1 == 0) {
                this.intPt[0] = new Coordinate(q1);
            } else if (Pq2 == 0) {
                this.intPt[0] = new Coordinate(q2);
            } else if (Qp1 == 0) {
                this.intPt[0] = new Coordinate(p1);
            } else if (Qp2 == 0) {
                this.intPt[0] = new Coordinate(p2);
            }
        } else {
            this.isProper = true;
            this.intPt[0] = intersection(p1, p2, q1, q2);
        }
        return 1;
    }

    private int computeCollinearIntersection(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
        boolean p1q1p2 = Envelope.intersects(p1, p2, q1);
        boolean p1q2p2 = Envelope.intersects(p1, p2, q2);
        boolean q1p1q2 = Envelope.intersects(q1, q2, p1);
        boolean q1p2q2 = Envelope.intersects(q1, q2, p2);
        if (p1q1p2 && p1q2p2) {
            this.intPt[0] = q1;
            this.intPt[1] = q2;
            return 2;
        } else if (q1p1q2 && q1p2q2) {
            this.intPt[0] = p1;
            this.intPt[1] = p2;
            return 2;
        } else if (p1q1p2 && q1p1q2) {
            this.intPt[0] = q1;
            this.intPt[1] = p1;
            if (!q1.equals(p1) || p1q2p2 || q1p2q2) {
                return 2;
            }
            return 1;
        } else if (p1q1p2 && q1p2q2) {
            this.intPt[0] = q1;
            this.intPt[1] = p2;
            if (!q1.equals(p2) || p1q2p2 || q1p1q2) {
                return 2;
            }
            return 1;
        } else if (p1q2p2 && q1p1q2) {
            this.intPt[0] = q2;
            this.intPt[1] = p1;
            if (!q2.equals(p1) || p1q1p2 || q1p2q2) {
                return 2;
            }
            return 1;
        } else if (!p1q2p2 || !q1p2q2) {
            return 0;
        } else {
            this.intPt[0] = q2;
            this.intPt[1] = p2;
            if (!q2.equals(p2) || p1q1p2 || q1p1q2) {
                return 2;
            }
            return 1;
        }
    }

    private Coordinate intersection(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
        Coordinate intPt = intersectionWithNormalization(p1, p2, q1, q2);
        if (!isInSegmentEnvelopes(intPt)) {
            intPt = CentralEndpointIntersector.getIntersection(p1, p2, q1, q2);
        }
        if (this.precisionModel != null) {
            this.precisionModel.makePrecise(intPt);
        }
        return intPt;
    }

    private Coordinate intersectionWithNormalization(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
        Coordinate n1 = new Coordinate(p1);
        Coordinate n2 = new Coordinate(p2);
        Coordinate n3 = new Coordinate(q1);
        Coordinate n4 = new Coordinate(q2);
        Coordinate normPt = new Coordinate();
        normalizeToEnvCentre(n1, n2, n3, n4, normPt);
        Coordinate intPt = safeHCoordinateIntersection(n1, n2, n3, n4);
        intPt.f1295x += normPt.f1295x;
        intPt.f1296y += normPt.f1296y;
        return intPt;
    }

    private Coordinate safeHCoordinateIntersection(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
        try {
            return HCoordinate.intersection(p1, p2, q1, q2);
        } catch (NotRepresentableException e) {
            return CentralEndpointIntersector.getIntersection(p1, p2, q1, q2);
        }
    }

    private void normalizeToEnvCentre(Coordinate n00, Coordinate n01, Coordinate n10, Coordinate n11, Coordinate normPt) {
        double intMinX;
        double intMaxX;
        double intMinY;
        double intMaxY;
        double minX0 = n00.f1295x < n01.f1295x ? n00.f1295x : n01.f1295x;
        double minY0 = n00.f1296y < n01.f1296y ? n00.f1296y : n01.f1296y;
        double maxX0 = n00.f1295x > n01.f1295x ? n00.f1295x : n01.f1295x;
        double maxY0 = n00.f1296y > n01.f1296y ? n00.f1296y : n01.f1296y;
        double minX1 = n10.f1295x < n11.f1295x ? n10.f1295x : n11.f1295x;
        double minY1 = n10.f1296y < n11.f1296y ? n10.f1296y : n11.f1296y;
        double maxX1 = n10.f1295x > n11.f1295x ? n10.f1295x : n11.f1295x;
        double maxY1 = n10.f1296y > n11.f1296y ? n10.f1296y : n11.f1296y;
        if (minX0 > minX1) {
            intMinX = minX0;
        } else {
            intMinX = minX1;
        }
        if (maxX0 < maxX1) {
            intMaxX = maxX0;
        } else {
            intMaxX = maxX1;
        }
        if (minY0 > minY1) {
            intMinY = minY0;
        } else {
            intMinY = minY1;
        }
        if (maxY0 < maxY1) {
            intMaxY = maxY0;
        } else {
            intMaxY = maxY1;
        }
        double intMidY = (intMinY + intMaxY) / 2.0d;
        normPt.f1295x = (intMinX + intMaxX) / 2.0d;
        normPt.f1296y = intMidY;
        n00.f1295x -= normPt.f1295x;
        n00.f1296y -= normPt.f1296y;
        n01.f1295x -= normPt.f1295x;
        n01.f1296y -= normPt.f1296y;
        n10.f1295x -= normPt.f1295x;
        n10.f1296y -= normPt.f1296y;
        n11.f1295x -= normPt.f1295x;
        n11.f1296y -= normPt.f1296y;
    }

    private boolean isInSegmentEnvelopes(Coordinate intPt) {
        Envelope env0 = new Envelope(this.inputLines[0][0], this.inputLines[0][1]);
        Envelope env1 = new Envelope(this.inputLines[1][0], this.inputLines[1][1]);
        if (env0.contains(intPt) && env1.contains(intPt)) {
            return true;
        }
        return false;
    }
}
