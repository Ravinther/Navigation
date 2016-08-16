package com.google.android.gms.location.places;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class PlacePhotoResult implements Result, SafeParcelable {
    public static final Creator<PlacePhotoResult> CREATOR;
    private final Bitmap mBitmap;
    final int mVersionCode;
    private final Status zzQA;
    final BitmapTeleporter zzaDG;

    static {
        CREATOR = new zzi();
    }

    PlacePhotoResult(int versionCode, Status status, BitmapTeleporter teleporter) {
        this.mVersionCode = versionCode;
        this.zzQA = status;
        this.zzaDG = teleporter;
        if (this.zzaDG != null) {
            this.mBitmap = teleporter.zznQ();
        } else {
            this.mBitmap = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public Status getStatus() {
        return this.zzQA;
    }

    public String toString() {
        return zzw.zzu(this).zzg("status", this.zzQA).zzg("bitmap", this.mBitmap).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzi.zza(this, parcel, flags);
    }
}
