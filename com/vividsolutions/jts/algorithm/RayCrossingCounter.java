package com.vividsolutions.jts.algorithm;

import com.vividsolutions.jts.geom.Coordinate;

public class RayCrossingCounter {
    private int crossingCount;
    private boolean isPointOnSegment;
    private Coordinate f1294p;

    public static int locatePointInRing(Coordinate p, Coordinate[] ring) {
        RayCrossingCounter counter = new RayCrossingCounter(p);
        for (int i = 1; i < ring.length; i++) {
            counter.countSegment(ring[i], ring[i - 1]);
            if (counter.isOnSegment()) {
                return counter.getLocation();
            }
        }
        return counter.getLocation();
    }

    public RayCrossingCounter(Coordinate p) {
        this.crossingCount = 0;
        this.isPointOnSegment = false;
        this.f1294p = p;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void countSegment(com.vividsolutions.jts.geom.Coordinate r21, com.vividsolutions.jts.geom.Coordinate r22) {
        /*
        r20 = this;
        r0 = r21;
        r0 = r0.f1295x;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1295x;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 >= 0) goto L_0x002d;
    L_0x0016:
        r0 = r22;
        r0 = r0.f1295x;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1295x;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 >= 0) goto L_0x002d;
    L_0x002c:
        return;
    L_0x002d:
        r0 = r20;
        r0 = r0.f1294p;
        r16 = r0;
        r0 = r16;
        r0 = r0.f1295x;
        r16 = r0;
        r0 = r22;
        r0 = r0.f1295x;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 != 0) goto L_0x0062;
    L_0x0043:
        r0 = r20;
        r0 = r0.f1294p;
        r16 = r0;
        r0 = r16;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r22;
        r0 = r0.f1296y;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 != 0) goto L_0x0062;
    L_0x0059:
        r16 = 1;
        r0 = r16;
        r1 = r20;
        r1.isPointOnSegment = r0;
        goto L_0x002c;
    L_0x0062:
        r0 = r21;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 != 0) goto L_0x00cc;
    L_0x0078:
        r0 = r22;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 != 0) goto L_0x00cc;
    L_0x008e:
        r0 = r21;
        r12 = r0.f1295x;
        r0 = r22;
        r10 = r0.f1295x;
        r16 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
        if (r16 <= 0) goto L_0x00a2;
    L_0x009a:
        r0 = r22;
        r12 = r0.f1295x;
        r0 = r21;
        r10 = r0.f1295x;
    L_0x00a2:
        r0 = r20;
        r0 = r0.f1294p;
        r16 = r0;
        r0 = r16;
        r0 = r0.f1295x;
        r16 = r0;
        r16 = (r16 > r12 ? 1 : (r16 == r12 ? 0 : -1));
        if (r16 < 0) goto L_0x002c;
    L_0x00b2:
        r0 = r20;
        r0 = r0.f1294p;
        r16 = r0;
        r0 = r16;
        r0 = r0.f1295x;
        r16 = r0;
        r16 = (r16 > r10 ? 1 : (r16 == r10 ? 0 : -1));
        if (r16 > 0) goto L_0x002c;
    L_0x00c2:
        r16 = 1;
        r0 = r16;
        r1 = r20;
        r1.isPointOnSegment = r0;
        goto L_0x002c;
    L_0x00cc:
        r0 = r21;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 <= 0) goto L_0x00f8;
    L_0x00e2:
        r0 = r22;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 <= 0) goto L_0x0124;
    L_0x00f8:
        r0 = r22;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 <= 0) goto L_0x002c;
    L_0x010e:
        r0 = r21;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 > 0) goto L_0x002c;
    L_0x0124:
        r0 = r21;
        r0 = r0.f1295x;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1295x;
        r18 = r0;
        r2 = r16 - r18;
        r0 = r21;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r4 = r16 - r18;
        r0 = r22;
        r0 = r0.f1295x;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1295x;
        r18 = r0;
        r6 = r16 - r18;
        r0 = r22;
        r0 = r0.f1296y;
        r16 = r0;
        r0 = r20;
        r0 = r0.f1294p;
        r18 = r0;
        r0 = r18;
        r0 = r0.f1296y;
        r18 = r0;
        r8 = r16 - r18;
        r16 = com.vividsolutions.jts.algorithm.RobustDeterminant.signOfDet2x2(r2, r4, r6, r8);
        r0 = r16;
        r14 = (double) r0;
        r16 = 0;
        r16 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r16 != 0) goto L_0x018b;
    L_0x0181:
        r16 = 1;
        r0 = r16;
        r1 = r20;
        r1.isPointOnSegment = r0;
        goto L_0x002c;
    L_0x018b:
        r16 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1));
        if (r16 >= 0) goto L_0x0190;
    L_0x018f:
        r14 = -r14;
    L_0x0190:
        r16 = 0;
        r16 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r16 <= 0) goto L_0x002c;
    L_0x0196:
        r0 = r20;
        r0 = r0.crossingCount;
        r16 = r0;
        r16 = r16 + 1;
        r0 = r16;
        r1 = r20;
        r1.crossingCount = r0;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vividsolutions.jts.algorithm.RayCrossingCounter.countSegment(com.vividsolutions.jts.geom.Coordinate, com.vividsolutions.jts.geom.Coordinate):void");
    }

    public boolean isOnSegment() {
        return this.isPointOnSegment;
    }

    public int getLocation() {
        if (this.isPointOnSegment) {
            return 1;
        }
        if (this.crossingCount % 2 == 1) {
            return 0;
        }
        return 2;
    }
}
