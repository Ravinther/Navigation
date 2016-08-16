package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.SearchAdRequestParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.appdatasearch.DocumentContents;
import com.google.android.gms.appdatasearch.DocumentSection;
import com.google.android.gms.appdatasearch.GetRecentContextCall.Request;
import com.google.android.gms.appdatasearch.GetRecentContextCall.Response;
import com.google.android.gms.appdatasearch.UsageInfo;
import com.google.android.gms.appindexing.AndroidAppUri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.internal.zzgt.zza;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import loquendo.tts.engine.TTSConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public final class zzgn {
    private static final SimpleDateFormat zzEY;

    static {
        zzEY = new SimpleDateFormat("yyyyMMdd", Locale.US);
    }

    private static String zzH(int i) {
        return String.format(Locale.US, "#%06x", new Object[]{Integer.valueOf(16777215 & i)});
    }

    public static AdResponseParcel zza(Context context, AdRequestInfoParcel adRequestInfoParcel, String str) {
        try {
            String str2;
            AdResponseParcel adResponseParcel;
            int i;
            List list;
            List list2;
            List list3;
            long j;
            String optString;
            String str3;
            boolean optBoolean;
            JSONObject jSONObject = new JSONObject(str);
            String optString2 = jSONObject.optString("ad_base_url", null);
            Object optString3 = jSONObject.optString("ad_url", null);
            String optString4 = jSONObject.optString("ad_size", null);
            boolean z = (adRequestInfoParcel == null || adRequestInfoParcel.zzDE == 0) ? false : true;
            CharSequence optString5 = z ? jSONObject.optString("ad_json", null) : jSONObject.optString("ad_html", null);
            long j2 = -1;
            String optString6 = jSONObject.optString("debug_dialog", null);
            long j3 = jSONObject.has("interstitial_timeout") ? (long) (jSONObject.getDouble("interstitial_timeout") * 1000.0d) : -1;
            String optString7 = jSONObject.optString("orientation", null);
            int i2 = -1;
            if ("portrait".equals(optString7)) {
                i2 = zzp.zzbz().zzgw();
            } else if ("landscape".equals(optString7)) {
                i2 = zzp.zzbz().zzgv();
            }
            if (TextUtils.isEmpty(optString5)) {
                if (TextUtils.isEmpty(optString3)) {
                    zzb.zzaE("Could not parse the mediation config: Missing required " + (z ? "ad_json" : "ad_html") + " or " + "ad_url" + " field.");
                    return new AdResponseParcel(0);
                }
                AdResponseParcel zza = zzgm.zza(adRequestInfoParcel, context, adRequestInfoParcel.zzqb.zzIz, optString3, null, null, null, null);
                optString2 = zza.zzAT;
                str2 = zza.body;
                j2 = zza.zzEb;
                adResponseParcel = zza;
            } else if (TextUtils.isEmpty(optString2)) {
                zzb.zzaE("Could not parse the mediation config: Missing required ad_base_url field");
                return new AdResponseParcel(0);
            } else {
                adResponseParcel = null;
                CharSequence charSequence = optString5;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("click_urls");
            List list4 = adResponseParcel == null ? null : adResponseParcel.zzyw;
            if (optJSONArray != null) {
                if (list4 == null) {
                    list4 = new LinkedList();
                }
                for (i = 0; i < optJSONArray.length(); i++) {
                    list4.add(optJSONArray.getString(i));
                }
                list = list4;
            } else {
                list = list4;
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("impression_urls");
            list4 = adResponseParcel == null ? null : adResponseParcel.zzyx;
            if (optJSONArray2 != null) {
                if (list4 == null) {
                    list4 = new LinkedList();
                }
                for (i = 0; i < optJSONArray2.length(); i++) {
                    list4.add(optJSONArray2.getString(i));
                }
                list2 = list4;
            } else {
                list2 = list4;
            }
            JSONArray optJSONArray3 = jSONObject.optJSONArray("manual_impression_urls");
            list4 = adResponseParcel == null ? null : adResponseParcel.zzDZ;
            if (optJSONArray3 != null) {
                if (list4 == null) {
                    list4 = new LinkedList();
                }
                for (i = 0; i < optJSONArray3.length(); i++) {
                    list4.add(optJSONArray3.getString(i));
                }
                list3 = list4;
            } else {
                list3 = list4;
            }
            if (adResponseParcel != null) {
                if (adResponseParcel.orientation != -1) {
                    i2 = adResponseParcel.orientation;
                }
                if (adResponseParcel.zzDW > 0) {
                    j = adResponseParcel.zzDW;
                    optString = jSONObject.optString("active_view");
                    str3 = null;
                    optBoolean = jSONObject.optBoolean("ad_is_javascript", false);
                    if (optBoolean) {
                        str3 = jSONObject.optString("ad_passback_url", null);
                    }
                    return new AdResponseParcel(adRequestInfoParcel, optString2, str2, list, list2, j, jSONObject.optBoolean("mediation", false), jSONObject.optLong("mediation_config_cache_time_milliseconds", -1), list3, jSONObject.optLong("refresh_interval_milliseconds", -1), i2, optString4, j2, optString6, optBoolean, str3, optString, jSONObject.optBoolean("custom_render_allowed", false), z, adRequestInfoParcel.zzDG, jSONObject.optBoolean("content_url_opted_out", true), jSONObject.optBoolean("prefetch", false), jSONObject.optInt("oauth2_token_status", 0));
                }
            }
            j = j3;
            optString = jSONObject.optString("active_view");
            str3 = null;
            optBoolean = jSONObject.optBoolean("ad_is_javascript", false);
            if (optBoolean) {
                str3 = jSONObject.optString("ad_passback_url", null);
            }
            return new AdResponseParcel(adRequestInfoParcel, optString2, str2, list, list2, j, jSONObject.optBoolean("mediation", false), jSONObject.optLong("mediation_config_cache_time_milliseconds", -1), list3, jSONObject.optLong("refresh_interval_milliseconds", -1), i2, optString4, j2, optString6, optBoolean, str3, optString, jSONObject.optBoolean("custom_render_allowed", false), z, adRequestInfoParcel.zzDG, jSONObject.optBoolean("content_url_opted_out", true), jSONObject.optBoolean("prefetch", false), jSONObject.optInt("oauth2_token_status", 0));
        } catch (JSONException e) {
            zzb.zzaE("Could not parse the mediation config: " + e.getMessage());
            return new AdResponseParcel(0);
        }
    }

    public static JSONObject zza(Context context, AdRequestInfoParcel adRequestInfoParcel, zzgr com_google_android_gms_internal_zzgr, zza com_google_android_gms_internal_zzgt_zza, Location location, zzbr com_google_android_gms_internal_zzbr, String str, String str2, List<String> list) {
        try {
            HashMap hashMap = new HashMap();
            if (list.size() > 0) {
                hashMap.put("eid", TextUtils.join(",", list));
            }
            if (adRequestInfoParcel.zzDx != null) {
                hashMap.put("ad_pos", adRequestInfoParcel.zzDx);
            }
            zza(hashMap, adRequestInfoParcel.zzDy);
            hashMap.put("format", adRequestInfoParcel.zzqf.zzsG);
            if (adRequestInfoParcel.zzqf.width == -1) {
                hashMap.put("smart_w", "full");
            }
            if (adRequestInfoParcel.zzqf.height == -2) {
                hashMap.put("smart_h", "auto");
            }
            if (adRequestInfoParcel.zzqf.zzsI != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (AdSizeParcel adSizeParcel : adRequestInfoParcel.zzqf.zzsI) {
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append("|");
                    }
                    stringBuilder.append(adSizeParcel.width == -1 ? (int) (((float) adSizeParcel.widthPixels) / com_google_android_gms_internal_zzgr.zzDK) : adSizeParcel.width);
                    stringBuilder.append("x");
                    stringBuilder.append(adSizeParcel.height == -2 ? (int) (((float) adSizeParcel.heightPixels) / com_google_android_gms_internal_zzgr.zzDK) : adSizeParcel.height);
                }
                hashMap.put("sz", stringBuilder);
            }
            if (adRequestInfoParcel.zzDE != 0) {
                hashMap.put("native_version", Integer.valueOf(adRequestInfoParcel.zzDE));
                hashMap.put("native_templates", adRequestInfoParcel.zzqv);
                hashMap.put("native_image_orientation", zzc(adRequestInfoParcel.zzqt));
                if (!adRequestInfoParcel.zzDS.isEmpty()) {
                    hashMap.put("native_custom_templates", adRequestInfoParcel.zzDS);
                }
            }
            hashMap.put("slotname", adRequestInfoParcel.zzpZ);
            hashMap.put("pn", adRequestInfoParcel.applicationInfo.packageName);
            if (adRequestInfoParcel.zzDz != null) {
                hashMap.put("vc", Integer.valueOf(adRequestInfoParcel.zzDz.versionCode));
            }
            hashMap.put("ms", str2);
            hashMap.put("seq_num", adRequestInfoParcel.zzDB);
            hashMap.put("session_id", adRequestInfoParcel.zzDC);
            hashMap.put("js", adRequestInfoParcel.zzqb.zzIz);
            zza(hashMap, com_google_android_gms_internal_zzgr, com_google_android_gms_internal_zzgt_zza);
            hashMap.put("fdz", Integer.valueOf(com_google_android_gms_internal_zzbr.zzdc()));
            hashMap.put("platform", Build.MANUFACTURER);
            hashMap.put("submodel", Build.MODEL);
            if (adRequestInfoParcel.zzDy.versionCode >= 2 && adRequestInfoParcel.zzDy.zzsy != null) {
                zza(hashMap, adRequestInfoParcel.zzDy.zzsy);
            }
            if (adRequestInfoParcel.versionCode >= 2) {
                hashMap.put("quality_signals", adRequestInfoParcel.zzDD);
            }
            if (adRequestInfoParcel.versionCode >= 4 && adRequestInfoParcel.zzDG) {
                hashMap.put("forceHttps", Boolean.valueOf(adRequestInfoParcel.zzDG));
            }
            if (adRequestInfoParcel.versionCode >= 4 && adRequestInfoParcel.zzDF != null) {
                Bundle bundle = adRequestInfoParcel.zzDF;
                zza(context, adRequestInfoParcel, bundle);
                hashMap.put("content_info", bundle);
            }
            if (adRequestInfoParcel.versionCode >= 5) {
                hashMap.put("u_sd", Float.valueOf(adRequestInfoParcel.zzDK));
                hashMap.put("sh", Integer.valueOf(adRequestInfoParcel.zzDJ));
                hashMap.put("sw", Integer.valueOf(adRequestInfoParcel.zzDI));
            } else {
                hashMap.put("u_sd", Float.valueOf(com_google_android_gms_internal_zzgr.zzDK));
                hashMap.put("sh", Integer.valueOf(com_google_android_gms_internal_zzgr.zzDJ));
                hashMap.put("sw", Integer.valueOf(com_google_android_gms_internal_zzgr.zzDI));
            }
            if (adRequestInfoParcel.versionCode >= 6) {
                if (!TextUtils.isEmpty(adRequestInfoParcel.zzDL)) {
                    try {
                        hashMap.put("view_hierarchy", new JSONObject(adRequestInfoParcel.zzDL));
                    } catch (Throwable e) {
                        zzb.zzd("Problem serializing view hierarchy to JSON", e);
                    }
                }
                if (((Boolean) zzby.zzuQ.get()).booleanValue() && adRequestInfoParcel.zzDM) {
                    hashMap.put("ga_hid", Integer.valueOf(adRequestInfoParcel.zzDN));
                    hashMap.put("ga_cid", adRequestInfoParcel.zzDO);
                }
                hashMap.put("correlation_id", Long.valueOf(adRequestInfoParcel.zzDP));
            }
            if (adRequestInfoParcel.versionCode >= 7) {
                hashMap.put("request_id", adRequestInfoParcel.zzDQ);
            }
            if (adRequestInfoParcel.versionCode >= 11 && adRequestInfoParcel.zzDU != null) {
                hashMap.put("capability", adRequestInfoParcel.zzDU.toBundle());
            }
            zza(hashMap, str);
            if (zzb.zzM(2)) {
                zzb.m1445v("Ad Request JSON: " + zzp.zzbx().zzx(hashMap).toString(2));
            }
            return zzp.zzbx().zzx(hashMap);
        } catch (JSONException e2) {
            zzb.zzaE("Problem serializing ad request to JSON: " + e2.getMessage());
            return null;
        }
    }

    static void zza(Context context, AdRequestInfoParcel adRequestInfoParcel, Bundle bundle) {
        if (!((Boolean) zzby.zzuK.get()).booleanValue()) {
            zzb.zzaD("App index is not enabled");
        } else if (!zzd.zzacF) {
            zzb.zzaD("Not on package side, return");
        } else if (zzk.zzcE().zzgI()) {
            zzb.zzaD("Cannot invoked on UI thread");
        } else if (adRequestInfoParcel == null || adRequestInfoParcel.zzDz == null) {
            zzb.zzaE("Invalid ad request info");
        } else {
            String str = adRequestInfoParcel.zzDz.packageName;
            if (TextUtils.isEmpty(str)) {
                zzb.zzaE("Fail to get package name");
                return;
            }
            try {
                zza(zzd(context, str), str, bundle);
            } catch (RuntimeException e) {
                zzb.zzaD("Fail to add app index to content info");
            }
        }
    }

    static void zza(UsageInfo usageInfo, String str, Bundle bundle) {
        if (usageInfo != null && usageInfo.zzle() != null) {
            DocumentContents zzle = usageInfo.zzle();
            Object zzkX = zzle.zzkX();
            if (!TextUtils.isEmpty(zzkX)) {
                bundle.putString("web_url", zzkX);
            }
            try {
                DocumentSection zzbu = zzle.zzbu("intent_data");
                if (zzbu != null && !TextUtils.isEmpty(zzbu.zzPe)) {
                    bundle.putString("app_uri", AndroidAppUri.newAndroidAppUri(str, Uri.parse(zzbu.zzPe)).toString());
                }
            } catch (IllegalArgumentException e) {
                zzb.zzaE("Failed to parse the third-party Android App URI");
            }
        }
    }

    private static void zza(HashMap<String, Object> hashMap, Location location) {
        HashMap hashMap2 = new HashMap();
        Float valueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long valueOf2 = Long.valueOf(location.getTime() * 1000);
        Long valueOf3 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long valueOf4 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        hashMap2.put("radius", valueOf);
        hashMap2.put("lat", valueOf3);
        hashMap2.put("long", valueOf4);
        hashMap2.put("time", valueOf2);
        hashMap.put("uule", hashMap2);
    }

    private static void zza(HashMap<String, Object> hashMap, AdRequestParcel adRequestParcel) {
        String zzgm = zzhp.zzgm();
        if (zzgm != null) {
            hashMap.put("abf", zzgm);
        }
        if (adRequestParcel.zzsq != -1) {
            hashMap.put("cust_age", zzEY.format(new Date(adRequestParcel.zzsq)));
        }
        if (adRequestParcel.extras != null) {
            hashMap.put("extras", adRequestParcel.extras);
        }
        if (adRequestParcel.zzsr != -1) {
            hashMap.put("cust_gender", Integer.valueOf(adRequestParcel.zzsr));
        }
        if (adRequestParcel.zzss != null) {
            hashMap.put("kw", adRequestParcel.zzss);
        }
        if (adRequestParcel.zzsu != -1) {
            hashMap.put("tag_for_child_directed_treatment", Integer.valueOf(adRequestParcel.zzsu));
        }
        if (adRequestParcel.zzst) {
            hashMap.put("adtest", "on");
        }
        if (adRequestParcel.versionCode >= 2) {
            if (adRequestParcel.zzsv) {
                hashMap.put("d_imp_hdr", Integer.valueOf(1));
            }
            if (!TextUtils.isEmpty(adRequestParcel.zzsw)) {
                hashMap.put("ppid", adRequestParcel.zzsw);
            }
            if (adRequestParcel.zzsx != null) {
                zza((HashMap) hashMap, adRequestParcel.zzsx);
            }
        }
        if (adRequestParcel.versionCode >= 3 && adRequestParcel.zzsz != null) {
            hashMap.put("url", adRequestParcel.zzsz);
        }
        if (adRequestParcel.versionCode >= 5) {
            if (adRequestParcel.zzsB != null) {
                hashMap.put("custom_targeting", adRequestParcel.zzsB);
            }
            if (adRequestParcel.zzsC != null) {
                hashMap.put("category_exclusions", adRequestParcel.zzsC);
            }
            if (adRequestParcel.zzsD != null) {
                hashMap.put("request_agent", adRequestParcel.zzsD);
            }
        }
        if (adRequestParcel.versionCode >= 6 && adRequestParcel.zzsE != null) {
            hashMap.put("request_pkg", adRequestParcel.zzsE);
        }
    }

    private static void zza(HashMap<String, Object> hashMap, SearchAdRequestParcel searchAdRequestParcel) {
        Object obj;
        Object obj2 = null;
        if (Color.alpha(searchAdRequestParcel.zztA) != 0) {
            hashMap.put("acolor", zzH(searchAdRequestParcel.zztA));
        }
        if (Color.alpha(searchAdRequestParcel.backgroundColor) != 0) {
            hashMap.put("bgcolor", zzH(searchAdRequestParcel.backgroundColor));
        }
        if (!(Color.alpha(searchAdRequestParcel.zztB) == 0 || Color.alpha(searchAdRequestParcel.zztC) == 0)) {
            hashMap.put("gradientto", zzH(searchAdRequestParcel.zztB));
            hashMap.put("gradientfrom", zzH(searchAdRequestParcel.zztC));
        }
        if (Color.alpha(searchAdRequestParcel.zztD) != 0) {
            hashMap.put("bcolor", zzH(searchAdRequestParcel.zztD));
        }
        hashMap.put("bthick", Integer.toString(searchAdRequestParcel.zztE));
        switch (searchAdRequestParcel.zztF) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                obj = "none";
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                obj = "dashed";
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                obj = "dotted";
                break;
            case TTSConst.TTSUNICODE /*3*/:
                obj = "solid";
                break;
            default:
                obj = null;
                break;
        }
        if (obj != null) {
            hashMap.put("btype", obj);
        }
        switch (searchAdRequestParcel.zztG) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                obj2 = "light";
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                obj2 = "medium";
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                obj2 = "dark";
                break;
        }
        if (obj2 != null) {
            hashMap.put("callbuttoncolor", obj2);
        }
        if (searchAdRequestParcel.zztH != null) {
            hashMap.put("channel", searchAdRequestParcel.zztH);
        }
        if (Color.alpha(searchAdRequestParcel.zztI) != 0) {
            hashMap.put("dcolor", zzH(searchAdRequestParcel.zztI));
        }
        if (searchAdRequestParcel.zztJ != null) {
            hashMap.put("font", searchAdRequestParcel.zztJ);
        }
        if (Color.alpha(searchAdRequestParcel.zztK) != 0) {
            hashMap.put("hcolor", zzH(searchAdRequestParcel.zztK));
        }
        hashMap.put("headersize", Integer.toString(searchAdRequestParcel.zztL));
        if (searchAdRequestParcel.zztM != null) {
            hashMap.put("q", searchAdRequestParcel.zztM);
        }
    }

    private static void zza(HashMap<String, Object> hashMap, zzgr com_google_android_gms_internal_zzgr, zza com_google_android_gms_internal_zzgt_zza) {
        hashMap.put("am", Integer.valueOf(com_google_android_gms_internal_zzgr.zzFB));
        hashMap.put("cog", zzx(com_google_android_gms_internal_zzgr.zzFC));
        hashMap.put("coh", zzx(com_google_android_gms_internal_zzgr.zzFD));
        if (!TextUtils.isEmpty(com_google_android_gms_internal_zzgr.zzFE)) {
            hashMap.put("carrier", com_google_android_gms_internal_zzgr.zzFE);
        }
        hashMap.put("gl", com_google_android_gms_internal_zzgr.zzFF);
        if (com_google_android_gms_internal_zzgr.zzFG) {
            hashMap.put("simulator", Integer.valueOf(1));
        }
        if (com_google_android_gms_internal_zzgr.zzFH) {
            hashMap.put("is_sidewinder", Integer.valueOf(1));
        }
        hashMap.put("ma", zzx(com_google_android_gms_internal_zzgr.zzFI));
        hashMap.put("sp", zzx(com_google_android_gms_internal_zzgr.zzFJ));
        hashMap.put("hl", com_google_android_gms_internal_zzgr.zzFK);
        if (!TextUtils.isEmpty(com_google_android_gms_internal_zzgr.zzFL)) {
            hashMap.put("mv", com_google_android_gms_internal_zzgr.zzFL);
        }
        hashMap.put("muv", Integer.valueOf(com_google_android_gms_internal_zzgr.zzFM));
        if (com_google_android_gms_internal_zzgr.zzFN != -2) {
            hashMap.put("cnt", Integer.valueOf(com_google_android_gms_internal_zzgr.zzFN));
        }
        hashMap.put("gnt", Integer.valueOf(com_google_android_gms_internal_zzgr.zzFO));
        hashMap.put("pt", Integer.valueOf(com_google_android_gms_internal_zzgr.zzFP));
        hashMap.put("rm", Integer.valueOf(com_google_android_gms_internal_zzgr.zzFQ));
        hashMap.put("riv", Integer.valueOf(com_google_android_gms_internal_zzgr.zzFR));
        Bundle bundle = new Bundle();
        bundle.putString("build", com_google_android_gms_internal_zzgr.zzFW);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("is_charging", com_google_android_gms_internal_zzgr.zzFT);
        bundle2.putDouble("battery_level", com_google_android_gms_internal_zzgr.zzFS);
        bundle.putBundle("battery", bundle2);
        bundle2 = new Bundle();
        bundle2.putInt("active_network_state", com_google_android_gms_internal_zzgr.zzFV);
        bundle2.putBoolean("active_network_metered", com_google_android_gms_internal_zzgr.zzFU);
        if (com_google_android_gms_internal_zzgt_zza != null) {
            Bundle bundle3 = new Bundle();
            bundle3.putInt("predicted_latency_micros", com_google_android_gms_internal_zzgt_zza.zzGb);
            bundle3.putLong("predicted_down_throughput_bps", com_google_android_gms_internal_zzgt_zza.zzGc);
            bundle3.putLong("predicted_up_throughput_bps", com_google_android_gms_internal_zzgt_zza.zzGd);
            bundle2.putBundle("predictions", bundle3);
        }
        bundle.putBundle("network", bundle2);
        hashMap.put("device", bundle);
    }

    private static void zza(HashMap<String, Object> hashMap, String str) {
        if (str != null) {
            Map hashMap2 = new HashMap();
            hashMap2.put("token", str);
            hashMap.put("pan", hashMap2);
        }
    }

    private static String zzc(NativeAdOptionsParcel nativeAdOptionsParcel) {
        switch (nativeAdOptionsParcel != null ? nativeAdOptionsParcel.zzwo : 0) {
            case TTSConst.TTSMULTILINE /*1*/:
                return "portrait";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "landscape";
            default:
                return "any";
        }
    }

    private static UsageInfo zzd(Context context, String str) {
        UsageInfo usageInfo;
        GoogleApiClient googleApiClient;
        Throwable th;
        GoogleApiClient build;
        try {
            build = new Builder(context).addApi(com.google.android.gms.appdatasearch.zza.zzOQ).build();
            try {
                build.connect();
                Response response = (Response) com.google.android.gms.appdatasearch.zza.zzOR.zza(build, new Request.zza().zzL(true).zzbw(str).zzlb()).await(1, TimeUnit.SECONDS);
                if (response == null || !response.getStatus().isSuccess()) {
                    zzb.zzaD("Fail to obtain recent context call");
                    if (build == null) {
                        return null;
                    }
                    build.disconnect();
                    return null;
                } else if (response.zzPw == null || response.zzPw.isEmpty()) {
                    zzb.zzaD("Fail to obtain recent context");
                    if (build == null) {
                        return null;
                    }
                    build.disconnect();
                    return null;
                } else {
                    usageInfo = (UsageInfo) response.zzPw.get(0);
                    if (build != null) {
                        build.disconnect();
                    }
                    return usageInfo;
                }
            } catch (SecurityException e) {
                googleApiClient = build;
                try {
                    zzb.zzaE("Fail to get recent context");
                    if (googleApiClient == null) {
                        googleApiClient.disconnect();
                        usageInfo = null;
                    } else {
                        usageInfo = null;
                    }
                    return usageInfo;
                } catch (Throwable th2) {
                    build = googleApiClient;
                    th = th2;
                    if (build != null) {
                        build.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (build != null) {
                    build.disconnect();
                }
                throw th;
            }
        } catch (SecurityException e2) {
            googleApiClient = null;
            zzb.zzaE("Fail to get recent context");
            if (googleApiClient == null) {
                usageInfo = null;
            } else {
                googleApiClient.disconnect();
                usageInfo = null;
            }
            return usageInfo;
        } catch (Throwable th4) {
            th = th4;
            build = null;
            if (build != null) {
                build.disconnect();
            }
            throw th;
        }
    }

    private static Integer zzx(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }
}
