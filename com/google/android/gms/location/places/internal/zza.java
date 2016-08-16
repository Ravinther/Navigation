package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.places.internal.AutocompletePredictionEntity.SubstringEntity;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zza implements Creator<AutocompletePredictionEntity> {
    static void zza(AutocompletePredictionEntity autocompletePredictionEntity, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, autocompletePredictionEntity.zzaoB, false);
        zzb.zzc(parcel, 1000, autocompletePredictionEntity.mVersionCode);
        zzb.zza(parcel, 2, autocompletePredictionEntity.zzaDH, false);
        zzb.zza(parcel, 3, autocompletePredictionEntity.zzaDi, false);
        zzb.zzc(parcel, 4, autocompletePredictionEntity.zzaEb, false);
        zzb.zzc(parcel, 5, autocompletePredictionEntity.zzaEc);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeJ(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhi(x0);
    }

    public AutocompletePredictionEntity zzeJ(Parcel parcel) {
        int i = 0;
        List list = null;
        int zzaj = com.google.android.gms.common.internal.safeparcel.zza.zzaj(parcel);
        List list2 = null;
        String str = null;
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = com.google.android.gms.common.internal.safeparcel.zza.zzai(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    list2 = com.google.android.gms.common.internal.safeparcel.zza.zzB(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    list = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, zzai, SubstringEntity.CREATOR);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    break;
                case 1000:
                    i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new AutocompletePredictionEntity(i2, str2, str, list2, list, i);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public AutocompletePredictionEntity[] zzhi(int i) {
        return new AutocompletePredictionEntity[i];
    }
}
