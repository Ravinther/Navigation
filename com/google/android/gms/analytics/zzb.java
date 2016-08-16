package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzc;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zziy;
import com.google.android.gms.internal.zziz;
import com.google.android.gms.internal.zzja;
import com.google.android.gms.internal.zzjb;
import com.google.android.gms.internal.zzob;
import com.google.android.gms.internal.zzoh;
import com.google.android.gms.internal.zzok;
import com.google.android.gms.internal.zzol;
import com.google.android.gms.internal.zzom;
import com.google.android.gms.internal.zzon;
import com.google.android.gms.internal.zzoo;
import com.google.android.gms.internal.zzop;
import com.google.android.gms.internal.zzoq;
import com.google.android.gms.internal.zzor;
import com.google.android.gms.internal.zzos;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class zzb extends zzc implements zzoh {
    private static DecimalFormat zzKk;
    private final zzf zzKa;
    private final Uri zzKl;
    private final boolean zzKm;
    private final boolean zzKn;
    private final String zztw;

    public zzb(zzf com_google_android_gms_analytics_internal_zzf, String str) {
        this(com_google_android_gms_analytics_internal_zzf, str, true, false);
    }

    public zzb(zzf com_google_android_gms_analytics_internal_zzf, String str, boolean z, boolean z2) {
        super(com_google_android_gms_analytics_internal_zzf);
        zzx.zzcs(str);
        this.zzKa = com_google_android_gms_analytics_internal_zzf;
        this.zztw = str;
        this.zzKm = z;
        this.zzKn = z2;
        this.zzKl = zzaP(this.zztw);
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != 0.0d) {
            map.put(str, zzb(d));
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i > 0 && i2 > 0) {
            map.put(str, i + "x" + i2);
        }
    }

    private static void zza(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, "1");
        }
    }

    static Uri zzaP(String str) {
        zzx.zzcs(str);
        Builder builder = new Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    static String zzb(double d) {
        if (zzKk == null) {
            zzKk = new DecimalFormat("0.######");
        }
        return zzKk.format(d);
    }

    public static Map<String, String> zzc(zzob com_google_android_gms_internal_zzob) {
        CharSequence zzP;
        Map hashMap = new HashMap();
        zzja com_google_android_gms_internal_zzja = (zzja) com_google_android_gms_internal_zzob.zzd(zzja.class);
        if (com_google_android_gms_internal_zzja != null) {
            for (Entry entry : com_google_android_gms_internal_zzja.zzhJ().entrySet()) {
                String zzh = zzh(entry.getValue());
                if (zzh != null) {
                    hashMap.put(entry.getKey(), zzh);
                }
            }
        }
        zzjb com_google_android_gms_internal_zzjb = (zzjb) com_google_android_gms_internal_zzob.zzd(zzjb.class);
        if (com_google_android_gms_internal_zzjb != null) {
            zza(hashMap, "t", com_google_android_gms_internal_zzjb.zzhK());
            zza(hashMap, "cid", com_google_android_gms_internal_zzjb.getClientId());
            zza(hashMap, "uid", com_google_android_gms_internal_zzjb.getUserId());
            zza(hashMap, "sc", com_google_android_gms_internal_zzjb.zzhN());
            zza(hashMap, "sf", com_google_android_gms_internal_zzjb.zzhP());
            zza(hashMap, "ni", com_google_android_gms_internal_zzjb.zzhO());
            zza(hashMap, "adid", com_google_android_gms_internal_zzjb.zzhL());
            zza(hashMap, "ate", com_google_android_gms_internal_zzjb.zzhM());
        }
        zzoq com_google_android_gms_internal_zzoq = (zzoq) com_google_android_gms_internal_zzob.zzd(zzoq.class);
        if (com_google_android_gms_internal_zzoq != null) {
            zza(hashMap, "cd", com_google_android_gms_internal_zzoq.zzxT());
            zza(hashMap, "a", (double) com_google_android_gms_internal_zzoq.zzbp());
            zza(hashMap, "dr", com_google_android_gms_internal_zzoq.zzxW());
        }
        zzoo com_google_android_gms_internal_zzoo = (zzoo) com_google_android_gms_internal_zzob.zzd(zzoo.class);
        if (com_google_android_gms_internal_zzoo != null) {
            zza(hashMap, "ec", com_google_android_gms_internal_zzoo.zzxQ());
            zza(hashMap, "ea", com_google_android_gms_internal_zzoo.getAction());
            zza(hashMap, "el", com_google_android_gms_internal_zzoo.getLabel());
            zza(hashMap, "ev", (double) com_google_android_gms_internal_zzoo.getValue());
        }
        zzol com_google_android_gms_internal_zzol = (zzol) com_google_android_gms_internal_zzob.zzd(zzol.class);
        if (com_google_android_gms_internal_zzol != null) {
            zza(hashMap, "cn", com_google_android_gms_internal_zzol.getName());
            zza(hashMap, "cs", com_google_android_gms_internal_zzol.getSource());
            zza(hashMap, "cm", com_google_android_gms_internal_zzol.zzxB());
            zza(hashMap, "ck", com_google_android_gms_internal_zzol.zzxC());
            zza(hashMap, "cc", com_google_android_gms_internal_zzol.getContent());
            zza(hashMap, "ci", com_google_android_gms_internal_zzol.getId());
            zza(hashMap, "anid", com_google_android_gms_internal_zzol.zzxD());
            zza(hashMap, "gclid", com_google_android_gms_internal_zzol.zzxE());
            zza(hashMap, "dclid", com_google_android_gms_internal_zzol.zzxF());
            zza(hashMap, "aclid", com_google_android_gms_internal_zzol.zzxG());
        }
        zzop com_google_android_gms_internal_zzop = (zzop) com_google_android_gms_internal_zzob.zzd(zzop.class);
        if (com_google_android_gms_internal_zzop != null) {
            zza(hashMap, "exd", com_google_android_gms_internal_zzop.getDescription());
            zza(hashMap, "exf", com_google_android_gms_internal_zzop.zzxR());
        }
        zzor com_google_android_gms_internal_zzor = (zzor) com_google_android_gms_internal_zzob.zzd(zzor.class);
        if (com_google_android_gms_internal_zzor != null) {
            zza(hashMap, "sn", com_google_android_gms_internal_zzor.zzya());
            zza(hashMap, "sa", com_google_android_gms_internal_zzor.getAction());
            zza(hashMap, "st", com_google_android_gms_internal_zzor.getTarget());
        }
        zzos com_google_android_gms_internal_zzos = (zzos) com_google_android_gms_internal_zzob.zzd(zzos.class);
        if (com_google_android_gms_internal_zzos != null) {
            zza(hashMap, "utv", com_google_android_gms_internal_zzos.zzyb());
            zza(hashMap, "utt", (double) com_google_android_gms_internal_zzos.getTimeInMillis());
            zza(hashMap, "utc", com_google_android_gms_internal_zzos.zzxQ());
            zza(hashMap, "utl", com_google_android_gms_internal_zzos.getLabel());
        }
        zziy com_google_android_gms_internal_zziy = (zziy) com_google_android_gms_internal_zzob.zzd(zziy.class);
        if (com_google_android_gms_internal_zziy != null) {
            for (Entry entry2 : com_google_android_gms_internal_zziy.zzhH().entrySet()) {
                zzP = zzc.zzP(((Integer) entry2.getKey()).intValue());
                if (!TextUtils.isEmpty(zzP)) {
                    hashMap.put(zzP, entry2.getValue());
                }
            }
        }
        zziz com_google_android_gms_internal_zziz = (zziz) com_google_android_gms_internal_zzob.zzd(zziz.class);
        if (com_google_android_gms_internal_zziz != null) {
            for (Entry entry22 : com_google_android_gms_internal_zziz.zzhI().entrySet()) {
                zzP = zzc.zzR(((Integer) entry22.getKey()).intValue());
                if (!TextUtils.isEmpty(zzP)) {
                    hashMap.put(zzP, zzb(((Double) entry22.getValue()).doubleValue()));
                }
            }
        }
        zzon com_google_android_gms_internal_zzon = (zzon) com_google_android_gms_internal_zzob.zzd(zzon.class);
        if (com_google_android_gms_internal_zzon != null) {
            ProductAction zzxM = com_google_android_gms_internal_zzon.zzxM();
            if (zzxM != null) {
                for (Entry entry3 : zzxM.build().entrySet()) {
                    if (((String) entry3.getKey()).startsWith("&")) {
                        hashMap.put(((String) entry3.getKey()).substring(1), entry3.getValue());
                    } else {
                        hashMap.put(entry3.getKey(), entry3.getValue());
                    }
                }
            }
            int i = 1;
            for (Promotion zzaV : com_google_android_gms_internal_zzon.zzxP()) {
                hashMap.putAll(zzaV.zzaV(zzc.zzV(i)));
                i++;
            }
            i = 1;
            for (Product zzaV2 : com_google_android_gms_internal_zzon.zzxN()) {
                hashMap.putAll(zzaV2.zzaV(zzc.zzT(i)));
                i++;
            }
            i = 1;
            for (Entry entry222 : com_google_android_gms_internal_zzon.zzxO().entrySet()) {
                List<Product> list = (List) entry222.getValue();
                String zzY = zzc.zzY(i);
                int i2 = 1;
                for (Product zzaV22 : list) {
                    hashMap.putAll(zzaV22.zzaV(zzY + zzc.zzW(i2)));
                    i2++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry222.getKey())) {
                    hashMap.put(zzY + "nm", entry222.getKey());
                }
                i++;
            }
        }
        zzom com_google_android_gms_internal_zzom = (zzom) com_google_android_gms_internal_zzob.zzd(zzom.class);
        if (com_google_android_gms_internal_zzom != null) {
            zza(hashMap, "ul", com_google_android_gms_internal_zzom.getLanguage());
            zza(hashMap, "sd", (double) com_google_android_gms_internal_zzom.zzxH());
            zza(hashMap, "sr", com_google_android_gms_internal_zzom.zzxI(), com_google_android_gms_internal_zzom.zzxJ());
            zza(hashMap, "vp", com_google_android_gms_internal_zzom.zzxK(), com_google_android_gms_internal_zzom.zzxL());
        }
        zzok com_google_android_gms_internal_zzok = (zzok) com_google_android_gms_internal_zzob.zzd(zzok.class);
        if (com_google_android_gms_internal_zzok != null) {
            zza(hashMap, "an", com_google_android_gms_internal_zzok.zzjZ());
            zza(hashMap, "aid", com_google_android_gms_internal_zzok.zztW());
            zza(hashMap, "aiid", com_google_android_gms_internal_zzok.zzxA());
            zza(hashMap, "av", com_google_android_gms_internal_zzok.zzkb());
        }
        return hashMap;
    }

    private static String zzh(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            return TextUtils.isEmpty(str) ? null : str;
        } else if (!(obj instanceof Double)) {
            return obj instanceof Boolean ? obj != Boolean.FALSE ? "1" : null : String.valueOf(obj);
        } else {
            Double d = (Double) obj;
            return d.doubleValue() != 0.0d ? zzb(d.doubleValue()) : null;
        }
    }

    private static String zzy(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String) entry.getValue());
        }
        return stringBuilder.toString();
    }

    public void zzb(zzob com_google_android_gms_internal_zzob) {
        zzx.zzv(com_google_android_gms_internal_zzob);
        zzx.zzb(com_google_android_gms_internal_zzob.zzxm(), (Object) "Can't deliver not submitted measurement");
        zzx.zzci("deliver should be called on worker thread");
        zzob zzxh = com_google_android_gms_internal_zzob.zzxh();
        zzjb com_google_android_gms_internal_zzjb = (zzjb) zzxh.zze(zzjb.class);
        if (TextUtils.isEmpty(com_google_android_gms_internal_zzjb.zzhK())) {
            zzie().zzh(zzc(zzxh), "Ignoring measurement without type");
        } else if (TextUtils.isEmpty(com_google_android_gms_internal_zzjb.getClientId())) {
            zzie().zzh(zzc(zzxh), "Ignoring measurement without client id");
        } else if (!this.zzKa.zzis().getAppOptOut()) {
            double zzhP = com_google_android_gms_internal_zzjb.zzhP();
            if (zzam.zza(zzhP, com_google_android_gms_internal_zzjb.getClientId())) {
                zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(zzhP));
                return;
            }
            Map zzc = zzc(zzxh);
            zzc.put("v", "1");
            zzc.put("_v", zze.zzLB);
            zzc.put("tid", this.zztw);
            if (this.zzKa.zzis().isDryRunEnabled()) {
                zzc("Dry run is enabled. GoogleAnalytics would have sent", zzy(zzc));
                return;
            }
            Map hashMap = new HashMap();
            zzam.zzb(hashMap, "uid", com_google_android_gms_internal_zzjb.getUserId());
            zzok com_google_android_gms_internal_zzok = (zzok) com_google_android_gms_internal_zzob.zzd(zzok.class);
            if (com_google_android_gms_internal_zzok != null) {
                zzam.zzb(hashMap, "an", com_google_android_gms_internal_zzok.zzjZ());
                zzam.zzb(hashMap, "aid", com_google_android_gms_internal_zzok.zztW());
                zzam.zzb(hashMap, "av", com_google_android_gms_internal_zzok.zzkb());
                zzam.zzb(hashMap, "aiid", com_google_android_gms_internal_zzok.zzxA());
            }
            zzc.put("_s", String.valueOf(zzhz().zza(new zzh(0, com_google_android_gms_internal_zzjb.getClientId(), this.zztw, !TextUtils.isEmpty(com_google_android_gms_internal_zzjb.zzhL()), 0, hashMap))));
            zzhz().zza(new zzab(zzie(), zzc, com_google_android_gms_internal_zzob.zzxk(), true));
        }
    }

    public Uri zzhs() {
        return this.zzKl;
    }
}
