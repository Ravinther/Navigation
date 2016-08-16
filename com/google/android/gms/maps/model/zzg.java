package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzg implements Creator<PointOfInterest> {
    static void zza(PointOfInterest pointOfInterest, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, pointOfInterest.getVersionCode());
        zzb.zza(parcel, 2, pointOfInterest.zzaHy, i, false);
        zzb.zza(parcel, 3, pointOfInterest.zzaHz, false);
        zzb.zza(parcel, 4, pointOfInterest.name, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzfc(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhC(x0);
    }

    public PointOfInterest zzfc(Parcel parcel) {
        String str = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        String str2 = null;
        LatLng latLng = null;
        while (parcel.dataPosition() < zzaj) {
            LatLng latLng2;
            int zzg;
            String str3;
            int zzai = zza.zzai(parcel);
            String str4;
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str4 = str;
                    str = str2;
                    latLng2 = latLng;
                    zzg = zza.zzg(parcel, zzai);
                    str3 = str4;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    zzg = i;
                    str4 = str2;
                    latLng2 = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    str3 = str;
                    str = str4;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    latLng2 = latLng;
                    zzg = i;
                    str4 = str;
                    str = zza.zzo(parcel, zzai);
                    str3 = str4;
                    break;
                case TTSConst.TTSXML /*4*/:
                    str3 = zza.zzo(parcel, zzai);
                    str = str2;
                    latLng2 = latLng;
                    zzg = i;
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    str3 = str;
                    str = str2;
                    latLng2 = latLng;
                    zzg = i;
                    break;
            }
            i = zzg;
            latLng = latLng2;
            str2 = str;
            str = str3;
        }
        if (parcel.dataPosition() == zzaj) {
            return new PointOfInterest(i, latLng, str2, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public PointOfInterest[] zzhC(int i) {
        return new PointOfInterest[i];
    }
}
