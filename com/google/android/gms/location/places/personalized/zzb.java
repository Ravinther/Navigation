package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.location.places.personalized.HereContent.Action;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzb implements Creator<HereContent> {
    static void zza(HereContent hereContent, Parcel parcel, int i) {
        int zzak = com.google.android.gms.common.internal.safeparcel.zzb.zzak(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, hereContent.getData(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, hereContent.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, hereContent.getActions(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeQ(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhq(x0);
    }

    public HereContent zzeQ(Parcel parcel) {
        List list = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    list = zza.zzc(parcel, zzai, Action.CREATOR);
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
            return new HereContent(i, str, list);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public HereContent[] zzhq(int i) {
        return new HereContent[i];
    }
}
