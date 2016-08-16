package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class GroupEntry extends StoreEntry {
    public static final Creator<GroupEntry> CREATOR;

    /* renamed from: com.sygic.aura.store.data.GroupEntry.1 */
    static class C16981 implements Creator<GroupEntry> {
        C16981() {
        }

        public GroupEntry createFromParcel(Parcel source) {
            return new GroupEntry(source);
        }

        public GroupEntry[] newArray(int size) {
            return new GroupEntry[size];
        }
    }

    public GroupEntry(String id, String title, String iconUrl, int index) {
        super(id, title, iconUrl, index);
    }

    public EViewType getType() {
        return EViewType.TYPE_GROUP;
    }

    protected GroupEntry(Parcel in) {
        super(in);
    }

    static {
        CREATOR = new C16981();
    }
}
