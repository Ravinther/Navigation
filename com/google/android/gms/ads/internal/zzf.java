package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhj;
import com.google.android.gms.internal.zzip;
import com.google.android.gms.internal.zziq.zzb;
import java.util.List;

@zzgk
public class zzf extends zzc {
    private boolean zzoM;

    /* renamed from: com.google.android.gms.ads.internal.zzf.1 */
    class C06011 implements zzb {
        final /* synthetic */ zzhj zzoN;
        final /* synthetic */ zzf zzoO;

        C06011(zzf com_google_android_gms_ads_internal_zzf, zzhj com_google_android_gms_internal_zzhj) {
            this.zzoO = com_google_android_gms_ads_internal_zzf;
            this.zzoN = com_google_android_gms_internal_zzhj;
        }

        public void zzbf() {
            this.zzoO.zzou.zza(this.zzoO.zzos.zzqf, this.zzoN).zza(this.zzoN.zzAR);
        }
    }

    public zzf(Context context, AdSizeParcel adSizeParcel, String str, zzeh com_google_android_gms_internal_zzeh, VersionInfoParcel versionInfoParcel) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzeh, versionInfoParcel, null);
    }

    private boolean zzb(zzhj com_google_android_gms_internal_zzhj, zzhj com_google_android_gms_internal_zzhj2) {
        View view;
        if (com_google_android_gms_internal_zzhj2.zzDX) {
            try {
                zzd view2 = com_google_android_gms_internal_zzhj2.zzyR.getView();
                if (view2 == null) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaE("View in mediation adapter is null.");
                    return false;
                }
                view = (View) zze.zzp(view2);
                View nextView = this.zzos.zzqc.getNextView();
                if (nextView != null) {
                    if (nextView instanceof zzip) {
                        ((zzip) nextView).destroy();
                    }
                    this.zzos.zzqc.removeView(nextView);
                }
                try {
                    zzb(view);
                } catch (Throwable th) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not add mediation view to view hierarchy.", th);
                    return false;
                }
            } catch (Throwable th2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get View from mediation adapter.", th2);
                return false;
            }
        } else if (!(com_google_android_gms_internal_zzhj2.zzGH == null || com_google_android_gms_internal_zzhj2.zzAR == null)) {
            com_google_android_gms_internal_zzhj2.zzAR.zza(com_google_android_gms_internal_zzhj2.zzGH);
            this.zzos.zzqc.removeAllViews();
            this.zzos.zzqc.setMinimumWidth(com_google_android_gms_internal_zzhj2.zzGH.widthPixels);
            this.zzos.zzqc.setMinimumHeight(com_google_android_gms_internal_zzhj2.zzGH.heightPixels);
            zzb(com_google_android_gms_internal_zzhj2.zzAR.getWebView());
        }
        if (this.zzos.zzqc.getChildCount() > 1) {
            this.zzos.zzqc.showNext();
        }
        if (com_google_android_gms_internal_zzhj != null) {
            view = this.zzos.zzqc.getNextView();
            if (view instanceof zzip) {
                ((zzip) view).zza(this.zzos.context, this.zzos.zzqf);
            } else if (view != null) {
                this.zzos.zzqc.removeView(view);
            }
            this.zzos.zzbO();
        }
        this.zzos.zzqc.setVisibility(0);
        return true;
    }

    public void setManualImpressionsEnabled(boolean enabled) {
        zzx.zzch("setManualImpressionsEnabled must be called from the main thread.");
        this.zzoM = enabled;
    }

    public void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by BannerAdManager.");
    }

    public boolean zza(AdRequestParcel adRequestParcel) {
        return super.zza(zzd(adRequestParcel));
    }

    public boolean zza(zzhj com_google_android_gms_internal_zzhj, zzhj com_google_android_gms_internal_zzhj2) {
        if (!super.zza(com_google_android_gms_internal_zzhj, com_google_android_gms_internal_zzhj2)) {
            return false;
        }
        if (!this.zzos.zzbP() || zzb(com_google_android_gms_internal_zzhj, com_google_android_gms_internal_zzhj2)) {
            zza(com_google_android_gms_internal_zzhj2, false);
            if (this.zzos.zzbP()) {
                if (com_google_android_gms_internal_zzhj2.zzAR != null) {
                    if (com_google_android_gms_internal_zzhj2.zzGF != null) {
                        this.zzou.zza(this.zzos.zzqf, com_google_android_gms_internal_zzhj2);
                    }
                    if (com_google_android_gms_internal_zzhj2.zzbY()) {
                        this.zzou.zza(this.zzos.zzqf, com_google_android_gms_internal_zzhj2).zza(com_google_android_gms_internal_zzhj2.zzAR);
                    } else {
                        com_google_android_gms_internal_zzhj2.zzAR.zzgS().zza(new C06011(this, com_google_android_gms_internal_zzhj2));
                    }
                }
            } else if (!(this.zzos.zzqy == null || com_google_android_gms_internal_zzhj2.zzGF == null)) {
                this.zzou.zza(this.zzos.zzqf, com_google_android_gms_internal_zzhj2, this.zzos.zzqy);
            }
            return true;
        }
        zze(0);
        return false;
    }

    protected boolean zzaU() {
        boolean z = true;
        if (!zzp.zzbx().zza(this.zzos.context.getPackageManager(), this.zzos.context.getPackageName(), "android.permission.INTERNET")) {
            zzk.zzcE().zza(this.zzos.zzqc, this.zzos.zzqf, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            z = false;
        }
        if (!zzp.zzbx().zzI(this.zzos.context)) {
            zzk.zzcE().zza(this.zzos.zzqc, this.zzos.zzqf, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            z = false;
        }
        if (!(z || this.zzos.zzqc == null)) {
            this.zzos.zzqc.setVisibility(0);
        }
        return z;
    }

    AdRequestParcel zzd(AdRequestParcel adRequestParcel) {
        if (adRequestParcel.zzsv == this.zzoM) {
            return adRequestParcel;
        }
        int i = adRequestParcel.versionCode;
        long j = adRequestParcel.zzsq;
        Bundle bundle = adRequestParcel.extras;
        int i2 = adRequestParcel.zzsr;
        List list = adRequestParcel.zzss;
        boolean z = adRequestParcel.zzst;
        int i3 = adRequestParcel.zzsu;
        boolean z2 = adRequestParcel.zzsv || this.zzoM;
        return new AdRequestParcel(i, j, bundle, i2, list, z, i3, z2, adRequestParcel.zzsw, adRequestParcel.zzsx, adRequestParcel.zzsy, adRequestParcel.zzsz, adRequestParcel.zzsA, adRequestParcel.zzsB, adRequestParcel.zzsC, adRequestParcel.zzsD, adRequestParcel.zzsE);
    }
}
