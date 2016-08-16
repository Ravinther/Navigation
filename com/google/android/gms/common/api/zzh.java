package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.zzc.zza;
import java.util.Collections;

public class zzh implements zzj {
    private final zzi zzZq;

    public zzh(zzi com_google_android_gms_common_api_zzi) {
        this.zzZq = com_google_android_gms_common_api_zzi;
    }

    public void begin() {
        this.zzZq.zzny();
        this.zzZq.zzaah = Collections.emptySet();
    }

    public void connect() {
        this.zzZq.zznz();
    }

    public void disconnect() {
        for (zze cancel : this.zzZq.zzZZ) {
            cancel.cancel();
        }
        this.zzZq.zzZZ.clear();
        this.zzZq.zzaag.clear();
        this.zzZq.zznx();
    }

    public String getName() {
        return "DISCONNECTED";
    }

    public void onConnected(Bundle connectionHint) {
    }

    public void onConnectionSuspended(int cause) {
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zza(T t) {
        this.zzZq.zzZZ.add(t);
        return t;
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
