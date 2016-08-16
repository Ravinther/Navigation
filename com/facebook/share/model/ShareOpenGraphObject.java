package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ShareOpenGraphObject extends ShareOpenGraphValueContainer<ShareOpenGraphObject, Object> {
    public static final Creator<ShareOpenGraphObject> CREATOR;

    /* renamed from: com.facebook.share.model.ShareOpenGraphObject.1 */
    static class C03961 implements Creator<ShareOpenGraphObject> {
        C03961() {
        }

        public ShareOpenGraphObject createFromParcel(Parcel in) {
            return new ShareOpenGraphObject(in);
        }

        public ShareOpenGraphObject[] newArray(int size) {
            return new ShareOpenGraphObject[size];
        }
    }

    ShareOpenGraphObject(Parcel in) {
        super(in);
    }

    static {
        CREATOR = new C03961();
    }
}
