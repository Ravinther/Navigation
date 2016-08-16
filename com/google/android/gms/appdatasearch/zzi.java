package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzi implements Creator<RegisterSectionInfo> {
    static void zza(RegisterSectionInfo registerSectionInfo, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, registerSectionInfo.name, false);
        zzb.zzc(parcel, 1000, registerSectionInfo.mVersionCode);
        zzb.zza(parcel, 2, registerSectionInfo.zzPA, false);
        zzb.zza(parcel, 3, registerSectionInfo.zzPB);
        zzb.zzc(parcel, 4, registerSectionInfo.weight);
        zzb.zza(parcel, 5, registerSectionInfo.zzPC);
        zzb.zza(parcel, 6, registerSectionInfo.zzPD, false);
        zzb.zza(parcel, 7, registerSectionInfo.zzPE, i, false);
        zzb.zza(parcel, 8, registerSectionInfo.zzPF, false);
        zzb.zza(parcel, 11, registerSectionInfo.zzPG, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzy(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzal(x0);
    }

    public RegisterSectionInfo[] zzal(int i) {
        return new RegisterSectionInfo[i];
    }

    public RegisterSectionInfo zzy(Parcel parcel) {
        boolean z = false;
        String str = null;
        int zzaj = zza.zzaj(parcel);
        int i = 1;
        int[] iArr = null;
        Feature[] featureArr = null;
        String str2 = null;
        boolean z2 = false;
        String str3 = null;
        String str4 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str4 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str3 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    featureArr = (Feature[]) zza.zzb(parcel, zzai, Feature.CREATOR);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    iArr = zza.zzu(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    str = zza.zzo(parcel, zzai);
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
            return new RegisterSectionInfo(i2, str4, str3, z2, i, z, str2, featureArr, iArr, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
