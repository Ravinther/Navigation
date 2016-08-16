package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.places.personalized.internal.TestDataImpl;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zze implements Creator<PlaceUserData> {
    static void zza(PlaceUserData placeUserData, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, placeUserData.zzww(), false);
        zzb.zzc(parcel, 1000, placeUserData.mVersionCode);
        zzb.zza(parcel, 2, placeUserData.getPlaceId(), false);
        zzb.zzc(parcel, 5, placeUserData.zzwz(), false);
        zzb.zzc(parcel, 6, placeUserData.zzwx(), false);
        zzb.zzc(parcel, 7, placeUserData.zzwy(), false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeS(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhs(x0);
    }

    public PlaceUserData zzeS(Parcel parcel) {
        List list = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        List list2 = null;
        List list3 = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    list3 = zza.zzc(parcel, zzai, TestDataImpl.CREATOR);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    list2 = zza.zzc(parcel, zzai, PlaceAlias.CREATOR);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    list = zza.zzc(parcel, zzai, HereContent.CREATOR);
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
            return new PlaceUserData(i, str2, str, list3, list2, list);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public PlaceUserData[] zzhs(int i) {
        return new PlaceUserData[i];
    }
}
