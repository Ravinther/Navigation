package com.sygic.aura.news;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.resources.ResourceManager;

public class NewsItem implements Parcelable {
    public static final Creator<NewsItem> CREATOR;
    private String mStrDescription;
    private String mStrImageSourceLink;
    private String mStrTitle;

    /* renamed from: com.sygic.aura.news.NewsItem.1 */
    static class C14151 implements Creator<NewsItem> {
        C14151() {
        }

        public NewsItem createFromParcel(Parcel source) {
            return new NewsItem(source);
        }

        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    }

    public NewsItem(String strTitle, String strDescription, String strImageSourceLink) {
        this.mStrDescription = strDescription;
        this.mStrTitle = strTitle;
        this.mStrImageSourceLink = ResourceManager.sanitizeUrl(strImageSourceLink);
    }

    public NewsItem(Parcel source) {
        this.mStrDescription = source.readString();
        this.mStrTitle = source.readString();
        this.mStrImageSourceLink = source.readString();
    }

    public String getTitle() {
        return this.mStrTitle;
    }

    public String getDescription() {
        return this.mStrDescription;
    }

    public String getImageSourceLink() {
        return this.mStrImageSourceLink;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mStrTitle);
        dest.writeString(this.mStrDescription);
        dest.writeString(this.mStrImageSourceLink);
    }

    static {
        CREATOR = new C14151();
    }
}
