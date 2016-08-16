package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@zzgk
public final class zzht {
    private static final ExecutorService zzHy;
    private static final ExecutorService zzHz;

    /* renamed from: com.google.android.gms.internal.zzht.1 */
    static class C09451 implements Callable<Void> {
        final /* synthetic */ Runnable zzHA;

        C09451(Runnable runnable) {
            this.zzHA = runnable;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzgp();
        }

        public Void zzgp() {
            this.zzHA.run();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzht.2 */
    static class C09462 implements Callable<Void> {
        final /* synthetic */ Runnable zzHA;

        C09462(Runnable runnable) {
            this.zzHA = runnable;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzgp();
        }

        public Void zzgp() {
            this.zzHA.run();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzht.3 */
    static class C09473 implements Runnable {
        final /* synthetic */ Callable zzHB;
        final /* synthetic */ zzie zzrp;

        C09473(zzie com_google_android_gms_internal_zzie, Callable callable) {
            this.zzrp = com_google_android_gms_internal_zzie;
            this.zzHB = callable;
        }

        public void run() {
            try {
                Process.setThreadPriority(10);
                this.zzrp.zzf(this.zzHB.call());
            } catch (Throwable e) {
                zzp.zzbA().zzc(e, true);
                this.zzrp.cancel(true);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzht.4 */
    static class C09484 implements ThreadFactory {
        private final AtomicInteger zzHC;
        final /* synthetic */ String zzHD;

        C09484(String str) {
            this.zzHD = str;
            this.zzHC = new AtomicInteger(1);
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AdWorker(" + this.zzHD + ") #" + this.zzHC.getAndIncrement());
        }
    }

    static {
        zzHy = Executors.newFixedThreadPool(10, zzav("Default"));
        zzHz = Executors.newFixedThreadPool(5, zzav("Loader"));
    }

    public static zzih<Void> zza(int i, Runnable runnable) {
        return i == 1 ? zza(zzHz, new C09451(runnable)) : zza(zzHy, new C09462(runnable));
    }

    public static zzih<Void> zza(Runnable runnable) {
        return zza(0, runnable);
    }

    public static <T> zzih<T> zza(Callable<T> callable) {
        return zza(zzHy, (Callable) callable);
    }

    public static <T> zzih<T> zza(ExecutorService executorService, Callable<T> callable) {
        Object com_google_android_gms_internal_zzie = new zzie();
        try {
            executorService.submit(new C09473(com_google_android_gms_internal_zzie, callable));
        } catch (Throwable e) {
            zzb.zzd("Thread execution is rejected.", e);
            com_google_android_gms_internal_zzie.cancel(true);
        }
        return com_google_android_gms_internal_zzie;
    }

    private static ThreadFactory zzav(String str) {
        return new C09484(str);
    }
}
