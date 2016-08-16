package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzbb.zza;
import java.util.concurrent.Future;

@zzgk
public class zzbc {

    /* renamed from: com.google.android.gms.internal.zzbc.1 */
    class C08581 implements Runnable {
        final /* synthetic */ Context zzrn;
        final /* synthetic */ VersionInfoParcel zzro;
        final /* synthetic */ zzie zzrp;
        final /* synthetic */ zzan zzrq;
        final /* synthetic */ String zzrr;
        final /* synthetic */ zzbc zzrs;

        C08581(zzbc com_google_android_gms_internal_zzbc, Context context, VersionInfoParcel versionInfoParcel, zzie com_google_android_gms_internal_zzie, zzan com_google_android_gms_internal_zzan, String str) {
            this.zzrs = com_google_android_gms_internal_zzbc;
            this.zzrn = context;
            this.zzro = versionInfoParcel;
            this.zzrp = com_google_android_gms_internal_zzie;
            this.zzrq = com_google_android_gms_internal_zzan;
            this.zzrr = str;
        }

        public void run() {
            this.zzrs.zza(this.zzrn, this.zzro, this.zzrp, this.zzrq).zzt(this.zzrr);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbc.2 */
    class C08592 implements zza {
        final /* synthetic */ zzie zzrp;
        final /* synthetic */ zzbc zzrs;
        final /* synthetic */ zzbb zzrt;

        C08592(zzbc com_google_android_gms_internal_zzbc, zzie com_google_android_gms_internal_zzie, zzbb com_google_android_gms_internal_zzbb) {
            this.zzrs = com_google_android_gms_internal_zzbc;
            this.zzrp = com_google_android_gms_internal_zzie;
            this.zzrt = com_google_android_gms_internal_zzbb;
        }

        public void zzcj() {
            this.zzrp.zzf(this.zzrt);
        }
    }

    private zzbb zza(Context context, VersionInfoParcel versionInfoParcel, zzie<zzbb> com_google_android_gms_internal_zzie_com_google_android_gms_internal_zzbb, zzan com_google_android_gms_internal_zzan) {
        zzbb com_google_android_gms_internal_zzbd = new zzbd(context, versionInfoParcel, com_google_android_gms_internal_zzan);
        com_google_android_gms_internal_zzbd.zza(new C08592(this, com_google_android_gms_internal_zzie_com_google_android_gms_internal_zzbb, com_google_android_gms_internal_zzbd));
        return com_google_android_gms_internal_zzbd;
    }

    public Future<zzbb> zza(Context context, VersionInfoParcel versionInfoParcel, String str, zzan com_google_android_gms_internal_zzan) {
        Future com_google_android_gms_internal_zzie = new zzie();
        zzhu.zzHK.post(new C08581(this, context, versionInfoParcel, com_google_android_gms_internal_zzie, com_google_android_gms_internal_zzan, str));
        return com_google_android_gms_internal_zzie;
    }
}
