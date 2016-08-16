package com.google.android.gms.auth.api.credentials.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzf implements Creator<DeleteRequest> {
    static void zza(DeleteRequest deleteRequest, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, deleteRequest.getCredential(), i, false);
        zzb.zzc(parcel, 1000, deleteRequest.mVersionCode);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzI(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzay(x0);
    }

    public DeleteRequest zzI(Parcel parcel) {
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        Credential credential = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    credential = (Credential) zza.zza(parcel, zzai, Credential.CREATOR);
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
            return new DeleteRequest(i, credential);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public DeleteRequest[] zzay(int i) {
        return new DeleteRequest[i];
    }
}
