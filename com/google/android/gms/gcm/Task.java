package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class Task implements Parcelable {
    private final Bundle mExtras;
    private final String mTag;
    private final String zzaAh;
    private final boolean zzaAi;
    private final boolean zzaAj;
    private final int zzaAk;
    private final boolean zzaAl;

    @Deprecated
    Task(Parcel in) {
        boolean z = true;
        this.zzaAh = in.readString();
        this.mTag = in.readString();
        this.zzaAi = in.readInt() == 1;
        if (in.readInt() != 1) {
            z = false;
        }
        this.zzaAj = z;
        this.zzaAk = 2;
        this.zzaAl = false;
        this.mExtras = null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.zzaAh);
        parcel.writeString(this.mTag);
        parcel.writeInt(this.zzaAi ? 1 : 0);
        if (!this.zzaAj) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
