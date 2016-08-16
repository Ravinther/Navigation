package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzod;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ProductAction {
    Map<String, String> zzLj;

    public ProductAction(String action) {
        this.zzLj = new HashMap();
        put("&pa", action);
    }

    public Map<String, String> build() {
        return new HashMap(this.zzLj);
    }

    void put(String name, String value) {
        zzx.zzb((Object) name, (Object) "Name should be non-null");
        this.zzLj.put(name, value);
    }

    public ProductAction setCheckoutOptions(String value) {
        put("&col", value);
        return this;
    }

    public ProductAction setCheckoutStep(int value) {
        put("&cos", Integer.toString(value));
        return this;
    }

    public ProductAction setProductActionList(String value) {
        put("&pal", value);
        return this;
    }

    public ProductAction setTransactionAffiliation(String value) {
        put("&ta", value);
        return this;
    }

    public ProductAction setTransactionCouponCode(String value) {
        put("&tcc", value);
        return this;
    }

    public ProductAction setTransactionId(String value) {
        put("&ti", value);
        return this;
    }

    public ProductAction setTransactionRevenue(double value) {
        put("&tr", Double.toString(value));
        return this;
    }

    public ProductAction setTransactionShipping(double value) {
        put("&ts", Double.toString(value));
        return this;
    }

    public ProductAction setTransactionTax(double value) {
        put("&tt", Double.toString(value));
        return this;
    }

    public String toString() {
        Map hashMap = new HashMap();
        for (Entry entry : this.zzLj.entrySet()) {
            if (((String) entry.getKey()).startsWith("&")) {
                hashMap.put(((String) entry.getKey()).substring(1), entry.getValue());
            } else {
                hashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return zzod.zzF(hashMap);
    }
}
