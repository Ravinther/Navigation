package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zze implements Creator<SafeParcelResponse> {
    static void zza(SafeParcelResponse safeParcelResponse, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, safeParcelResponse.getVersionCode());
        zzb.zza(parcel, 2, safeParcelResponse.zzpt(), false);
        zzb.zza(parcel, 3, safeParcelResponse.zzpu(), i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzat(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzbS(x0);
    }

    public SafeParcelResponse zzat(Parcel parcel) {
        FieldMappingDictionary fieldMappingDictionary = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    parcel2 = zza.zzD(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    fieldMappingDictionary = (FieldMappingDictionary) zza.zza(parcel, zzai, FieldMappingDictionary.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new SafeParcelResponse(i, parcel2, fieldMappingDictionary);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public SafeParcelResponse[] zzbS(int i) {
        return new SafeParcelResponse[i];
    }
}
