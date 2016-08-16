package com.google.android.gms.appdatasearch;

import android.accounts.Account;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class DocumentContents implements SafeParcelable {
    public static final zzb CREATOR;
    public final Account account;
    final int mVersionCode;
    final DocumentSection[] zzOS;
    public final String zzOT;
    public final boolean zzOU;

    static {
        CREATOR = new zzb();
    }

    DocumentContents(int versionCode, DocumentSection[] sectionContents, String schemaOrgType, boolean globalSearchEnabled, Account account) {
        this.mVersionCode = versionCode;
        this.zzOS = sectionContents;
        this.zzOT = schemaOrgType;
        this.zzOU = globalSearchEnabled;
        this.account = account;
    }

    public int describeContents() {
        zzb com_google_android_gms_appdatasearch_zzb = CREATOR;
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzb com_google_android_gms_appdatasearch_zzb = CREATOR;
        zzb.zza(this, dest, flags);
    }

    public DocumentSection zzbu(String str) {
        zzx.zzcs(str);
        if (this.zzOS == null) {
            return null;
        }
        for (DocumentSection documentSection : this.zzOS) {
            if (str.equals(documentSection.zzkZ().name)) {
                return documentSection;
            }
        }
        return null;
    }

    public String zzkX() {
        DocumentSection zzbu = zzbu("web_url");
        return zzbu != null ? zzbu.zzPe : null;
    }
}
