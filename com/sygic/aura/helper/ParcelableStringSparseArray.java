package com.sygic.aura.helper;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import java.util.List;

public class ParcelableStringSparseArray extends SparseArray<List<String>> implements Parcelable {
    public static final Creator<ParcelableStringSparseArray> CREATOR;
    private int[] mKeys;

    /* renamed from: com.sygic.aura.helper.ParcelableStringSparseArray.1 */
    static class C12781 implements Creator<ParcelableStringSparseArray> {
        C12781() {
        }

        public ParcelableStringSparseArray createFromParcel(Parcel in) {
            return new ParcelableStringSparseArray(in);
        }

        public ParcelableStringSparseArray[] newArray(int size) {
            return new ParcelableStringSparseArray[size];
        }
    }

    public ParcelableStringSparseArray() {
        this.mKeys = null;
    }

    public ParcelableStringSparseArray(Parcel in) {
        this.mKeys = null;
        in.readIntArray(this.mKeys);
        for (int key : this.mKeys) {
            in.readStringList(null);
            put(this.mKeys[key], null);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        int e;
        this.mKeys = new int[size()];
        for (e = 0; e < this.mKeys.length; e++) {
            this.mKeys[e] = keyAt(e);
        }
        out.writeIntArray(this.mKeys);
        for (e = 0; e < this.mKeys.length; e++) {
            out.writeStringList((List) valueAt(e));
        }
    }

    static {
        CREATOR = new C12781();
    }
}
