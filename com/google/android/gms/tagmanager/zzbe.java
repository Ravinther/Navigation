package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzlm;

class zzbe implements zzcd {
    private final long zzOe;
    private final int zzOf;
    private double zzOg;
    private long zzOh;
    private final Object zzOi;
    private final String zzOj;
    private final long zzaQB;
    private final zzlm zzpO;

    public zzbe(int i, long j, long j2, String str, zzlm com_google_android_gms_internal_zzlm) {
        this.zzOi = new Object();
        this.zzOf = i;
        this.zzOg = (double) this.zzOf;
        this.zzOe = j;
        this.zzaQB = j2;
        this.zzOj = str;
        this.zzpO = com_google_android_gms_internal_zzlm;
    }

    public boolean zzkp() {
        boolean z = false;
        synchronized (this.zzOi) {
            long currentTimeMillis = this.zzpO.currentTimeMillis();
            if (currentTimeMillis - this.zzOh < this.zzaQB) {
                zzbg.zzaE("Excessive " + this.zzOj + " detected; call ignored.");
            } else {
                if (this.zzOg < ((double) this.zzOf)) {
                    double d = ((double) (currentTimeMillis - this.zzOh)) / ((double) this.zzOe);
                    if (d > 0.0d) {
                        this.zzOg = Math.min((double) this.zzOf, d + this.zzOg);
                    }
                }
                this.zzOh = currentTimeMillis;
                if (this.zzOg >= 1.0d) {
                    this.zzOg -= 1.0d;
                    z = true;
                } else {
                    zzbg.zzaE("Excessive " + this.zzOj + " detected; call ignored.");
                }
            }
        }
        return z;
    }
}
