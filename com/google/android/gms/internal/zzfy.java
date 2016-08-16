package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzhj.zza;

@zzgk
public class zzfy extends zzfw {

    /* renamed from: com.google.android.gms.internal.zzfy.1 */
    class C09201 implements Runnable {
        final /* synthetic */ zzfx zzCB;
        final /* synthetic */ zzfy zzCC;

        C09201(zzfy com_google_android_gms_internal_zzfy, zzfx com_google_android_gms_internal_zzfx) {
            this.zzCC = com_google_android_gms_internal_zzfy;
            this.zzCB = com_google_android_gms_internal_zzfx;
        }

        public void run() {
            synchronized (this.zzCC.zzpc) {
                if (this.zzCC.zzCG.errorCode != -2) {
                    return;
                }
                this.zzCC.zzoL.zzgS().zza(this.zzCC);
                this.zzCB.zza(this.zzCC.zzCG);
            }
        }
    }

    zzfy(Context context, zza com_google_android_gms_internal_zzhj_zza, zzip com_google_android_gms_internal_zzip, zzga.zza com_google_android_gms_internal_zzga_zza) {
        super(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzga_zza);
    }

    protected void zzh(long j) throws zza {
        int i;
        int i2;
        AdSizeParcel zzaN = this.zzoL.zzaN();
        if (zzaN.zzsH) {
            i = this.mContext.getResources().getDisplayMetrics().widthPixels;
            i2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        } else {
            i = zzaN.widthPixels;
            i2 = zzaN.heightPixels;
        }
        zzfx com_google_android_gms_internal_zzfx = new zzfx(this, this.zzoL, i, i2);
        zzhu.zzHK.post(new C09201(this, com_google_android_gms_internal_zzfx));
        zzg(j);
        if (com_google_android_gms_internal_zzfx.zzfn()) {
            zzb.zzaC("Ad-Network indicated no fill with passback URL.");
            throw new zza("AdNetwork sent passback url", 3);
        } else if (!com_google_android_gms_internal_zzfx.zzfo()) {
            throw new zza("AdNetwork timed out", 2);
        }
    }
}
