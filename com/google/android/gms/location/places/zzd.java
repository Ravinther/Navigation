package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzd implements Creator<NearbyAlertFilter> {
    static void zza(NearbyAlertFilter nearbyAlertFilter, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzb(parcel, 1, nearbyAlertFilter.zzaDo, false);
        zzb.zzc(parcel, 1000, nearbyAlertFilter.mVersionCode);
        zzb.zza(parcel, 2, nearbyAlertFilter.zzaDm, false);
        zzb.zzc(parcel, 3, nearbyAlertFilter.zzaDp, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeB(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzgY(x0);
    }

    public NearbyAlertFilter zzeB(Parcel parcel) {
        List list = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        List list2 = null;
        List list3 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    list3 = zza.zzC(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    list2 = zza.zzB(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    list = zza.zzc(parcel, zzai, UserDataType.CREATOR);
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
            return new NearbyAlertFilter(i, list3, list2, list);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public NearbyAlertFilter[] zzgY(int i) {
        return new NearbyAlertFilter[i];
    }
}
