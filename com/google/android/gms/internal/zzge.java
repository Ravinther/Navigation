package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzn;
import com.google.android.gms.internal.zzga.zza;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzgk
public class zzge extends zzhq {
    private final zza zzCD;
    private final zzhj.zza zzCF;
    private final AdResponseParcel zzCG;
    private final zzgf zzCP;
    private Future<zzhj> zzCQ;
    private final Object zzpc;

    /* renamed from: com.google.android.gms.internal.zzge.1 */
    class C09241 implements Runnable {
        final /* synthetic */ zzge zzCR;
        final /* synthetic */ zzhj zzpx;

        C09241(zzge com_google_android_gms_internal_zzge, zzhj com_google_android_gms_internal_zzhj) {
            this.zzCR = com_google_android_gms_internal_zzge;
            this.zzpx = com_google_android_gms_internal_zzhj;
        }

        public void run() {
            this.zzCR.zzCD.zzb(this.zzpx);
        }
    }

    public zzge(Context context, zzn com_google_android_gms_ads_internal_zzn, zzbc com_google_android_gms_internal_zzbc, zzhj.zza com_google_android_gms_internal_zzhj_zza, zzan com_google_android_gms_internal_zzan, zza com_google_android_gms_internal_zzga_zza) {
        this(com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzga_zza, new zzgf(context, com_google_android_gms_ads_internal_zzn, com_google_android_gms_internal_zzbc, new zzhy(context), com_google_android_gms_internal_zzan, com_google_android_gms_internal_zzhj_zza));
    }

    zzge(zzhj.zza com_google_android_gms_internal_zzhj_zza, zza com_google_android_gms_internal_zzga_zza, zzgf com_google_android_gms_internal_zzgf) {
        this.zzpc = new Object();
        this.zzCF = com_google_android_gms_internal_zzhj_zza;
        this.zzCG = com_google_android_gms_internal_zzhj_zza.zzGM;
        this.zzCD = com_google_android_gms_internal_zzga_zza;
        this.zzCP = com_google_android_gms_internal_zzgf;
    }

    private zzhj zzA(int i) {
        return new zzhj(this.zzCF.zzGL.zzDy, null, null, i, null, null, this.zzCG.orientation, this.zzCG.zzyA, this.zzCF.zzGL.zzDB, false, null, null, null, null, null, this.zzCG.zzDY, this.zzCF.zzqf, this.zzCG.zzDW, this.zzCF.zzGI, this.zzCG.zzEb, this.zzCG.zzEc, this.zzCF.zzGF, null, this.zzCF.zzGL.zzDO);
    }

    public void onStop() {
        synchronized (this.zzpc) {
            if (this.zzCQ != null) {
                this.zzCQ.cancel(true);
            }
        }
    }

    public void zzdG() {
        zzhj com_google_android_gms_internal_zzhj;
        int i;
        try {
            synchronized (this.zzpc) {
                this.zzCQ = zzht.zza(this.zzCP);
            }
            com_google_android_gms_internal_zzhj = (zzhj) this.zzCQ.get(60000, TimeUnit.MILLISECONDS);
            i = -2;
        } catch (TimeoutException e) {
            zzb.zzaE("Timed out waiting for native ad.");
            this.zzCQ.cancel(true);
            i = 2;
            com_google_android_gms_internal_zzhj = null;
        } catch (ExecutionException e2) {
            i = 0;
            com_google_android_gms_internal_zzhj = null;
        } catch (InterruptedException e3) {
            com_google_android_gms_internal_zzhj = null;
            i = -1;
        } catch (CancellationException e4) {
            com_google_android_gms_internal_zzhj = null;
            i = -1;
        }
        if (com_google_android_gms_internal_zzhj == null) {
            com_google_android_gms_internal_zzhj = zzA(i);
        }
        zzhu.zzHK.post(new C09241(this, com_google_android_gms_internal_zzhj));
    }
}
