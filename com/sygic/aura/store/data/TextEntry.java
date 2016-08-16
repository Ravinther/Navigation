package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class TextEntry extends StoreEntry {
    public static final Creator<TextEntry> CREATOR;
    private String mText;

    /* renamed from: com.sygic.aura.store.data.TextEntry.1 */
    static class C17081 implements Creator<TextEntry> {
        C17081() {
        }

        public TextEntry createFromParcel(Parcel source) {
            return new TextEntry(null);
        }

        public TextEntry[] newArray(int size) {
            return new TextEntry[size];
        }
    }

    public TextEntry(String id, String title, String iconUrl, String text, int index) {
        super(id, title, iconUrl, index);
        this.mText = null;
        this.mText = text;
    }

    public String getText() {
        return this.mText;
    }

    public EViewType getType() {
        return EViewType.TYPE_TEXT;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mText);
    }

    private TextEntry(Parcel in) {
        super(in);
        this.mText = null;
        this.mText = in.readString();
    }

    static {
        CREATOR = new C17081();
    }
}
