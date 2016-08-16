package com.facebook.share.internal;

import android.os.Parcel;
import com.facebook.share.model.ShareModel;

public class LikeContent implements ShareModel {
    private final String objectId;
    private final String objectType;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.objectId);
        out.writeString(this.objectType);
    }
}
