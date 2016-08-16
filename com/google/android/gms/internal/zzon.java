package com.google.android.gms.internal;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzon extends zzod<zzon> {
    private ProductAction zzKC;
    private final Map<String, List<Product>> zzKD;
    private final List<Promotion> zzKE;
    private final List<Product> zzKF;

    public zzon() {
        this.zzKF = new ArrayList();
        this.zzKE = new ArrayList();
        this.zzKD = new HashMap();
    }

    public String toString() {
        Map hashMap = new HashMap();
        if (!this.zzKF.isEmpty()) {
            hashMap.put("products", this.zzKF);
        }
        if (!this.zzKE.isEmpty()) {
            hashMap.put("promotions", this.zzKE);
        }
        if (!this.zzKD.isEmpty()) {
            hashMap.put("impressions", this.zzKD);
        }
        hashMap.put("productAction", this.zzKC);
        return zzod.zzA(hashMap);
    }

    public void zza(Product product, String str) {
        if (product != null) {
            Object obj;
            if (str == null) {
                obj = "";
            }
            if (!this.zzKD.containsKey(obj)) {
                this.zzKD.put(obj, new ArrayList());
            }
            ((List) this.zzKD.get(obj)).add(product);
        }
    }

    public void zza(zzon com_google_android_gms_internal_zzon) {
        com_google_android_gms_internal_zzon.zzKF.addAll(this.zzKF);
        com_google_android_gms_internal_zzon.zzKE.addAll(this.zzKE);
        for (Entry entry : this.zzKD.entrySet()) {
            String str = (String) entry.getKey();
            for (Product zza : (List) entry.getValue()) {
                com_google_android_gms_internal_zzon.zza(zza, str);
            }
        }
        if (this.zzKC != null) {
            com_google_android_gms_internal_zzon.zzKC = this.zzKC;
        }
    }

    public ProductAction zzxM() {
        return this.zzKC;
    }

    public List<Product> zzxN() {
        return Collections.unmodifiableList(this.zzKF);
    }

    public Map<String, List<Product>> zzxO() {
        return this.zzKD;
    }

    public List<Promotion> zzxP() {
        return Collections.unmodifiableList(this.zzKE);
    }
}
