package com.amazon.device.iap.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.UUID;

public final class RequestId implements Parcelable {
    public static final Creator<RequestId> CREATOR;
    private final String encodedId;

    /* renamed from: com.amazon.device.iap.model.RequestId.1 */
    static class C01521 implements Creator<RequestId> {
        C01521() {
        }

        public RequestId createFromParcel(Parcel parcel) {
            return new RequestId(null);
        }

        public RequestId[] newArray(int i) {
            return new RequestId[i];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.encodedId);
    }

    static {
        CREATOR = new C01521();
    }

    private RequestId(Parcel parcel) {
        this.encodedId = parcel.readString();
    }

    public RequestId() {
        this.encodedId = UUID.randomUUID().toString();
    }

    public String toString() {
        return this.encodedId;
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.encodedId.equals(((RequestId) obj).encodedId);
    }

    public int hashCode() {
        return (this.encodedId == null ? 0 : this.encodedId.hashCode()) + 31;
    }
}
