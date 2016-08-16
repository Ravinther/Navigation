package com.google.android.gms.internal;

import java.util.LinkedHashMap;

public class zzlf<K, V> {
    private int size;
    private int zzaeA;
    private int zzaeB;
    private int zzaeC;
    private int zzaeD;
    private final LinkedHashMap<K, V> zzaex;
    private int zzaey;
    private int zzaez;

    private int zzc(K k, V v) {
        int sizeOf = sizeOf(k, v);
        if (sizeOf >= 0) {
            return sizeOf;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    protected V create(K k) {
        return null;
    }

    protected void entryRemoved(boolean evicted, K k, V v, V v2) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final V get(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v = this.zzaex.get(key);
            if (v != null) {
                this.zzaeC++;
                return v;
            }
            this.zzaeD++;
            V create = create(key);
            if (create == null) {
                return null;
            }
            synchronized (this) {
                this.zzaeA++;
                v = this.zzaex.put(key, create);
                if (v != null) {
                    this.zzaex.put(key, v);
                } else {
                    this.size += zzc(key, create);
                }
            }
            if (v != null) {
                entryRemoved(false, key, create, v);
                return v;
            }
            trimToSize(this.zzaey);
            return create;
        }
    }

    public final V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        V put;
        synchronized (this) {
            this.zzaez++;
            this.size += zzc(key, value);
            put = this.zzaex.put(key, value);
            if (put != null) {
                this.size -= zzc(key, put);
            }
        }
        if (put != null) {
            entryRemoved(false, key, put, value);
        }
        trimToSize(this.zzaey);
        return put;
    }

    protected int sizeOf(K k, V v) {
        return 1;
    }

    public final synchronized String toString() {
        String format;
        int i = 0;
        synchronized (this) {
            int i2 = this.zzaeC + this.zzaeD;
            if (i2 != 0) {
                i = (this.zzaeC * 100) / i2;
            }
            format = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.zzaey), Integer.valueOf(this.zzaeC), Integer.valueOf(this.zzaeD), Integer.valueOf(i)});
        }
        return format;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r5) {
        /*
        r4 = this;
    L_0x0000:
        monitor-enter(r4);
        r0 = r4.size;	 Catch:{ all -> 0x0033 }
        if (r0 < 0) goto L_0x0011;
    L_0x0005:
        r0 = r4.zzaex;	 Catch:{ all -> 0x0033 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0036;
    L_0x000d:
        r0 = r4.size;	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0036;
    L_0x0011:
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0033 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0033 }
        r1.<init>();	 Catch:{ all -> 0x0033 }
        r2 = r4.getClass();	 Catch:{ all -> 0x0033 }
        r2 = r2.getName();	 Catch:{ all -> 0x0033 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x0033 }
        r2 = ".sizeOf() is reporting inconsistent results!";
        r1 = r1.append(r2);	 Catch:{ all -> 0x0033 }
        r1 = r1.toString();	 Catch:{ all -> 0x0033 }
        r0.<init>(r1);	 Catch:{ all -> 0x0033 }
        throw r0;	 Catch:{ all -> 0x0033 }
    L_0x0033:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0033 }
        throw r0;
    L_0x0036:
        r0 = r4.size;	 Catch:{ all -> 0x0033 }
        if (r0 <= r5) goto L_0x0042;
    L_0x003a:
        r0 = r4.zzaex;	 Catch:{ all -> 0x0033 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0044;
    L_0x0042:
        monitor-exit(r4);	 Catch:{ all -> 0x0033 }
        return;
    L_0x0044:
        r0 = r4.zzaex;	 Catch:{ all -> 0x0033 }
        r0 = r0.entrySet();	 Catch:{ all -> 0x0033 }
        r0 = r0.iterator();	 Catch:{ all -> 0x0033 }
        r0 = r0.next();	 Catch:{ all -> 0x0033 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ all -> 0x0033 }
        r1 = r0.getKey();	 Catch:{ all -> 0x0033 }
        r0 = r0.getValue();	 Catch:{ all -> 0x0033 }
        r2 = r4.zzaex;	 Catch:{ all -> 0x0033 }
        r2.remove(r1);	 Catch:{ all -> 0x0033 }
        r2 = r4.size;	 Catch:{ all -> 0x0033 }
        r3 = r4.zzc(r1, r0);	 Catch:{ all -> 0x0033 }
        r2 = r2 - r3;
        r4.size = r2;	 Catch:{ all -> 0x0033 }
        r2 = r4.zzaeB;	 Catch:{ all -> 0x0033 }
        r2 = r2 + 1;
        r4.zzaeB = r2;	 Catch:{ all -> 0x0033 }
        monitor-exit(r4);	 Catch:{ all -> 0x0033 }
        r2 = 1;
        r3 = 0;
        r4.entryRemoved(r2, r1, r0, r3);
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzlf.trimToSize(int):void");
    }
}
