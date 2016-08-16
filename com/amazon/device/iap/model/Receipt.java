package com.amazon.device.iap.model;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public final class Receipt {
    public static final Date DATE_CANCELED;
    private final Date cancelDate;
    private final ProductType productType;
    private final Date purchaseDate;
    private final String receiptId;
    private final String sku;

    static {
        DATE_CANCELED = new Date(0);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.receiptId == null ? 0 : this.receiptId.hashCode()) + (((this.purchaseDate == null ? 0 : this.purchaseDate.hashCode()) + (((this.productType == null ? 0 : this.productType.hashCode()) + (((this.cancelDate == null ? 0 : this.cancelDate.hashCode()) + 31) * 31)) * 31)) * 31)) * 31;
        if (this.sku != null) {
            i = this.sku.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Receipt receipt = (Receipt) obj;
        if (this.cancelDate == null) {
            if (receipt.cancelDate != null) {
                return false;
            }
        } else if (!this.cancelDate.equals(receipt.cancelDate)) {
            return false;
        }
        if (this.productType != receipt.productType) {
            return false;
        }
        if (this.purchaseDate == null) {
            if (receipt.purchaseDate != null) {
                return false;
            }
        } else if (!this.purchaseDate.equals(receipt.purchaseDate)) {
            return false;
        }
        if (this.receiptId == null) {
            if (receipt.receiptId != null) {
                return false;
            }
        } else if (!this.receiptId.equals(receipt.receiptId)) {
            return false;
        }
        if (this.sku == null) {
            if (receipt.sku != null) {
                return false;
            }
            return true;
        } else if (this.sku.equals(receipt.sku)) {
            return true;
        } else {
            return false;
        }
    }

    public String getReceiptId() {
        return this.receiptId;
    }

    public String getSku() {
        return this.sku;
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("receiptId", this.receiptId);
            jSONObject.put("sku", this.sku);
            jSONObject.put("itemType", this.productType);
            jSONObject.put("purchaseDate", this.purchaseDate);
            jSONObject.put("endDate", this.cancelDate);
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
