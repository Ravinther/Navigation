package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.internal.zzaf.zzj;
import com.google.android.gms.internal.zzlm;
import com.google.android.gms.internal.zzlo;
import com.google.android.gms.internal.zzqk;
import com.google.android.gms.internal.zzqk.zza;
import com.google.android.gms.internal.zzqo;
import com.google.android.gms.internal.zzqp.zzc;

public class zzp extends zzb<ContainerHolder> {
    private final Context mContext;
    private final Looper zzYV;
    private final String zzaOS;
    private final TagManager zzaPe;
    private final zzd zzaPh;
    private final zzcd zzaPi;
    private final int zzaPj;
    private zzf zzaPk;
    private zzqk zzaPl;
    private volatile zzo zzaPm;
    private zzj zzaPo;
    private String zzaPp;
    private zze zzaPq;
    private final zzlm zzpO;

    interface zze extends Releasable {
        void zzeB(String str);
    }

    interface zzf extends Releasable {
    }

    /* renamed from: com.google.android.gms.tagmanager.zzp.1 */
    class C10261 implements zza {
        final /* synthetic */ String zzaPs;
        final /* synthetic */ zzp zzaPt;

        /* renamed from: com.google.android.gms.tagmanager.zzp.1.1 */
        class C10251 implements zzo.zza {
            final /* synthetic */ C10261 zzaPu;

            C10251(C10261 c10261) {
                this.zzaPu = c10261;
            }
        }

        C10261(zzp com_google_android_gms_tagmanager_zzp, String str) {
            this.zzaPt = com_google_android_gms_tagmanager_zzp;
            this.zzaPs = str;
        }

        public void zza(zzqo com_google_android_gms_internal_zzqo) {
            if (com_google_android_gms_internal_zzqo.getStatus() != Status.zzaaD) {
                zzbg.m1447e("Load request failed for the container " + this.zzaPt.zzaOS);
                this.zzaPt.zza(this.zzaPt.zzbg(Status.zzaaF));
                return;
            }
            zzc zzBA = com_google_android_gms_internal_zzqo.zzBw().zzBA();
            if (zzBA == null) {
                String str = "Response doesn't have the requested container";
                zzbg.m1447e(str);
                this.zzaPt.zza(this.zzaPt.zzbg(new Status(8, str, null)));
                return;
            }
            this.zzaPt.zzaPm = new zzo(this.zzaPt.zzaPe, this.zzaPt.zzYV, new Container(this.zzaPt.mContext, this.zzaPt.zzaPe.getDataLayer(), this.zzaPt.zzaOS, com_google_android_gms_internal_zzqo.zzBw().zzBB(), zzBA), new C10251(this));
            this.zzaPt.zza(this.zzaPt.zzaPm);
        }
    }

    private class zzd implements zzo.zza {
        final /* synthetic */ zzp zzaPt;

        private zzd(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzaPt = com_google_android_gms_tagmanager_zzp;
        }
    }

    zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzf com_google_android_gms_tagmanager_zzp_zzf, zze com_google_android_gms_tagmanager_zzp_zze, zzqk com_google_android_gms_internal_zzqk, zzlm com_google_android_gms_internal_zzlm, zzcd com_google_android_gms_tagmanager_zzcd) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.zzaPe = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzYV = looper;
        this.zzaOS = str;
        this.zzaPj = i;
        this.zzaPk = com_google_android_gms_tagmanager_zzp_zzf;
        this.zzaPq = com_google_android_gms_tagmanager_zzp_zze;
        this.zzaPl = com_google_android_gms_internal_zzqk;
        this.zzaPh = new zzd();
        this.zzaPo = new zzj();
        this.zzpO = com_google_android_gms_internal_zzlm;
        this.zzaPi = com_google_android_gms_tagmanager_zzcd;
        if (zzzK()) {
            zzey(zzcb.zzAv().zzAx());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzs com_google_android_gms_tagmanager_zzs) {
        this(context, tagManager, looper, str, i, new zzcn(context, str), new zzcm(context, str, com_google_android_gms_tagmanager_zzs), new zzqk(context), zzlo.zzpN(), new zzbe(30, 900000, 5000, "refreshing", zzlo.zzpN()));
        this.zzaPl.zzfj(com_google_android_gms_tagmanager_zzs.zzzN());
    }

    private boolean zzzK() {
        zzcb zzAv = zzcb.zzAv();
        return (zzAv.zzAw() == zza.CONTAINER || zzAv.zzAw() == zza.CONTAINER_DEBUG) && this.zzaOS.equals(zzAv.getContainerId());
    }

    public void load(String resourceIdParameterName) {
        this.zzaPl.zza(this.zzaOS, this.zzaPj != -1 ? Integer.valueOf(this.zzaPj) : null, resourceIdParameterName, new C10261(this, resourceIdParameterName));
    }

    protected /* synthetic */ Result zzb(Status status) {
        return zzbg(status);
    }

    protected ContainerHolder zzbg(Status status) {
        if (this.zzaPm != null) {
            return this.zzaPm;
        }
        if (status == Status.zzaaG) {
            zzbg.m1447e("timer expired: setting result to failure");
        }
        return new zzo(status);
    }

    synchronized void zzey(String str) {
        this.zzaPp = str;
        if (this.zzaPq != null) {
            this.zzaPq.zzeB(str);
        }
    }
}
