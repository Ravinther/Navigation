package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.zzp;
import java.util.WeakHashMap;

@zzgk
public final class zzgs {
    private WeakHashMap<Context, zza> zzFX;

    private class zza {
        public final long zzFY;
        public final zzgr zzFZ;
        final /* synthetic */ zzgs zzGa;

        public zza(zzgs com_google_android_gms_internal_zzgs, zzgr com_google_android_gms_internal_zzgr) {
            this.zzGa = com_google_android_gms_internal_zzgs;
            this.zzFY = zzp.zzbB().currentTimeMillis();
            this.zzFZ = com_google_android_gms_internal_zzgr;
        }

        public boolean hasExpired() {
            return ((Long) zzby.zzuY.get()).longValue() + this.zzFY < zzp.zzbB().currentTimeMillis();
        }
    }

    public zzgs() {
        this.zzFX = new WeakHashMap();
    }

    public zzgr zzD(Context context) {
        zza com_google_android_gms_internal_zzgs_zza = (zza) this.zzFX.get(context);
        zzgr zzfN = (com_google_android_gms_internal_zzgs_zza == null || com_google_android_gms_internal_zzgs_zza.hasExpired() || !((Boolean) zzby.zzuX.get()).booleanValue()) ? new com.google.android.gms.internal.zzgr.zza(context).zzfN() : new com.google.android.gms.internal.zzgr.zza(context, com_google_android_gms_internal_zzgs_zza.zzFZ).zzfN();
        this.zzFX.put(context, new zza(this, zzfN));
        return zzfN;
    }
}
