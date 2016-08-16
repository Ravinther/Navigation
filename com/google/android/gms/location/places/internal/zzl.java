package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzl implements Creator<PlaceLikelihoodEntity> {
    static void zza(PlaceLikelihoodEntity placeLikelihoodEntity, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, placeLikelihoodEntity.zzaEI, i, false);
        zzb.zzc(parcel, 1000, placeLikelihoodEntity.mVersionCode);
        zzb.zza(parcel, 2, placeLikelihoodEntity.zzaEJ);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeL(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhl(x0);
    }

    public PlaceLikelihoodEntity zzeL(Parcel parcel) {
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        PlaceImpl placeImpl = null;
        float f = 0.0f;
        while (parcel.dataPosition() < zzaj) {
            int i2;
            float f2;
            PlaceImpl placeImpl2;
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i2 = i;
                    PlaceImpl placeImpl3 = (PlaceImpl) zza.zza(parcel, zzai, PlaceImpl.CREATOR);
                    f2 = f;
                    placeImpl2 = placeImpl3;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    f2 = zza.zzl(parcel, zzai);
                    placeImpl2 = placeImpl;
                    i2 = i;
                    break;
                case 1000:
                    float f3 = f;
                    placeImpl2 = placeImpl;
                    i2 = zza.zzg(parcel, zzai);
                    f2 = f3;
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    f2 = f;
                    placeImpl2 = placeImpl;
                    i2 = i;
                    break;
            }
            i = i2;
            placeImpl = placeImpl2;
            f = f2;
        }
        if (parcel.dataPosition() == zzaj) {
            return new PlaceLikelihoodEntity(i, placeImpl, f);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public PlaceLikelihoodEntity[] zzhl(int i) {
        return new PlaceLikelihoodEntity[i];
    }
}
