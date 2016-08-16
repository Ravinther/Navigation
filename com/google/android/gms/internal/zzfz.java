package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import org.json.JSONObject;

@zzgk
public abstract class zzfz extends zzhq {
    protected final Context mContext;
    protected final com.google.android.gms.internal.zzga.zza zzCD;
    protected final Object zzCE;
    protected final com.google.android.gms.internal.zzhj.zza zzCF;
    protected AdResponseParcel zzCG;
    protected final zzip zzoL;
    protected final Object zzpc;

    /* renamed from: com.google.android.gms.internal.zzfz.1 */
    class C09211 implements Runnable {
        final /* synthetic */ zzfz zzCH;

        C09211(zzfz com_google_android_gms_internal_zzfz) {
            this.zzCH = com_google_android_gms_internal_zzfz;
        }

        public void run() {
            this.zzCH.onStop();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfz.2 */
    class C09222 implements Runnable {
        final /* synthetic */ zzfz zzCH;
        final /* synthetic */ zzhj zzpx;

        C09222(zzfz com_google_android_gms_internal_zzfz, zzhj com_google_android_gms_internal_zzhj) {
            this.zzCH = com_google_android_gms_internal_zzfz;
            this.zzpx = com_google_android_gms_internal_zzhj;
        }

        public void run() {
            synchronized (this.zzCH.zzpc) {
                this.zzCH.zzi(this.zzpx);
            }
        }
    }

    protected static final class zza extends Exception {
        private final int zzCI;

        public zza(String str, int i) {
            super(str);
            this.zzCI = i;
        }

        public int getErrorCode() {
            return this.zzCI;
        }
    }

    protected zzfz(Context context, com.google.android.gms.internal.zzhj.zza com_google_android_gms_internal_zzhj_zza, zzip com_google_android_gms_internal_zzip, com.google.android.gms.internal.zzga.zza com_google_android_gms_internal_zzga_zza) {
        this.zzpc = new Object();
        this.zzCE = new Object();
        this.mContext = context;
        this.zzCF = com_google_android_gms_internal_zzhj_zza;
        this.zzCG = com_google_android_gms_internal_zzhj_zza.zzGM;
        this.zzoL = com_google_android_gms_internal_zzip;
        this.zzCD = com_google_android_gms_internal_zzga_zza;
    }

    public void onStop() {
    }

    public void zzdG() {
        int errorCode;
        synchronized (this.zzpc) {
            zzb.zzaC("AdRendererBackgroundTask started.");
            int i = this.zzCF.errorCode;
            try {
                zzh(SystemClock.elapsedRealtime());
            } catch (zza e) {
                errorCode = e.getErrorCode();
                if (errorCode == 3 || errorCode == -1) {
                    zzb.zzaD(e.getMessage());
                } else {
                    zzb.zzaE(e.getMessage());
                }
                if (this.zzCG == null) {
                    this.zzCG = new AdResponseParcel(errorCode);
                } else {
                    this.zzCG = new AdResponseParcel(errorCode, this.zzCG.zzyA);
                }
                zzhu.zzHK.post(new C09211(this));
                i = errorCode;
            }
            zzhu.zzHK.post(new C09222(this, zzz(i)));
        }
    }

    protected abstract void zzh(long j) throws zza;

    protected void zzi(zzhj com_google_android_gms_internal_zzhj) {
        this.zzCD.zzb(com_google_android_gms_internal_zzhj);
    }

    protected zzhj zzz(int i) {
        AdRequestInfoParcel adRequestInfoParcel = this.zzCF.zzGL;
        long j = this.zzCG.zzDY;
        AdSizeParcel adSizeParcel = this.zzCF.zzqf;
        long j2 = this.zzCG.zzDW;
        long j3 = this.zzCF.zzGI;
        long j4 = this.zzCG.zzEb;
        String str = this.zzCG.zzEc;
        JSONObject jSONObject = this.zzCF.zzGF;
        String str2 = adRequestInfoParcel.zzDO;
        return new zzhj(adRequestInfoParcel.zzDy, this.zzoL, this.zzCG.zzyw, i, this.zzCG.zzyx, this.zzCG.zzDZ, this.zzCG.orientation, this.zzCG.zzyA, adRequestInfoParcel.zzDB, this.zzCG.zzDX, null, null, null, null, null, r0, r0, r0, r0, r0, r0, r0, null, r0);
    }
}
