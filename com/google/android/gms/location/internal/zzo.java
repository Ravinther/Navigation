package com.google.android.gms.location.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzc.zzb;
import com.google.android.gms.location.LocationServices.zza;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;

public class zzo implements SettingsApi {

    /* renamed from: com.google.android.gms.location.internal.zzo.1 */
    class C09931 extends zza<LocationSettingsResult> {
        final /* synthetic */ LocationSettingsRequest zzaDe;
        final /* synthetic */ String zzaDf;
        final /* synthetic */ zzo zzaDg;

        C09931(zzo com_google_android_gms_location_internal_zzo, GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String str) {
            this.zzaDg = com_google_android_gms_location_internal_zzo;
            this.zzaDe = locationSettingsRequest;
            this.zzaDf = str;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzaDe, (zzb) this, this.zzaDf);
        }

        public LocationSettingsResult zzaL(Status status) {
            return new LocationSettingsResult(status);
        }

        public /* synthetic */ Result zzb(Status status) {
            return zzaL(status);
        }
    }

    public PendingResult<LocationSettingsResult> checkLocationSettings(GoogleApiClient client, LocationSettingsRequest request) {
        return zza(client, request, null);
    }

    public PendingResult<LocationSettingsResult> zza(GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String str) {
        return googleApiClient.zza(new C09931(this, googleApiClient, locationSettingsRequest, str));
    }
}
