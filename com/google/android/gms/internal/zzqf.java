package com.google.android.gms.internal;

public final class zzqf {
    private final boolean zzaTm;
    private final boolean zzaTn;
    private final String zzaTo;
    private final String zztw;

    public static final class zza {
        private boolean zzaTm;
        private boolean zzaTn;
        private final String zzaTo;
        private String zztw;

        public zza(String str) {
            this.zzaTm = true;
            this.zzaTn = false;
            this.zzaTo = str;
        }

        public zzqf zzBm() {
            return new zzqf();
        }

        public zza zzau(boolean z) {
            this.zzaTm = z;
            return this;
        }

        public zza zzav(boolean z) {
            this.zzaTn = z;
            return this;
        }

        public zza zzfh(String str) {
            this.zztw = str;
            return this;
        }
    }

    private zzqf(zza com_google_android_gms_internal_zzqf_zza) {
        this.zzaTo = com_google_android_gms_internal_zzqf_zza.zzaTo;
        this.zzaTm = com_google_android_gms_internal_zzqf_zza.zzaTm;
        this.zzaTn = com_google_android_gms_internal_zzqf_zza.zzaTn;
        this.zztw = com_google_android_gms_internal_zzqf_zza.zztw;
    }

    public String getTrackingId() {
        return this.zztw;
    }

    public String zzBj() {
        return this.zzaTo;
    }

    public boolean zzBk() {
        return this.zzaTm;
    }

    public boolean zzBl() {
        return this.zzaTn;
    }
}
