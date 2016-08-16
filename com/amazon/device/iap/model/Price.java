package com.amazon.device.iap.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.math.BigDecimal;
import java.util.Currency;
import org.json.JSONException;
import org.json.JSONObject;

public final class Price implements Parcelable {
    public static final Creator<Price> CREATOR;
    private final Currency currency;
    private final BigDecimal value;

    /* renamed from: com.amazon.device.iap.model.Price.1 */
    static class C01501 implements Creator<Price> {
        C01501() {
        }

        public Price createFromParcel(Parcel parcel) {
            return new Price(null);
        }

        public Price[] newArray(int i) {
            return new Price[i];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.currency.getCurrencyCode());
        parcel.writeString(this.value.toString());
    }

    static {
        CREATOR = new C01501();
    }

    private Price(Parcel parcel) {
        this.currency = Currency.getInstance(parcel.readString());
        this.value = new BigDecimal(parcel.readString());
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("currency", this.currency);
        jSONObject.put("value", this.value);
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
