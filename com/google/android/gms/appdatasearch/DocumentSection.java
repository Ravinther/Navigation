package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import com.google.android.gms.appdatasearch.RegisterSectionInfo.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class DocumentSection implements SafeParcelable {
    public static final zzd CREATOR;
    public static final int zzPc;
    private static final RegisterSectionInfo zzPd;
    final int mVersionCode;
    public final String zzPe;
    final RegisterSectionInfo zzPf;
    public final int zzPg;
    public final byte[] zzPh;

    static {
        zzPc = Integer.parseInt("-1");
        CREATOR = new zzd();
        zzPd = new zza("SsbContext").zzM(true).zzby("blob").zzld();
    }

    DocumentSection(int versionCode, String content, RegisterSectionInfo sectionInfo, int globalSearchSectionType, byte[] blobContent) {
        boolean z = globalSearchSectionType == zzPc || zzh.zzaj(globalSearchSectionType) != null;
        zzx.zzb(z, "Invalid section type " + globalSearchSectionType);
        this.mVersionCode = versionCode;
        this.zzPe = content;
        this.zzPf = sectionInfo;
        this.zzPg = globalSearchSectionType;
        this.zzPh = blobContent;
        String zzla = zzla();
        if (zzla != null) {
            throw new IllegalArgumentException(zzla);
        }
    }

    public int describeContents() {
        zzd com_google_android_gms_appdatasearch_zzd = CREATOR;
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzd com_google_android_gms_appdatasearch_zzd = CREATOR;
        zzd.zza(this, dest, flags);
    }

    public RegisterSectionInfo zzkZ() {
        return this.zzPf;
    }

    public String zzla() {
        return (this.zzPg == zzPc || zzh.zzaj(this.zzPg) != null) ? (this.zzPe == null || this.zzPh == null) ? null : "Both content and blobContent set" : "Invalid section type " + this.zzPg;
    }
}
