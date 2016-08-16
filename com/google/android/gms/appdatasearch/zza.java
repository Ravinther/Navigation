package com.google.android.gms.appdatasearch;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.internal.zzjf;
import com.google.android.gms.internal.zzjh;

public final class zza {
    public static final zzc<zzjf> zzOO;
    private static final com.google.android.gms.common.api.Api.zza<zzjf, NoOptions> zzOP;
    public static final Api<NoOptions> zzOQ;
    public static final zzk zzOR;

    /* renamed from: com.google.android.gms.appdatasearch.zza.1 */
    static class C06341 extends com.google.android.gms.common.api.Api.zza<zzjf, NoOptions> {
        C06341() {
        }

        public zzjf zza(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzjf(context, looper, com_google_android_gms_common_internal_zzf, connectionCallbacks, onConnectionFailedListener);
        }
    }

    static {
        zzOO = new zzc();
        zzOP = new C06341();
        zzOQ = new Api("AppDataSearch.LIGHTWEIGHT_API", zzOP, zzOO);
        zzOR = new zzjh();
    }
}
