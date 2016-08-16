package com.google.android.gms.internal;

import com.google.ads.AdRequest.Gender;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.zza;
import java.util.Date;
import java.util.HashSet;
import loquendo.tts.engine.TTSConst;

@zzgk
public final class zzeu {
    public static AdSize zzb(AdSizeParcel adSizeParcel) {
        int i = 0;
        AdSize[] adSizeArr = new AdSize[]{AdSize.SMART_BANNER, AdSize.BANNER, AdSize.IAB_MRECT, AdSize.IAB_BANNER, AdSize.IAB_LEADERBOARD, AdSize.IAB_WIDE_SKYSCRAPER};
        while (i < adSizeArr.length) {
            if (adSizeArr[i].getWidth() == adSizeParcel.width && adSizeArr[i].getHeight() == adSizeParcel.height) {
                return adSizeArr[i];
            }
            i++;
        }
        return new AdSize(zza.zza(adSizeParcel.width, adSizeParcel.height, adSizeParcel.zzsG));
    }

    public static MediationAdRequest zzg(AdRequestParcel adRequestParcel) {
        return new MediationAdRequest(new Date(adRequestParcel.zzsq), zzr(adRequestParcel.zzsr), adRequestParcel.zzss != null ? new HashSet(adRequestParcel.zzss) : null, adRequestParcel.zzst, adRequestParcel.zzsy);
    }

    public static Gender zzr(int i) {
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
                return Gender.MALE;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return Gender.FEMALE;
            default:
                return Gender.UNKNOWN;
        }
    }
}
