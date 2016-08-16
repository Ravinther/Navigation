package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzg implements Creator<PlaceFilter> {
    static void zza(PlaceFilter placeFilter, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, placeFilter.zzaDm, false);
        zzb.zzc(parcel, 1000, placeFilter.mVersionCode);
        zzb.zza(parcel, 3, placeFilter.zzaDz);
        zzb.zzc(parcel, 4, placeFilter.zzaDp, false);
        zzb.zzb(parcel, 6, placeFilter.zzaDo, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeD(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzha(x0);
    }

    public PlaceFilter zzeD(Parcel parcel) {
        boolean z = false;
        List list = null;
        int zzaj = zza.zzaj(parcel);
        List list2 = null;
        List list3 = null;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    list3 = zza.zzB(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    list = zza.zzc(parcel, zzai, UserDataType.CREATOR);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    list2 = zza.zzC(parcel, zzai);
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
            return new PlaceFilter(i, list3, z, list2, list);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public PlaceFilter[] zzha(int i) {
        return new PlaceFilter[i];
    }
}
