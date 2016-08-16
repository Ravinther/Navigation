package com.google.android.gms.location.internal;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class zzd implements FusedLocationProviderApi {

    private static abstract class zza extends com.google.android.gms.location.LocationServices.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result zzb(Status status) {
            return zzd(status);
        }

        public Status zzd(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.1 */
    class C09911 extends zza {
        final /* synthetic */ LocationRequest zzaCA;
        final /* synthetic */ LocationListener zzaCB;
        final /* synthetic */ zzd zzaCC;

        C09911(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
            this.zzaCC = com_google_android_gms_location_internal_zzd;
            this.zzaCA = locationRequest;
            this.zzaCB = locationListener;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzaCA, this.zzaCB, null);
            zza(Status.zzaaD);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.7 */
    class C09927 extends zza {
        final /* synthetic */ LocationListener zzaCB;
        final /* synthetic */ zzd zzaCC;

        C09927(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationListener locationListener) {
            this.zzaCC = com_google_android_gms_location_internal_zzd;
            this.zzaCB = locationListener;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzaCB);
            zza(Status.zzaaD);
        }
    }

    public Location getLastLocation(GoogleApiClient client) {
        try {
            return LocationServices.zze(client).getLastLocation();
        } catch (Exception e) {
            return null;
        }
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient client, LocationListener listener) {
        return client.zzb(new C09927(this, client, listener));
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient client, LocationRequest request, LocationListener listener) {
        return client.zzb(new C09911(this, client, request, listener));
    }
}
