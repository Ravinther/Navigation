package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.zzr.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zzf;
import com.google.android.gms.ads.internal.zzk;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzeg;
import com.google.android.gms.internal.zzgk;

@zzgk
public class zze extends zzg<zzs> {
    public zze() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    private zzr zza(Context context, AdSizeParcel adSizeParcel, String str, zzeg com_google_android_gms_internal_zzeg, int i) {
        Throwable e;
        try {
            return zza.zzk(((zzs) zzar(context)).zza(com.google.android.gms.dynamic.zze.zzx(context), adSizeParcel, str, com_google_android_gms_internal_zzeg, 7895000, i));
        } catch (RemoteException e2) {
            e = e2;
            zzb.zza("Could not create remote AdManager.", e);
            return null;
        } catch (zzg.zza e3) {
            e = e3;
            zzb.zza("Could not create remote AdManager.", e);
            return null;
        }
    }

    public zzr zza(Context context, AdSizeParcel adSizeParcel, String str, zzeg com_google_android_gms_internal_zzeg) {
        if (zzk.zzcE().zzR(context)) {
            zzr zza = zza(context, adSizeParcel, str, com_google_android_gms_internal_zzeg, 1);
            if (zza != null) {
                return zza;
            }
        }
        zzb.zzaC("Using BannerAdManager from the client jar.");
        return new zzf(context, adSizeParcel, str, com_google_android_gms_internal_zzeg, new VersionInfoParcel(7895000, 7895000, true));
    }

    public zzr zzb(Context context, AdSizeParcel adSizeParcel, String str, zzeg com_google_android_gms_internal_zzeg) {
        if (zzk.zzcE().zzR(context)) {
            zzr zza = zza(context, adSizeParcel, str, com_google_android_gms_internal_zzeg, 2);
            if (zza != null) {
                return zza;
            }
        }
        zzb.zzaE("Using InterstitialAdManager from the client jar.");
        return new zzk(context, adSizeParcel, str, com_google_android_gms_internal_zzeg, new VersionInfoParcel(7895000, 7895000, true), zzd.zzbd());
    }

    protected /* synthetic */ Object zzd(IBinder iBinder) {
        return zze(iBinder);
    }

    protected zzs zze(IBinder iBinder) {
        return zzs.zza.zzl(iBinder);
    }
}
