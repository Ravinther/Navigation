package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.List;

public class zzoj {
    private static final zza[] zzaIm;
    private static zzoj zzaIn;
    private final Application zzaIo;
    private zzoq zzaIp;
    private final List<zza> zzaIq;
    private zzot zzaIr;

    public interface zza {
        void zza(zzoq com_google_android_gms_internal_zzoq);

        void zza(zzoq com_google_android_gms_internal_zzoq, Activity activity);
    }

    static {
        zzaIm = new zza[0];
    }

    private zzoj(Application application) {
        zzx.zzv(application);
        this.zzaIo = application;
        this.zzaIq = new ArrayList();
    }

    public static zzoj zzaJ(Context context) {
        zzoj com_google_android_gms_internal_zzoj;
        zzx.zzv(context);
        Application application = (Application) context.getApplicationContext();
        zzx.zzv(application);
        synchronized (zzoj.class) {
            if (zzaIn == null) {
                zzaIn = new zzoj(application);
            }
            com_google_android_gms_internal_zzoj = zzaIn;
        }
        return com_google_android_gms_internal_zzoj;
    }

    private zza[] zzxz() {
        zza[] com_google_android_gms_internal_zzoj_zzaArr;
        synchronized (this.zzaIq) {
            if (this.zzaIq.isEmpty()) {
                com_google_android_gms_internal_zzoj_zzaArr = zzaIm;
            } else {
                com_google_android_gms_internal_zzoj_zzaArr = (zza[]) this.zzaIq.toArray(new zza[this.zzaIq.size()]);
            }
        }
        return com_google_android_gms_internal_zzoj_zzaArr;
    }

    public void zza(zza com_google_android_gms_internal_zzoj_zza) {
        zzx.zzv(com_google_android_gms_internal_zzoj_zza);
        synchronized (this.zzaIq) {
            this.zzaIq.remove(com_google_android_gms_internal_zzoj_zza);
            this.zzaIq.add(com_google_android_gms_internal_zzoj_zza);
        }
    }

    public void zzaj(boolean z) {
        if (VERSION.SDK_INT < 14) {
            Log.i("com.google.android.gms.measurement.ScreenViewService", "AutoScreeViewTracking is not supported on API 14 or earlier devices");
        } else if (zzxy() == z) {
        } else {
            if (z) {
                this.zzaIr = new zzot(this);
                this.zzaIo.registerActivityLifecycleCallbacks(this.zzaIr);
                return;
            }
            this.zzaIo.unregisterActivityLifecycleCallbacks(this.zzaIr);
            this.zzaIr = null;
        }
    }

    public void zzb(zzoq com_google_android_gms_internal_zzoq, Activity activity) {
        int i = 0;
        zzx.zzv(com_google_android_gms_internal_zzoq);
        zza[] com_google_android_gms_internal_zzoj_zzaArr = null;
        if (com_google_android_gms_internal_zzoq.isMutable()) {
            if (activity instanceof zzoi) {
                ((zzoi) activity).zzb(com_google_android_gms_internal_zzoq);
            }
            if (this.zzaIp != null) {
                com_google_android_gms_internal_zzoq.zzhT(this.zzaIp.zzbp());
                com_google_android_gms_internal_zzoq.zzdU(this.zzaIp.zzxT());
            }
            zza[] zzxz = zzxz();
            for (zza zza : zzxz) {
                zza.zza(com_google_android_gms_internal_zzoq, activity);
            }
            com_google_android_gms_internal_zzoq.zzxX();
            if (!TextUtils.isEmpty(com_google_android_gms_internal_zzoq.zzxT())) {
                com_google_android_gms_internal_zzoj_zzaArr = zzxz;
            } else {
                return;
            }
        }
        if (this.zzaIp == null || this.zzaIp.zzbp() != com_google_android_gms_internal_zzoq.zzbp()) {
            zzxx();
            this.zzaIp = com_google_android_gms_internal_zzoq;
            if (com_google_android_gms_internal_zzoj_zzaArr == null) {
                com_google_android_gms_internal_zzoj_zzaArr = zzxz();
            }
            while (i < com_google_android_gms_internal_zzoj_zzaArr.length) {
                com_google_android_gms_internal_zzoj_zzaArr[i].zza(com_google_android_gms_internal_zzoq);
                i++;
            }
            return;
        }
        this.zzaIp = com_google_android_gms_internal_zzoq;
    }

    public zzoq zzxw() {
        return this.zzaIp;
    }

    public void zzxx() {
        this.zzaIp = null;
    }

    public boolean zzxy() {
        return this.zzaIr != null;
    }
}
