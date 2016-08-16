package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Builder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class CredentialRequest implements SafeParcelable {
    public static final Creator<CredentialRequest> CREATOR;
    final int mVersionCode;
    private final boolean zzRj;
    private final String[] zzRk;
    private final CredentialPickerConfig zzRl;
    private final CredentialPickerConfig zzRm;

    static {
        CREATOR = new zzc();
    }

    CredentialRequest(int version, boolean supportsPasswordLogin, String[] accountTypes, CredentialPickerConfig credentialPickerConfig, CredentialPickerConfig credentialHintPickerConfig) {
        this.mVersionCode = version;
        this.zzRj = supportsPasswordLogin;
        this.zzRk = (String[]) zzx.zzv(accountTypes);
        if (credentialPickerConfig == null) {
            credentialPickerConfig = new Builder().build();
        }
        this.zzRl = credentialPickerConfig;
        if (credentialHintPickerConfig == null) {
            credentialHintPickerConfig = new Builder().build();
        }
        this.zzRm = credentialHintPickerConfig;
    }

    public int describeContents() {
        return 0;
    }

    public String[] getAccountTypes() {
        return this.zzRk;
    }

    public CredentialPickerConfig getCredentialHintPickerConfig() {
        return this.zzRm;
    }

    public CredentialPickerConfig getCredentialPickerConfig() {
        return this.zzRl;
    }

    public boolean getSupportsPasswordLogin() {
        return this.zzRj;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzc.zza(this, out, flags);
    }
}
