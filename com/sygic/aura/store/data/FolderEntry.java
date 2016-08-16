package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class FolderEntry extends StoreEntry {
    public static final Creator<FolderEntry> CREATOR;
    private final int mNumber;
    private final boolean mPositionRequired;
    private final String mSubtitle;

    /* renamed from: com.sygic.aura.store.data.FolderEntry.1 */
    static class C16971 implements Creator<FolderEntry> {
        C16971() {
        }

        public FolderEntry createFromParcel(Parcel source) {
            return new FolderEntry(source);
        }

        public FolderEntry[] newArray(int size) {
            return new FolderEntry[size];
        }
    }

    public FolderEntry(String id, String title, String iconUrl, String subtitle, int number, boolean positionRequired, int index) {
        super(id, title, iconUrl, index);
        this.mSubtitle = subtitle;
        this.mNumber = number;
        this.mPositionRequired = positionRequired;
    }

    public boolean isPositionRequired() {
        return this.mPositionRequired;
    }

    public String getSummary() {
        return this.mSubtitle;
    }

    public EViewType getType() {
        return EViewType.TYPE_FOLDER;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mSubtitle);
        dest.writeInt(this.mNumber);
        dest.writeByte(this.mPositionRequired ? (byte) 1 : (byte) 0);
    }

    protected FolderEntry(Parcel in) {
        super(in);
        this.mSubtitle = in.readString();
        this.mNumber = in.readInt();
        this.mPositionRequired = in.readByte() != null;
    }

    static {
        CREATOR = new C16971();
    }
}
