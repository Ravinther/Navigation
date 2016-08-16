package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zza implements Creator<EmailSignInConfig> {
    static void zza(EmailSignInConfig emailSignInConfig, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, emailSignInConfig.versionCode);
        zzb.zza(parcel, 2, emailSignInConfig.zzlA(), i, false);
        zzb.zza(parcel, 3, emailSignInConfig.zzlC(), false);
        zzb.zza(parcel, 4, emailSignInConfig.zzlB(), i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzN(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzaD(x0);
    }

    public EmailSignInConfig zzN(Parcel parcel) {
        Uri uri = null;
        int zzaj = com.google.android.gms.common.internal.safeparcel.zza.zzaj(parcel);
        int i = 0;
        String str = null;
        Uri uri2 = null;
        while (parcel.dataPosition() < zzaj) {
            String str2;
            Uri uri3;
            int zzg;
            Uri uri4;
            int zzai = com.google.android.gms.common.internal.safeparcel.zza.zzai(parcel);
            Uri uri5;
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    uri5 = uri;
                    str2 = str;
                    uri3 = uri2;
                    zzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    uri4 = uri5;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    zzg = i;
                    String str3 = str;
                    uri3 = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, zzai, Uri.CREATOR);
                    uri4 = uri;
                    str2 = str3;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    uri3 = uri2;
                    zzg = i;
                    uri5 = uri;
                    str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, zzai);
                    uri4 = uri5;
                    break;
                case TTSConst.TTSXML /*4*/:
                    uri4 = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, zzai, Uri.CREATOR);
                    str2 = str;
                    uri3 = uri2;
                    zzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzai);
                    uri4 = uri;
                    str2 = str;
                    uri3 = uri2;
                    zzg = i;
                    break;
            }
            i = zzg;
            uri2 = uri3;
            str = str2;
            uri = uri4;
        }
        if (parcel.dataPosition() == zzaj) {
            return new EmailSignInConfig(i, uri2, str, uri);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public EmailSignInConfig[] zzaD(int i) {
        return new EmailSignInConfig[i];
    }
}
