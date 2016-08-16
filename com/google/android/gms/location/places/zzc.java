package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.Collection;
import loquendo.tts.engine.TTSConst;

public class zzc implements Creator<AutocompleteFilter> {
    static void zza(AutocompleteFilter autocompleteFilter, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, autocompleteFilter.zzvY());
        zzb.zzc(parcel, 1000, autocompleteFilter.mVersionCode);
        zzb.zza(parcel, 2, autocompleteFilter.zzaDm, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeA(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzgX(x0);
    }

    public AutocompleteFilter zzeA(Parcel parcel) {
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        Collection collection = null;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    collection = zza.zzB(parcel, zzai);
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
            return new AutocompleteFilter(i, z, collection);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public AutocompleteFilter[] zzgX(int i) {
        return new AutocompleteFilter[i];
    }
}
