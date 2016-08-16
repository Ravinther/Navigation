package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzd implements Creator<LatLngBounds> {
    static void zza(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, latLngBounds.getVersionCode());
        zzb.zza(parcel, 2, latLngBounds.southwest, i, false);
        zzb.zza(parcel, 3, latLngBounds.northeast, i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeZ(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhz(x0);
    }

    public LatLngBounds zzeZ(Parcel parcel) {
        LatLng latLng = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        LatLng latLng2 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzg;
            LatLng latLng3;
            int zzai = zza.zzai(parcel);
            LatLng latLng4;
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    latLng4 = latLng;
                    latLng = latLng2;
                    zzg = zza.zzg(parcel, zzai);
                    latLng3 = latLng4;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    zzg = i;
                    latLng4 = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    latLng3 = latLng;
                    latLng = latLng4;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    latLng3 = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    latLng = latLng2;
                    zzg = i;
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    latLng3 = latLng;
                    latLng = latLng2;
                    zzg = i;
                    break;
            }
            i = zzg;
            latLng2 = latLng;
            latLng = latLng3;
        }
        if (parcel.dataPosition() == zzaj) {
            return new LatLngBounds(i, latLng2, latLng);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public LatLngBounds[] zzhz(int i) {
        return new LatLngBounds[i];
    }
}
