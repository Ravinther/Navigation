package com.google.android.gms.ads.internal.request;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.sygic.aura.C1090R;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzf implements Creator<AdRequestInfoParcel> {
    static void zza(AdRequestInfoParcel adRequestInfoParcel, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, adRequestInfoParcel.versionCode);
        zzb.zza(parcel, 2, adRequestInfoParcel.zzDx, false);
        zzb.zza(parcel, 3, adRequestInfoParcel.zzDy, i, false);
        zzb.zza(parcel, 4, adRequestInfoParcel.zzqf, i, false);
        zzb.zza(parcel, 5, adRequestInfoParcel.zzpZ, false);
        zzb.zza(parcel, 6, adRequestInfoParcel.applicationInfo, i, false);
        zzb.zza(parcel, 7, adRequestInfoParcel.zzDz, i, false);
        zzb.zza(parcel, 8, adRequestInfoParcel.zzDA, false);
        zzb.zza(parcel, 9, adRequestInfoParcel.zzDB, false);
        zzb.zza(parcel, 10, adRequestInfoParcel.zzDC, false);
        zzb.zza(parcel, 11, adRequestInfoParcel.zzqb, i, false);
        zzb.zza(parcel, 12, adRequestInfoParcel.zzDD, false);
        zzb.zzc(parcel, 13, adRequestInfoParcel.zzDE);
        zzb.zzb(parcel, 14, adRequestInfoParcel.zzqv, false);
        zzb.zza(parcel, 15, adRequestInfoParcel.zzDF, false);
        zzb.zza(parcel, 17, adRequestInfoParcel.zzDH, i, false);
        zzb.zza(parcel, 16, adRequestInfoParcel.zzDG);
        zzb.zzc(parcel, 19, adRequestInfoParcel.zzDJ);
        zzb.zzc(parcel, 18, adRequestInfoParcel.zzDI);
        zzb.zza(parcel, 21, adRequestInfoParcel.zzDL, false);
        zzb.zza(parcel, 20, adRequestInfoParcel.zzDK);
        zzb.zzc(parcel, 23, adRequestInfoParcel.zzDN);
        zzb.zza(parcel, 22, adRequestInfoParcel.zzDM);
        zzb.zza(parcel, 25, adRequestInfoParcel.zzDP);
        zzb.zza(parcel, 24, adRequestInfoParcel.zzDO, false);
        zzb.zzb(parcel, 27, adRequestInfoParcel.zzDR, false);
        zzb.zza(parcel, 26, adRequestInfoParcel.zzDQ, false);
        zzb.zza(parcel, 29, adRequestInfoParcel.zzqt, i, false);
        zzb.zza(parcel, 28, adRequestInfoParcel.zzpY, false);
        zzb.zza(parcel, 31, adRequestInfoParcel.zzDT);
        zzb.zzb(parcel, 30, adRequestInfoParcel.zzDS, false);
        zzb.zza(parcel, 32, adRequestInfoParcel.zzDU, i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzj(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzC(x0);
    }

    public AdRequestInfoParcel[] zzC(int i) {
        return new AdRequestInfoParcel[i];
    }

    public AdRequestInfoParcel zzj(Parcel parcel) {
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        Bundle bundle = null;
        AdRequestParcel adRequestParcel = null;
        AdSizeParcel adSizeParcel = null;
        String str = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        VersionInfoParcel versionInfoParcel = null;
        Bundle bundle2 = null;
        int i2 = 0;
        List list = null;
        Bundle bundle3 = null;
        boolean z = false;
        Messenger messenger = null;
        int i3 = 0;
        int i4 = 0;
        float f = 0.0f;
        String str5 = null;
        boolean z2 = false;
        int i5 = 0;
        String str6 = null;
        long j = 0;
        String str7 = null;
        List list2 = null;
        String str8 = null;
        NativeAdOptionsParcel nativeAdOptionsParcel = null;
        List list3 = null;
        long j2 = 0;
        CapabilityParcel capabilityParcel = null;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    bundle = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    adRequestParcel = (AdRequestParcel) zza.zza(parcel, zzai, AdRequestParcel.CREATOR);
                    break;
                case TTSConst.TTSXML /*4*/:
                    adSizeParcel = (AdSizeParcel) zza.zza(parcel, zzai, AdSizeParcel.CREATOR);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    applicationInfo = (ApplicationInfo) zza.zza(parcel, zzai, ApplicationInfo.CREATOR);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    packageInfo = (PackageInfo) zza.zza(parcel, zzai, PackageInfo.CREATOR);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    str3 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    str4 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    versionInfoParcel = (VersionInfoParcel) zza.zza(parcel, zzai, VersionInfoParcel.CREATOR);
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    bundle2 = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    list = zza.zzC(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                    bundle3 = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_ERROR /*16*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_JUMP /*17*/:
                    messenger = (Messenger) zza.zza(parcel, zzai, Messenger.CREATOR);
                    break;
                case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                    i4 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_STYLECHANGE /*20*/:
                    f = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_PERSONACHANGE /*21*/:
                    str5 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SAYASCHANGE /*22*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionBarDivider /*23*/:
                    i5 = zza.zzg(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionBarItemBackground /*24*/:
                    str6 = zza.zzo(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionMenuTextAppearance /*25*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionMenuTextColor /*26*/:
                    str7 = zza.zzo(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionModeStyle /*27*/:
                    list2 = zza.zzC(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionModeCloseButtonStyle /*28*/:
                    str8 = zza.zzo(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionModeBackground /*29*/:
                    nativeAdOptionsParcel = (NativeAdOptionsParcel) zza.zza(parcel, zzai, NativeAdOptionsParcel.CREATOR);
                    break;
                case C1090R.styleable.Theme_actionModeSplitBackground /*30*/:
                    list3 = zza.zzC(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionModeCloseDrawable /*31*/:
                    j2 = zza.zzi(parcel, zzai);
                    break;
                case C1090R.styleable.Theme_actionModeCutDrawable /*32*/:
                    capabilityParcel = (CapabilityParcel) zza.zza(parcel, zzai, CapabilityParcel.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new AdRequestInfoParcel(i, bundle, adRequestParcel, adSizeParcel, str, applicationInfo, packageInfo, str2, str3, str4, versionInfoParcel, bundle2, i2, list, bundle3, z, messenger, i3, i4, f, str5, z2, i5, str6, j, str7, list2, str8, nativeAdOptionsParcel, list3, j2, capabilityParcel);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }
}
