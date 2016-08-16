package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.common.internal.zzx;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzof {
    private static volatile zzof zzaId;
    private final Context mContext;
    private volatile zzok zzMm;
    private final List<zzog> zzaIe;
    private final zzoa zzaIf;
    private final zza zzaIg;
    private UncaughtExceptionHandler zzaIh;

    /* renamed from: com.google.android.gms.internal.zzof.1 */
    class C09761 implements Runnable {
        final /* synthetic */ zzob zzaIi;
        final /* synthetic */ zzof zzaIj;

        C09761(zzof com_google_android_gms_internal_zzof, zzob com_google_android_gms_internal_zzob) {
            this.zzaIj = com_google_android_gms_internal_zzof;
            this.zzaIi = com_google_android_gms_internal_zzob;
        }

        public void run() {
            this.zzaIi.zzxo().zza(this.zzaIi);
            for (zzog zza : this.zzaIj.zzaIe) {
                zza.zza(this.zzaIi);
            }
            this.zzaIj.zzb(this.zzaIi);
        }
    }

    private class zza extends ThreadPoolExecutor {
        final /* synthetic */ zzof zzaIj;

        /* renamed from: com.google.android.gms.internal.zzof.zza.1 */
        class C09771 extends FutureTask<T> {
            final /* synthetic */ zza zzaIk;

            C09771(zza com_google_android_gms_internal_zzof_zza, Runnable runnable, Object obj) {
                this.zzaIk = com_google_android_gms_internal_zzof_zza;
                super(runnable, obj);
            }

            protected void setException(Throwable error) {
                UncaughtExceptionHandler zzb = this.zzaIk.zzaIj.zzaIh;
                if (zzb != null) {
                    zzb.uncaughtException(Thread.currentThread(), error);
                } else if (Log.isLoggable("GAv4", 6)) {
                    Log.e("GAv4", "MeasurementExecutor: job failed with " + error);
                }
                super.setException(error);
            }
        }

        public zza(zzof com_google_android_gms_internal_zzof) {
            this.zzaIj = com_google_android_gms_internal_zzof;
            super(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
            setThreadFactory(new zzb());
        }

        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
            return new C09771(this, runnable, value);
        }
    }

    private static class zzb implements ThreadFactory {
        private static final AtomicInteger zzaIl;

        static {
            zzaIl = new AtomicInteger();
        }

        private zzb() {
        }

        public Thread newThread(Runnable target) {
            return new zzc(target, "measurement-" + zzaIl.incrementAndGet());
        }
    }

    private static class zzc extends Thread {
        zzc(Runnable runnable, String str) {
            super(runnable, str);
        }

        public void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    zzof(Context context) {
        Context applicationContext = context.getApplicationContext();
        zzx.zzv(applicationContext);
        this.mContext = applicationContext;
        this.zzaIg = new zza(this);
        this.zzaIe = new CopyOnWriteArrayList();
        this.zzaIf = new zzoa();
    }

    public static zzof zzaI(Context context) {
        zzx.zzv(context);
        if (zzaId == null) {
            synchronized (zzof.class) {
                if (zzaId == null) {
                    zzaId = new zzof(context);
                }
            }
        }
        return zzaId;
    }

    private void zzb(zzob com_google_android_gms_internal_zzob) {
        zzx.zzci("deliver should be called from worker thread");
        zzx.zzb(com_google_android_gms_internal_zzob.zzxm(), (Object) "Measurement must be submitted");
        List<zzoh> zzxj = com_google_android_gms_internal_zzob.zzxj();
        if (!zzxj.isEmpty()) {
            Set hashSet = new HashSet();
            for (zzoh com_google_android_gms_internal_zzoh : zzxj) {
                Uri zzhs = com_google_android_gms_internal_zzoh.zzhs();
                if (!hashSet.contains(zzhs)) {
                    hashSet.add(zzhs);
                    com_google_android_gms_internal_zzoh.zzb(com_google_android_gms_internal_zzob);
                }
            }
        }
    }

    public static void zzic() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void zza(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzaIh = uncaughtExceptionHandler;
    }

    public <V> Future<V> zzb(Callable<V> callable) {
        zzx.zzv(callable);
        if (!(Thread.currentThread() instanceof zzc)) {
            return this.zzaIg.submit(callable);
        }
        Future futureTask = new FutureTask(callable);
        futureTask.run();
        return futureTask;
    }

    void zze(zzob com_google_android_gms_internal_zzob) {
        if (com_google_android_gms_internal_zzob.zzxq()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        } else if (com_google_android_gms_internal_zzob.zzxm()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        } else {
            zzob zzxh = com_google_android_gms_internal_zzob.zzxh();
            zzxh.zzxn();
            this.zzaIg.execute(new C09761(this, zzxh));
        }
    }

    public void zzf(Runnable runnable) {
        zzx.zzv(runnable);
        this.zzaIg.submit(runnable);
    }

    public zzok zzxu() {
        if (this.zzMm == null) {
            synchronized (this) {
                if (this.zzMm == null) {
                    zzok com_google_android_gms_internal_zzok = new zzok();
                    PackageManager packageManager = this.mContext.getPackageManager();
                    String packageName = this.mContext.getPackageName();
                    com_google_android_gms_internal_zzok.setAppId(packageName);
                    com_google_android_gms_internal_zzok.setAppInstallerId(packageManager.getInstallerPackageName(packageName));
                    String str = null;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.mContext.getPackageName(), 0);
                        if (packageInfo != null) {
                            CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            if (!TextUtils.isEmpty(applicationLabel)) {
                                packageName = applicationLabel.toString();
                            }
                            str = packageInfo.versionName;
                        }
                    } catch (NameNotFoundException e) {
                        Log.e("GAv4", "Error retrieving package info: appName set to " + packageName);
                    }
                    com_google_android_gms_internal_zzok.setAppName(packageName);
                    com_google_android_gms_internal_zzok.setAppVersion(str);
                    this.zzMm = com_google_android_gms_internal_zzok;
                }
            }
        }
        return this.zzMm;
    }

    public zzom zzxv() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        zzom com_google_android_gms_internal_zzom = new zzom();
        com_google_android_gms_internal_zzom.setLanguage(zzam.zza(Locale.getDefault()));
        com_google_android_gms_internal_zzom.zzhO(displayMetrics.widthPixels);
        com_google_android_gms_internal_zzom.zzhP(displayMetrics.heightPixels);
        return com_google_android_gms_internal_zzom;
    }
}
