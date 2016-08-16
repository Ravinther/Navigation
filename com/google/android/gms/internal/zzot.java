package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.internal.zzx;
import java.util.HashMap;
import java.util.Map;

public final class zzot implements ActivityLifecycleCallbacks {
    private final zzoj zzaIR;
    private final Map<Activity, zzoq> zzaIS;

    public zzot(zzoj com_google_android_gms_internal_zzoj) {
        zzx.zzv(com_google_android_gms_internal_zzoj);
        this.zzaIR = com_google_android_gms_internal_zzoj;
        this.zzaIS = new HashMap();
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle("com.google.android.gms.measurement.screen_view");
            if (bundle != null) {
                int i = bundle.getInt("id");
                if (i <= 0) {
                    Log.w("com.google.android.gms.measurement.internal.ActivityLifecycleTracker", "Invalid screenId in saved activity state");
                    return;
                }
                zzoq zza = zza(activity, i);
                zza.setScreenName(bundle.getString("name"));
                zza.zzhT(bundle.getInt("referrer_id"));
                zza.zzdU(bundle.getString("referrer_name"));
                zza.zzam(bundle.getBoolean("interstitial"));
                zza.zzxX();
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
        this.zzaIS.remove(activity);
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (outState != null) {
            zzoq com_google_android_gms_internal_zzoq = (zzoq) this.zzaIS.get(activity);
            if (com_google_android_gms_internal_zzoq != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", com_google_android_gms_internal_zzoq.zzbp());
                bundle.putString("name", com_google_android_gms_internal_zzoq.zzxT());
                bundle.putInt("referrer_id", com_google_android_gms_internal_zzoq.zzxU());
                bundle.putString("referrer_name", com_google_android_gms_internal_zzoq.zzxV());
                bundle.putBoolean("interstitial", com_google_android_gms_internal_zzoq.zzxY());
                outState.putBundle("com.google.android.gms.measurement.screen_view", bundle);
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        this.zzaIR.zzb(zza(activity, 0), activity);
    }

    public void onActivityStopped(Activity activity) {
    }

    zzoq zza(Activity activity, int i) {
        zzx.zzv(activity);
        zzoq com_google_android_gms_internal_zzoq = (zzoq) this.zzaIS.get(activity);
        if (com_google_android_gms_internal_zzoq == null) {
            com_google_android_gms_internal_zzoq = i == 0 ? new zzoq(true) : new zzoq(true, i);
            com_google_android_gms_internal_zzoq.setScreenName(activity.getClass().getCanonicalName());
            this.zzaIS.put(activity, com_google_android_gms_internal_zzoq);
        }
        return com_google_android_gms_internal_zzoq;
    }
}
