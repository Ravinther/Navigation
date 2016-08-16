package com.google.android.gms.internal;

public class zzig {

    public interface zza<D, R> {
        R zze(D d);
    }

    /* renamed from: com.google.android.gms.internal.zzig.1 */
    static class C09581 implements Runnable {
        final /* synthetic */ zzie zzIG;
        final /* synthetic */ zza zzIH;
        final /* synthetic */ zzih zzII;

        C09581(zzie com_google_android_gms_internal_zzie, zza com_google_android_gms_internal_zzig_zza, zzih com_google_android_gms_internal_zzih) {
            this.zzIG = com_google_android_gms_internal_zzie;
            this.zzIH = com_google_android_gms_internal_zzig_zza;
            this.zzII = com_google_android_gms_internal_zzih;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r3 = this;
            r0 = r3.zzIG;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r1 = r3.zzIH;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r2 = r3.zzII;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r2 = r2.get();	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r1 = r1.zze(r2);	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r0.zzf(r1);	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
        L_0x0011:
            return;
        L_0x0012:
            r0 = move-exception;
        L_0x0013:
            r0 = r3.zzIG;
            r1 = 1;
            r0.cancel(r1);
            goto L_0x0011;
        L_0x001a:
            r0 = move-exception;
            goto L_0x0013;
        L_0x001c:
            r0 = move-exception;
            goto L_0x0013;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzig.1.run():void");
        }
    }

    public static <A, B> zzih<B> zza(zzih<A> com_google_android_gms_internal_zzih_A, zza<A, B> com_google_android_gms_internal_zzig_zza_A__B) {
        zzih com_google_android_gms_internal_zzie = new zzie();
        com_google_android_gms_internal_zzih_A.zzc(new C09581(com_google_android_gms_internal_zzie, com_google_android_gms_internal_zzig_zza_A__B, com_google_android_gms_internal_zzih_A));
        return com_google_android_gms_internal_zzie;
    }
}
