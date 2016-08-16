package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.tagmanager.zzbg;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zzqk {
    private final Context mContext;
    private String zzaPw;
    private final Map<String, zzqz> zzaTA;
    private final zzqr zzaTy;
    Map<String, zzc<com.google.android.gms.internal.zzqp.zzc>> zzaTz;
    private final zzlm zzpO;

    /* renamed from: com.google.android.gms.internal.zzqk.1 */
    class C09801 implements zzqq {
        final /* synthetic */ zzqi zzaTB;
        final /* synthetic */ zza zzaTC;
        final /* synthetic */ zzqk zzaTD;

        C09801(zzqk com_google_android_gms_internal_zzqk, zzqi com_google_android_gms_internal_zzqi, zza com_google_android_gms_internal_zzqk_zza) {
            this.zzaTD = com_google_android_gms_internal_zzqk;
            this.zzaTB = com_google_android_gms_internal_zzqi;
            this.zzaTC = com_google_android_gms_internal_zzqk_zza;
        }

        public void zza(Status status, Object obj, Integer num, long j) {
            com.google.android.gms.internal.zzqo.zza com_google_android_gms_internal_zzqo_zza;
            if (status.isSuccess()) {
                com_google_android_gms_internal_zzqo_zza = new com.google.android.gms.internal.zzqo.zza(Status.zzaaD, this.zzaTB, null, (com.google.android.gms.internal.zzqp.zzc) obj, num == zzqr.zzaUg ? com.google.android.gms.internal.zzqo.zza.zza.DEFAULT : com.google.android.gms.internal.zzqo.zza.zza.DISK, j);
            } else {
                com_google_android_gms_internal_zzqo_zza = new com.google.android.gms.internal.zzqo.zza(new Status(16, "There is no valid resource for the container: " + this.zzaTB.getContainerId()), null, com.google.android.gms.internal.zzqo.zza.zza.DISK);
            }
            this.zzaTC.zza(new zzqo(com_google_android_gms_internal_zzqo_zza));
        }
    }

    public interface zza {
        void zza(zzqo com_google_android_gms_internal_zzqo);
    }

    class zzb extends zzqx {
        final /* synthetic */ zzqk zzaTD;
        private final zza zzaTE;

        zzb(zzqk com_google_android_gms_internal_zzqk, zzqn com_google_android_gms_internal_zzqn, zzql com_google_android_gms_internal_zzql, zza com_google_android_gms_internal_zzqk_zza) {
            this.zzaTD = com_google_android_gms_internal_zzqk;
            super(com_google_android_gms_internal_zzqn, com_google_android_gms_internal_zzql);
            this.zzaTE = com_google_android_gms_internal_zzqk_zza;
        }

        protected com.google.android.gms.internal.zzqx.zzb zza(zzqi com_google_android_gms_internal_zzqi) {
            return null;
        }

        protected void zza(zzqo com_google_android_gms_internal_zzqo) {
            com.google.android.gms.internal.zzqo.zza zzBw = com_google_android_gms_internal_zzqo.zzBw();
            this.zzaTD.zza(zzBw);
            if (zzBw.getStatus() != Status.zzaaD || zzBw.zzBx() != com.google.android.gms.internal.zzqo.zza.zza.NETWORK || zzBw.zzBy() == null || zzBw.zzBy().length <= 0) {
                zzbg.m1448v("Response status: " + (zzBw.getStatus().isSuccess() ? "SUCCESS" : "FAILURE"));
                if (zzBw.getStatus().isSuccess()) {
                    zzbg.m1448v("Response source: " + zzBw.zzBx().toString());
                    zzbg.m1448v("Response size: " + zzBw.zzBy().length);
                }
                this.zzaTD.zza(zzBw.zzBz(), this.zzaTE);
                return;
            }
            this.zzaTD.zzaTy.zze(zzBw.zzBz().zzBr(), zzBw.zzBy());
            zzbg.m1448v("Resource successfully load from Network.");
            this.zzaTE.zza(com_google_android_gms_internal_zzqo);
        }
    }

    static class zzc<T> {
        private T mData;
        private Status zzQA;
        private long zzaTF;

        public zzc(Status status, T t, long j) {
            this.zzQA = status;
            this.mData = t;
            this.zzaTF = j;
        }

        public long zzBu() {
            return this.zzaTF;
        }

        public void zzQ(T t) {
            this.mData = t;
        }

        public void zzU(long j) {
            this.zzaTF = j;
        }

        public void zzbh(Status status) {
            this.zzQA = status;
        }
    }

    public zzqk(Context context) {
        this(context, new HashMap(), new zzqr(context), zzlo.zzpN());
    }

    zzqk(Context context, Map<String, zzqz> map, zzqr com_google_android_gms_internal_zzqr, zzlm com_google_android_gms_internal_zzlm) {
        this.zzaPw = null;
        this.zzaTz = new HashMap();
        this.mContext = context;
        this.zzpO = com_google_android_gms_internal_zzlm;
        this.zzaTy = com_google_android_gms_internal_zzqr;
        this.zzaTA = map;
    }

    private void zza(zzqn com_google_android_gms_internal_zzqn, zza com_google_android_gms_internal_zzqk_zza) {
        boolean z = true;
        List zzBv = com_google_android_gms_internal_zzqn.zzBv();
        if (zzBv.size() != 1) {
            z = false;
        }
        zzx.zzZ(z);
        zza((zzqi) zzBv.get(0), com_google_android_gms_internal_zzqk_zza);
    }

    void zza(zzqi com_google_android_gms_internal_zzqi, zza com_google_android_gms_internal_zzqk_zza) {
        this.zzaTy.zza(com_google_android_gms_internal_zzqi.zzBr(), com_google_android_gms_internal_zzqi.zzBp(), zzqm.zzaTG, new C09801(this, com_google_android_gms_internal_zzqi, com_google_android_gms_internal_zzqk_zza));
    }

    void zza(zzqn com_google_android_gms_internal_zzqn, zza com_google_android_gms_internal_zzqk_zza, zzqx com_google_android_gms_internal_zzqx) {
        Object obj = null;
        for (zzqi com_google_android_gms_internal_zzqi : com_google_android_gms_internal_zzqn.zzBv()) {
            zzc com_google_android_gms_internal_zzqk_zzc = (zzc) this.zzaTz.get(com_google_android_gms_internal_zzqi.getContainerId());
            obj = (com_google_android_gms_internal_zzqk_zzc != null ? com_google_android_gms_internal_zzqk_zzc.zzBu() : this.zzaTy.zzfp(com_google_android_gms_internal_zzqi.getContainerId())) + 900000 < this.zzpO.currentTimeMillis() ? 1 : obj;
        }
        if (obj != null) {
            zzqz com_google_android_gms_internal_zzqz;
            zzqz com_google_android_gms_internal_zzqz2 = (zzqz) this.zzaTA.get(com_google_android_gms_internal_zzqn.getId());
            if (com_google_android_gms_internal_zzqz2 == null) {
                com_google_android_gms_internal_zzqz2 = this.zzaPw == null ? new zzqz() : new zzqz(this.zzaPw);
                this.zzaTA.put(com_google_android_gms_internal_zzqn.getId(), com_google_android_gms_internal_zzqz2);
                com_google_android_gms_internal_zzqz = com_google_android_gms_internal_zzqz2;
            } else {
                com_google_android_gms_internal_zzqz = com_google_android_gms_internal_zzqz2;
            }
            com_google_android_gms_internal_zzqz.zza(this.mContext, com_google_android_gms_internal_zzqn, 0, com_google_android_gms_internal_zzqx);
            return;
        }
        zza(com_google_android_gms_internal_zzqn, com_google_android_gms_internal_zzqk_zza);
    }

    void zza(com.google.android.gms.internal.zzqo.zza com_google_android_gms_internal_zzqo_zza) {
        String containerId = com_google_android_gms_internal_zzqo_zza.zzBz().getContainerId();
        Status status = com_google_android_gms_internal_zzqo_zza.getStatus();
        com.google.android.gms.internal.zzqp.zzc zzBA = com_google_android_gms_internal_zzqo_zza.zzBA();
        if (this.zzaTz.containsKey(containerId)) {
            zzc com_google_android_gms_internal_zzqk_zzc = (zzc) this.zzaTz.get(containerId);
            com_google_android_gms_internal_zzqk_zzc.zzU(this.zzpO.currentTimeMillis());
            if (status == Status.zzaaD) {
                com_google_android_gms_internal_zzqk_zzc.zzbh(status);
                com_google_android_gms_internal_zzqk_zzc.zzQ(zzBA);
                return;
            }
            return;
        }
        this.zzaTz.put(containerId, new zzc(status, zzBA, this.zzpO.currentTimeMillis()));
    }

    public void zza(String str, Integer num, String str2, zza com_google_android_gms_internal_zzqk_zza) {
        zzqn zzb = new zzqn().zzb(new zzqi(str, num, str2, false));
        zza(zzb, com_google_android_gms_internal_zzqk_zza, new zzb(this, zzb, zzqm.zzaTG, com_google_android_gms_internal_zzqk_zza));
    }

    public void zzfj(String str) {
        this.zzaPw = str;
    }
}
