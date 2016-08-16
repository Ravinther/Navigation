package com.google.android.gms.common.api;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.zzc.zza;

public class zzf implements zzj {
    private final zzi zzZq;

    /* renamed from: com.google.android.gms.common.api.zzf.1 */
    class C06371 extends zzb {
        final /* synthetic */ zzf zzZr;

        C06371(zzf com_google_android_gms_common_api_zzf, zzj com_google_android_gms_common_api_zzj) {
            this.zzZr = com_google_android_gms_common_api_zzf;
            super(com_google_android_gms_common_api_zzj);
        }

        public void zznn() {
            this.zzZr.onConnectionSuspended(1);
        }
    }

    public zzf(zzi com_google_android_gms_common_api_zzi) {
        this.zzZq = com_google_android_gms_common_api_zzi;
    }

    private <A extends zzb> void zza(zze<A> com_google_android_gms_common_api_zzi_zze_A) throws DeadObjectException {
        this.zzZq.zzb((zze) com_google_android_gms_common_api_zzi_zze_A);
        zzb zza = this.zzZq.zza(com_google_android_gms_common_api_zzi_zze_A.zznd());
        if (zza.isConnected() || !this.zzZq.zzaag.containsKey(com_google_android_gms_common_api_zzi_zze_A.zznd())) {
            com_google_android_gms_common_api_zzi_zze_A.zzb(zza);
        } else {
            com_google_android_gms_common_api_zzi_zze_A.zzx(new Status(17));
        }
    }

    public void begin() {
        while (!this.zzZq.zzZZ.isEmpty()) {
            try {
                zza((zze) this.zzZq.zzZZ.remove());
            } catch (Throwable e) {
                Log.w("GoogleApiClientConnected", "Service died while flushing queue", e);
            }
        }
    }

    public void connect() {
    }

    public void disconnect() {
        this.zzZq.zzaag.clear();
        this.zzZq.zznx();
        this.zzZq.zzg(null);
        this.zzZq.zzZY.zzoI();
    }

    public String getName() {
        return "CONNECTED";
    }

    public void onConnected(Bundle connectionHint) {
    }

    public void onConnectionSuspended(int cause) {
        if (cause == 1) {
            this.zzZq.zznD();
        }
        for (zze zzw : this.zzZq.zzaal) {
            zzw.zzw(new Status(8, "The connection to Google Play services was lost"));
        }
        this.zzZq.zzg(null);
        this.zzZq.zzZY.zzbB(cause);
        this.zzZq.zzZY.zzoI();
        if (cause == 2) {
            this.zzZq.connect();
        }
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zza(T t) {
        return zzb(t);
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzb(T t) {
        try {
            zza((zze) t);
        } catch (DeadObjectException e) {
            this.zzZq.zza(new C06371(this, this));
        }
        return t;
    }
}
