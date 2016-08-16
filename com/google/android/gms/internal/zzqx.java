package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqp.zzc;
import com.google.android.gms.internal.zzqp.zzg;
import com.google.android.gms.tagmanager.zzbg;

public abstract class zzqx {
    private zzqn zzaUo;
    private zzql zzaUp;
    private zzlm zzpO;

    public enum zza {
        NOT_AVAILABLE,
        IO_ERROR,
        SERVER_ERROR
    }

    public class zzb {
        private final com.google.android.gms.internal.zzqo.zza.zza zzaTJ;
        private final long zzaTL;
        private final Object zzaUu;

        public long zzBB() {
            return this.zzaTL;
        }

        public Object zzBX() {
            return this.zzaUu;
        }

        public com.google.android.gms.internal.zzqo.zza.zza zzBx() {
            return this.zzaTJ;
        }
    }

    public zzqx(zzqn com_google_android_gms_internal_zzqn, zzql com_google_android_gms_internal_zzql) {
        this(com_google_android_gms_internal_zzqn, com_google_android_gms_internal_zzql, zzlo.zzpN());
    }

    public zzqx(zzqn com_google_android_gms_internal_zzqn, zzql com_google_android_gms_internal_zzql, zzlm com_google_android_gms_internal_zzlm) {
        boolean z = true;
        if (com_google_android_gms_internal_zzqn.zzBv().size() != 1) {
            z = false;
        }
        zzx.zzZ(z);
        this.zzaUo = com_google_android_gms_internal_zzqn;
        this.zzaUp = com_google_android_gms_internal_zzql;
        this.zzpO = com_google_android_gms_internal_zzlm;
    }

    protected abstract zzb zza(zzqi com_google_android_gms_internal_zzqi);

    protected abstract void zza(zzqo com_google_android_gms_internal_zzqo);

    public void zza(zza com_google_android_gms_internal_zzqx_zza) {
        zzbg.m1447e("ResourceManager: Failed to download a resource: " + com_google_android_gms_internal_zzqx_zza.name());
        zzqi com_google_android_gms_internal_zzqi = (zzqi) this.zzaUo.zzBv().get(0);
        zzb zza = zza(com_google_android_gms_internal_zzqi);
        com.google.android.gms.internal.zzqo.zza com_google_android_gms_internal_zzqo_zza = (zza == null || !(zza.zzBX() instanceof zzc)) ? new com.google.android.gms.internal.zzqo.zza(Status.zzaaF, com_google_android_gms_internal_zzqi, com.google.android.gms.internal.zzqo.zza.zza.NETWORK) : new com.google.android.gms.internal.zzqo.zza(Status.zzaaD, com_google_android_gms_internal_zzqi, null, (zzc) zza.zzBX(), zza.zzBx(), zza.zzBB());
        zza(new zzqo(com_google_android_gms_internal_zzqo_zza));
    }

    public void zzu(byte[] bArr) {
        Object zzt;
        long j;
        com.google.android.gms.internal.zzqo.zza.zza com_google_android_gms_internal_zzqo_zza_zza;
        Object obj;
        com.google.android.gms.internal.zzqo.zza com_google_android_gms_internal_zzqo_zza;
        zzbg.m1448v("ResourceManager: Resource downloaded from Network: " + this.zzaUo.getId());
        zzqi com_google_android_gms_internal_zzqi = (zzqi) this.zzaUo.zzBv().get(0);
        com.google.android.gms.internal.zzqo.zza.zza com_google_android_gms_internal_zzqo_zza_zza2 = com.google.android.gms.internal.zzqo.zza.zza.NETWORK;
        zzb zza;
        try {
            zzt = this.zzaUp.zzt(bArr);
            long currentTimeMillis = this.zzpO.currentTimeMillis();
            if (zzt == null) {
                zzbg.zzaD("Parsed resource from network is null");
                zza = zza(com_google_android_gms_internal_zzqi);
                if (zza != null) {
                    zzt = zza.zzBX();
                    com_google_android_gms_internal_zzqo_zza_zza2 = zza.zzBx();
                    currentTimeMillis = zza.zzBB();
                }
            }
            j = currentTimeMillis;
            com_google_android_gms_internal_zzqo_zza_zza = com_google_android_gms_internal_zzqo_zza_zza2;
            obj = zzt;
        } catch (zzg e) {
            zzbg.zzaD("Resource from network is corrupted");
            zza = zza(com_google_android_gms_internal_zzqi);
            if (zza != null) {
                zzt = zza.zzBX();
                j = 0;
                com_google_android_gms_internal_zzqo_zza_zza = zza.zzBx();
                obj = zzt;
            } else {
                j = 0;
                com_google_android_gms_internal_zzqo_zza_zza = com_google_android_gms_internal_zzqo_zza_zza2;
                obj = null;
            }
        }
        if (obj != null) {
            com_google_android_gms_internal_zzqo_zza = new com.google.android.gms.internal.zzqo.zza(Status.zzaaD, com_google_android_gms_internal_zzqi, bArr, (zzc) obj, com_google_android_gms_internal_zzqo_zza_zza, j);
        } else {
            com_google_android_gms_internal_zzqo_zza = new com.google.android.gms.internal.zzqo.zza(Status.zzaaF, com_google_android_gms_internal_zzqi, com.google.android.gms.internal.zzqo.zza.zza.NETWORK);
        }
        zza(new zzqo(com_google_android_gms_internal_zzqo_zza));
    }
}
