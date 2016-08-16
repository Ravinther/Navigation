package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class EditEntry extends StoreEntry {
    public static final Creator<EditEntry> CREATOR;

    /* renamed from: com.sygic.aura.store.data.EditEntry.1 */
    static class C16961 implements Creator<EditEntry> {
        C16961() {
        }

        public EditEntry createFromParcel(Parcel source) {
            return new EditEntry(source);
        }

        public EditEntry[] newArray(int size) {
            return new EditEntry[size];
        }
    }

    protected EditEntry(String id, String title, String iconUrl, int index) {
        super(id, title, iconUrl, index);
    }

    public EditEntry(String id, String title, String iconUrl, int status, int index) {
        this(id, title, iconUrl, index);
    }

    public EViewType getType() {
        return EViewType.TYPE_EDIT;
    }

    protected EditEntry(Parcel in) {
        super(in);
    }

    static {
        CREATOR = new C16961();
    }
}
