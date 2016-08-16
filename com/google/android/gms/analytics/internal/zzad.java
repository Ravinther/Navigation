package com.google.android.gms.analytics.internal;

public class zzad {
    private final long zzOe;
    private final int zzOf;
    private double zzOg;
    private long zzOh;
    private final Object zzOi;
    private final String zzOj;

    public zzad(int i, long j, String str) {
        this.zzOi = new Object();
        this.zzOf = i;
        this.zzOg = (double) this.zzOf;
        this.zzOe = j;
        this.zzOj = str;
    }

    public zzad(String str) {
        this(60, 2000, str);
    }

    public boolean zzkp() {
        boolean z;
        synchronized (this.zzOi) {
            long currentTimeMillis = System.currentTimeMillis();
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
                zzae.zzaE("Excessive " + this.zzOj + " detected; call ignored.");
                z = false;
            }
        }
        return z;
    }
}
