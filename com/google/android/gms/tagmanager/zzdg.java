package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zzdg extends zzdd {
    private static final String ID;
    private static final String zzaSW;
    private static final String zzaSX;
    private static final String zzaSY;
    private static final String zzaSZ;
    private static final String zzaTa;
    private static final String zzaTb;
    private static final String zzaTc;
    private static final String zzaTd;
    private static final String zzaTe;
    private static final List<String> zzaTf;
    private static final Pattern zzaTg;
    private static final Pattern zzaTh;
    private static Map<String, String> zzaTi;
    private static Map<String, String> zzaTj;
    private final DataLayer zzaOT;
    private final Set<String> zzaTk;
    private final zzdc zzaTl;

    static {
        ID = zzad.UNIVERSAL_ANALYTICS.toString();
        zzaSW = zzae.ACCOUNT.toString();
        zzaSX = zzae.ANALYTICS_PASS_THROUGH.toString();
        zzaSY = zzae.ENABLE_ECOMMERCE.toString();
        zzaSZ = zzae.ECOMMERCE_USE_DATA_LAYER.toString();
        zzaTa = zzae.ECOMMERCE_MACRO_DATA.toString();
        zzaTb = zzae.ANALYTICS_FIELDS.toString();
        zzaTc = zzae.TRACK_TRANSACTION.toString();
        zzaTd = zzae.TRANSACTION_DATALAYER_MAP.toString();
        zzaTe = zzae.TRANSACTION_ITEM_DATALAYER_MAP.toString();
        zzaTf = Arrays.asList(new String[]{"detail", "checkout", "checkout_option", "click", "add", "remove", "purchase", "refund"});
        zzaTg = Pattern.compile("dimension(\\d+)");
        zzaTh = Pattern.compile("metric(\\d+)");
    }

    public zzdg(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzdc(context));
    }

    zzdg(Context context, DataLayer dataLayer, zzdc com_google_android_gms_tagmanager_zzdc) {
        super(ID, new String[0]);
        this.zzaOT = dataLayer;
        this.zzaTl = com_google_android_gms_tagmanager_zzdc;
        this.zzaTk = new HashSet();
        this.zzaTk.add("");
        this.zzaTk.add("0");
        this.zzaTk.add("false");
    }

    private Double zzO(Object obj) {
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cannot convert the object to Double: " + e.getMessage());
            }
        } else if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        } else {
            if (obj instanceof Double) {
                return (Double) obj;
            }
            throw new RuntimeException("Cannot convert the object to Double: " + obj.toString());
        }
    }

    private Integer zzP(Object obj) {
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cannot convert the object to Integer: " + e.getMessage());
            }
        } else if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        } else {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            throw new RuntimeException("Cannot convert the object to Integer: " + obj.toString());
        }
    }

    private Promotion zzQ(Map<String, String> map) {
        Promotion promotion = new Promotion();
        String str = (String) map.get("id");
        if (str != null) {
            promotion.setId(String.valueOf(str));
        }
        str = (String) map.get("name");
        if (str != null) {
            promotion.setName(String.valueOf(str));
        }
        str = (String) map.get("creative");
        if (str != null) {
            promotion.setCreative(String.valueOf(str));
        }
        str = (String) map.get("position");
        if (str != null) {
            promotion.setPosition(String.valueOf(str));
        }
        return promotion;
    }

    private Product zzR(Map<String, Object> map) {
        Product product = new Product();
        Object obj = map.get("id");
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        obj = map.get("name");
        if (obj != null) {
            product.setName(String.valueOf(obj));
        }
        obj = map.get("brand");
        if (obj != null) {
            product.setBrand(String.valueOf(obj));
        }
        obj = map.get("category");
        if (obj != null) {
            product.setCategory(String.valueOf(obj));
        }
        obj = map.get("variant");
        if (obj != null) {
            product.setVariant(String.valueOf(obj));
        }
        obj = map.get("coupon");
        if (obj != null) {
            product.setCouponCode(String.valueOf(obj));
        }
        obj = map.get("position");
        if (obj != null) {
            product.setPosition(zzP(obj).intValue());
        }
        obj = map.get("price");
        if (obj != null) {
            product.setPrice(zzO(obj).doubleValue());
        }
        obj = map.get("quantity");
        if (obj != null) {
            product.setQuantity(zzP(obj).intValue());
        }
        for (String str : map.keySet()) {
            Matcher matcher = zzaTg.matcher(str);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(str)));
                } catch (NumberFormatException e) {
                    zzbg.zzaE("illegal number in custom dimension value: " + str);
                }
            } else {
                matcher = zzaTh.matcher(str);
                if (matcher.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher.group(1)), zzP(map.get(str)).intValue());
                    } catch (NumberFormatException e2) {
                        zzbg.zzaE("illegal number in custom metric value: " + str);
                    }
                }
            }
        }
        return product;
    }

    private Map<String, String> zzS(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaTd);
        if (com_google_android_gms_internal_zzag_zza != null) {
            return zzc(com_google_android_gms_internal_zzag_zza);
        }
        if (zzaTi == null) {
            Map hashMap = new HashMap();
            hashMap.put("transactionId", "&ti");
            hashMap.put("transactionAffiliation", "&ta");
            hashMap.put("transactionTax", "&tt");
            hashMap.put("transactionShipping", "&ts");
            hashMap.put("transactionTotal", "&tr");
            hashMap.put("transactionCurrency", "&cu");
            zzaTi = hashMap;
        }
        return zzaTi;
    }

    private Map<String, String> zzT(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaTe);
        if (com_google_android_gms_internal_zzag_zza != null) {
            return zzc(com_google_android_gms_internal_zzag_zza);
        }
        if (zzaTj == null) {
            Map hashMap = new HashMap();
            hashMap.put("name", "&in");
            hashMap.put("sku", "&ic");
            hashMap.put("category", "&iv");
            hashMap.put("price", "&ip");
            hashMap.put("quantity", "&iq");
            hashMap.put("currency", "&cu");
            zzaTj = hashMap;
        }
        return zzaTj;
    }

    private void zza(Tracker tracker, Map<String, zza> map) {
        String zzfd = zzfd("transactionId");
        if (zzfd == null) {
            zzbg.m1447e("Cannot find transactionId in data layer.");
            return;
        }
        List<Map> linkedList = new LinkedList();
        try {
            Map zzm = zzm((zza) map.get(zzaTb));
            zzm.put("&t", "transaction");
            for (Entry entry : zzS(map).entrySet()) {
                zzd(zzm, (String) entry.getValue(), zzfd((String) entry.getKey()));
            }
            linkedList.add(zzm);
            List<Map> zzfe = zzfe("transactionProducts");
            if (zzfe != null) {
                for (Map map2 : zzfe) {
                    if (map2.get("name") == null) {
                        zzbg.m1447e("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map zzm2 = zzm((zza) map.get(zzaTb));
                    zzm2.put("&t", "item");
                    zzm2.put("&ti", zzfd);
                    for (Entry entry2 : zzT(map).entrySet()) {
                        zzd(zzm2, (String) entry2.getValue(), (String) map2.get(entry2.getKey()));
                    }
                    linkedList.add(zzm2);
                }
            }
            for (Map map22 : linkedList) {
                tracker.send(map22);
            }
        } catch (Throwable e) {
            zzbg.zzb("Unable to send transaction", e);
        }
    }

    private void zzb(Tracker tracker, Map<String, zza> map) {
        Object obj;
        Map map2;
        ScreenViewBuilder screenViewBuilder = new ScreenViewBuilder();
        Map zzm = zzm((zza) map.get(zzaTb));
        screenViewBuilder.setAll(zzm);
        if (zzj(map, zzaSZ)) {
            obj = this.zzaOT.get("ecommerce");
            map2 = obj instanceof Map ? (Map) obj : null;
        } else {
            obj = zzdf.zzl((zza) map.get(zzaTa));
            map2 = obj instanceof Map ? (Map) obj : null;
        }
        if (map2 != null) {
            Map map3;
            List<Map> list;
            String str = (String) zzm.get("&cu");
            if (str == null) {
                str = (String) map2.get("currencyCode");
            }
            if (str != null) {
                screenViewBuilder.set("&cu", str);
            }
            obj = map2.get("impressions");
            if (obj instanceof List) {
                for (Map map4 : (List) obj) {
                    try {
                        screenViewBuilder.addImpression(zzR(map4), (String) map4.get("list"));
                    } catch (RuntimeException e) {
                        zzbg.m1447e("Failed to extract a product from DataLayer. " + e.getMessage());
                    }
                }
            }
            List list2 = map2.containsKey("promoClick") ? (List) ((Map) map2.get("promoClick")).get("promotions") : map2.containsKey("promoView") ? (List) ((Map) map2.get("promoView")).get("promotions") : null;
            if (r0 != null) {
                for (Map map42 : r0) {
                    try {
                        screenViewBuilder.addPromotion(zzQ(map42));
                    } catch (RuntimeException e2) {
                        zzbg.m1447e("Failed to extract a promotion from DataLayer. " + e2.getMessage());
                    }
                }
                if (map2.containsKey("promoClick")) {
                    screenViewBuilder.set("&promoa", "click");
                    obj = null;
                    if (obj != null) {
                        for (String str2 : zzaTf) {
                            if (map2.containsKey(str2)) {
                                map3 = (Map) map2.get(str2);
                                list = (List) map3.get("products");
                                if (list != null) {
                                    for (Map map22 : list) {
                                        try {
                                            screenViewBuilder.addProduct(zzR(map22));
                                        } catch (RuntimeException e3) {
                                            zzbg.m1447e("Failed to extract a product from DataLayer. " + e3.getMessage());
                                        }
                                    }
                                }
                                try {
                                    screenViewBuilder.setProductAction(map3.containsKey("actionField") ? zze(str2, (Map) map3.get("actionField")) : new ProductAction(str2));
                                } catch (RuntimeException e22) {
                                    zzbg.m1447e("Failed to extract a product action from DataLayer. " + e22.getMessage());
                                }
                            }
                        }
                    }
                } else {
                    screenViewBuilder.set("&promoa", "view");
                }
            }
            int i = 1;
            if (obj != null) {
                for (String str22 : zzaTf) {
                    if (map22.containsKey(str22)) {
                        map3 = (Map) map22.get(str22);
                        list = (List) map3.get("products");
                        if (list != null) {
                            while (r4.hasNext()) {
                                screenViewBuilder.addProduct(zzR(map22));
                            }
                        }
                        if (map3.containsKey("actionField")) {
                        }
                        screenViewBuilder.setProductAction(map3.containsKey("actionField") ? zze(str22, (Map) map3.get("actionField")) : new ProductAction(str22));
                    }
                }
            }
        }
        tracker.send(screenViewBuilder.build());
    }

    private Map<String, String> zzc(zza com_google_android_gms_internal_zzag_zza) {
        Object zzl = zzdf.zzl(com_google_android_gms_internal_zzag_zza);
        if (!(zzl instanceof Map)) {
            return null;
        }
        Map map = (Map) zzl;
        Map<String, String> linkedHashMap = new LinkedHashMap();
        for (Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private void zzd(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private ProductAction zze(String str, Map<String, Object> map) {
        ProductAction productAction = new ProductAction(str);
        Object obj = map.get("id");
        if (obj != null) {
            productAction.setTransactionId(String.valueOf(obj));
        }
        obj = map.get("affiliation");
        if (obj != null) {
            productAction.setTransactionAffiliation(String.valueOf(obj));
        }
        obj = map.get("coupon");
        if (obj != null) {
            productAction.setTransactionCouponCode(String.valueOf(obj));
        }
        obj = map.get("list");
        if (obj != null) {
            productAction.setProductActionList(String.valueOf(obj));
        }
        obj = map.get("option");
        if (obj != null) {
            productAction.setCheckoutOptions(String.valueOf(obj));
        }
        obj = map.get("revenue");
        if (obj != null) {
            productAction.setTransactionRevenue(zzO(obj).doubleValue());
        }
        obj = map.get("tax");
        if (obj != null) {
            productAction.setTransactionTax(zzO(obj).doubleValue());
        }
        obj = map.get("shipping");
        if (obj != null) {
            productAction.setTransactionShipping(zzO(obj).doubleValue());
        }
        obj = map.get("step");
        if (obj != null) {
            productAction.setCheckoutStep(zzP(obj).intValue());
        }
        return productAction;
    }

    private String zzfd(String str) {
        Object obj = this.zzaOT.get(str);
        return obj == null ? null : obj.toString();
    }

    private List<Map<String, String>> zzfe(String str) {
        Object obj = this.zzaOT.get(str);
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            for (Object obj2 : (List) obj) {
                if (!(obj2 instanceof Map)) {
                    throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
                }
            }
            return (List) obj;
        }
        throw new IllegalArgumentException("transactionProducts should be of type List.");
    }

    private boolean zzj(Map<String, zza> map, String str) {
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(str);
        return com_google_android_gms_internal_zzag_zza == null ? false : zzdf.zzk(com_google_android_gms_internal_zzag_zza).booleanValue();
    }

    private Map<String, String> zzm(zza com_google_android_gms_internal_zzag_zza) {
        if (com_google_android_gms_internal_zzag_zza == null) {
            return new HashMap();
        }
        Map<String, String> zzc = zzc(com_google_android_gms_internal_zzag_zza);
        if (zzc == null) {
            return new HashMap();
        }
        String str = (String) zzc.get("&aip");
        if (str != null && this.zzaTk.contains(str.toLowerCase())) {
            zzc.remove("&aip");
        }
        return zzc;
    }

    public /* bridge */ /* synthetic */ String zzAc() {
        return super.zzAc();
    }

    public /* bridge */ /* synthetic */ Set zzAd() {
        return super.zzAd();
    }

    public /* bridge */ /* synthetic */ zza zzG(Map map) {
        return super.zzG(map);
    }

    public void zzI(Map<String, zza> map) {
        Tracker zzeV = this.zzaTl.zzeV("_GTM_DEFAULT_TRACKER_");
        zzeV.enableAdvertisingIdCollection(zzj(map, "collect_adid"));
        if (zzj(map, zzaSY)) {
            zzb(zzeV, map);
        } else if (zzj(map, zzaSX)) {
            zzeV.send(zzm((zza) map.get(zzaTb)));
        } else if (zzj(map, zzaTc)) {
            zza(zzeV, map);
        } else {
            zzbg.zzaE("Ignoring unknown tag.");
        }
    }

    public /* bridge */ /* synthetic */ boolean zzzx() {
        return super.zzzx();
    }
}
