package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.resources.ResourceManager;

public class StoreEntry implements Parcelable {
    public static final Creator<StoreEntry> CREATOR;
    protected String mISO;
    protected final String mIconUrl;
    protected final String mId;
    protected final int mIndex;
    protected int mPosition;
    protected final String mTitle;

    /* renamed from: com.sygic.aura.store.data.StoreEntry.1 */
    static class C17051 implements Creator<StoreEntry> {
        C17051() {
        }

        public StoreEntry createFromParcel(Parcel source) {
            return new StoreEntry(source);
        }

        public StoreEntry[] newArray(int size) {
            return new StoreEntry[size];
        }
    }

    public enum EViewType {
        TYPE_NONE,
        TYPE_FOLDER,
        TYPE_PRODUCT,
        TYPE_COMPONENT,
        TYPE_TEXT,
        TYPE_GROUP,
        TYPE_BUTTON,
        TYPE_IMAGE,
        TYPE_EDIT,
        TYPE_PAGINATOR,
        TYPE_SELECT,
        TYPE_HEADER
    }

    protected StoreEntry(String id, String title, String iconUrl, int index) {
        this.mPosition = -1;
        this.mId = id;
        this.mTitle = title;
        this.mIndex = index;
        this.mIconUrl = ResourceManager.sanitizeUrl(iconUrl);
    }

    public StoreEntry(String id, String title, String iconUrl, int status, int index) {
        this(id, title, iconUrl, index);
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getId() {
        return this.mId;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getPosition() {
        return this.mPosition >= 0 ? this.mPosition : this.mIndex;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public String getSummary() {
        return null;
    }

    public EViewType getType() {
        return EViewType.TYPE_NONE;
    }

    public String getISO() {
        return this.mISO;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mIconUrl);
        dest.writeString(this.mISO);
        dest.writeInt(this.mIndex);
        dest.writeInt(this.mPosition);
    }

    protected StoreEntry(Parcel in) {
        this.mPosition = -1;
        this.mId = in.readString();
        this.mTitle = in.readString();
        this.mIconUrl = in.readString();
        this.mISO = in.readString();
        this.mIndex = in.readInt();
        this.mPosition = in.readInt();
    }

    static {
        CREATOR = new C17051();
    }
}
