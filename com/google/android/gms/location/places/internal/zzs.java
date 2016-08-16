package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzs implements Creator<PlacesParams> {
    static void zza(PlacesParams placesParams, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, placesParams.zzaEZ, false);
        zzb.zzc(parcel, 1000, placesParams.versionCode);
        zzb.zza(parcel, 2, placesParams.zzaFa, false);
        zzb.zza(parcel, 3, placesParams.zzaFb, false);
        zzb.zza(parcel, 4, placesParams.zzaDU, false);
        zzb.zza(parcel, 5, placesParams.zzaFc, false);
        zzb.zzc(parcel, 6, placesParams.zzaFd);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeN(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhn(x0);
    }

    public PlacesParams zzeN(Parcel parcel) {
        int i = 0;
        String str = null;
        int zzaj = zza.zzaj(parcel);
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str5 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str4 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    str3 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case 1000:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new PlacesParams(i2, str5, str4, str3, str2, str, i);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public PlacesParams[] zzhn(int i) {
        return new PlacesParams[i];
    }
}
