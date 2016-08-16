package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.Map;

@zzgk
public class zzds implements zzdg {
    public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
        zzdq zzbK = zzp.zzbK();
        if (!map.containsKey("abort")) {
            String str = (String) map.get("src");
            if (str == null) {
                zzb.zzaE("Precache video action is missing the src parameter.");
                return;
            }
            int parseInt;
            try {
                parseInt = Integer.parseInt((String) map.get("player"));
            } catch (NumberFormatException e) {
                parseInt = 0;
            }
            String str2 = map.containsKey("mimetype") ? (String) map.get("mimetype") : "";
            if (zzbK.zzb(com_google_android_gms_internal_zzip)) {
                zzb.zzaE("Precache task already running.");
                return;
            }
            com.google.android.gms.common.internal.zzb.zzr(com_google_android_gms_internal_zzip.zzgP());
            new zzdp(com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzip.zzgP().zzoF.zza(com_google_android_gms_internal_zzip, parseInt, str2), str).zzgn();
        } else if (!zzbK.zza(com_google_android_gms_internal_zzip)) {
            zzb.zzaE("Precache abort but no preload task running.");
        }
    }
}
