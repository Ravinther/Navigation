package com.facebook.share.model;

import android.os.Parcel;

public final class AppInviteContent implements ShareModel {
    private final String applinkUrl;
    private final String previewImageUrl;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.applinkUrl);
        out.writeString(this.previewImageUrl);
    }
}
