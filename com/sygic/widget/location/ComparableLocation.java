package com.sygic.widget.location;

import android.location.Location;
import android.os.Build.VERSION;

public class ComparableLocation extends Location {
    public ComparableLocation(String provider) {
        super(provider);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r9) {
        /*
        r8 = this;
        r2 = 1;
        r3 = 0;
        if (r8 != r9) goto L_0x0006;
    L_0x0004:
        r3 = r2;
    L_0x0005:
        return r3;
    L_0x0006:
        if (r9 == 0) goto L_0x0005;
    L_0x0008:
        r4 = r8.getClass();
        r5 = r9.getClass();
        if (r4 != r5) goto L_0x0005;
    L_0x0012:
        r1 = r9;
        r1 = (com.sygic.widget.location.ComparableLocation) r1;
        r4 = r8.getTime();
        r6 = r1.getTime();
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 != 0) goto L_0x0005;
    L_0x0021:
        r4 = r1.getLatitude();
        r6 = r8.getLatitude();
        r4 = java.lang.Double.compare(r4, r6);
        if (r4 != 0) goto L_0x0005;
    L_0x002f:
        r4 = r1.getLongitude();
        r6 = r8.getLongitude();
        r4 = java.lang.Double.compare(r4, r6);
        if (r4 != 0) goto L_0x0005;
    L_0x003d:
        r4 = r8.hasAltitude();
        r5 = r1.hasAltitude();
        if (r4 != r5) goto L_0x0005;
    L_0x0047:
        r4 = r1.getAltitude();
        r6 = r8.getAltitude();
        r4 = java.lang.Double.compare(r4, r6);
        if (r4 != 0) goto L_0x0005;
    L_0x0055:
        r4 = r8.hasSpeed();
        r5 = r1.hasSpeed();
        if (r4 != r5) goto L_0x0005;
    L_0x005f:
        r4 = r1.getSpeed();
        r5 = r8.getSpeed();
        r4 = java.lang.Float.compare(r4, r5);
        if (r4 != 0) goto L_0x0005;
    L_0x006d:
        r4 = r8.hasBearing();
        r5 = r1.hasBearing();
        if (r4 != r5) goto L_0x0005;
    L_0x0077:
        r4 = r1.getBearing();
        r5 = r8.getBearing();
        r4 = java.lang.Float.compare(r4, r5);
        if (r4 != 0) goto L_0x0005;
    L_0x0085:
        r4 = r8.hasAccuracy();
        r5 = r1.hasAccuracy();
        if (r4 != r5) goto L_0x0005;
    L_0x008f:
        r4 = r1.getAccuracy();
        r5 = r8.getAccuracy();
        r4 = java.lang.Float.compare(r4, r5);
        if (r4 != 0) goto L_0x0005;
    L_0x009d:
        r4 = r8.getProvider();
        if (r4 == 0) goto L_0x00e9;
    L_0x00a3:
        r4 = r8.getProvider();
        r5 = r1.getProvider();
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x0005;
    L_0x00b1:
        r0 = android.os.Build.VERSION.SDK_INT;
        r4 = 17;
        if (r0 < r4) goto L_0x00c3;
    L_0x00b7:
        r4 = r8.getElapsedRealtimeNanos();
        r6 = r1.getElapsedRealtimeNanos();
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 != 0) goto L_0x0005;
    L_0x00c3:
        r4 = 18;
        if (r0 < r4) goto L_0x00d1;
    L_0x00c7:
        r4 = r8.isFromMockProvider();
        r5 = r1.isFromMockProvider();
        if (r4 != r5) goto L_0x0005;
    L_0x00d1:
        r4 = r8.getExtras();
        if (r4 == 0) goto L_0x00f1;
    L_0x00d7:
        r4 = r8.getExtras();
        r5 = r1.getExtras();
        r4 = r4.equals(r5);
        if (r4 != 0) goto L_0x00e6;
    L_0x00e5:
        r2 = r3;
    L_0x00e6:
        r3 = r2;
        goto L_0x0005;
    L_0x00e9:
        r4 = r1.getProvider();
        if (r4 == 0) goto L_0x00b1;
    L_0x00ef:
        goto L_0x0005;
    L_0x00f1:
        r4 = r1.getExtras();
        if (r4 != 0) goto L_0x00e5;
    L_0x00f7:
        goto L_0x00e6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.widget.location.ComparableLocation.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int result;
        int i;
        int i2 = 1;
        if (getProvider() != null) {
            result = getProvider().hashCode();
        } else {
            result = 0;
        }
        result = (result * 31) + ((int) (getTime() ^ (getTime() >>> 32)));
        long temp = Double.doubleToLongBits(getLatitude());
        result = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        temp = Double.doubleToLongBits(getLongitude());
        int i3 = ((result * 31) + ((int) ((temp >>> 32) ^ temp))) * 31;
        if (hasAltitude()) {
            i = 1;
        } else {
            i = 0;
        }
        result = i3 + i;
        temp = Double.doubleToLongBits(getAltitude());
        i3 = ((result * 31) + ((int) ((temp >>> 32) ^ temp))) * 31;
        if (hasSpeed()) {
            i = 1;
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (getSpeed() != 0.0f) {
            i = Float.floatToIntBits(getSpeed());
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (hasBearing()) {
            i = 1;
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (getBearing() != 0.0f) {
            i = Float.floatToIntBits(getBearing());
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (hasAccuracy()) {
            i = 1;
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (getAccuracy() != 0.0f) {
            i = Float.floatToIntBits(getAccuracy());
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (getExtras() != null) {
            i = getExtras().hashCode();
        } else {
            i = 0;
        }
        result = i3 + i;
        int build = VERSION.SDK_INT;
        if (build >= 17) {
            result = (result * 31) + ((int) (getElapsedRealtimeNanos() ^ (getElapsedRealtimeNanos() >>> 32)));
        }
        if (build < 18) {
            return result;
        }
        i = result * 31;
        if (!isFromMockProvider()) {
            i2 = 0;
        }
        return i + i2;
    }
}
