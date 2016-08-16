package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.internal.formats.zzf;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzgf.zza;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzgi implements zza<zzf> {
    private final boolean zzDl;

    public zzgi(boolean z) {
        this.zzDl = z;
    }

    private void zza(zzgf com_google_android_gms_internal_zzgf, JSONObject jSONObject, zzlh<String, Future<zzc>> com_google_android_gms_internal_zzlh_java_lang_String__java_util_concurrent_Future_com_google_android_gms_ads_internal_formats_zzc) throws JSONException {
        com_google_android_gms_internal_zzlh_java_lang_String__java_util_concurrent_Future_com_google_android_gms_ads_internal_formats_zzc.put(jSONObject.getString("name"), com_google_android_gms_internal_zzgf.zza(jSONObject, "image_value", this.zzDl));
    }

    private void zza(JSONObject jSONObject, zzlh<String, String> com_google_android_gms_internal_zzlh_java_lang_String__java_lang_String) throws JSONException {
        com_google_android_gms_internal_zzlh_java_lang_String__java_lang_String.put(jSONObject.getString("name"), jSONObject.getString("string_value"));
    }

    private <K, V> zzlh<K, V> zzc(zzlh<K, Future<V>> com_google_android_gms_internal_zzlh_K__java_util_concurrent_Future_V) throws InterruptedException, ExecutionException {
        zzlh<K, V> com_google_android_gms_internal_zzlh = new zzlh();
        for (int i = 0; i < com_google_android_gms_internal_zzlh_K__java_util_concurrent_Future_V.size(); i++) {
            com_google_android_gms_internal_zzlh.put(com_google_android_gms_internal_zzlh_K__java_util_concurrent_Future_V.keyAt(i), ((Future) com_google_android_gms_internal_zzlh_K__java_util_concurrent_Future_V.valueAt(i)).get());
        }
        return com_google_android_gms_internal_zzlh;
    }

    public /* synthetic */ zzh.zza zza(zzgf com_google_android_gms_internal_zzgf, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        return zzd(com_google_android_gms_internal_zzgf, jSONObject);
    }

    public zzf zzd(zzgf com_google_android_gms_internal_zzgf, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        zzlh com_google_android_gms_internal_zzlh = new zzlh();
        zzlh com_google_android_gms_internal_zzlh2 = new zzlh();
        Future zze = com_google_android_gms_internal_zzgf.zze(jSONObject);
        JSONArray jSONArray = jSONObject.getJSONArray("custom_assets");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            String string = jSONObject2.getString("type");
            if ("string".equals(string)) {
                zza(jSONObject2, com_google_android_gms_internal_zzlh2);
            } else if ("image".equals(string)) {
                zza(com_google_android_gms_internal_zzgf, jSONObject2, com_google_android_gms_internal_zzlh);
            } else {
                zzb.zzaE("Unknown custom asset type: " + string);
            }
        }
        return new zzf(jSONObject.getString("custom_template_id"), zzc(com_google_android_gms_internal_zzlh), com_google_android_gms_internal_zzlh2, (com.google.android.gms.ads.internal.formats.zza) zze.get());
    }
}
