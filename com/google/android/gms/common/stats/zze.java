package com.google.android.gms.common.stats;

import android.support.v4.util.SimpleArrayMap;

public class zze {
    private final long zzafP;
    private final int zzafQ;
    private final SimpleArrayMap<String, Long> zzafR;

    public zze() {
        this.zzafP = 60000;
        this.zzafQ = 10;
        this.zzafR = new SimpleArrayMap(10);
    }

    public zze(int i, long j) {
        this.zzafP = j;
        this.zzafQ = i;
        this.zzafR = new SimpleArrayMap();
    }

    private void zzb(long j, long j2) {
        for (int size = this.zzafR.size() - 1; size >= 0; size--) {
            if (j2 - ((Long) this.zzafR.valueAt(size)).longValue() > j) {
                this.zzafR.removeAt(size);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Long zzcy(java.lang.String r8) {
        /*
        r7 = this;
        r2 = android.os.SystemClock.elapsedRealtime();
        r0 = r7.zzafP;
        monitor-enter(r7);
    L_0x0007:
        r4 = r7.zzafR;	 Catch:{ all -> 0x003f }
        r4 = r4.size();	 Catch:{ all -> 0x003f }
        r5 = r7.zzafQ;	 Catch:{ all -> 0x003f }
        if (r4 < r5) goto L_0x0042;
    L_0x0011:
        r7.zzb(r0, r2);	 Catch:{ all -> 0x003f }
        r4 = 2;
        r0 = r0 / r4;
        r4 = "ConnectionTracker";
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003f }
        r5.<init>();	 Catch:{ all -> 0x003f }
        r6 = "The max capacity ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x003f }
        r6 = r7.zzafQ;	 Catch:{ all -> 0x003f }
        r5 = r5.append(r6);	 Catch:{ all -> 0x003f }
        r6 = " is not enough. Current durationThreshold is: ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x003f }
        r5 = r5.append(r0);	 Catch:{ all -> 0x003f }
        r5 = r5.toString();	 Catch:{ all -> 0x003f }
        android.util.Log.w(r4, r5);	 Catch:{ all -> 0x003f }
        goto L_0x0007;
    L_0x003f:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x003f }
        throw r0;
    L_0x0042:
        r0 = r7.zzafR;	 Catch:{ all -> 0x003f }
        r1 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x003f }
        r0 = r0.put(r8, r1);	 Catch:{ all -> 0x003f }
        r0 = (java.lang.Long) r0;	 Catch:{ all -> 0x003f }
        monitor-exit(r7);	 Catch:{ all -> 0x003f }
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.stats.zze.zzcy(java.lang.String):java.lang.Long");
    }

    public boolean zzcz(String str) {
        boolean z;
        synchronized (this) {
            z = this.zzafR.remove(str) != null;
        }
        return z;
    }
}
