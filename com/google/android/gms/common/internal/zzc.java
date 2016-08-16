package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzc implements Creator<AuthAccountRequest> {
    static void zza(AuthAccountRequest authAccountRequest, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, authAccountRequest.mVersionCode);
        zzb.zza(parcel, 2, authAccountRequest.zzacC, false);
        zzb.zza(parcel, 3, authAccountRequest.zzacD, i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzac(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzbw(x0);
    }

    public AuthAccountRequest zzac(Parcel parcel) {
        Scope[] scopeArr = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    iBinder = zza.zzp(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    scopeArr = (Scope[]) zza.zzb(parcel, zzai, Scope.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new AuthAccountRequest(i, iBinder, scopeArr);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public AuthAccountRequest[] zzbw(int i) {
        return new AuthAccountRequest[i];
    }
}
