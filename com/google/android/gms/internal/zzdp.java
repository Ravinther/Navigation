package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzp;

@zzgk
public class zzdp extends zzhq {
    final zzip zzoL;
    final zzdr zzxr;
    private final String zzxs;

    /* renamed from: com.google.android.gms.internal.zzdp.1 */
    class C08881 implements Runnable {
        final /* synthetic */ zzdp zzxt;

        C08881(zzdp com_google_android_gms_internal_zzdp) {
            this.zzxt = com_google_android_gms_internal_zzdp;
        }

        public void run() {
            zzp.zzbK().zzb(this.zzxt);
        }
    }

    zzdp(zzip com_google_android_gms_internal_zzip, zzdr com_google_android_gms_internal_zzdr, String str) {
        this.zzoL = com_google_android_gms_internal_zzip;
        this.zzxr = com_google_android_gms_internal_zzdr;
        this.zzxs = str;
        zzp.zzbK().zza(this);
    }

    public void onStop() {
        this.zzxr.abort();
    }

    public void zzdG() {
        try {
            this.zzxr.zzZ(this.zzxs);
        } finally {
            zzhu.zzHK.post(new C08881(this));
        }
    }
}
