package com.google.android.gms.internal;

import com.google.android.gms.appdatasearch.GetRecentContextCall.Request;
import com.google.android.gms.appdatasearch.GetRecentContextCall.Response;
import com.google.android.gms.appdatasearch.GetRecentContextCall.zza;
import com.google.android.gms.appdatasearch.zzk;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;

public final class zzjh implements zzk {
    public PendingResult<Response> zza(GoogleApiClient googleApiClient, Request request) {
        return googleApiClient.zza(new zza(request, googleApiClient));
    }
}
