package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zza implements Creator<FavaDiagnosticsEntity> {
    static void zza(FavaDiagnosticsEntity favaDiagnosticsEntity, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, favaDiagnosticsEntity.mVersionCode);
        zzb.zza(parcel, 2, favaDiagnosticsEntity.zzaeI, false);
        zzb.zzc(parcel, 3, favaDiagnosticsEntity.zzaeJ);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzal(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzbK(x0);
    }

    public FavaDiagnosticsEntity zzal(Parcel parcel) {
        int i = 0;
        int zzaj = com.google.android.gms.common.internal.safeparcel.zza.zzaj(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = com.google.android.gms.common.internal.safeparcel.zza.zzai(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new FavaDiagnosticsEntity(i2, str, i);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public FavaDiagnosticsEntity[] zzbK(int i) {
        return new FavaDiagnosticsEntity[i];
    }
}
