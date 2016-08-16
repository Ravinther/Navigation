package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzi implements Creator<CapabilityParcel> {
    static void zza(CapabilityParcel capabilityParcel, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, capabilityParcel.versionCode);
        zzb.zza(parcel, 2, capabilityParcel.zzEm);
        zzb.zza(parcel, 3, capabilityParcel.zzEn);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzl(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzE(x0);
    }

    public CapabilityParcel[] zzE(int i) {
        return new CapabilityParcel[i];
    }

    public CapabilityParcel zzl(Parcel parcel) {
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        boolean z2 = false;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new CapabilityParcel(i, z2, z);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
