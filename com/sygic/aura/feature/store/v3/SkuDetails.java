package com.sygic.aura.feature.store.v3;

import com.sygic.aura.fragments.AbstractFragment;
import org.json.JSONException;
import org.json.JSONObject;

public class SkuDetails {
    String mCurrency;
    String mDescription;
    String mItemType;
    String mJson;
    String mPrice;
    String mPriceAmountMicros;
    String mSku;
    String mTitle;
    String mType;

    public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {
        this.mItemType = itemType;
        this.mJson = jsonSkuDetails;
        JSONObject o = new JSONObject(this.mJson);
        this.mSku = o.optString("productId");
        this.mType = o.optString("type");
        this.mPrice = o.optString("price");
        this.mTitle = o.optString(AbstractFragment.ARG_TITLE);
        this.mDescription = o.optString("description");
        this.mPriceAmountMicros = o.optString("price_amount_micros");
        this.mCurrency = o.optString("price_currency_code");
    }

    public String getSku() {
        return this.mSku;
    }

    public String getPriceAmountMicros() {
        return this.mPriceAmountMicros;
    }

    public String getCurrency() {
        return this.mCurrency;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String toString() {
        return "SkuDetails:" + this.mJson;
    }
}
