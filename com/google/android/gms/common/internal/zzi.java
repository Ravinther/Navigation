package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzi implements Creator<GetServiceRequest> {
    static void zza(GetServiceRequest getServiceRequest, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, getServiceRequest.version);
        zzb.zzc(parcel, 2, getServiceRequest.zzadn);
        zzb.zzc(parcel, 3, getServiceRequest.zzado);
        zzb.zza(parcel, 4, getServiceRequest.zzadp, false);
        zzb.zza(parcel, 5, getServiceRequest.zzadq, false);
        zzb.zza(parcel, 6, getServiceRequest.zzadr, i, false);
        zzb.zza(parcel, 7, getServiceRequest.zzads, false);
        zzb.zza(parcel, 8, getServiceRequest.zzadt, i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzae(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzby(x0);
    }

    public GetServiceRequest zzae(Parcel parcel) {
        int i = 0;
        Account account = null;
        int zzaj = zza.zzaj(parcel);
        Bundle bundle = null;
        Scope[] scopeArr = null;
        IBinder iBinder = null;
        String str = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    iBinder = zza.zzp(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    scopeArr = (Scope[]) zza.zzb(parcel, zzai, Scope.CREATOR);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    bundle = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    account = (Account) zza.zza(parcel, zzai, Account.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new GetServiceRequest(i3, i2, i, str, iBinder, scopeArr, bundle, account);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public GetServiceRequest[] zzby(int i) {
        return new GetServiceRequest[i];
    }
}
