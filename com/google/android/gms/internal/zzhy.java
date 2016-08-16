package com.google.android.gms.internal;

import android.content.Context;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

@zzgk
public class zzhy {
    private static zzl zzIf;
    public static final zza<Void> zzIg;
    private static final Object zzpm;

    public interface zza<T> {
        T zzfv();

        T zzh(InputStream inputStream);
    }

    /* renamed from: com.google.android.gms.internal.zzhy.1 */
    static class C09541 implements zza {
        C09541() {
        }

        public /* synthetic */ Object zzfv() {
            return zzgA();
        }

        public Void zzgA() {
            return null;
        }

        public /* synthetic */ Object zzh(InputStream inputStream) {
            return zzi(inputStream);
        }

        public Void zzi(InputStream inputStream) {
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhy.2 */
    class C09552 implements com.google.android.gms.internal.zzm.zza {
        final /* synthetic */ zzc zzIh;
        final /* synthetic */ zzhy zzIi;
        final /* synthetic */ String zzxv;

        C09552(zzhy com_google_android_gms_internal_zzhy, String str, zzc com_google_android_gms_internal_zzhy_zzc) {
            this.zzIi = com_google_android_gms_internal_zzhy;
            this.zzxv = str;
            this.zzIh = com_google_android_gms_internal_zzhy_zzc;
        }

        public void zze(zzr com_google_android_gms_internal_zzr) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Failed to load URL: " + this.zzxv + "\n" + com_google_android_gms_internal_zzr.toString());
            this.zzIh.zzb(null);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhy.3 */
    class C09563 extends zzab {
        final /* synthetic */ zzhy zzIi;
        final /* synthetic */ Map zzIj;

        C09563(zzhy com_google_android_gms_internal_zzhy, String str, com.google.android.gms.internal.zzm.zzb com_google_android_gms_internal_zzm_zzb, com.google.android.gms.internal.zzm.zza com_google_android_gms_internal_zzm_zza, Map map) {
            this.zzIi = com_google_android_gms_internal_zzhy;
            this.zzIj = map;
            super(str, com_google_android_gms_internal_zzm_zzb, com_google_android_gms_internal_zzm_zza);
        }

        public Map<String, String> getHeaders() throws zza {
            return this.zzIj == null ? super.getHeaders() : this.zzIj;
        }
    }

    private static class zzb<T> extends zzk<InputStream> {
        private final zza<T> zzIk;
        private final com.google.android.gms.internal.zzm.zzb<T> zzaG;

        /* renamed from: com.google.android.gms.internal.zzhy.zzb.1 */
        class C09571 implements com.google.android.gms.internal.zzm.zza {
            final /* synthetic */ com.google.android.gms.internal.zzm.zzb zzIl;
            final /* synthetic */ zza zzIm;

            C09571(com.google.android.gms.internal.zzm.zzb com_google_android_gms_internal_zzm_zzb, zza com_google_android_gms_internal_zzhy_zza) {
                this.zzIl = com_google_android_gms_internal_zzm_zzb;
                this.zzIm = com_google_android_gms_internal_zzhy_zza;
            }

            public void zze(zzr com_google_android_gms_internal_zzr) {
                this.zzIl.zzb(this.zzIm.zzfv());
            }
        }

        public zzb(String str, zza<T> com_google_android_gms_internal_zzhy_zza_T, com.google.android.gms.internal.zzm.zzb<T> com_google_android_gms_internal_zzm_zzb_T) {
            super(0, str, new C09571(com_google_android_gms_internal_zzm_zzb_T, com_google_android_gms_internal_zzhy_zza_T));
            this.zzIk = com_google_android_gms_internal_zzhy_zza_T;
            this.zzaG = com_google_android_gms_internal_zzm_zzb_T;
        }

        protected zzm<InputStream> zza(zzi com_google_android_gms_internal_zzi) {
            return zzm.zza(new ByteArrayInputStream(com_google_android_gms_internal_zzi.data), zzx.zzb(com_google_android_gms_internal_zzi));
        }

        protected /* synthetic */ void zza(Object obj) {
            zzj((InputStream) obj);
        }

        protected void zzj(InputStream inputStream) {
            this.zzaG.zzb(this.zzIk.zzh(inputStream));
        }
    }

    private class zzc<T> extends zzie<T> implements com.google.android.gms.internal.zzm.zzb<T> {
        final /* synthetic */ zzhy zzIi;

        private zzc(zzhy com_google_android_gms_internal_zzhy) {
            this.zzIi = com_google_android_gms_internal_zzhy;
        }

        public void zzb(T t) {
            super.zzf(t);
        }
    }

    static {
        zzpm = new Object();
        zzIg = new C09541();
    }

    public zzhy(Context context) {
        zzIf = zzP(context);
    }

    private static zzl zzP(Context context) {
        zzl com_google_android_gms_internal_zzl;
        synchronized (zzpm) {
            if (zzIf == null) {
                zzIf = zzac.zza(context.getApplicationContext());
            }
            com_google_android_gms_internal_zzl = zzIf;
        }
        return com_google_android_gms_internal_zzl;
    }

    public <T> zzih<T> zza(String str, zza<T> com_google_android_gms_internal_zzhy_zza_T) {
        Object com_google_android_gms_internal_zzhy_zzc = new zzc();
        zzIf.zze(new zzb(str, com_google_android_gms_internal_zzhy_zza_T, com_google_android_gms_internal_zzhy_zzc));
        return com_google_android_gms_internal_zzhy_zzc;
    }

    public zzih<String> zzb(String str, Map<String, String> map) {
        Object com_google_android_gms_internal_zzhy_zzc = new zzc();
        zzIf.zze(new C09563(this, str, com_google_android_gms_internal_zzhy_zzc, new C09552(this, str, com_google_android_gms_internal_zzhy_zzc), map));
        return com_google_android_gms_internal_zzhy_zzc;
    }
}
