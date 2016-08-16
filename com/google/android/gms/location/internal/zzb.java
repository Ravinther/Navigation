package com.google.android.gms.location.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.location.internal.zzg.zza;

public class zzb extends zzj<zzg> {
    protected final zzn<zzg> zzaCs;
    private final String zzaCy;

    /* renamed from: com.google.android.gms.location.internal.zzb.1 */
    class C09901 implements zzn<zzg> {
        final /* synthetic */ zzb zzaCz;

        C09901(zzb com_google_android_gms_location_internal_zzb) {
            this.zzaCz = com_google_android_gms_location_internal_zzb;
        }

        public /* synthetic */ IInterface zzoA() throws DeadObjectException {
            return zzvN();
        }

        public void zzoz() {
            this.zzaCz.zzoz();
        }

        public zzg zzvN() throws DeadObjectException {
            return (zzg) this.zzaCz.zzoA();
        }
    }

    public zzb(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzf com_google_android_gms_common_internal_zzf) {
        super(context, looper, 23, com_google_android_gms_common_internal_zzf, connectionCallbacks, onConnectionFailedListener);
        this.zzaCs = new C09901(this);
        this.zzaCy = str;
    }

    protected /* synthetic */ IInterface zzV(IBinder iBinder) {
        return zzbY(iBinder);
    }

    protected zzg zzbY(IBinder iBinder) {
        return zza.zzca(iBinder);
    }

    protected String zzfA() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    protected String zzfB() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    protected Bundle zzli() {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.zzaCy);
        return bundle;
    }
}
