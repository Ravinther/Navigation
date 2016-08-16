package com.google.android.gms.auth.api.signin;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;
import loquendo.tts.engine.TTSConst;

public class zzb implements Creator<FacebookSignInConfig> {
    static void zza(FacebookSignInConfig facebookSignInConfig, Parcel parcel, int i) {
        int zzak = com.google.android.gms.common.internal.safeparcel.zzb.zzak(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, facebookSignInConfig.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, facebookSignInConfig.zzlD(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 3, facebookSignInConfig.zzlE(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzO(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzaE(x0);
    }

    public FacebookSignInConfig zzO(Parcel parcel) {
        ArrayList arrayList = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        Intent intent = null;
        while (parcel.dataPosition() < zzaj) {
            Intent intent2;
            int zzg;
            ArrayList arrayList2;
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    ArrayList arrayList3 = arrayList;
                    intent2 = intent;
                    zzg = zza.zzg(parcel, zzai);
                    arrayList2 = arrayList3;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    zzg = i;
                    Intent intent3 = (Intent) zza.zza(parcel, zzai, Intent.CREATOR);
                    arrayList2 = arrayList;
                    intent2 = intent3;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    arrayList2 = zza.zzC(parcel, zzai);
                    intent2 = intent;
                    zzg = i;
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    arrayList2 = arrayList;
                    intent2 = intent;
                    zzg = i;
                    break;
            }
            i = zzg;
            intent = intent2;
            arrayList = arrayList2;
        }
        if (parcel.dataPosition() == zzaj) {
            return new FacebookSignInConfig(i, intent, arrayList);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public FacebookSignInConfig[] zzaE(int i) {
        return new FacebookSignInConfig[i];
    }
}
