package com.google.android.gms.internal;

import android.content.Context;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.internal.zzhj.zza;
import loquendo.tts.engine.TTSConst;

@zzgk
public class zzgd extends zzfz {
    private zzdy zzCN;
    protected zzee zzCO;
    private final zzcd zzon;
    private zzeh zzow;
    private zzea zzye;

    zzgd(Context context, zza com_google_android_gms_internal_zzhj_zza, zzip com_google_android_gms_internal_zzip, zzeh com_google_android_gms_internal_zzeh, zzga.zza com_google_android_gms_internal_zzga_zza, zzcd com_google_android_gms_internal_zzcd) {
        super(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzga_zza);
        this.zzow = com_google_android_gms_internal_zzeh;
        this.zzye = com_google_android_gms_internal_zzhj_zza.zzGG;
        this.zzon = com_google_android_gms_internal_zzcd;
    }

    public void onStop() {
        synchronized (this.zzCE) {
            super.onStop();
            if (this.zzCN != null) {
                this.zzCN.cancel();
            }
        }
    }

    protected void zzh(long j) throws zza {
        synchronized (this.zzCE) {
            this.zzCN = new zzdy(this.mContext, this.zzCF.zzGL, this.zzow, this.zzye, this.zzCG.zzsJ);
        }
        this.zzCO = this.zzCN.zza(j, 60000, this.zzon);
        switch (this.zzCO.zzyP) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSMULTILINE /*1*/:
                throw new zza("No fill from any mediation ad networks.", 3);
            default:
                throw new zza("Unexpected mediation result: " + this.zzCO.zzyP, 0);
        }
    }

    protected zzhj zzz(int i) {
        AdRequestInfoParcel adRequestInfoParcel = this.zzCF.zzGL;
        return new zzhj(adRequestInfoParcel.zzDy, this.zzoL, this.zzCG.zzyw, i, this.zzCG.zzyx, this.zzCG.zzDZ, this.zzCG.orientation, this.zzCG.zzyA, adRequestInfoParcel.zzDB, this.zzCG.zzDX, this.zzCO != null ? this.zzCO.zzyQ : null, this.zzCO != null ? this.zzCO.zzyR : null, this.zzCO != null ? this.zzCO.zzyS : AdMobAdapter.class.getName(), this.zzye, this.zzCO != null ? this.zzCO.zzyT : null, this.zzCG.zzDY, this.zzCF.zzqf, this.zzCG.zzDW, this.zzCF.zzGI, this.zzCG.zzEb, this.zzCG.zzEc, this.zzCF.zzGF, null, adRequestInfoParcel.zzDO);
    }
}
