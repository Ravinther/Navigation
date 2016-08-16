package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class LocationAvailabilityCreator implements Creator<LocationAvailability> {
    static void zza(LocationAvailability locationAvailability, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, locationAvailability.zzaBS);
        zzb.zzc(parcel, 1000, locationAvailability.getVersionCode());
        zzb.zzc(parcel, 2, locationAvailability.zzaBT);
        zzb.zza(parcel, 3, locationAvailability.zzaBU);
        zzb.zzc(parcel, 4, locationAvailability.zzaBV);
        zzb.zzH(parcel, zzak);
    }

    public LocationAvailability createFromParcel(Parcel parcel) {
        int i = 1;
        int zzaj = zza.zzaj(parcel);
        int i2 = 0;
        int i3 = 1000;
        long j = 0;
        int i4 = 1;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i4 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    i3 = zza.zzg(parcel, zzai);
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
            return new LocationAvailability(i2, i3, i4, i, j);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public LocationAvailability[] newArray(int size) {
        return new LocationAvailability[size];
    }
}
