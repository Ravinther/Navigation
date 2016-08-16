package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

@zzgk
public final class zzdc implements zzdg {
    private final zzdd zzwH;

    public zzdc(zzdd com_google_android_gms_internal_zzdd) {
        this.zzwH = com_google_android_gms_internal_zzdd;
    }

    public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
        String str = (String) map.get("name");
        if (str == null) {
            zzb.zzaE("App event with no name parameter.");
        } else {
            this.zzwH.onAppEvent(str, (String) map.get("info"));
        }
    }
}
