package com.amazon.device.iap.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public final class UserData implements Parcelable {
    public static final Creator<UserData> CREATOR;
    private final String marketplace;
    private final String userId;

    /* renamed from: com.amazon.device.iap.model.UserData.1 */
    static class C01531 implements Creator<UserData> {
        C01531() {
        }

        public UserData createFromParcel(Parcel parcel) {
            return new UserData(null);
        }

        public UserData[] newArray(int i) {
            return new UserData[i];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.userId, this.marketplace});
    }

    static {
        CREATOR = new C01531();
    }

    private UserData(Parcel parcel) {
        this.userId = parcel.readString();
        this.marketplace = parcel.readString();
    }

    public String getUserId() {
        return this.userId;
    }

    public String getMarketplace() {
        return this.marketplace;
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("userId", this.userId);
            jSONObject.put("marketplace", this.marketplace);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public String toString() {
        String str = null;
        try {
            str = toJSON().toString(4);
        } catch (JSONException e) {
        }
        return str;
    }
}
