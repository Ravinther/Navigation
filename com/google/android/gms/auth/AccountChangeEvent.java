package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import loquendo.tts.engine.TTSConst;

public class AccountChangeEvent implements SafeParcelable {
    public static final Creator<AccountChangeEvent> CREATOR;
    final int mVersion;
    final long zzQD;
    final String zzQE;
    final int zzQF;
    final int zzQG;
    final String zzQH;

    static {
        CREATOR = new zza();
    }

    AccountChangeEvent(int version, long id, String accountName, int changeType, int eventIndex, String changeData) {
        this.mVersion = version;
        this.zzQD = id;
        this.zzQE = (String) zzx.zzv(accountName);
        this.zzQF = changeType;
        this.zzQG = eventIndex;
        this.zzQH = changeData;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof AccountChangeEvent)) {
            return false;
        }
        AccountChangeEvent accountChangeEvent = (AccountChangeEvent) that;
        return this.mVersion == accountChangeEvent.mVersion && this.zzQD == accountChangeEvent.zzQD && zzw.equal(this.zzQE, accountChangeEvent.zzQE) && this.zzQF == accountChangeEvent.zzQF && this.zzQG == accountChangeEvent.zzQG && zzw.equal(this.zzQH, accountChangeEvent.zzQH);
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.mVersion), Long.valueOf(this.zzQD), this.zzQE, Integer.valueOf(this.zzQF), Integer.valueOf(this.zzQG), this.zzQH);
    }

    public String toString() {
        String str = "UNKNOWN";
        switch (this.zzQF) {
            case TTSConst.TTSMULTILINE /*1*/:
                str = "ADDED";
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                str = "REMOVED";
                break;
            case TTSConst.TTSUNICODE /*3*/:
                str = "RENAMED_FROM";
                break;
            case TTSConst.TTSXML /*4*/:
                str = "RENAMED_TO";
                break;
        }
        return "AccountChangeEvent {accountName = " + this.zzQE + ", changeType = " + str + ", changeData = " + this.zzQH + ", eventIndex = " + this.zzQG + "}";
    }

    public void writeToParcel(Parcel dest, int flags) {
        zza.zza(this, dest, flags);
    }
}
