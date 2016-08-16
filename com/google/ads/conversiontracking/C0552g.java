package com.google.ads.conversiontracking;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import com.google.ads.conversiontracking.C0556i.C0555a;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import loquendo.tts.engine.TTSConst;

@TargetApi(4)
/* renamed from: com.google.ads.conversiontracking.g */
public class C0552g {
    private static final Map<String, String> f1204a;
    private static boolean f1205b;
    private static long f1206c;
    private static boolean f1207d;
    private static boolean f1208e;
    private static final Object f1209f;
    private static C0543e f1210g;
    private static boolean f1211h;

    /* renamed from: com.google.ads.conversiontracking.g.1 */
    class C05461 implements Runnable {
        final /* synthetic */ SharedPreferences f1177a;
        final /* synthetic */ List f1178b;
        final /* synthetic */ C0549b f1179c;
        final /* synthetic */ String f1180d;

        C05461(SharedPreferences sharedPreferences, List list, C0549b c0549b, String str) {
            this.f1177a = sharedPreferences;
            this.f1178b = list;
            this.f1179c = c0549b;
            this.f1180d = str;
        }

        public void run() {
            Editor edit = this.f1177a.edit();
            for (String remove : this.f1178b) {
                edit.remove(remove);
            }
            edit.putString(this.f1179c.f1185a, this.f1180d);
            edit.commit();
        }
    }

    /* renamed from: com.google.ads.conversiontracking.g.2 */
    static /* synthetic */ class C05472 {
        static final /* synthetic */ int[] f1181a;

        static {
            f1181a = new int[C0551d.m1380a().length];
            try {
                f1181a[C0551d.DOUBLECLICK_CONVERSION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1181a[C0551d.IAP_CONVERSION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1181a[C0551d.GOOGLE_CONVERSION.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.google.ads.conversiontracking.g.a */
    public static class C0548a {
        private final String f1182a;
        private final String f1183b;
        private final long f1184c;

        private C0548a(String str, String str2, long j) {
            this.f1182a = str;
            this.f1183b = str2;
            this.f1184c = j;
        }

        public C0548a(String str, String str2) {
            this(str, str2, C0552g.m1381a());
        }

        public boolean m1356a() {
            return this.f1184c + 7776000000L < C0552g.m1381a();
        }

        public static C0548a m1352a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            String[] split = str.split(" ");
            if (split.length != 3) {
                return null;
            }
            try {
                C0548a c0548a = new C0548a(split[0], split[1], Long.parseLong(split[2]));
                if (c0548a.m1356a()) {
                    return null;
                }
                return c0548a;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /* renamed from: com.google.ads.conversiontracking.g.b */
    public static class C0549b {
        private final String f1185a;
        private final C0548a f1186b;

        public C0549b(String str, C0548a c0548a) {
            this.f1185a = str;
            this.f1186b = c0548a;
        }
    }

    /* renamed from: com.google.ads.conversiontracking.g.c */
    public static class C0550c {
        private String f1187a;
        private boolean f1188b;
        private boolean f1189c;
        private C0551d f1190d;
        private String f1191e;
        private String f1192f;
        private String f1193g;
        private C0548a f1194h;
        private Map<String, ?> f1195i;
        private String f1196j;
        private long f1197k;
        private boolean f1198l;

        public C0550c m1374a(String str) {
            this.f1187a = str;
            return this;
        }

        public C0550c m1373a(C0551d c0551d) {
            this.f1190d = c0551d;
            return this;
        }

        public C0550c m1377b(String str) {
            this.f1191e = str;
            return this;
        }

        public C0550c m1378c(String str) {
            this.f1192f = str;
            return this;
        }

        public C0550c m1379d(String str) {
            this.f1193g = str;
            return this;
        }

        public C0550c m1372a(C0548a c0548a) {
            this.f1194h = c0548a;
            return this;
        }

        public C0550c m1375a(boolean z) {
            this.f1188b = z;
            return this;
        }

        public C0550c m1371a(long j) {
            this.f1197k = TimeUnit.MILLISECONDS.toSeconds(j);
            return this;
        }

        public C0550c m1376b() {
            this.f1198l = true;
            return this;
        }
    }

    /* renamed from: com.google.ads.conversiontracking.g.d */
    public enum C0551d {
        DOUBLECLICK_AUDIENCE,
        DOUBLECLICK_CONVERSION,
        GOOGLE_CONVERSION,
        IAP_CONVERSION;

        public static C0551d[] m1380a() {
            return (C0551d[]) f1203e.clone();
        }
    }

    static {
        f1204a = new HashMap();
        f1205b = false;
        f1206c = -1;
        f1207d = true;
        f1208e = false;
        f1209f = new Object();
        f1210g = null;
        f1211h = false;
    }

    public static C0543e m1382a(Context context) {
        C0543e c0543e;
        synchronized (f1209f) {
            if (f1210g == null) {
                f1210g = new C0543e(context);
            }
            c0543e = f1210g;
        }
        return c0543e;
    }

    public static boolean m1399a(Context context, C0550c c0550c, boolean z) {
        return C0552g.m1400a(context, C0552g.m1389a(c0550c), C0552g.m1402b(c0550c), z);
    }

    public static boolean m1400a(Context context, String str, String str2, boolean z) {
        if (f1205b && f1208e) {
            return f1207d;
        }
        if (z) {
            return true;
        }
        boolean z2;
        boolean z3 = context.getSharedPreferences(str, 0).getBoolean(str2, false);
        if (z3) {
            String str3 = "GoogleConversionReporter";
            String str4 = "Already sent ping for conversion ";
            String valueOf = String.valueOf(str2);
            Log.i(str3, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
        }
        if (z3) {
            z2 = false;
        } else {
            z2 = true;
        }
        return z2;
    }

    public static long m1401b(Context context) {
        return context.getSharedPreferences("google_conversion", 0).getLong("last_retry_time", 0);
    }

    public static void m1394a(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putBoolean(str2, true);
        edit.commit();
    }

    public static void m1405c(Context context) {
        Editor edit = context.getSharedPreferences("google_conversion", 0).edit();
        edit.putLong("last_retry_time", C0552g.m1381a());
        edit.commit();
    }

    public static String m1386a(Context context, C0550c c0550c) throws NoSuchAlgorithmException {
        return C0552g.m1387a(context, c0550c, new C0536a(context).m1318a());
    }

    public static String m1387a(Context context, C0550c c0550c, C0555a c0555a) throws NoSuchAlgorithmException {
        String packageName = context.getPackageName();
        String str = "";
        try {
            str = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (Throwable e) {
            Log.w("GoogleConversionReporter", "Error to retrieve app version", e);
        }
        String str2 = null;
        if (c0555a == null) {
            str2 = C0552g.m1407e(context);
        }
        if (!c0550c.f1189c && c0550c.f1190d == C0551d.DOUBLECLICK_CONVERSION) {
            return C0552g.m1391a(c0550c, packageName, str, c0555a, str2);
        }
        if (c0550c.f1190d == C0551d.DOUBLECLICK_AUDIENCE) {
            return C0552g.m1390a(c0550c, c0555a);
        }
        if (c0550c.f1190d == C0551d.IAP_CONVERSION) {
            return C0552g.m1404c(c0550c, packageName, str, c0555a, str2);
        }
        return C0552g.m1403b(c0550c, packageName, str, c0555a, str2);
    }

    private static void m1396a(Builder builder, boolean z, Map<String, ?> map) {
        if (z && map != null) {
            for (Entry entry : map.entrySet()) {
                String valueOf;
                if (entry.getValue() instanceof String) {
                    valueOf = String.valueOf("data.");
                    String valueOf2 = String.valueOf((String) entry.getKey());
                    builder.appendQueryParameter(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), (String) entry.getValue());
                } else if (entry.getValue() instanceof String[]) {
                    for (String str : (String[]) entry.getValue()) {
                        String valueOf3 = String.valueOf("data.");
                        valueOf = String.valueOf((String) entry.getKey());
                        builder.appendQueryParameter(valueOf.length() != 0 ? valueOf3.concat(valueOf) : new String(valueOf3), str);
                    }
                }
            }
        }
    }

    public static C0549b m1384a(Uri uri) {
        if (uri == null) {
            return null;
        }
        CharSequence queryParameter = uri.getQueryParameter("referrer");
        if (TextUtils.isEmpty(queryParameter)) {
            return null;
        }
        String str = "http://hostname/?";
        String valueOf = String.valueOf(queryParameter);
        Uri parse = Uri.parse(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        Object queryParameter2 = parse.getQueryParameter("conv");
        Object queryParameter3 = parse.getQueryParameter("gclid");
        if (TextUtils.isEmpty(queryParameter2) || TextUtils.isEmpty(queryParameter3)) {
            return null;
        }
        valueOf = parse.getQueryParameter("ai");
        if (valueOf == null) {
            valueOf = "";
        }
        return new C0549b(queryParameter2, new C0548a(queryParameter3, valueOf));
    }

    public static String m1388a(C0548a c0548a) {
        if (c0548a == null) {
            return "";
        }
        String valueOf;
        String valueOf2;
        if (TextUtils.isEmpty(c0548a.f1183b)) {
            valueOf = String.valueOf("&gclid=");
            valueOf2 = String.valueOf(c0548a.f1182a);
            return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else {
            valueOf2 = String.valueOf("&gclid=");
            valueOf = c0548a.f1182a;
            String valueOf3 = String.valueOf("ai");
            String a = c0548a.f1183b;
            return new StringBuilder((((String.valueOf(valueOf2).length() + 2) + String.valueOf(valueOf).length()) + String.valueOf(valueOf3).length()) + String.valueOf(a).length()).append(valueOf2).append(valueOf).append("&").append(valueOf3).append("=").append(a).toString();
        }
    }

    private static List<String> m1393a(SharedPreferences sharedPreferences) {
        List<String> arrayList = new ArrayList();
        for (Entry entry : sharedPreferences.getAll().entrySet()) {
            if (C0548a.m1352a((String) entry.getValue()) == null) {
                arrayList.add(entry.getKey());
            }
        }
        return arrayList;
    }

    public static boolean m1398a(Context context, C0549b c0549b) {
        if (c0549b == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("google_conversion_click_referrer", 0);
        List<String> a = C0552g.m1393a(sharedPreferences);
        if (sharedPreferences.getString(c0549b.f1185a, null) == null && sharedPreferences.getAll().size() == 100 && a.isEmpty()) {
            return false;
        }
        String b = c0549b.f1186b.f1182a;
        String valueOf = String.valueOf(" ");
        String a2 = c0549b.f1186b.f1183b;
        String valueOf2 = String.valueOf(" ");
        valueOf = new StringBuilder((((String.valueOf(b).length() + 20) + String.valueOf(valueOf).length()) + String.valueOf(a2).length()) + String.valueOf(valueOf2).length()).append(b).append(valueOf).append(a2).append(valueOf2).append(c0549b.f1186b.f1184c).toString();
        synchronized (f1204a) {
            for (String b2 : a) {
                f1204a.remove(b2);
            }
            f1204a.put(c0549b.f1185a, valueOf);
        }
        new Thread(new C05461(sharedPreferences, a, c0549b, valueOf)).start();
        return true;
    }

    public static C0548a m1383a(Context context, String str) {
        String str2;
        synchronized (f1204a) {
            str2 = (String) f1204a.get(str);
        }
        if (str2 == null) {
            str2 = context.getSharedPreferences("google_conversion_click_referrer", 0).getString(str, "");
        }
        return C0548a.m1352a(str2);
    }

    static String m1385a(long j) {
        return String.format(Locale.US, "%d.%03d", new Object[]{Long.valueOf(j / 1000), Long.valueOf(j % 1000)});
    }

    private static String m1392a(C0555a c0555a) {
        if (c0555a == null) {
            return null;
        }
        return c0555a.m1409b() ? "1" : "0";
    }

    private static void m1397a(StringBuilder stringBuilder, C0555a c0555a, String str) {
        String a = C0552g.m1392a(c0555a);
        if (a != null) {
            String valueOf = String.valueOf(";dc_lat=");
            a = String.valueOf(a);
            stringBuilder.append(a.length() != 0 ? valueOf.concat(a) : new String(valueOf));
        }
        if (c0555a == null) {
            valueOf = String.valueOf(";isu=");
            a = String.valueOf(str);
            stringBuilder.append(a.length() != 0 ? valueOf.concat(a) : new String(valueOf));
            return;
        }
        valueOf = String.valueOf(";dc_rdid=");
        a = String.valueOf(c0555a.m1408a());
        stringBuilder.append(a.length() != 0 ? valueOf.concat(a) : new String(valueOf));
    }

    private static void m1395a(Builder builder, C0555a c0555a, String str) {
        if (C0552g.m1392a(c0555a) != null) {
            builder.appendQueryParameter("lat", C0552g.m1392a(c0555a));
        }
        if (c0555a != null) {
            builder.appendQueryParameter("rdid", c0555a.m1408a());
        } else {
            builder.appendQueryParameter("muid", str);
        }
    }

    public static String m1391a(C0550c c0550c, String str, String str2, C0555a c0555a, String str3) {
        String valueOf = String.valueOf("https://pubads.g.doubleclick.net/activity;xsp=");
        String c = c0550c.f1187a;
        String valueOf2 = String.valueOf("ait");
        String valueOf3 = String.valueOf("bundleid");
        String valueOf4 = String.valueOf("appversion");
        String valueOf5 = String.valueOf("osversion");
        String valueOf6 = String.valueOf(VERSION.RELEASE);
        String valueOf7 = String.valueOf("sdkversion");
        String valueOf8 = String.valueOf("ct-sdk-a-v2.2.3");
        String valueOf9 = String.valueOf("timestamp");
        String a = C0552g.m1385a(C0552g.m1381a());
        StringBuilder stringBuilder = new StringBuilder(new StringBuilder(((((((((((((String.valueOf(valueOf).length() + 13) + String.valueOf(c).length()) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(str).length()) + String.valueOf(valueOf4).length()) + String.valueOf(str2).length()) + String.valueOf(valueOf5).length()) + String.valueOf(valueOf6).length()) + String.valueOf(valueOf7).length()) + String.valueOf(valueOf8).length()) + String.valueOf(valueOf9).length()) + String.valueOf(a).length()).append(valueOf).append(c).append(";").append(valueOf2).append("=").append("1").append(";").append(valueOf3).append("=").append(str).append(";").append(valueOf4).append("=").append(str2).append(";").append(valueOf5).append("=").append(valueOf6).append(";").append(valueOf7).append("=").append(valueOf8).append(";").append(valueOf9).append("=").append(a).toString());
        C0552g.m1397a(stringBuilder, c0555a, str3);
        return stringBuilder.toString();
    }

    public static String m1390a(C0550c c0550c, C0555a c0555a) {
        if (c0555a == null) {
            return null;
        }
        String valueOf = String.valueOf("https://pubads.g.doubleclick.net/activity;dc_iu=");
        String valueOf2 = String.valueOf(c0550c.f1192f);
        if (valueOf2.length() != 0) {
            valueOf2 = valueOf.concat(valueOf2);
        } else {
            valueOf2 = new String(valueOf);
        }
        StringBuilder stringBuilder = new StringBuilder(valueOf2);
        C0552g.m1397a(stringBuilder, c0555a, null);
        if (c0550c.f1195i != null) {
            for (Entry entry : c0550c.f1195i.entrySet()) {
                String encode = Uri.encode((String) entry.getKey());
                valueOf2 = Uri.encode(entry.getValue().toString());
                stringBuilder.append(new StringBuilder((String.valueOf(encode).length() + 2) + String.valueOf(valueOf2).length()).append(";").append(encode).append("=").append(valueOf2).toString());
            }
        }
        return stringBuilder.toString();
    }

    public static String m1403b(C0550c c0550c, String str, String str2, C0555a c0555a, String str3) {
        String a = C0552g.m1388a(c0550c.f1194h);
        Builder appendQueryParameter = Uri.parse("https://www.googleadservices.com/pagead/conversion/").buildUpon().appendEncodedPath(String.valueOf(c0550c.f1187a).concat("/")).appendQueryParameter("bundleid", str).appendQueryParameter("appversion", str2).appendQueryParameter("osversion", VERSION.RELEASE).appendQueryParameter("sdkversion", "ct-sdk-a-v2.2.3").appendQueryParameter("gms", c0555a != null ? "1" : "0");
        C0552g.m1395a(appendQueryParameter, c0555a, str3);
        if (!(c0550c.f1191e == null || c0550c.f1192f == null)) {
            appendQueryParameter.appendQueryParameter("label", c0550c.f1191e).appendQueryParameter("value", c0550c.f1192f);
        }
        if (c0550c.f1197k != 0) {
            appendQueryParameter.appendQueryParameter("timestamp", C0552g.m1385a(c0550c.f1197k));
        } else {
            appendQueryParameter.appendQueryParameter("timestamp", C0552g.m1385a(C0552g.m1381a()));
        }
        if (c0550c.f1189c) {
            appendQueryParameter.appendQueryParameter("remarketing_only", "1");
        }
        if (c0550c.f1198l) {
            appendQueryParameter.appendQueryParameter("auto", "1");
        }
        if (c0550c.f1188b) {
            appendQueryParameter.appendQueryParameter("usage_tracking_enabled", "1");
        } else {
            appendQueryParameter.appendQueryParameter("usage_tracking_enabled", "0");
        }
        if (c0550c.f1193g != null) {
            appendQueryParameter.appendQueryParameter("currency_code", c0550c.f1193g);
        }
        C0552g.m1396a(appendQueryParameter, c0550c.f1189c, c0550c.f1195i);
        String valueOf = String.valueOf(appendQueryParameter.build());
        return new StringBuilder((String.valueOf(valueOf).length() + 0) + String.valueOf(a).length()).append(valueOf).append(a).toString();
    }

    public static String m1404c(C0550c c0550c, String str, String str2, C0555a c0555a, String str3) {
        Builder appendQueryParameter = Uri.parse("https://www.googleadservices.com/pagead/conversion/").buildUpon().appendQueryParameter("sku", c0550c.f1196j).appendQueryParameter("value", c0550c.f1192f).appendQueryParameter("bundleid", str).appendQueryParameter("appversion", str2).appendQueryParameter("osversion", VERSION.RELEASE).appendQueryParameter("sdkversion", "ct-sdk-a-v2.2.3").appendQueryParameter("timestamp", C0552g.m1385a(C0552g.m1381a()));
        C0552g.m1395a(appendQueryParameter, c0555a, str3);
        return appendQueryParameter.build().toString();
    }

    public static String m1389a(C0550c c0550c) {
        switch (C05472.f1181a[c0550c.f1190d.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return "doubleclick_nonrepeatable_conversion";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "iap_nonrepeatable_conversion";
            default:
                return "google_nonrepeatable_conversion";
        }
    }

    public static String m1402b(C0550c c0550c) {
        switch (C05472.f1181a[c0550c.f1190d.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return c0550c.f1187a;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return String.format("google_iap_ping:%s", new Object[]{c0550c.f1196j});
            default:
                return c0550c.f1191e;
        }
    }

    static long m1381a() {
        if (!f1205b || f1206c < 0) {
            return System.currentTimeMillis();
        }
        return f1206c;
    }

    private static String m1407e(Context context) throws NoSuchAlgorithmException {
        String string = Secure.getString(context.getContentResolver(), "android_id");
        if (string == null) {
            string = "null";
        }
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(string.getBytes());
        return C0567s.m1441a(instance.digest(), false);
    }

    public static boolean m1406d(Context context) {
        if (f1205b) {
            return f1211h;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return false;
            }
        }
        return true;
    }
}
