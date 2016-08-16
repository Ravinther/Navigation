package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzd implements Creator<IdToken> {
    static void zza(IdToken idToken, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, idToken.getAccountType(), false);
        zzb.zzc(parcel, 1000, idToken.mVersionCode);
        zzb.zza(parcel, 2, idToken.zzlv(), false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzG(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzaw(x0);
    }

    public IdToken zzG(Parcel parcel) {
        String str = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
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
                case 1000:
                    i = zza.zzg(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new IdToken(i, str2, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public IdToken[] zzaw(int i) {
        return new IdToken[i];
    }
}
