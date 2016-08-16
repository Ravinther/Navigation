package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ShareLinkContent extends ShareContent<ShareLinkContent, Object> {
    public static final Creator<ShareLinkContent> CREATOR;
    private final String contentDescription;
    private final String contentTitle;
    private final Uri imageUrl;

    /* renamed from: com.facebook.share.model.ShareLinkContent.1 */
    static class C03931 implements Creator<ShareLinkContent> {
        C03931() {
        }

        public ShareLinkContent createFromParcel(Parcel in) {
            return new ShareLinkContent(in);
        }

        public ShareLinkContent[] newArray(int size) {
            return new ShareLinkContent[size];
        }
    }

    ShareLinkContent(Parcel in) {
        super(in);
        this.contentDescription = in.readString();
        this.contentTitle = in.readString();
        this.imageUrl = (Uri) in.readParcelable(Uri.class.getClassLoader());
    }

    public String getContentDescription() {
        return this.contentDescription;
    }

    public String getContentTitle() {
        return this.contentTitle;
    }

    public Uri getImageUrl() {
        return this.imageUrl;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(this.contentDescription);
        out.writeString(this.contentTitle);
        out.writeParcelable(this.imageUrl, 0);
    }

    static {
        CREATOR = new C03931();
    }
}
