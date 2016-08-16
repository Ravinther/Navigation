package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.ArrayList;
import java.util.List;

@zzgk
public final class zzdy {
    private final Context mContext;
    private final zzeh zzow;
    private final Object zzpc;
    private final AdRequestInfoParcel zzyd;
    private final zzea zzye;
    private final boolean zzyf;
    private boolean zzyg;
    private zzed zzyh;

    /* renamed from: com.google.android.gms.internal.zzdy.1 */
    class C09091 implements Runnable {
        final /* synthetic */ zzee zzyi;
        final /* synthetic */ zzdy zzyj;

        C09091(zzdy com_google_android_gms_internal_zzdy, zzee com_google_android_gms_internal_zzee) {
            this.zzyj = com_google_android_gms_internal_zzdy;
            this.zzyi = com_google_android_gms_internal_zzee;
        }

        public void run() {
            try {
                this.zzyi.zzyR.destroy();
            } catch (Throwable e) {
                zzb.zzd("Could not destroy mediation adapter.", e);
            }
        }
    }

    public zzdy(Context context, AdRequestInfoParcel adRequestInfoParcel, zzeh com_google_android_gms_internal_zzeh, zzea com_google_android_gms_internal_zzea, boolean z) {
        this.zzpc = new Object();
        this.zzyg = false;
        this.mContext = context;
        this.zzyd = adRequestInfoParcel;
        this.zzow = com_google_android_gms_internal_zzeh;
        this.zzye = com_google_android_gms_internal_zzea;
        this.zzyf = z;
    }

    public void cancel() {
        synchronized (this.zzpc) {
            this.zzyg = true;
            if (this.zzyh != null) {
                this.zzyh.cancel();
            }
        }
    }

    public zzee zza(long j, long j2, zzcd com_google_android_gms_internal_zzcd) {
        zzb.zzaC("Starting mediation.");
        List arrayList = new ArrayList();
        zzcc zzdl = com_google_android_gms_internal_zzcd.zzdl();
        for (zzdz com_google_android_gms_internal_zzdz : this.zzye.zzyu) {
            zzb.zzaD("Trying mediation network: " + com_google_android_gms_internal_zzdz.zzyl);
            for (String str : com_google_android_gms_internal_zzdz.zzym) {
                zzcc zzdl2 = com_google_android_gms_internal_zzcd.zzdl();
                synchronized (this.zzpc) {
                    if (this.zzyg) {
                        zzee com_google_android_gms_internal_zzee = new zzee(-1);
                        return com_google_android_gms_internal_zzee;
                    }
                    this.zzyh = new zzed(this.mContext, str, this.zzow, this.zzye, com_google_android_gms_internal_zzdz, this.zzyd.zzDy, this.zzyd.zzqf, this.zzyd.zzqb, this.zzyf, this.zzyd.zzqt, this.zzyd.zzqv);
                    com_google_android_gms_internal_zzee = this.zzyh.zza(j, j2);
                    if (com_google_android_gms_internal_zzee.zzyP == 0) {
                        zzb.zzaC("Adapter succeeded.");
                        com_google_android_gms_internal_zzcd.zzd("mediation_network_succeed", str);
                        if (!arrayList.isEmpty()) {
                            zzcd com_google_android_gms_internal_zzcd2 = com_google_android_gms_internal_zzcd;
                            com_google_android_gms_internal_zzcd2.zzd("mediation_networks_fail", TextUtils.join(",", arrayList));
                        }
                        com_google_android_gms_internal_zzcd.zza(zzdl2, "mls");
                        com_google_android_gms_internal_zzcd.zza(zzdl, "ttm");
                        return com_google_android_gms_internal_zzee;
                    }
                    arrayList.add(str);
                    com_google_android_gms_internal_zzcd.zza(zzdl2, "mlf");
                    if (com_google_android_gms_internal_zzee.zzyR != null) {
                        zzhu.zzHK.post(new C09091(this, com_google_android_gms_internal_zzee));
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            com_google_android_gms_internal_zzcd2 = com_google_android_gms_internal_zzcd;
            com_google_android_gms_internal_zzcd2.zzd("mediation_networks_fail", TextUtils.join(",", arrayList));
        }
        return new zzee(1);
    }
}
