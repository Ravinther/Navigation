package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzh implements Creator<PolygonOptions> {
    static void zza(PolygonOptions polygonOptions, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, polygonOptions.getVersionCode());
        zzb.zzc(parcel, 2, polygonOptions.getPoints(), false);
        zzb.zzd(parcel, 3, polygonOptions.zzxe(), false);
        zzb.zza(parcel, 4, polygonOptions.getStrokeWidth());
        zzb.zzc(parcel, 5, polygonOptions.getStrokeColor());
        zzb.zzc(parcel, 6, polygonOptions.getFillColor());
        zzb.zza(parcel, 7, polygonOptions.getZIndex());
        zzb.zza(parcel, 8, polygonOptions.isVisible());
        zzb.zza(parcel, 9, polygonOptions.isGeodesic());
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzfd(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhD(x0);
    }

    public PolygonOptions zzfd(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        List list = null;
        List arrayList = new ArrayList();
        boolean z2 = false;
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        int i3 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    list = zza.zzc(parcel, zzai, LatLng.CREATOR);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    zza.zza(parcel, zzai, arrayList, getClass().getClassLoader());
                    break;
                case TTSConst.TTSXML /*4*/:
                    f2 = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    f = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new PolygonOptions(i3, list, arrayList, f2, i2, i, f, z2, z);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public PolygonOptions[] zzhD(int i) {
        return new PolygonOptions[i];
    }
}
