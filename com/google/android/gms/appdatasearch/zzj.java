package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzj implements Creator<UsageInfo> {
    static void zza(UsageInfo usageInfo, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, usageInfo.zzPP, i, false);
        zzb.zzc(parcel, 1000, usageInfo.mVersionCode);
        zzb.zza(parcel, 2, usageInfo.zzPQ);
        zzb.zzc(parcel, 3, usageInfo.zzPR);
        zzb.zza(parcel, 4, usageInfo.zztM, false);
        zzb.zza(parcel, 5, usageInfo.zzPS, i, false);
        zzb.zza(parcel, 6, usageInfo.zzPT);
        zzb.zzc(parcel, 7, usageInfo.zzPU);
        zzb.zzc(parcel, 8, usageInfo.zzPV);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzz(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzao(x0);
    }

    public UsageInfo[] zzao(int i) {
        return new UsageInfo[i];
    }

    public UsageInfo zzz(Parcel parcel) {
        DocumentContents documentContents = null;
        int i = 0;
        int zzaj = zza.zzaj(parcel);
        long j = 0;
        int i2 = -1;
        boolean z = false;
        String str = null;
        int i3 = 0;
        DocumentId documentId = null;
        int i4 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    documentId = (DocumentId) zza.zza(parcel, zzai, DocumentId.CREATOR);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    documentContents = (DocumentContents) zza.zza(parcel, zzai, DocumentContents.CREATOR);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case 1000:
                    i4 = zza.zzg(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new UsageInfo(i4, documentId, j, i3, str, documentContents, z, i2, i);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
