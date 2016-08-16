package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzad implements Creator<MobileAdsSettingsParcel> {
    static void zza(MobileAdsSettingsParcel mobileAdsSettingsParcel, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, mobileAdsSettingsParcel.versionCode);
        zzb.zza(parcel, 2, mobileAdsSettingsParcel.zzty);
        zzb.zza(parcel, 3, mobileAdsSettingsParcel.zztz, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzd(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzn(x0);
    }

    public MobileAdsSettingsParcel zzd(Parcel parcel) {
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new MobileAdsSettingsParcel(i, z, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public MobileAdsSettingsParcel[] zzn(int i) {
        return new MobileAdsSettingsParcel[i];
    }
}
