package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Window;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzcd;
import com.google.android.gms.internal.zzdk;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhj;
import com.google.android.gms.internal.zzhj.zza;
import com.google.android.gms.internal.zzip;

@zzgk
public class zzk extends zzc implements zzdk {
    protected transient boolean zzpj;

    public zzk(Context context, AdSizeParcel adSizeParcel, String str, zzeh com_google_android_gms_internal_zzeh, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzeh, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
        this.zzpj = false;
    }

    private void zzb(Bundle bundle) {
        zzp.zzbx().zzb(this.zzos.context, this.zzos.zzqb.zzIz, "gmob-apps", bundle, false);
    }

    private void zzbm() {
        if (this.zzos.zzbP()) {
            this.zzos.zzbM();
            this.zzos.zzqg = null;
            this.zzos.zzpk = false;
            this.zzpj = false;
        }
    }

    public void showInterstitial() {
        zzx.zzch("showInterstitial must be called on the main UI thread.");
        if (this.zzos.zzqg == null) {
            zzb.zzaE("The interstitial has not loaded.");
            return;
        }
        if (((Boolean) zzby.zzuZ.get()).booleanValue()) {
            Bundle bundle;
            String packageName = this.zzos.context.getApplicationContext() != null ? this.zzos.context.getApplicationContext().getPackageName() : this.zzos.context.getPackageName();
            if (!this.zzpj) {
                zzb.zzaE("It is not recommended to show an interstitial before onAdLoaded completes.");
                bundle = new Bundle();
                bundle.putString("appid", packageName);
                bundle.putString("action", "show_interstitial_before_load_finish");
                zzb(bundle);
            }
            if (!zzp.zzbx().zzO(this.zzos.context)) {
                zzb.zzaE("It is not recommended to show an interstitial when app is not in foreground.");
                bundle = new Bundle();
                bundle.putString("appid", packageName);
                bundle.putString("action", "show_interstitial_app_not_in_foreground");
                zzb(bundle);
            }
        }
        if (!this.zzos.zzbQ()) {
            if (this.zzos.zzqg.zzDX) {
                try {
                    this.zzos.zzqg.zzyR.showInterstitial();
                } catch (Throwable e) {
                    zzb.zzd("Could not show interstitial.", e);
                    zzbm();
                }
            } else if (this.zzos.zzqg.zzAR == null) {
                zzb.zzaE("The interstitial failed to load.");
            } else if (this.zzos.zzqg.zzAR.zzgW()) {
                zzb.zzaE("The interstitial is already showing.");
            } else {
                this.zzos.zzqg.zzAR.zzC(true);
                if (this.zzos.zzqg.zzGF != null) {
                    this.zzou.zza(this.zzos.zzqf, this.zzos.zzqg);
                }
                InterstitialAdParameterParcel interstitialAdParameterParcel = new InterstitialAdParameterParcel(this.zzos.zzpk, zzbl());
                int requestedOrientation = this.zzos.zzqg.zzAR.getRequestedOrientation();
                if (requestedOrientation == -1) {
                    requestedOrientation = this.zzos.zzqg.orientation;
                }
                zzp.zzbv().zza(this.zzos.context, new AdOverlayInfoParcel(this, this, this, this.zzos.zzqg.zzAR, requestedOrientation, this.zzos.zzqb, this.zzos.zzqg.zzEc, interstitialAdParameterParcel));
            }
        }
    }

    protected zzip zza(zza com_google_android_gms_internal_zzhj_zza, zze com_google_android_gms_ads_internal_zze) {
        zzip zza = zzp.zzby().zza(this.zzos.context, this.zzos.zzqf, false, false, this.zzos.zzqa, this.zzos.zzqb, this.zzov);
        zza.zzgS().zzb(this, null, this, this, ((Boolean) zzby.zzuN.get()).booleanValue(), this, this, com_google_android_gms_ads_internal_zze, null);
        zza.zzaG(com_google_android_gms_internal_zzhj_zza.zzGL.zzDQ);
        return zza;
    }

    public boolean zza(AdRequestParcel adRequestParcel, zzcd com_google_android_gms_internal_zzcd) {
        if (this.zzos.zzqg == null) {
            return super.zza(adRequestParcel, com_google_android_gms_internal_zzcd);
        }
        zzb.zzaE("An interstitial is already loading. Aborting.");
        return false;
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzhj com_google_android_gms_internal_zzhj, boolean z) {
        if (this.zzos.zzbP() && com_google_android_gms_internal_zzhj.zzAR != null) {
            zzp.zzbz().zza(com_google_android_gms_internal_zzhj.zzAR.getWebView());
        }
        return this.zzor.zzbr();
    }

    public boolean zza(zzhj com_google_android_gms_internal_zzhj, zzhj com_google_android_gms_internal_zzhj2) {
        if (!super.zza(com_google_android_gms_internal_zzhj, com_google_android_gms_internal_zzhj2)) {
            return false;
        }
        if (!(this.zzos.zzbP() || this.zzos.zzqy == null || com_google_android_gms_internal_zzhj2.zzGF == null)) {
            this.zzou.zza(this.zzos.zzqf, com_google_android_gms_internal_zzhj2, this.zzos.zzqy);
        }
        return true;
    }

    protected boolean zzaT() {
        if (!super.zzaT()) {
            return false;
        }
        this.zzpj = true;
        return true;
    }

    public void zzaV() {
        zzbm();
        super.zzaV();
    }

    public void zzaW() {
        recordImpression();
        super.zzaW();
    }

    protected boolean zzbl() {
        if (!(this.zzos.context instanceof Activity)) {
            return false;
        }
        Window window = ((Activity) this.zzos.context).getWindow();
        if (window == null || window.getDecorView() == null) {
            return false;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        window.getDecorView().getGlobalVisibleRect(rect, null);
        window.getDecorView().getWindowVisibleDisplayFrame(rect2);
        boolean z = (rect.bottom == 0 || rect2.bottom == 0 || rect.top != rect2.top) ? false : true;
        return z;
    }

    public void zzd(boolean z) {
        this.zzos.zzpk = z;
    }
}
