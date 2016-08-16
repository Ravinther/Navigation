package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zze implements Creator<DataHolder> {
    static void zza(DataHolder dataHolder, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, dataHolder.zznU(), false);
        zzb.zzc(parcel, 1000, dataHolder.getVersionCode());
        zzb.zza(parcel, 2, dataHolder.zznV(), i, false);
        zzb.zzc(parcel, 3, dataHolder.getStatusCode());
        zzb.zza(parcel, 4, dataHolder.zznP(), false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzaa(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzbq(x0);
    }

    public DataHolder zzaa(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int zzaj = zza.zzaj(parcel);
        CursorWindow[] cursorWindowArr = null;
        String[] strArr = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    strArr = zza.zzA(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    cursorWindowArr = (CursorWindow[]) zza.zzb(parcel, zzai, CursorWindow.CREATOR);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    bundle = zza.zzq(parcel, zzai);
                    break;
                case 1000:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() != zzaj) {
            throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
        }
        DataHolder dataHolder = new DataHolder(i2, strArr, cursorWindowArr, i, bundle);
        dataHolder.zznT();
        return dataHolder;
    }

    public DataHolder[] zzbq(int i) {
        return new DataHolder[i];
    }
}
