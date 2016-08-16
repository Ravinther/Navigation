package com.google.android.gms.ads.internal.overlay;

import com.google.android.gms.internal.zzhu;

class zzq implements Runnable {
    private boolean mCancelled;
    private zzk zzAy;

    zzq(zzk com_google_android_gms_ads_internal_overlay_zzk) {
        this.mCancelled = false;
        this.zzAy = com_google_android_gms_ads_internal_overlay_zzk;
    }

    public void cancel() {
        this.mCancelled = true;
        zzhu.zzHK.removeCallbacks(this);
    }

    public void run() {
        if (!this.mCancelled) {
            this.zzAy.zzeR();
            zzfa();
        }
    }

    public void zzfa() {
        zzhu.zzHK.postDelayed(this, 250);
    }
}
