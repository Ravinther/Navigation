package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzh implements Creator<AdSizeParcel> {
    static void zza(AdSizeParcel adSizeParcel, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, adSizeParcel.versionCode);
        zzb.zza(parcel, 2, adSizeParcel.zzsG, false);
        zzb.zzc(parcel, 3, adSizeParcel.height);
        zzb.zzc(parcel, 4, adSizeParcel.heightPixels);
        zzb.zza(parcel, 5, adSizeParcel.zzsH);
        zzb.zzc(parcel, 6, adSizeParcel.width);
        zzb.zzc(parcel, 7, adSizeParcel.widthPixels);
        zzb.zza(parcel, 8, adSizeParcel.zzsI, i, false);
        zzb.zza(parcel, 9, adSizeParcel.zzsJ);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzc(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzl(x0);
    }

    public AdSizeParcel zzc(Parcel parcel) {
        AdSizeParcel[] adSizeParcelArr = null;
        boolean z = false;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        int i4 = 0;
        String str = null;
        int i5 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i5 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    i4 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    adSizeParcelArr = (AdSizeParcel[]) zza.zzb(parcel, zzai, AdSizeParcel.CREATOR);
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new AdSizeParcel(i5, str, i4, i3, z2, i2, i, adSizeParcelArr, z);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public AdSizeParcel[] zzl(int i) {
        return new AdSizeParcel[i];
    }
}
