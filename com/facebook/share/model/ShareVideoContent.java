package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.SharePhoto.Builder;

public final class ShareVideoContent extends ShareContent<ShareVideoContent, Object> implements ShareModel {
    public static final Creator<ShareVideoContent> CREATOR;
    private final String contentDescription;
    private final String contentTitle;
    private final SharePhoto previewPhoto;
    private final ShareVideo video;

    /* renamed from: com.facebook.share.model.ShareVideoContent.1 */
    static class C04001 implements Creator<ShareVideoContent> {
        C04001() {
        }

        public ShareVideoContent createFromParcel(Parcel in) {
            return new ShareVideoContent(in);
        }

        public ShareVideoContent[] newArray(int size) {
            return new ShareVideoContent[size];
        }
    }

    ShareVideoContent(Parcel in) {
        super(in);
        this.contentDescription = in.readString();
        this.contentTitle = in.readString();
        Builder previewPhotoBuilder = new Builder().readFrom(in);
        if (previewPhotoBuilder.getImageUrl() == null && previewPhotoBuilder.getBitmap() == null) {
            this.previewPhoto = null;
        } else {
            this.previewPhoto = previewPhotoBuilder.build();
        }
        this.video = new ShareVideo.Builder().readFrom(in).build();
    }

    public String getContentDescription() {
        return this.contentDescription;
    }

    public String getContentTitle() {
        return this.contentTitle;
    }

    public SharePhoto getPreviewPhoto() {
        return this.previewPhoto;
    }

    public ShareVideo getVideo() {
        return this.video;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(this.contentDescription);
        out.writeString(this.contentTitle);
        out.writeParcelable(this.previewPhoto, 0);
        out.writeParcelable(this.video, 0);
    }

    static {
        CREATOR = new C04001();
    }
}
