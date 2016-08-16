package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzp implements Creator<VisibleRegion> {
    static void zza(VisibleRegion visibleRegion, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, visibleRegion.getVersionCode());
        zzb.zza(parcel, 2, visibleRegion.nearLeft, i, false);
        zzb.zza(parcel, 3, visibleRegion.nearRight, i, false);
        zzb.zza(parcel, 4, visibleRegion.farLeft, i, false);
        zzb.zza(parcel, 5, visibleRegion.farRight, i, false);
        zzb.zza(parcel, 6, visibleRegion.latLngBounds, i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzfl(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhL(x0);
    }

    public VisibleRegion zzfl(Parcel parcel) {
        LatLngBounds latLngBounds = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        LatLng latLng = null;
        LatLng latLng2 = null;
        LatLng latLng3 = null;
        LatLng latLng4 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    latLng4 = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    latLng3 = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    break;
                case TTSConst.TTSXML /*4*/:
                    latLng2 = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    latLng = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    latLngBounds = (LatLngBounds) zza.zza(parcel, zzai, LatLngBounds.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new VisibleRegion(i, latLng4, latLng3, latLng2, latLng, latLngBounds);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public VisibleRegion[] zzhL(int i) {
        return new VisibleRegion[i];
    }
}
