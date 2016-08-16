package com.google.android.gms.appdatasearch;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import loquendo.tts.engine.TTSConst;

public class zzb implements Creator<DocumentContents> {
    static void zza(DocumentContents documentContents, Parcel parcel, int i) {
        int zzak = com.google.android.gms.common.internal.safeparcel.zzb.zzak(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, documentContents.zzOS, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, documentContents.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, documentContents.zzOT, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, documentContents.zzOU);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, documentContents.account, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzs(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzad(x0);
    }

    public DocumentContents[] zzad(int i) {
        return new DocumentContents[i];
    }

    public DocumentContents zzs(Parcel parcel) {
        boolean z = false;
        Account account = null;
        int zzaj = zza.zzaj(parcel);
        String str = null;
        DocumentSection[] documentSectionArr = null;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    documentSectionArr = (DocumentSection[]) zza.zzb(parcel, zzai, DocumentSection.CREATOR);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    account = (Account) zza.zza(parcel, zzai, Account.CREATOR);
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
            return new DocumentContents(i, documentSectionArr, str, z, account);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
