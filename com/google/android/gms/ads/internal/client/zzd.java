package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.ads.internal.client.zzp.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzj;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzeg;
import com.google.android.gms.internal.zzgk;

@zzgk
public final class zzd extends zzg<zzq> {
    private static final zzd zzsp;

    static {
        zzsp = new zzd();
    }

    private zzd() {
        super("com.google.android.gms.ads.AdLoaderBuilderCreatorImpl");
    }

    public static zzp zza(Context context, String str, zzeg com_google_android_gms_internal_zzeg) {
        if (zzk.zzcE().zzR(context)) {
            zzp zzb = zzsp.zzb(context, str, com_google_android_gms_internal_zzeg);
            if (zzb != null) {
                return zzb;
            }
        }
        zzb.zzaC("Using AdLoader from the client jar.");
        return new zzj(context, str, com_google_android_gms_internal_zzeg, new VersionInfoParcel(7895000, 7895000, true));
    }

    private zzp zzb(Context context, String str, zzeg com_google_android_gms_internal_zzeg) {
        try {
            return zza.zzi(((zzq) zzar(context)).zza(zze.zzx(context), str, com_google_android_gms_internal_zzeg, 7895000));
        } catch (Throwable e) {
            zzb.zzd("Could not create remote builder for AdLoader.", e);
            return null;
        } catch (Throwable e2) {
            zzb.zzd("Could not create remote builder for AdLoader.", e2);
            return null;
        }
    }

    protected zzq zzc(IBinder iBinder) {
        return zzq.zza.zzj(iBinder);
    }

    protected /* synthetic */ Object zzd(IBinder iBinder) {
        return zzc(iBinder);
    }
}
