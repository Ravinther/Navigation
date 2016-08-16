package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.EmailSignInConfig;
import com.google.android.gms.auth.api.signin.FacebookSignInConfig;
import com.google.android.gms.auth.api.signin.GoogleSignInConfig;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zze implements Creator<SignInConfiguration> {
    static void zza(SignInConfiguration signInConfiguration, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, signInConfiguration.versionCode);
        zzb.zza(parcel, 2, signInConfiguration.zzlF(), false);
        zzb.zza(parcel, 3, signInConfiguration.zzlG(), false);
        zzb.zza(parcel, 4, signInConfiguration.zzlH(), i, false);
        zzb.zza(parcel, 5, signInConfiguration.zzlI(), i, false);
        zzb.zza(parcel, 6, signInConfiguration.zzlJ(), i, false);
        zzb.zza(parcel, 7, signInConfiguration.zzlK(), false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzQ(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzaG(x0);
    }

    public SignInConfiguration zzQ(Parcel parcel) {
        String str = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        FacebookSignInConfig facebookSignInConfig = null;
        GoogleSignInConfig googleSignInConfig = null;
        EmailSignInConfig emailSignInConfig = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str3 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    emailSignInConfig = (EmailSignInConfig) zza.zza(parcel, zzai, EmailSignInConfig.CREATOR);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    googleSignInConfig = (GoogleSignInConfig) zza.zza(parcel, zzai, GoogleSignInConfig.CREATOR);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    facebookSignInConfig = (FacebookSignInConfig) zza.zza(parcel, zzai, FacebookSignInConfig.CREATOR);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new SignInConfiguration(i, str3, str2, emailSignInConfig, googleSignInConfig, facebookSignInConfig, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public SignInConfiguration[] zzaG(int i) {
        return new SignInConfiguration[i];
    }
}
