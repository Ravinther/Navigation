package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import org.json.JSONObject;

@zzgk
public class zzdl implements zzdg {
    final HashMap<String, zzie<JSONObject>> zzxi;

    public zzdl() {
        this.zzxi = new HashMap();
    }

    public Future<JSONObject> zzW(String str) {
        Future com_google_android_gms_internal_zzie = new zzie();
        this.zzxi.put(str, com_google_android_gms_internal_zzie);
        return com_google_android_gms_internal_zzie;
    }

    public void zzX(String str) {
        zzie com_google_android_gms_internal_zzie = (zzie) this.zzxi.get(str);
        if (com_google_android_gms_internal_zzie == null) {
            zzb.m1444e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        if (!com_google_android_gms_internal_zzie.isDone()) {
            com_google_android_gms_internal_zzie.cancel(true);
        }
        this.zzxi.remove(str);
    }

    public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
        zze((String) map.get("request_id"), (String) map.get("fetched_ad"));
    }

    public void zze(String str, String str2) {
        zzb.zzaC("Received ad from the cache.");
        zzie com_google_android_gms_internal_zzie = (zzie) this.zzxi.get(str);
        if (com_google_android_gms_internal_zzie == null) {
            zzb.m1444e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        try {
            com_google_android_gms_internal_zzie.zzf(new JSONObject(str2));
        } catch (Throwable e) {
            zzb.zzb("Failed constructing JSON object from value passed from javascript", e);
            com_google_android_gms_internal_zzie.zzf(null);
        } finally {
            this.zzxi.remove(str);
        }
    }
}
