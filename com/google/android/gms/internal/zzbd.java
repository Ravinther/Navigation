package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.overlay.zzn;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zziq.zza;
import org.json.JSONObject;

@zzgk
public class zzbd implements zzbb {
    private final zzip zzoL;

    /* renamed from: com.google.android.gms.internal.zzbd.1 */
    class C08601 implements Runnable {
        final /* synthetic */ String zzru;
        final /* synthetic */ JSONObject zzrv;
        final /* synthetic */ zzbd zzrw;

        C08601(zzbd com_google_android_gms_internal_zzbd, String str, JSONObject jSONObject) {
            this.zzrw = com_google_android_gms_internal_zzbd;
            this.zzru = str;
            this.zzrv = jSONObject;
        }

        public void run() {
            this.zzrw.zzoL.zza(this.zzru, this.zzrv);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.2 */
    class C08612 implements Runnable {
        final /* synthetic */ String zzru;
        final /* synthetic */ zzbd zzrw;
        final /* synthetic */ String zzrx;

        C08612(zzbd com_google_android_gms_internal_zzbd, String str, String str2) {
            this.zzrw = com_google_android_gms_internal_zzbd;
            this.zzru = str;
            this.zzrx = str2;
        }

        public void run() {
            this.zzrw.zzoL.zza(this.zzru, this.zzrx);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.3 */
    class C08623 implements Runnable {
        final /* synthetic */ zzbd zzrw;
        final /* synthetic */ String zzry;

        C08623(zzbd com_google_android_gms_internal_zzbd, String str) {
            this.zzrw = com_google_android_gms_internal_zzbd;
            this.zzry = str;
        }

        public void run() {
            this.zzrw.zzoL.loadData(this.zzry, "text/html", "UTF-8");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.4 */
    class C08634 implements Runnable {
        final /* synthetic */ zzbd zzrw;
        final /* synthetic */ String zzry;

        C08634(zzbd com_google_android_gms_internal_zzbd, String str) {
            this.zzrw = com_google_android_gms_internal_zzbd;
            this.zzry = str;
        }

        public void run() {
            this.zzrw.zzoL.loadData(this.zzry, "text/html", "UTF-8");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.5 */
    class C08645 implements Runnable {
        final /* synthetic */ String zzrr;
        final /* synthetic */ zzbd zzrw;

        C08645(zzbd com_google_android_gms_internal_zzbd, String str) {
            this.zzrw = com_google_android_gms_internal_zzbd;
            this.zzrr = str;
        }

        public void run() {
            this.zzrw.zzoL.loadUrl(this.zzrr);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.6 */
    class C08656 implements zza {
        final /* synthetic */ zzbd zzrw;
        final /* synthetic */ zzbb.zza zzrz;

        C08656(zzbd com_google_android_gms_internal_zzbd, zzbb.zza com_google_android_gms_internal_zzbb_zza) {
            this.zzrw = com_google_android_gms_internal_zzbd;
            this.zzrz = com_google_android_gms_internal_zzbb_zza;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, boolean z) {
            this.zzrz.zzcj();
        }
    }

    public zzbd(Context context, VersionInfoParcel versionInfoParcel, zzan com_google_android_gms_internal_zzan) {
        this.zzoL = zzp.zzby().zza(context, new AdSizeParcel(), false, false, com_google_android_gms_internal_zzan, versionInfoParcel);
        this.zzoL.setWillNotDraw(true);
    }

    private void runOnUiThread(Runnable runnable) {
        if (zzk.zzcE().zzgI()) {
            runnable.run();
        } else {
            zzhu.zzHK.post(runnable);
        }
    }

    public void destroy() {
        this.zzoL.destroy();
    }

    public void zza(com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzdd com_google_android_gms_internal_zzdd, zzn com_google_android_gms_ads_internal_overlay_zzn, boolean z, zzdi com_google_android_gms_internal_zzdi, zzdk com_google_android_gms_internal_zzdk, zze com_google_android_gms_ads_internal_zze, zzfc com_google_android_gms_internal_zzfc) {
        this.zzoL.zzgS().zzb(com_google_android_gms_ads_internal_client_zza, com_google_android_gms_ads_internal_overlay_zzg, com_google_android_gms_internal_zzdd, com_google_android_gms_ads_internal_overlay_zzn, z, com_google_android_gms_internal_zzdi, com_google_android_gms_internal_zzdk, new zze(false), com_google_android_gms_internal_zzfc);
    }

    public void zza(zzbb.zza com_google_android_gms_internal_zzbb_zza) {
        this.zzoL.zzgS().zza(new C08656(this, com_google_android_gms_internal_zzbb_zza));
    }

    public void zza(String str, zzdg com_google_android_gms_internal_zzdg) {
        this.zzoL.zzgS().zza(str, com_google_android_gms_internal_zzdg);
    }

    public void zza(String str, String str2) {
        runOnUiThread(new C08612(this, str, str2));
    }

    public void zza(String str, JSONObject jSONObject) {
        runOnUiThread(new C08601(this, str, jSONObject));
    }

    public void zzb(String str, zzdg com_google_android_gms_internal_zzdg) {
        this.zzoL.zzgS().zzb(str, com_google_android_gms_internal_zzdg);
    }

    public void zzb(String str, JSONObject jSONObject) {
        this.zzoL.zzb(str, jSONObject);
    }

    public zzbf zzci() {
        return new zzbg(this);
    }

    public void zzs(String str) {
        runOnUiThread(new C08623(this, String.format("<!DOCTYPE html><html><head><script src=\"%s\"></script></head><body></body></html>", new Object[]{str})));
    }

    public void zzt(String str) {
        runOnUiThread(new C08645(this, str));
    }

    public void zzu(String str) {
        runOnUiThread(new C08634(this, str));
    }
}
