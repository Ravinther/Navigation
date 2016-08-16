package com.google.android.gms.ads.internal;

import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzip;

@zzgk
public class zze {
    private zza zzoH;
    private boolean zzoI;
    private boolean zzoJ;

    public interface zza {
        void zzq(String str);
    }

    @zzgk
    public static class zzb implements zza {
        private final com.google.android.gms.internal.zzhj.zza zzoK;
        private final zzip zzoL;

        public zzb(com.google.android.gms.internal.zzhj.zza com_google_android_gms_internal_zzhj_zza, zzip com_google_android_gms_internal_zzip) {
            this.zzoK = com_google_android_gms_internal_zzhj_zza;
            this.zzoL = com_google_android_gms_internal_zzip;
        }

        public void zzq(String str) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("An auto-clicking creative is blocked");
            Builder builder = new Builder();
            builder.scheme("https");
            builder.path("//pagead2.googlesyndication.com/pagead/gen_204");
            builder.appendQueryParameter("id", "gmob-apps-blocked-navigation");
            if (!TextUtils.isEmpty(str)) {
                builder.appendQueryParameter("navigationURL", str);
            }
            if (!(this.zzoK == null || this.zzoK.zzGM == null || TextUtils.isEmpty(this.zzoK.zzGM.zzEc))) {
                builder.appendQueryParameter("debugDialog", this.zzoK.zzGM.zzEc);
            }
            zzp.zzbx().zzc(this.zzoL.getContext(), this.zzoL.zzgV().zzIz, builder.toString());
        }
    }

    public zze() {
        this.zzoJ = ((Boolean) zzby.zzud.get()).booleanValue();
    }

    public zze(boolean z) {
        this.zzoJ = z;
    }

    public void recordClick() {
        this.zzoI = true;
    }

    public void zza(zza com_google_android_gms_ads_internal_zze_zza) {
        this.zzoH = com_google_android_gms_ads_internal_zze_zza;
    }

    public boolean zzbe() {
        return !this.zzoJ || this.zzoI;
    }

    public void zzp(String str) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaC("Action was blocked because no click was detected.");
        if (this.zzoH != null) {
            this.zzoH.zzq(str);
        }
    }
}
