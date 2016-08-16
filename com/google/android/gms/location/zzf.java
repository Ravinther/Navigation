package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzf implements Creator<LocationSettingsRequest> {
    static void zza(LocationSettingsRequest locationSettingsRequest, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, locationSettingsRequest.zzsr(), false);
        zzb.zzc(parcel, 1000, locationSettingsRequest.getVersionCode());
        zzb.zza(parcel, 2, locationSettingsRequest.zzvJ());
        zzb.zza(parcel, 3, locationSettingsRequest.zzvK());
        zzb.zza(parcel, 4, locationSettingsRequest.zzvL());
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzer(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzgJ(x0);
    }

    public LocationSettingsRequest zzer(Parcel parcel) {
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        List list = null;
        boolean z2 = false;
        boolean z3 = false;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    list = zza.zzc(parcel, zzai, LocationRequest.CREATOR);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    z3 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case 1000:
                    i = zza.zzg(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new LocationSettingsRequest(i, list, z3, z2, z);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public LocationSettingsRequest[] zzgJ(int i) {
        return new LocationSettingsRequest[i];
    }
}
