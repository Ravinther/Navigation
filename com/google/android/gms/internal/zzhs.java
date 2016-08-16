package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import java.util.concurrent.Future;

@zzgk
public final class zzhs {

    public interface zzb {
        void zzd(Bundle bundle);
    }

    private static abstract class zza extends zzhq {
        private zza() {
        }

        public void onStop() {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhs.1 */
    static class C09411 extends zza {
        final /* synthetic */ boolean zzHv;
        final /* synthetic */ Context zzrn;

        C09411(Context context, boolean z) {
            this.zzrn = context;
            this.zzHv = z;
            super();
        }

        public void zzdG() {
            Editor edit = zzhs.zzv(this.zzrn).edit();
            edit.putBoolean("use_https", this.zzHv);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhs.2 */
    static class C09422 extends zza {
        final /* synthetic */ zzb zzHw;
        final /* synthetic */ Context zzrn;

        C09422(Context context, zzb com_google_android_gms_internal_zzhs_zzb) {
            this.zzrn = context;
            this.zzHw = com_google_android_gms_internal_zzhs_zzb;
            super();
        }

        public void zzdG() {
            SharedPreferences zzH = zzhs.zzv(this.zzrn);
            Bundle bundle = new Bundle();
            bundle.putBoolean("use_https", zzH.getBoolean("use_https", true));
            if (this.zzHw != null) {
                this.zzHw.zzd(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhs.3 */
    static class C09433 extends zza {
        final /* synthetic */ int zzHx;
        final /* synthetic */ Context zzrn;

        C09433(Context context, int i) {
            this.zzrn = context;
            this.zzHx = i;
            super();
        }

        public void zzdG() {
            Editor edit = zzhs.zzv(this.zzrn).edit();
            edit.putInt("webview_cache_version", this.zzHx);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhs.4 */
    static class C09444 extends zza {
        final /* synthetic */ zzb zzHw;
        final /* synthetic */ Context zzrn;

        C09444(Context context, zzb com_google_android_gms_internal_zzhs_zzb) {
            this.zzrn = context;
            this.zzHw = com_google_android_gms_internal_zzhs_zzb;
            super();
        }

        public void zzdG() {
            SharedPreferences zzH = zzhs.zzv(this.zzrn);
            Bundle bundle = new Bundle();
            bundle.putInt("webview_cache_version", zzH.getInt("webview_cache_version", 0));
            if (this.zzHw != null) {
                this.zzHw.zzd(bundle);
            }
        }
    }

    public static Future zza(Context context, int i) {
        return new C09433(context, i).zzgn();
    }

    public static Future zza(Context context, zzb com_google_android_gms_internal_zzhs_zzb) {
        return new C09422(context, com_google_android_gms_internal_zzhs_zzb).zzgn();
    }

    public static Future zza(Context context, boolean z) {
        return new C09411(context, z).zzgn();
    }

    public static Future zzb(Context context, zzb com_google_android_gms_internal_zzhs_zzb) {
        return new C09444(context, com_google_android_gms_internal_zzhs_zzb).zzgn();
    }

    private static SharedPreferences zzv(Context context) {
        return context.getSharedPreferences("admob", 0);
    }
}
