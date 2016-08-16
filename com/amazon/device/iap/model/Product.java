package com.amazon.device.iap.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.fragments.AbstractFragment;
import org.json.JSONException;
import org.json.JSONObject;

public final class Product implements Parcelable {
    public static final Creator<Product> CREATOR;
    private final String description;
    private final Price price;
    private final ProductType productType;
    private final String sku;
    private final String smallIconUrl;
    private final String title;

    /* renamed from: com.amazon.device.iap.model.Product.1 */
    static class C01511 implements Creator<Product> {
        C01511() {
        }

        public Product createFromParcel(Parcel parcel) {
            return new Product(null);
        }

        public Product[] newArray(int i) {
            return new Product[i];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sku);
        parcel.writeString(this.productType.toString());
        parcel.writeString(this.description);
        parcel.writeParcelable(this.price, i);
        parcel.writeString(this.smallIconUrl);
        parcel.writeString(this.title);
    }

    static {
        CREATOR = new C01511();
    }

    private Product(Parcel parcel) {
        this.sku = parcel.readString();
        this.productType = ProductType.valueOf(parcel.readString());
        this.description = parcel.readString();
        this.price = (Price) parcel.readParcelable(Price.class.getClassLoader());
        this.smallIconUrl = parcel.readString();
        this.title = parcel.readString();
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("sku", this.sku);
        jSONObject.put("productType", this.productType);
        jSONObject.put("description", this.description);
        jSONObject.put("price", this.price);
        jSONObject.put("smallIconUrl", this.smallIconUrl);
        jSONObject.put(AbstractFragment.ARG_TITLE, this.title);
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
