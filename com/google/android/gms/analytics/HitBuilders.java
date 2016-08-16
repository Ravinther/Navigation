package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzae;
import com.google.android.gms.analytics.internal.zzam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HitBuilders {

    protected static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> zzKB;
        ProductAction zzKC;
        Map<String, List<Product>> zzKD;
        List<Promotion> zzKE;
        List<Product> zzKF;

        protected HitBuilder() {
            this.zzKB = new HashMap();
            this.zzKD = new HashMap();
            this.zzKE = new ArrayList();
            this.zzKF = new ArrayList();
        }

        public T addImpression(Product product, String impressionList) {
            if (product == null) {
                zzae.zzaE("product should be non-null");
            } else {
                if (impressionList == null) {
                    impressionList = "";
                }
                if (!this.zzKD.containsKey(impressionList)) {
                    this.zzKD.put(impressionList, new ArrayList());
                }
                ((List) this.zzKD.get(impressionList)).add(product);
            }
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzae.zzaE("product should be non-null");
            } else {
                this.zzKF.add(product);
            }
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzae.zzaE("promotion should be non-null");
            } else {
                this.zzKE.add(promotion);
            }
            return this;
        }

        public Map<String, String> build() {
            Map<String, String> hashMap = new HashMap(this.zzKB);
            if (this.zzKC != null) {
                hashMap.putAll(this.zzKC.build());
            }
            int i = 1;
            for (Promotion zzaV : this.zzKE) {
                hashMap.putAll(zzaV.zzaV(zzc.zzU(i)));
                i++;
            }
            i = 1;
            for (Product zzaV2 : this.zzKF) {
                hashMap.putAll(zzaV2.zzaV(zzc.zzS(i)));
                i++;
            }
            int i2 = 1;
            for (Entry entry : this.zzKD.entrySet()) {
                List<Product> list = (List) entry.getValue();
                String zzX = zzc.zzX(i2);
                int i3 = 1;
                for (Product zzaV3 : list) {
                    hashMap.putAll(zzaV3.zzaV(zzX + zzc.zzW(i3)));
                    i3++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    hashMap.put(zzX + "nm", entry.getKey());
                }
                i2++;
            }
            return hashMap;
        }

        public final T set(String paramName, String paramValue) {
            if (paramName != null) {
                this.zzKB.put(paramName, paramValue);
            } else {
                zzae.zzaE(" HitBuilder.set() called with a null paramName.");
            }
            return this;
        }

        public final T setAll(Map<String, String> params) {
            if (params != null) {
                this.zzKB.putAll(new HashMap(params));
            }
            return this;
        }

        public T setCampaignParamsFromUrl(String utmParams) {
            Object zzbp = zzam.zzbp(utmParams);
            if (!TextUtils.isEmpty(zzbp)) {
                Map zzbn = zzam.zzbn(zzbp);
                set("&cc", (String) zzbn.get("utm_content"));
                set("&cm", (String) zzbn.get("utm_medium"));
                set("&cn", (String) zzbn.get("utm_campaign"));
                set("&cs", (String) zzbn.get("utm_source"));
                set("&ck", (String) zzbn.get("utm_term"));
                set("&ci", (String) zzbn.get("utm_id"));
                set("&anid", (String) zzbn.get("anid"));
                set("&gclid", (String) zzbn.get("gclid"));
                set("&dclid", (String) zzbn.get("dclid"));
                set("&aclid", (String) zzbn.get("aclid"));
                set("&gmob_t", (String) zzbn.get("gmob_t"));
            }
            return this;
        }

        public T setCustomDimension(int index, String dimension) {
            set(zzc.zzO(index), dimension);
            return this;
        }

        public T setProductAction(ProductAction action) {
            this.zzKC = action;
            return this;
        }
    }

    @Deprecated
    public static class AppViewBuilder extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            set("&t", "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            set("&t", "event");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public EventBuilder setAction(String action) {
            set("&ea", action);
            return this;
        }

        public EventBuilder setCategory(String category) {
            set("&ec", category);
            return this;
        }

        public EventBuilder setLabel(String label) {
            set("&el", label);
            return this;
        }
    }

    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            set("&t", "exception");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ExceptionBuilder setDescription(String description) {
            set("&exd", description);
            return this;
        }

        public ExceptionBuilder setFatal(boolean fatal) {
            set("&exf", zzam.zzJ(fatal));
            return this;
        }
    }

    @Deprecated
    public static class ItemBuilder extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            set("&t", "item");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ItemBuilder setCurrencyCode(String currencyCode) {
            set("&cu", currencyCode);
            return this;
        }

        public ItemBuilder setName(String name) {
            set("&in", name);
            return this;
        }

        public ItemBuilder setPrice(double price) {
            set("&ip", Double.toString(price));
            return this;
        }

        public ItemBuilder setQuantity(long quantity) {
            set("&iq", Long.toString(quantity));
            return this;
        }

        public ItemBuilder setSku(String sku) {
            set("&ic", sku);
            return this;
        }

        public ItemBuilder setTransactionId(String transactionid) {
            set("&ti", transactionid);
            return this;
        }
    }

    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            set("&t", "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    @Deprecated
    public static class TransactionBuilder extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            set("&t", "transaction");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public TransactionBuilder setAffiliation(String affiliation) {
            set("&ta", affiliation);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String currencyCode) {
            set("&cu", currencyCode);
            return this;
        }

        public TransactionBuilder setRevenue(double revenue) {
            set("&tr", Double.toString(revenue));
            return this;
        }

        public TransactionBuilder setShipping(double shipping) {
            set("&ts", Double.toString(shipping));
            return this;
        }

        public TransactionBuilder setTax(double tax) {
            set("&tt", Double.toString(tax));
            return this;
        }

        public TransactionBuilder setTransactionId(String transactionid) {
            set("&ti", transactionid);
            return this;
        }
    }
}
