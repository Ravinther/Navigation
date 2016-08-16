package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.tagmanager.Container;

class zzqe {
    private final Context mContext;
    private final zzqf zzpo;

    static class zza implements com.google.android.gms.internal.zzoj.zza {
        private final Tracker zzKq;

        zza(Tracker tracker) {
            this.zzKq = tracker;
        }

        public void zza(zzoq com_google_android_gms_internal_zzoq) {
            this.zzKq.setScreenName(com_google_android_gms_internal_zzoq.zzxT());
            ScreenViewBuilder screenViewBuilder = new ScreenViewBuilder();
            screenViewBuilder.set("&a", String.valueOf(com_google_android_gms_internal_zzoq.zzbp()));
            this.zzKq.send(screenViewBuilder.build());
        }

        public void zza(zzoq com_google_android_gms_internal_zzoq, Activity activity) {
        }
    }

    public zzqe(Context context, Container container, zzqf com_google_android_gms_internal_zzqf) {
        this.mContext = context;
        this.zzpo = zza(container, com_google_android_gms_internal_zzqf);
        zzBi();
    }

    private void zzBi() {
        if (this.zzpo.zzBk() && !TextUtils.isEmpty(this.zzpo.getTrackingId())) {
            Tracker zzfg = zzfg(this.zzpo.getTrackingId());
            zzfg.enableAdvertisingIdCollection(this.zzpo.zzBl());
            zzb(new zza(zzfg));
        }
    }

    static zzqf zza(Container container, zzqf com_google_android_gms_internal_zzqf) {
        if (container == null || container.isDefault()) {
            return com_google_android_gms_internal_zzqf;
        }
        com.google.android.gms.internal.zzqf.zza com_google_android_gms_internal_zzqf_zza = new com.google.android.gms.internal.zzqf.zza(com_google_android_gms_internal_zzqf.zzBj());
        com_google_android_gms_internal_zzqf_zza.zzfh(container.getString("trackingId")).zzau(container.getBoolean("trackScreenViews")).zzav(container.getBoolean("collectAdIdentifiers"));
        return com_google_android_gms_internal_zzqf_zza.zzBm();
    }

    public zzqf zzBh() {
        return this.zzpo;
    }

    void zzb(com.google.android.gms.internal.zzoj.zza com_google_android_gms_internal_zzoj_zza) {
        zzx.zzv(com_google_android_gms_internal_zzoj_zza);
        zzoj zzaJ = zzoj.zzaJ(this.mContext);
        zzaJ.zzaj(true);
        zzaJ.zza(com_google_android_gms_internal_zzoj_zza);
    }

    Tracker zzfg(String str) {
        return GoogleAnalytics.getInstance(this.mContext).newTracker(str);
    }
}
