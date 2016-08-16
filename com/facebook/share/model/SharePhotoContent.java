package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.SharePhoto.Builder;
import java.util.Collections;
import java.util.List;

public final class SharePhotoContent extends ShareContent<SharePhotoContent, Object> {
    public static final Creator<SharePhotoContent> CREATOR;
    private final List<SharePhoto> photos;

    /* renamed from: com.facebook.share.model.SharePhotoContent.1 */
    static class C03981 implements Creator<SharePhotoContent> {
        C03981() {
        }

        public SharePhotoContent createFromParcel(Parcel in) {
            return new SharePhotoContent(in);
        }

        public SharePhotoContent[] newArray(int size) {
            return new SharePhotoContent[size];
        }
    }

    SharePhotoContent(Parcel in) {
        super(in);
        this.photos = Collections.unmodifiableList(Builder.readListFrom(in));
    }

    public List<SharePhoto> getPhotos() {
        return this.photos;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        Builder.writeListTo(out, this.photos);
    }

    static {
        CREATOR = new C03981();
    }
}
