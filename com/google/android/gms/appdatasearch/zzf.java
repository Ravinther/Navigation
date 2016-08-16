package com.google.android.gms.appdatasearch;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.appdatasearch.GetRecentContextCall.Request;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzf implements Creator<Request> {
    static void zza(Request request, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, request.zzPl, i, false);
        zzb.zzc(parcel, 1000, request.mVersionCode);
        zzb.zza(parcel, 2, request.zzPm);
        zzb.zza(parcel, 3, request.zzPn);
        zzb.zza(parcel, 4, request.zzPo);
        zzb.zza(parcel, 5, request.zzPp, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzw(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzah(x0);
    }

    public Request[] zzah(int i) {
        return new Request[i];
    }

    public Request zzw(Parcel parcel) {
        String str = null;
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        boolean z2 = false;
        boolean z3 = false;
        Account account = null;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    account = (Account) zza.zza(parcel, zzai, Account.CREATOR);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    z3 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    str = zza.zzo(parcel, zzai);
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
            return new Request(i, account, z3, z2, z, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
