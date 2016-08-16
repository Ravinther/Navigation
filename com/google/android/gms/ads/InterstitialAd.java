package com.google.android.gms.ads;

import android.content.Context;
import com.google.android.gms.ads.internal.client.zza;
import com.google.android.gms.ads.internal.client.zzz;

public final class InterstitialAd {
    private final zzz zznT;

    public InterstitialAd(Context context) {
        this.zznT = new zzz(context);
    }

    public void loadAd(AdRequest adRequest) {
        this.zznT.zza(adRequest.zzaF());
    }

    public void setAdListener(AdListener adListener) {
        this.zznT.setAdListener(adListener);
        if (adListener != null && (adListener instanceof zza)) {
            this.zznT.zza((zza) adListener);
        } else if (adListener == null) {
            this.zznT.zza(null);
        }
    }

    public void setAdUnitId(String adUnitId) {
        this.zznT.setAdUnitId(adUnitId);
    }

    public void show() {
        this.zznT.show();
    }
}
