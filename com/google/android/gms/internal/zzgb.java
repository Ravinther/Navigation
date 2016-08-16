package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzhj.zza;

@zzgk
public class zzgb extends zzfw {

    /* renamed from: com.google.android.gms.internal.zzgb.1 */
    class C09231 implements Runnable {
        final /* synthetic */ zzgb zzCJ;

        C09231(zzgb com_google_android_gms_internal_zzgb) {
            this.zzCJ = com_google_android_gms_internal_zzgb;
        }

        public void run() {
            synchronized (this.zzCJ.zzpc) {
                if (this.zzCJ.zzCG.errorCode != -2) {
                    return;
                }
                this.zzCJ.zzoL.zzgS().zza(this.zzCJ);
                this.zzCJ.zzfp();
                zzb.m1445v("Loading HTML in WebView.");
                this.zzCJ.zzoL.loadDataWithBaseURL(zzp.zzbx().zzaw(this.zzCJ.zzCG.zzAT), this.zzCJ.zzCG.body, "text/html", "UTF-8", null);
            }
        }
    }

    zzgb(Context context, zza com_google_android_gms_internal_zzhj_zza, zzip com_google_android_gms_internal_zzip, zzga.zza com_google_android_gms_internal_zzga_zza) {
        super(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzga_zza);
    }

    protected void zzfp() {
    }

    protected void zzh(long j) throws zza {
        zzhu.zzHK.post(new C09231(this));
        zzg(j);
    }
}
