package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

@zzgk
public class zzhj {
    public final int errorCode;
    public final int orientation;
    public final zzip zzAR;
    public final String zzDB;
    public final String zzDO;
    public final long zzDW;
    public final boolean zzDX;
    public final long zzDY;
    public final List<String> zzDZ;
    public final AdRequestParcel zzDy;
    public final String zzEc;
    public final JSONObject zzGF;
    public final zzea zzGG;
    public final AdSizeParcel zzGH;
    public final long zzGI;
    public final long zzGJ;
    public final com.google.android.gms.ads.internal.formats.zzh.zza zzGK;
    public final long zzyA;
    public final zzdz zzyQ;
    public final zzei zzyR;
    public final String zzyS;
    public final zzec zzyT;
    public final List<String> zzyw;
    public final List<String> zzyx;

    @zzgk
    public static final class zza {
        public final int errorCode;
        public final JSONObject zzGF;
        public final zzea zzGG;
        public final long zzGI;
        public final long zzGJ;
        public final AdRequestInfoParcel zzGL;
        public final AdResponseParcel zzGM;
        public final AdSizeParcel zzqf;

        public zza(AdRequestInfoParcel adRequestInfoParcel, AdResponseParcel adResponseParcel, zzea com_google_android_gms_internal_zzea, AdSizeParcel adSizeParcel, int i, long j, long j2, JSONObject jSONObject) {
            this.zzGL = adRequestInfoParcel;
            this.zzGM = adResponseParcel;
            this.zzGG = com_google_android_gms_internal_zzea;
            this.zzqf = adSizeParcel;
            this.errorCode = i;
            this.zzGI = j;
            this.zzGJ = j2;
            this.zzGF = jSONObject;
        }
    }

    public zzhj(AdRequestParcel adRequestParcel, zzip com_google_android_gms_internal_zzip, List<String> list, int i, List<String> list2, List<String> list3, int i2, long j, String str, boolean z, zzdz com_google_android_gms_internal_zzdz, zzei com_google_android_gms_internal_zzei, String str2, zzea com_google_android_gms_internal_zzea, zzec com_google_android_gms_internal_zzec, long j2, AdSizeParcel adSizeParcel, long j3, long j4, long j5, String str3, JSONObject jSONObject, com.google.android.gms.ads.internal.formats.zzh.zza com_google_android_gms_ads_internal_formats_zzh_zza, String str4) {
        this.zzDy = adRequestParcel;
        this.zzAR = com_google_android_gms_internal_zzip;
        this.zzyw = list != null ? Collections.unmodifiableList(list) : null;
        this.errorCode = i;
        this.zzyx = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.zzDZ = list3 != null ? Collections.unmodifiableList(list3) : null;
        this.orientation = i2;
        this.zzyA = j;
        this.zzDB = str;
        this.zzDX = z;
        this.zzyQ = com_google_android_gms_internal_zzdz;
        this.zzyR = com_google_android_gms_internal_zzei;
        this.zzyS = str2;
        this.zzGG = com_google_android_gms_internal_zzea;
        this.zzyT = com_google_android_gms_internal_zzec;
        this.zzDY = j2;
        this.zzGH = adSizeParcel;
        this.zzDW = j3;
        this.zzGI = j4;
        this.zzGJ = j5;
        this.zzEc = str3;
        this.zzGF = jSONObject;
        this.zzGK = com_google_android_gms_ads_internal_formats_zzh_zza;
        this.zzDO = str4;
    }

    public zzhj(zza com_google_android_gms_internal_zzhj_zza, zzip com_google_android_gms_internal_zzip, zzdz com_google_android_gms_internal_zzdz, zzei com_google_android_gms_internal_zzei, String str, zzec com_google_android_gms_internal_zzec, com.google.android.gms.ads.internal.formats.zzh.zza com_google_android_gms_ads_internal_formats_zzh_zza) {
        zzip com_google_android_gms_internal_zzip2 = com_google_android_gms_internal_zzip;
        zzdz com_google_android_gms_internal_zzdz2 = com_google_android_gms_internal_zzdz;
        zzei com_google_android_gms_internal_zzei2 = com_google_android_gms_internal_zzei;
        String str2 = str;
        zzec com_google_android_gms_internal_zzec2 = com_google_android_gms_internal_zzec;
        com.google.android.gms.ads.internal.formats.zzh.zza com_google_android_gms_ads_internal_formats_zzh_zza2 = com_google_android_gms_ads_internal_formats_zzh_zza;
        this(com_google_android_gms_internal_zzhj_zza.zzGL.zzDy, com_google_android_gms_internal_zzip2, com_google_android_gms_internal_zzhj_zza.zzGM.zzyw, com_google_android_gms_internal_zzhj_zza.errorCode, com_google_android_gms_internal_zzhj_zza.zzGM.zzyx, com_google_android_gms_internal_zzhj_zza.zzGM.zzDZ, com_google_android_gms_internal_zzhj_zza.zzGM.orientation, com_google_android_gms_internal_zzhj_zza.zzGM.zzyA, com_google_android_gms_internal_zzhj_zza.zzGL.zzDB, com_google_android_gms_internal_zzhj_zza.zzGM.zzDX, com_google_android_gms_internal_zzdz2, com_google_android_gms_internal_zzei2, str2, com_google_android_gms_internal_zzhj_zza.zzGG, com_google_android_gms_internal_zzec2, com_google_android_gms_internal_zzhj_zza.zzGM.zzDY, com_google_android_gms_internal_zzhj_zza.zzqf, com_google_android_gms_internal_zzhj_zza.zzGM.zzDW, com_google_android_gms_internal_zzhj_zza.zzGI, com_google_android_gms_internal_zzhj_zza.zzGJ, com_google_android_gms_internal_zzhj_zza.zzGM.zzEc, com_google_android_gms_internal_zzhj_zza.zzGF, com_google_android_gms_ads_internal_formats_zzh_zza2, com_google_android_gms_internal_zzhj_zza.zzGL.zzDO);
    }

    public boolean zzbY() {
        return (this.zzAR == null || this.zzAR.zzgS() == null) ? false : this.zzAR.zzgS().zzbY();
    }
}
