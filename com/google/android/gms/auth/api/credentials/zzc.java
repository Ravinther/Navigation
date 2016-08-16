package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzc implements Creator<CredentialRequest> {
    static void zza(CredentialRequest credentialRequest, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zza(parcel, 1, credentialRequest.getSupportsPasswordLogin());
        zzb.zzc(parcel, 1000, credentialRequest.mVersionCode);
        zzb.zza(parcel, 2, credentialRequest.getAccountTypes(), false);
        zzb.zza(parcel, 3, credentialRequest.getCredentialPickerConfig(), i, false);
        zzb.zza(parcel, 4, credentialRequest.getCredentialHintPickerConfig(), i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzF(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzav(x0);
    }

    public CredentialRequest zzF(Parcel parcel) {
        boolean z = false;
        CredentialPickerConfig credentialPickerConfig = null;
        int zzaj = zza.zzaj(parcel);
        CredentialPickerConfig credentialPickerConfig2 = null;
        String[] strArr = null;
        int i = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    strArr = zza.zzA(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    credentialPickerConfig2 = (CredentialPickerConfig) zza.zza(parcel, zzai, CredentialPickerConfig.CREATOR);
                    break;
                case TTSConst.TTSXML /*4*/:
                    credentialPickerConfig = (CredentialPickerConfig) zza.zza(parcel, zzai, CredentialPickerConfig.CREATOR);
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
            return new CredentialRequest(i, z, strArr, credentialPickerConfig2, credentialPickerConfig);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public CredentialRequest[] zzav(int i) {
        return new CredentialRequest[i];
    }
}
