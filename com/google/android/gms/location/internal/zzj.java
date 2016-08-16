package com.google.android.gms.location.internal;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.copresence.internal.CopresenceApiOptions;
import com.google.android.gms.location.copresence.internal.zzb;
import com.google.android.gms.location.internal.zzh.zza;

public class zzj extends zzb {
    private final zzi zzaCQ;
    private final zzb zzaCR;

    private static final class zzc extends zza {
        private com.google.android.gms.common.api.zzc.zzb<LocationSettingsResult> zzaCS;

        public zzc(com.google.android.gms.common.api.zzc.zzb<LocationSettingsResult> com_google_android_gms_common_api_zzc_zzb_com_google_android_gms_location_LocationSettingsResult) {
            zzx.zzb(com_google_android_gms_common_api_zzc_zzb_com_google_android_gms_location_LocationSettingsResult != null, (Object) "listener can't be null.");
            this.zzaCS = com_google_android_gms_common_api_zzc_zzb_com_google_android_gms_location_LocationSettingsResult;
        }

        public void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
            this.zzaCS.zzn(locationSettingsResult);
            this.zzaCS = null;
        }
    }

    public zzj(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzf com_google_android_gms_common_internal_zzf) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, str, com_google_android_gms_common_internal_zzf, CopresenceApiOptions.zzaCp);
    }

    public zzj(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzf com_google_android_gms_common_internal_zzf, CopresenceApiOptions copresenceApiOptions) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, str, com_google_android_gms_common_internal_zzf);
        this.zzaCQ = new zzi(context, this.zzaCs);
        this.zzaCR = zzb.zza(context, com_google_android_gms_common_internal_zzf.getAccountName(), com_google_android_gms_common_internal_zzf.zzol(), this.zzaCs, copresenceApiOptions);
    }

    public void disconnect() {
        synchronized (this.zzaCQ) {
            if (isConnected()) {
                try {
                    this.zzaCQ.removeAllListeners();
                    this.zzaCQ.zzvR();
                } catch (Throwable e) {
                    Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", e);
                }
            }
            super.disconnect();
        }
    }

    public Location getLastLocation() {
        return this.zzaCQ.getLastLocation();
    }

    public void zza(LocationListener locationListener) throws RemoteException {
        this.zzaCQ.zza(locationListener);
    }

    public void zza(LocationRequest locationRequest, LocationListener locationListener, Looper looper) throws RemoteException {
        synchronized (this.zzaCQ) {
            this.zzaCQ.zza(locationRequest, locationListener, looper);
        }
    }

    public void zza(LocationSettingsRequest locationSettingsRequest, com.google.android.gms.common.api.zzc.zzb<LocationSettingsResult> com_google_android_gms_common_api_zzc_zzb_com_google_android_gms_location_LocationSettingsResult, String str) throws RemoteException {
        boolean z = true;
        zzoz();
        zzx.zzb(locationSettingsRequest != null, (Object) "locationSettingsRequest can't be null nor empty.");
        if (com_google_android_gms_common_api_zzc_zzb_com_google_android_gms_location_LocationSettingsResult == null) {
            z = false;
        }
        zzx.zzb(z, (Object) "listener can't be null.");
        ((zzg) zzoA()).zza(locationSettingsRequest, new zzc(com_google_android_gms_common_api_zzc_zzb_com_google_android_gms_location_LocationSettingsResult), str);
    }

    public boolean zzoC() {
        return true;
    }
}
