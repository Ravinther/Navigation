package com.google.android.gms.tagmanager;

class zzcb {
    private static zzcb zzaQY;
    private volatile String zzaOS;
    private volatile zza zzaQZ;
    private volatile String zzaRa;
    private volatile String zzaRb;

    enum zza {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzcb() {
        clear();
    }

    static zzcb zzAv() {
        zzcb com_google_android_gms_tagmanager_zzcb;
        synchronized (zzcb.class) {
            if (zzaQY == null) {
                zzaQY = new zzcb();
            }
            com_google_android_gms_tagmanager_zzcb = zzaQY;
        }
        return com_google_android_gms_tagmanager_zzcb;
    }

    void clear() {
        this.zzaQZ = zza.NONE;
        this.zzaRa = null;
        this.zzaOS = null;
        this.zzaRb = null;
    }

    String getContainerId() {
        return this.zzaOS;
    }

    zza zzAw() {
        return this.zzaQZ;
    }

    String zzAx() {
        return this.zzaRa;
    }
}
