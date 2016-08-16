package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.ads.internal.request.zzj.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.internal.zzgk;

@zzgk
public class zze extends zzj<zzj> {
    final int zzDw;

    public zze(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, int i) {
        super(context, looper, 8, zzf.zzaj(context), connectionCallbacks, onConnectionFailedListener);
        this.zzDw = i;
    }

    protected zzj zzU(IBinder iBinder) {
        return zza.zzW(iBinder);
    }

    protected /* synthetic */ IInterface zzV(IBinder iBinder) {
        return zzU(iBinder);
    }

    protected String zzfA() {
        return "com.google.android.gms.ads.service.START";
    }

    protected String zzfB() {
        return "com.google.android.gms.ads.internal.request.IAdRequestService";
    }

    public zzj zzfC() throws DeadObjectException {
        return (zzj) super.zzoA();
    }
}
