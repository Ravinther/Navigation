package com.google.android.gms.ads.internal.purchase;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zza implements Creator<GInAppPurchaseManagerInfoParcel> {
    static void zza(GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcel, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, gInAppPurchaseManagerInfoParcel.versionCode);
        zzb.zza(parcel, 3, gInAppPurchaseManagerInfoParcel.zzfd(), false);
        zzb.zza(parcel, 4, gInAppPurchaseManagerInfoParcel.zzfe(), false);
        zzb.zza(parcel, 5, gInAppPurchaseManagerInfoParcel.zzff(), false);
        zzb.zza(parcel, 6, gInAppPurchaseManagerInfoParcel.zzfc(), false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzi(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzx(x0);
    }

    public GInAppPurchaseManagerInfoParcel zzi(Parcel parcel) {
        IBinder iBinder = null;
        int zzaj = com.google.android.gms.common.internal.safeparcel.zza.zzaj(parcel);
        int i = 0;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = com.google.android.gms.common.internal.safeparcel.zza.zzai(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    iBinder4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    iBinder3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    iBinder2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    iBinder = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, zzai);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new GInAppPurchaseManagerInfoParcel(i, iBinder4, iBinder3, iBinder2, iBinder);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public GInAppPurchaseManagerInfoParcel[] zzx(int i) {
        return new GInAppPurchaseManagerInfoParcel[i];
    }
}
