package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class PlacePhotoMetadataResult implements Result, SafeParcelable {
    public static final Creator<PlacePhotoMetadataResult> CREATOR;
    final int mVersionCode;
    private final Status zzQA;
    final DataHolder zzaDE;
    private final PlacePhotoMetadataBuffer zzaDF;

    static {
        CREATOR = new zzh();
    }

    PlacePhotoMetadataResult(int versionCode, Status status, DataHolder dataHolder) {
        this.mVersionCode = versionCode;
        this.zzQA = status;
        this.zzaDE = dataHolder;
        if (dataHolder == null) {
            this.zzaDF = null;
        } else {
            this.zzaDF = new PlacePhotoMetadataBuffer(this.zzaDE);
        }
    }

    public int describeContents() {
        return 0;
    }

    public Status getStatus() {
        return this.zzQA;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzh.zza(this, parcel, flags);
    }
}
