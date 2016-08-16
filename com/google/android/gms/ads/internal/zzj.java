package com.google.android.gms.ads.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.zzn;
import com.google.android.gms.ads.internal.client.zzo;
import com.google.android.gms.ads.internal.client.zzp.zza;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzct;
import com.google.android.gms.internal.zzcu;
import com.google.android.gms.internal.zzcv;
import com.google.android.gms.internal.zzcw;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzlh;

@zzgk
public class zzj extends zza {
    private final Context mContext;
    private zzn zzoS;
    private NativeAdOptionsParcel zzoX;
    private final String zzoZ;
    private final zzeh zzow;
    private final VersionInfoParcel zzpa;
    private zzct zzpf;
    private zzcu zzpg;
    private zzlh<String, zzcv> zzph;
    private zzlh<String, zzcw> zzpi;

    public zzj(Context context, String str, zzeh com_google_android_gms_internal_zzeh, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzoZ = str;
        this.zzow = com_google_android_gms_internal_zzeh;
        this.zzpa = versionInfoParcel;
        this.zzpi = new zzlh();
        this.zzph = new zzlh();
    }

    public void zza(NativeAdOptionsParcel nativeAdOptionsParcel) {
        this.zzoX = nativeAdOptionsParcel;
    }

    public void zza(zzct com_google_android_gms_internal_zzct) {
        this.zzpf = com_google_android_gms_internal_zzct;
    }

    public void zza(zzcu com_google_android_gms_internal_zzcu) {
        this.zzpg = com_google_android_gms_internal_zzcu;
    }

    public void zza(String str, zzcw com_google_android_gms_internal_zzcw, zzcv com_google_android_gms_internal_zzcv) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Custom template ID for native custom template ad is empty. Please provide a valid template id.");
        }
        this.zzpi.put(str, com_google_android_gms_internal_zzcw);
        this.zzph.put(str, com_google_android_gms_internal_zzcv);
    }

    public void zzb(zzn com_google_android_gms_ads_internal_client_zzn) {
        this.zzoS = com_google_android_gms_ads_internal_client_zzn;
    }

    public zzo zzbk() {
        return new zzi(this.mContext, this.zzoZ, this.zzow, this.zzpa, this.zzoS, this.zzpf, this.zzpg, this.zzpi, this.zzph, this.zzoX);
    }
}
