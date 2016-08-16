package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class PaginatorEntry extends StoreEntry {
    public static final Creator<PaginatorEntry> CREATOR;

    /* renamed from: com.sygic.aura.store.data.PaginatorEntry.1 */
    static class C17001 implements Creator<PaginatorEntry> {
        C17001() {
        }

        public PaginatorEntry createFromParcel(Parcel source) {
            return new PaginatorEntry(source);
        }

        public PaginatorEntry[] newArray(int size) {
            return new PaginatorEntry[size];
        }
    }

    protected PaginatorEntry(String id, String title, String iconUrl, int index) {
        super(id, title, iconUrl, index);
    }

    public PaginatorEntry(String id, String title, String iconUrl, int status, int index) {
        this(id, title, iconUrl, index);
    }

    public EViewType getType() {
        return EViewType.TYPE_PAGINATOR;
    }

    protected PaginatorEntry(Parcel in) {
        super(in);
    }

    static {
        CREATOR = new C17001();
    }
}
