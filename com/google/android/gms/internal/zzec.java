package com.google.android.gms.internal;

import com.google.android.gms.internal.zzej.zza;

@zzgk
public final class zzec extends zza {
    private final Object zzpc;
    private zzee.zza zzyF;
    private zzeb zzyG;

    public zzec() {
        this.zzpc = new Object();
    }

    public void onAdClicked() {
        synchronized (this.zzpc) {
            if (this.zzyG != null) {
                this.zzyG.zzaX();
            }
        }
    }

    public void onAdClosed() {
        synchronized (this.zzpc) {
            if (this.zzyG != null) {
                this.zzyG.zzaY();
            }
        }
    }

    public void onAdFailedToLoad(int error) {
        synchronized (this.zzpc) {
            if (this.zzyF != null) {
                this.zzyF.zzq(error == 3 ? 1 : 2);
                this.zzyF = null;
            }
        }
    }

    public void onAdLeftApplication() {
        synchronized (this.zzpc) {
            if (this.zzyG != null) {
                this.zzyG.zzaZ();
            }
        }
    }

    public void onAdLoaded() {
        synchronized (this.zzpc) {
            if (this.zzyF != null) {
                this.zzyF.zzq(0);
                this.zzyF = null;
                return;
            }
            if (this.zzyG != null) {
                this.zzyG.zzbb();
            }
        }
    }

    public void onAdOpened() {
        synchronized (this.zzpc) {
            if (this.zzyG != null) {
                this.zzyG.zzba();
            }
        }
    }

    public void zza(zzeb com_google_android_gms_internal_zzeb) {
        synchronized (this.zzpc) {
            this.zzyG = com_google_android_gms_internal_zzeb;
        }
    }

    public void zza(zzee.zza com_google_android_gms_internal_zzee_zza) {
        synchronized (this.zzpc) {
            this.zzyF = com_google_android_gms_internal_zzee_zza;
        }
    }
}
