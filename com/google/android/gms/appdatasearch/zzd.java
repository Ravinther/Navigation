package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzd implements Creator<DocumentSection> {
    static void zza(DocumentSection documentSection, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, documentSection.zzPe, false);
        zzb.zzc(parcel, 1000, documentSection.mVersionCode);
        zzb.zza(parcel, 3, documentSection.zzPf, i, false);
        zzb.zzc(parcel, 4, documentSection.zzPg);
        zzb.zza(parcel, 5, documentSection.zzPh, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzu(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzaf(x0);
    }

    public DocumentSection[] zzaf(int i) {
        return new DocumentSection[i];
    }

    public DocumentSection zzu(Parcel parcel) {
        byte[] bArr = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        int i2 = -1;
        RegisterSectionInfo registerSectionInfo = null;
        String str = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    registerSectionInfo = (RegisterSectionInfo) zza.zza(parcel, zzai, RegisterSectionInfo.CREATOR);
                    break;
                case TTSConst.TTSXML /*4*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    bArr = zza.zzr(parcel, zzai);
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
            return new DocumentSection(i, str, registerSectionInfo, i2, bArr);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
