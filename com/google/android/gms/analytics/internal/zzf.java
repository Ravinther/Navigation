package com.google.android.gms.analytics.internal;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzlm;
import com.google.android.gms.internal.zzlo;
import com.google.android.gms.internal.zzof;
import java.lang.Thread.UncaughtExceptionHandler;

public class zzf {
    private static zzf zzLC;
    private final Context mContext;
    private final Context zzLD;
    private final zzr zzLE;
    private final zzaf zzLF;
    private final zzof zzLG;
    private final zzb zzLH;
    private final zzv zzLI;
    private final zzan zzLJ;
    private final zzai zzLK;
    private final GoogleAnalytics zzLL;
    private final zzn zzLM;
    private final zza zzLN;
    private final zzk zzLO;
    private final zzu zzLP;
    private final zzlm zzpO;

    /* renamed from: com.google.android.gms.analytics.internal.zzf.1 */
    class C06211 implements UncaughtExceptionHandler {
        final /* synthetic */ zzf zzLQ;

        C06211(zzf com_google_android_gms_analytics_internal_zzf) {
            this.zzLQ = com_google_android_gms_analytics_internal_zzf;
        }

        public void uncaughtException(Thread thread, Throwable error) {
            zzaf zzir = this.zzLQ.zzir();
            if (zzir != null) {
                zzir.zze("Job execution failed", error);
            }
        }
    }

    protected zzf(zzg com_google_android_gms_analytics_internal_zzg) {
        Object applicationContext = com_google_android_gms_analytics_internal_zzg.getApplicationContext();
        zzx.zzb(applicationContext, (Object) "Application context can't be null");
        zzx.zzb(applicationContext instanceof Application, (Object) "getApplicationContext didn't return the application");
        Context zziq = com_google_android_gms_analytics_internal_zzg.zziq();
        zzx.zzv(zziq);
        this.mContext = applicationContext;
        this.zzLD = zziq;
        this.zzpO = com_google_android_gms_analytics_internal_zzg.zzh(this);
        this.zzLE = com_google_android_gms_analytics_internal_zzg.zzg(this);
        zzaf zzf = com_google_android_gms_analytics_internal_zzg.zzf(this);
        zzf.zza();
        this.zzLF = zzf;
        if (zzif().zzjk()) {
            zzie().zzba("Google Analytics " + zze.VERSION + " is starting up.");
        } else {
            zzie().zzba("Google Analytics " + zze.VERSION + " is starting up. " + "To enable debug logging on a device run:\n" + "  adb shell setprop log.tag.GAv4 DEBUG\n" + "  adb logcat -s GAv4");
        }
        zzai zzq = com_google_android_gms_analytics_internal_zzg.zzq(this);
        zzq.zza();
        this.zzLK = zzq;
        zzan zze = com_google_android_gms_analytics_internal_zzg.zze(this);
        zze.zza();
        this.zzLJ = zze;
        zzb zzl = com_google_android_gms_analytics_internal_zzg.zzl(this);
        zzn zzd = com_google_android_gms_analytics_internal_zzg.zzd(this);
        zza zzc = com_google_android_gms_analytics_internal_zzg.zzc(this);
        zzk zzb = com_google_android_gms_analytics_internal_zzg.zzb(this);
        zzu zza = com_google_android_gms_analytics_internal_zzg.zza(this);
        zzof zzY = com_google_android_gms_analytics_internal_zzg.zzY(applicationContext);
        zzY.zza(zzip());
        this.zzLG = zzY;
        GoogleAnalytics zzi = com_google_android_gms_analytics_internal_zzg.zzi(this);
        zzd.zza();
        this.zzLM = zzd;
        zzc.zza();
        this.zzLN = zzc;
        zzb.zza();
        this.zzLO = zzb;
        zza.zza();
        this.zzLP = zza;
        zzv zzp = com_google_android_gms_analytics_internal_zzg.zzp(this);
        zzp.zza();
        this.zzLI = zzp;
        zzl.zza();
        this.zzLH = zzl;
        if (zzif().zzjk()) {
            zzie().zzb("Device AnalyticsService version", zze.VERSION);
        }
        zzi.zza();
        this.zzLL = zzi;
        zzl.start();
    }

    public static zzf zzX(Context context) {
        zzx.zzv(context);
        if (zzLC == null) {
            synchronized (zzf.class) {
                if (zzLC == null) {
                    zzlm zzpN = zzlo.zzpN();
                    long elapsedRealtime = zzpN.elapsedRealtime();
                    zzf com_google_android_gms_analytics_internal_zzf = new zzf(new zzg(context.getApplicationContext()));
                    zzLC = com_google_android_gms_analytics_internal_zzf;
                    GoogleAnalytics.zzhx();
                    elapsedRealtime = zzpN.elapsedRealtime() - elapsedRealtime;
                    long longValue = ((Long) zzy.zzNO.get()).longValue();
                    if (elapsedRealtime > longValue) {
                        com_google_android_gms_analytics_internal_zzf.zzie().zzc("Slow initialization (ms)", Long.valueOf(elapsedRealtime), Long.valueOf(longValue));
                    }
                }
            }
        }
        return zzLC;
    }

    private void zza(zzd com_google_android_gms_analytics_internal_zzd) {
        zzx.zzb((Object) com_google_android_gms_analytics_internal_zzd, (Object) "Analytics service not created/initialized");
        zzx.zzb(com_google_android_gms_analytics_internal_zzd.isInitialized(), (Object) "Analytics service not initialized");
    }

    public Context getContext() {
        return this.mContext;
    }

    public zzan zzhA() {
        zza(this.zzLJ);
        return this.zzLJ;
    }

    public zzb zzhz() {
        zza(this.zzLH);
        return this.zzLH;
    }

    public void zzic() {
        zzof.zzic();
    }

    public zzlm zzid() {
        return this.zzpO;
    }

    public zzaf zzie() {
        zza(this.zzLF);
        return this.zzLF;
    }

    public zzr zzif() {
        return this.zzLE;
    }

    public zzof zzig() {
        zzx.zzv(this.zzLG);
        return this.zzLG;
    }

    public zzv zzih() {
        zza(this.zzLI);
        return this.zzLI;
    }

    public zzai zzii() {
        zza(this.zzLK);
        return this.zzLK;
    }

    public zzk zzil() {
        zza(this.zzLO);
        return this.zzLO;
    }

    public zzu zzim() {
        return this.zzLP;
    }

    protected UncaughtExceptionHandler zzip() {
        return new C06211(this);
    }

    public Context zziq() {
        return this.zzLD;
    }

    public zzaf zzir() {
        return this.zzLF;
    }

    public GoogleAnalytics zzis() {
        zzx.zzv(this.zzLL);
        zzx.zzb(this.zzLL.isInitialized(), (Object) "Analytics instance not initialized");
        return this.zzLL;
    }

    public zzai zzit() {
        return (this.zzLK == null || !this.zzLK.isInitialized()) ? null : this.zzLK;
    }

    public zza zziu() {
        zza(this.zzLN);
        return this.zzLN;
    }

    public zzn zziv() {
        zza(this.zzLM);
        return this.zzLM;
    }
}
