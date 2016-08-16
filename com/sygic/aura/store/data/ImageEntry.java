package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ImageEntry extends ButtonEntry {
    public static final Creator<ImageEntry> CREATOR;
    private String mImageUrl;

    /* renamed from: com.sygic.aura.store.data.ImageEntry.1 */
    static class C16991 implements Creator<ImageEntry> {
        C16991() {
        }

        public ImageEntry createFromParcel(Parcel source) {
            return new ImageEntry(source);
        }

        public ImageEntry[] newArray(int size) {
            return new ImageEntry[size];
        }
    }

    public ImageEntry(String id, String title, String iconUrl, int index, String imageUrl, int action, String url) {
        super(id, title, iconUrl, action, url, index);
        this.mImageUrl = ResourceManager.sanitizeUrl(imageUrl);
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }

    public EViewType getType() {
        return EViewType.TYPE_IMAGE;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mImageUrl);
    }

    protected ImageEntry(Parcel in) {
        super(in);
        this.mImageUrl = in.readString();
    }

    static {
        CREATOR = new C16991();
    }
}
