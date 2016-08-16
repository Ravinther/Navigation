package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzf implements Creator<AdRequestParcel> {
    static void zza(AdRequestParcel adRequestParcel, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, adRequestParcel.versionCode);
        zzb.zza(parcel, 2, adRequestParcel.zzsq);
        zzb.zza(parcel, 3, adRequestParcel.extras, false);
        zzb.zzc(parcel, 4, adRequestParcel.zzsr);
        zzb.zzb(parcel, 5, adRequestParcel.zzss, false);
        zzb.zza(parcel, 6, adRequestParcel.zzst);
        zzb.zzc(parcel, 7, adRequestParcel.zzsu);
        zzb.zza(parcel, 8, adRequestParcel.zzsv);
        zzb.zza(parcel, 9, adRequestParcel.zzsw, false);
        zzb.zza(parcel, 10, adRequestParcel.zzsx, i, false);
        zzb.zza(parcel, 11, adRequestParcel.zzsy, i, false);
        zzb.zza(parcel, 12, adRequestParcel.zzsz, false);
        zzb.zza(parcel, 13, adRequestParcel.zzsA, false);
        zzb.zza(parcel, 14, adRequestParcel.zzsB, false);
        zzb.zzb(parcel, 15, adRequestParcel.zzsC, false);
        zzb.zza(parcel, 17, adRequestParcel.zzsE, false);
        zzb.zza(parcel, 16, adRequestParcel.zzsD, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzb(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzk(x0);
    }

    public AdRequestParcel zzb(Parcel parcel) {
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        long j = 0;
        Bundle bundle = null;
        int i2 = 0;
        List list = null;
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        String str = null;
        SearchAdRequestParcel searchAdRequestParcel = null;
        Location location = null;
        String str2 = null;
        Bundle bundle2 = null;
        Bundle bundle3 = null;
        List list2 = null;
        String str3 = null;
        String str4 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    bundle = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    list = zza.zzC(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    searchAdRequestParcel = (SearchAdRequestParcel) zza.zza(parcel, zzai, SearchAdRequestParcel.CREATOR);
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    location = (Location) zza.zza(parcel, zzai, Location.CREATOR);
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    bundle2 = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    bundle3 = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                    list2 = zza.zzC(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_ERROR /*16*/:
                    str3 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_JUMP /*17*/:
                    str4 = zza.zzo(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new AdRequestParcel(i, j, bundle, i2, list, z, i3, z2, str, searchAdRequestParcel, location, str2, bundle2, bundle3, list2, str3, str4);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public AdRequestParcel[] zzk(int i) {
        return new AdRequestParcel[i];
    }
}
