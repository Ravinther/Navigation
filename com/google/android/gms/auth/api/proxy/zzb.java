package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import loquendo.tts.engine.TTSConst;

public class zzb implements Creator<ProxyRequest> {
    static void zza(ProxyRequest proxyRequest, Parcel parcel, int i) {
        int zzak = com.google.android.gms.common.internal.safeparcel.zzb.zzak(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, proxyRequest.url, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, proxyRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, proxyRequest.httpMethod);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, proxyRequest.timeoutMillis);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, proxyRequest.body, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, proxyRequest.zzRE, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzL(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzaB(x0);
    }

    public ProxyRequest zzL(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int zzaj = zza.zzaj(parcel);
        long j = 0;
        byte[] bArr = null;
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    bArr = zza.zzr(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    bundle = zza.zzq(parcel, zzai);
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
            return new ProxyRequest(i2, str, i, j, bArr, bundle);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public ProxyRequest[] zzaB(int i) {
        return new ProxyRequest[i];
    }
}
