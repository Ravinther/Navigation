package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzee.zza;
import java.util.List;

@zzgk
public final class zzed implements zza {
    private final Context mContext;
    private final NativeAdOptionsParcel zzoX;
    private final List<String> zzoY;
    private final zzeh zzow;
    private final VersionInfoParcel zzpa;
    private final Object zzpc;
    private final AdRequestParcel zzpz;
    private final String zzyH;
    private final long zzyI;
    private final zzdz zzyJ;
    private final AdSizeParcel zzyK;
    private zzei zzyL;
    private int zzyM;
    private final boolean zzyf;

    /* renamed from: com.google.android.gms.internal.zzed.1 */
    class C09111 implements Runnable {
        final /* synthetic */ zzec zzyN;
        final /* synthetic */ zzed zzyO;

        C09111(zzed com_google_android_gms_internal_zzed, zzec com_google_android_gms_internal_zzec) {
            this.zzyO = com_google_android_gms_internal_zzed;
            this.zzyN = com_google_android_gms_internal_zzec;
        }

        public void run() {
            synchronized (this.zzyO.zzpc) {
                if (this.zzyO.zzyM != -2) {
                    return;
                }
                this.zzyO.zzyL = this.zzyO.zzdR();
                if (this.zzyO.zzyL == null) {
                    this.zzyO.zzq(4);
                    return;
                }
                this.zzyN.zza(this.zzyO);
                this.zzyO.zza(this.zzyN);
            }
        }
    }

    public zzed(Context context, String str, zzeh com_google_android_gms_internal_zzeh, zzea com_google_android_gms_internal_zzea, zzdz com_google_android_gms_internal_zzdz, AdRequestParcel adRequestParcel, AdSizeParcel adSizeParcel, VersionInfoParcel versionInfoParcel, boolean z, NativeAdOptionsParcel nativeAdOptionsParcel, List<String> list) {
        this.zzpc = new Object();
        this.zzyM = -2;
        this.mContext = context;
        this.zzow = com_google_android_gms_internal_zzeh;
        this.zzyJ = com_google_android_gms_internal_zzdz;
        if ("com.google.ads.mediation.customevent.CustomEventAdapter".equals(str)) {
            this.zzyH = zzdQ();
        } else {
            this.zzyH = str;
        }
        this.zzyI = com_google_android_gms_internal_zzea.zzyv != -1 ? com_google_android_gms_internal_zzea.zzyv : 10000;
        this.zzpz = adRequestParcel;
        this.zzyK = adSizeParcel;
        this.zzpa = versionInfoParcel;
        this.zzyf = z;
        this.zzoX = nativeAdOptionsParcel;
        this.zzoY = list;
    }

    private void zza(long j, long j2, long j3, long j4) {
        while (this.zzyM == -2) {
            zzb(j, j2, j3, j4);
        }
    }

    private void zza(zzec com_google_android_gms_internal_zzec) {
        if ("com.google.ads.mediation.AdUrlAdapter".equals(this.zzyH)) {
            Bundle bundle = this.zzpz.zzsA.getBundle(this.zzyH);
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("sdk_less_network_id", this.zzyJ.zzyl);
            this.zzpz.zzsA.putBundle(this.zzyH, bundle);
        }
        try {
            if (this.zzpa.zzIB < 4100000) {
                if (this.zzyK.zzsH) {
                    this.zzyL.zza(zze.zzx(this.mContext), this.zzpz, this.zzyJ.zzyr, com_google_android_gms_internal_zzec);
                } else {
                    this.zzyL.zza(zze.zzx(this.mContext), this.zzyK, this.zzpz, this.zzyJ.zzyr, (zzej) com_google_android_gms_internal_zzec);
                }
            } else if (this.zzyf) {
                this.zzyL.zza(zze.zzx(this.mContext), this.zzpz, this.zzyJ.zzyr, this.zzyJ.zzyk, com_google_android_gms_internal_zzec, this.zzoX, this.zzoY);
            } else if (this.zzyK.zzsH) {
                this.zzyL.zza(zze.zzx(this.mContext), this.zzpz, this.zzyJ.zzyr, this.zzyJ.zzyk, (zzej) com_google_android_gms_internal_zzec);
            } else {
                this.zzyL.zza(zze.zzx(this.mContext), this.zzyK, this.zzpz, this.zzyJ.zzyr, this.zzyJ.zzyk, com_google_android_gms_internal_zzec);
            }
        } catch (Throwable e) {
            zzb.zzd("Could not request ad from mediation adapter.", e);
            zzq(5);
        }
    }

    private void zzb(long j, long j2, long j3, long j4) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j5 = j2 - (elapsedRealtime - j);
        elapsedRealtime = j4 - (elapsedRealtime - j3);
        if (j5 <= 0 || elapsedRealtime <= 0) {
            zzb.zzaD("Timed out waiting for adapter.");
            this.zzyM = 3;
            return;
        }
        try {
            this.zzpc.wait(Math.min(j5, elapsedRealtime));
        } catch (InterruptedException e) {
            this.zzyM = -1;
        }
    }

    private String zzdQ() {
        try {
            if (!TextUtils.isEmpty(this.zzyJ.zzyo)) {
                return this.zzow.zzac(this.zzyJ.zzyo) ? "com.google.android.gms.ads.mediation.customevent.CustomEventAdapter" : "com.google.ads.mediation.customevent.CustomEventAdapter";
            }
        } catch (RemoteException e) {
            zzb.zzaE("Fail to determine the custom event's version, assuming the old one.");
        }
        return "com.google.ads.mediation.customevent.CustomEventAdapter";
    }

    private zzei zzdR() {
        zzb.zzaD("Instantiating mediation adapter: " + this.zzyH);
        try {
            return this.zzow.zzab(this.zzyH);
        } catch (Throwable e) {
            zzb.zza("Could not instantiate mediation adapter: " + this.zzyH, e);
            return null;
        }
    }

    public void cancel() {
        synchronized (this.zzpc) {
            try {
                if (this.zzyL != null) {
                    this.zzyL.destroy();
                }
            } catch (Throwable e) {
                zzb.zzd("Could not destroy mediation adapter.", e);
            }
            this.zzyM = -1;
            this.zzpc.notify();
        }
    }

    public zzee zza(long j, long j2) {
        zzee com_google_android_gms_internal_zzee;
        synchronized (this.zzpc) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            zzec com_google_android_gms_internal_zzec = new zzec();
            zzhu.zzHK.post(new C09111(this, com_google_android_gms_internal_zzec));
            zza(elapsedRealtime, this.zzyI, j, j2);
            com_google_android_gms_internal_zzee = new zzee(this.zzyJ, this.zzyL, this.zzyH, com_google_android_gms_internal_zzec, this.zzyM);
        }
        return com_google_android_gms_internal_zzee;
    }

    public void zzq(int i) {
        synchronized (this.zzpc) {
            this.zzyM = i;
            this.zzpc.notify();
        }
    }
}
