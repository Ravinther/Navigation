package com.google.android.gms.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.appdatasearch.GetRecentContextCall.Response;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzc.zzb;
import com.google.android.gms.internal.zzjd.zza;

public abstract class zzje<T> extends zza {
    protected zzb<T> zzPW;

    public zzje(zzb<T> com_google_android_gms_common_api_zzc_zzb_T) {
        this.zzPW = com_google_android_gms_common_api_zzc_zzb_T;
    }

    public void zza(Response response) {
    }

    public void zza(Status status, ParcelFileDescriptor parcelFileDescriptor) {
    }

    public void zza(Status status, boolean z) {
    }

    public void zzc(Status status) {
    }
}
