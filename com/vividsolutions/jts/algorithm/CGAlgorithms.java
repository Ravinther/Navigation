package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;

public class CGAlgorithms {
    public static int orientationIndex(Coordinate p1, Coordinate p2, Coordinate q) {
        return CGAlgorithmsDD.orientationIndex(p1, p2, q);
    }

    public static boolean isPointInRing(Coordinate p, Coordinate[] ring) {
        return locatePointInRing(p, ring) != 2;
    }

    public static int locatePointInRing(Coordinate p, Coordinate[] ring) {
        return RayCrossingCounter.locatePointInRing(p, ring);
    }

    public static boolean isOnLine(Coordinate p, Coordinate[] pt) {
        LineIntersector lineIntersector = new RobustLineIntersector();
        for (int i = 1; i < pt.length; i++) {
            lineIntersector.computeIntersection(p, pt[i - 1], pt[i]);
            if (lineIntersector.hasIntersection()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCCW(Coordinate[] ring) {
        int nPts = ring.length - 1;
        if (nPts < 3) {
            throw new IllegalArgumentException("Ring has fewer than 3 points, so orientation cannot be determined");
        }
        Coordinate hiPt = ring[0];
        int hiIndex = 0;
        for (int i = 1; i <= nPts; i++) {
            Coordinate p = ring[i];
            if (p.f1296y > hiPt.f1296y) {
                hiPt = p;
                hiIndex = i;
            }
        }
        int iPrev = hiIndex;
        do {
            iPrev--;
            if (iPrev < 0) {
                iPrev = nPts;
            }
            if (!ring[iPrev].equals2D(hiPt)) {
                break;
            }
        } while (iPrev != hiIndex);
        int iNext = hiIndex;
        do {
            iNext = (iNext + 1) % nPts;
            if (!ring[iNext].equals2D(hiPt)) {
                break;
            }
        } while (iNext != hiIndex);
        Coordinate prev = ring[iPrev];
        Coordinate next = ring[iNext];
        if (prev.equals2D(hiPt) || next.equals2D(hiPt) || prev.equals2D(next)) {
            return false;
        }
        int disc = computeOrientation(prev, hiPt, next);
        if (disc == 0) {
            return prev.f1295x > next.f1295x;
        }
        return disc > 0;
    }

    public static int computeOrientation(Coordinate p1, Coordinate p2, Coordinate q) {
        return orientationIndex(p1, p2, q);
    }
}
