package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.ads.internal.formats.zze;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.internal.zzgf.zza;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzgh implements zza<zze> {
    private final boolean zzDl;
    private final boolean zzDm;

    public zzgh(boolean z, boolean z2) {
        this.zzDl = z;
        this.zzDm = z2;
    }

    public /* synthetic */ zzh.zza zza(zzgf com_google_android_gms_internal_zzgf, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        return zzc(com_google_android_gms_internal_zzgf, jSONObject);
    }

    public zze zzc(zzgf com_google_android_gms_internal_zzgf, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        List<zzih> zza = com_google_android_gms_internal_zzgf.zza(jSONObject, "images", true, this.zzDl, this.zzDm);
        Future zza2 = com_google_android_gms_internal_zzgf.zza(jSONObject, "secondary_image", false, this.zzDl);
        Future zze = com_google_android_gms_internal_zzgf.zze(jSONObject);
        List arrayList = new ArrayList();
        for (zzih com_google_android_gms_internal_zzih : zza) {
            arrayList.add(com_google_android_gms_internal_zzih.get());
        }
        return new zze(jSONObject.getString("headline"), arrayList, jSONObject.getString("body"), (zzcj) zza2.get(), jSONObject.getString("call_to_action"), jSONObject.getString("advertiser"), (com.google.android.gms.ads.internal.formats.zza) zze.get(), new Bundle());
    }
}
