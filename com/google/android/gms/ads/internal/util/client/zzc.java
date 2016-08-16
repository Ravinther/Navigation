package com.google.android.gms.ads.internal.util.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzc implements Creator<VersionInfoParcel> {
    static void zza(VersionInfoParcel versionInfoParcel, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, versionInfoParcel.versionCode);
        zzb.zza(parcel, 2, versionInfoParcel.zzIz, false);
        zzb.zzc(parcel, 3, versionInfoParcel.zzIA);
        zzb.zzc(parcel, 4, versionInfoParcel.zzIB);
        zzb.zza(parcel, 5, versionInfoParcel.zzIC);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzq(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzN(x0);
    }

    public VersionInfoParcel[] zzN(int i) {
        return new VersionInfoParcel[i];
    }

    public VersionInfoParcel zzq(Parcel parcel) {
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        String str = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new VersionInfoParcel(i3, str, i2, i, z);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
