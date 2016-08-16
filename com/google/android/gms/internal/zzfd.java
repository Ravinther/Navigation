package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;

@zzgk
public final class zzfd extends zzg<zzff> {
    private static final zzfd zzBC;

    private static final class zza extends Exception {
        public zza(String str) {
            super(str);
        }
    }

    static {
        zzBC = new zzfd();
    }

    private zzfd() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    public static zzfe zzb(Activity activity) {
        try {
            if (!zzc(activity)) {
                return zzBC.zzd(activity);
            }
            zzb.zzaC("Using AdOverlay from the client jar.");
            return new zzd(activity);
        } catch (zza e) {
            zzb.zzaE(e.getMessage());
            return null;
        }
    }

    private static boolean zzc(Activity activity) throws zza {
        Intent intent = activity.getIntent();
        if (intent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
            return intent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
        }
        throw new zza("Ad overlay requires the useClientJar flag in intent extras.");
    }

    private zzfe zzd(Activity activity) {
        try {
            return com.google.android.gms.internal.zzfe.zza.zzK(((zzff) zzar(activity)).zze(zze.zzx(activity)));
        } catch (Throwable e) {
            zzb.zzd("Could not create remote AdOverlay.", e);
            return null;
        } catch (Throwable e2) {
            zzb.zzd("Could not create remote AdOverlay.", e2);
            return null;
        }
    }

    protected zzff zzJ(IBinder iBinder) {
        return com.google.android.gms.internal.zzff.zza.zzL(iBinder);
    }

    protected /* synthetic */ Object zzd(IBinder iBinder) {
        return zzJ(iBinder);
    }
}
