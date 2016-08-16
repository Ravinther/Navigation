package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.math.DD;

public class CGAlgorithmsDD {
    public static int orientationIndex(Coordinate p1, Coordinate p2, Coordinate q) {
        int index = orientationIndexFilter(p1, p2, q);
        if (index <= 1) {
            return index;
        }
        return DD.valueOf(p2.f1295x).selfAdd(-p1.f1295x).selfMultiply(DD.valueOf(q.f1296y).selfAdd(-p2.f1296y)).selfSubtract(DD.valueOf(p2.f1296y).selfAdd(-p1.f1296y).selfMultiply(DD.valueOf(q.f1295x).selfAdd(-p2.f1295x))).signum();
    }

    private static int orientationIndexFilter(Coordinate pa, Coordinate pb, Coordinate pc) {
        double detsum;
        double detleft = (pa.f1295x - pc.f1295x) * (pb.f1296y - pc.f1296y);
        double detright = (pa.f1296y - pc.f1296y) * (pb.f1295x - pc.f1295x);
        double det = detleft - detright;
        if (detleft > 0.0d) {
            if (detright <= 0.0d) {
                return signum(det);
            }
            detsum = detleft + detright;
        } else if (detleft >= 0.0d) {
            return signum(det);
        } else {
            if (detright >= 0.0d) {
                return signum(det);
            }
            detsum = (-detleft) - detright;
        }
        double errbound = 1.0E-15d * detsum;
        if (det >= errbound || (-det) >= errbound) {
            return signum(det);
        }
        return 2;
    }

    private static int signum(double x) {
        if (x > 0.0d) {
            return 1;
        }
        if (x < 0.0d) {
            return -1;
        }
        return 0;
    }
}
