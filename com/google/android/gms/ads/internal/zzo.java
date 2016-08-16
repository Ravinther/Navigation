package com.google.android.gms.ads.internal;

import android.os.Handler;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhu;
import java.lang.ref.WeakReference;

@zzgk
public class zzo {
    private boolean zzpA;
    private boolean zzpB;
    private long zzpC;
    private final zza zzpy;
    private AdRequestParcel zzpz;
    private final Runnable zzx;

    /* renamed from: com.google.android.gms.ads.internal.zzo.1 */
    class C06071 implements Runnable {
        final /* synthetic */ WeakReference zzpD;
        final /* synthetic */ zzo zzpE;

        C06071(zzo com_google_android_gms_ads_internal_zzo, WeakReference weakReference) {
            this.zzpE = com_google_android_gms_ads_internal_zzo;
            this.zzpD = weakReference;
        }

        public void run() {
            this.zzpE.zzpA = false;
            zza com_google_android_gms_ads_internal_zza = (zza) this.zzpD.get();
            if (com_google_android_gms_ads_internal_zza != null) {
                com_google_android_gms_ads_internal_zza.zzc(this.zzpE.zzpz);
            }
        }
    }

    public static class zza {
        private final Handler mHandler;

        public zza(Handler handler) {
            this.mHandler = handler;
        }

        public boolean postDelayed(Runnable runnable, long timeFromNowInMillis) {
            return this.mHandler.postDelayed(runnable, timeFromNowInMillis);
        }

        public void removeCallbacks(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

    public zzo(zza com_google_android_gms_ads_internal_zza) {
        this(com_google_android_gms_ads_internal_zza, new zza(zzhu.zzHK));
    }

    zzo(zza com_google_android_gms_ads_internal_zza, zza com_google_android_gms_ads_internal_zzo_zza) {
        this.zzpA = false;
        this.zzpB = false;
        this.zzpC = 0;
        this.zzpy = com_google_android_gms_ads_internal_zzo_zza;
        this.zzx = new C06071(this, new WeakReference(com_google_android_gms_ads_internal_zza));
    }

    public void cancel() {
        this.zzpA = false;
        this.zzpy.removeCallbacks(this.zzx);
    }

    public void pause() {
        this.zzpB = true;
        if (this.zzpA) {
            this.zzpy.removeCallbacks(this.zzx);
        }
    }

    public void resume() {
        this.zzpB = false;
        if (this.zzpA) {
            this.zzpA = false;
            zza(this.zzpz, this.zzpC);
        }
    }

    public void zza(AdRequestParcel adRequestParcel, long j) {
        if (this.zzpA) {
            zzb.zzaE("An ad refresh is already scheduled.");
            return;
        }
        this.zzpz = adRequestParcel;
        this.zzpA = true;
        this.zzpC = j;
        if (!this.zzpB) {
            zzb.zzaD("Scheduling ad refresh " + j + " milliseconds from now.");
            this.zzpy.postDelayed(this.zzx, j);
        }
    }

    public boolean zzbr() {
        return this.zzpA;
    }

    public void zzf(AdRequestParcel adRequestParcel) {
        zza(adRequestParcel, 60000);
    }
}
