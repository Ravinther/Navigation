package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzad;
import com.google.android.gms.analytics.internal.zzal;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzd;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzok;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Tracker extends zzd {
    private boolean zzKH;
    private final Map<String, String> zzKI;
    private final zzad zzKJ;
    private final zza zzKK;
    private ExceptionReporter zzKL;
    private zzal zzKM;
    private final Map<String, String> zzvs;

    /* renamed from: com.google.android.gms.analytics.Tracker.1 */
    class C06131 implements Runnable {
        final /* synthetic */ Map zzKN;
        final /* synthetic */ boolean zzKO;
        final /* synthetic */ String zzKP;
        final /* synthetic */ long zzKQ;
        final /* synthetic */ boolean zzKR;
        final /* synthetic */ boolean zzKS;
        final /* synthetic */ String zzKT;
        final /* synthetic */ Tracker zzKU;

        C06131(Tracker tracker, Map map, boolean z, String str, long j, boolean z2, boolean z3, String str2) {
            this.zzKU = tracker;
            this.zzKN = map;
            this.zzKO = z;
            this.zzKP = str;
            this.zzKQ = j;
            this.zzKR = z2;
            this.zzKS = z3;
            this.zzKT = str2;
        }

        public void run() {
            boolean z = true;
            if (this.zzKU.zzKK.zzhE()) {
                this.zzKN.put("sc", "start");
            }
            zzam.zzc(this.zzKN, "cid", this.zzKU.zzhu().getClientId());
            String str = (String) this.zzKN.get("sf");
            if (str != null) {
                double zza = zzam.zza(str, 100.0d);
                if (zzam.zza(zza, (String) this.zzKN.get("cid"))) {
                    this.zzKU.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(zza));
                    return;
                }
            }
            com.google.android.gms.analytics.internal.zza zzb = this.zzKU.zzik();
            if (this.zzKO) {
                zzam.zzb(this.zzKN, "ate", zzb.zzhM());
                zzam.zzb(this.zzKN, "adid", zzb.zzhQ());
            } else {
                this.zzKN.remove("ate");
                this.zzKN.remove("adid");
            }
            zzok zziL = this.zzKU.zzil().zziL();
            zzam.zzb(this.zzKN, "an", zziL.zzjZ());
            zzam.zzb(this.zzKN, "av", zziL.zzkb());
            zzam.zzb(this.zzKN, "aid", zziL.zztW());
            zzam.zzb(this.zzKN, "aiid", zziL.zzxA());
            this.zzKN.put("v", "1");
            this.zzKN.put("_v", zze.zzLB);
            zzam.zzb(this.zzKN, "ul", this.zzKU.zzim().zzjS().getLanguage());
            zzam.zzb(this.zzKN, "sr", this.zzKU.zzim().zzjT());
            boolean z2 = this.zzKP.equals("transaction") || this.zzKP.equals("item");
            if (z2 || this.zzKU.zzKJ.zzkp()) {
                long zzbo = zzam.zzbo((String) this.zzKN.get("ht"));
                if (zzbo == 0) {
                    zzbo = this.zzKQ;
                }
                if (this.zzKR) {
                    this.zzKU.zzie().zzc("Dry run enabled. Would have sent hit", new zzab(this.zzKU, this.zzKN, zzbo, this.zzKS));
                    return;
                }
                String str2 = (String) this.zzKN.get("cid");
                Map hashMap = new HashMap();
                zzam.zza(hashMap, "uid", this.zzKN);
                zzam.zza(hashMap, "an", this.zzKN);
                zzam.zza(hashMap, "aid", this.zzKN);
                zzam.zza(hashMap, "av", this.zzKN);
                zzam.zza(hashMap, "aiid", this.zzKN);
                String str3 = this.zzKT;
                if (TextUtils.isEmpty((CharSequence) this.zzKN.get("adid"))) {
                    z = false;
                }
                this.zzKN.put("_s", String.valueOf(this.zzKU.zzhz().zza(new zzh(0, str2, str3, z, 0, hashMap))));
                this.zzKU.zzhz().zza(new zzab(this.zzKU, this.zzKN, zzbo, this.zzKS));
                return;
            }
            this.zzKU.zzie().zzh(this.zzKN, "Too many hits sent too quickly, rate limiting invoked");
        }
    }

    private class zza extends zzd implements zza {
        final /* synthetic */ Tracker zzKU;
        private boolean zzKV;
        private int zzKW;
        private long zzKX;
        private boolean zzKY;
        private long zzKZ;

        protected zza(Tracker tracker, zzf com_google_android_gms_analytics_internal_zzf) {
            this.zzKU = tracker;
            super(com_google_android_gms_analytics_internal_zzf);
            this.zzKX = -1;
        }

        private void zzhF() {
            if (this.zzKX >= 0 || this.zzKV) {
                zzhu().zza(this.zzKU.zzKK);
            } else {
                zzhu().zzb(this.zzKU.zzKK);
            }
        }

        public void enableAutoActivityTracking(boolean enabled) {
            this.zzKV = enabled;
            zzhF();
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.zzKX = sessionTimeout;
            zzhF();
        }

        protected void zzhB() {
        }

        public synchronized boolean zzhE() {
            boolean z;
            z = this.zzKY;
            this.zzKY = false;
            return z;
        }

        boolean zzhG() {
            return zzid().elapsedRealtime() >= this.zzKZ + Math.max(1000, this.zzKX);
        }

        public void zzo(Activity activity) {
            if (this.zzKW == 0 && zzhG()) {
                this.zzKY = true;
            }
            this.zzKW++;
            if (this.zzKV) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    this.zzKU.setCampaignParamsOnNextHit(intent.getData());
                }
                Map hashMap = new HashMap();
                hashMap.put("&t", "screenview");
                this.zzKU.set("&cd", this.zzKU.zzKM != null ? this.zzKU.zzKM.zzr(activity) : activity.getClass().getCanonicalName());
                if (TextUtils.isEmpty((CharSequence) hashMap.get("&dr"))) {
                    CharSequence zzq = Tracker.zzq(activity);
                    if (!TextUtils.isEmpty(zzq)) {
                        hashMap.put("&dr", zzq);
                    }
                }
                this.zzKU.send(hashMap);
            }
        }

        public void zzp(Activity activity) {
            this.zzKW--;
            this.zzKW = Math.max(0, this.zzKW);
            if (this.zzKW == 0) {
                this.zzKZ = zzid().elapsedRealtime();
            }
        }
    }

    Tracker(zzf analytics, String trackingId, zzad rateLimiter) {
        super(analytics);
        this.zzvs = new HashMap();
        this.zzKI = new HashMap();
        if (trackingId != null) {
            this.zzvs.put("&tid", trackingId);
        }
        this.zzvs.put("useSecure", "1");
        this.zzvs.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        if (rateLimiter == null) {
            this.zzKJ = new zzad("tracking");
        } else {
            this.zzKJ = rateLimiter;
        }
        this.zzKK = new zza(this, analytics);
    }

    private static void zza(Map<String, String> map, Map<String, String> map2) {
        zzx.zzv(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zzb = zzb(entry);
                if (zzb != null) {
                    map2.put(zzb, entry.getValue());
                }
            }
        }
    }

    private static boolean zza(Entry<String, String> entry) {
        String str = (String) entry.getKey();
        String str2 = (String) entry.getValue();
        return str.startsWith("&") && str.length() >= 2;
    }

    private static String zzb(Entry<String, String> entry) {
        return !zza((Entry) entry) ? null : ((String) entry.getKey()).substring(1);
    }

    private static void zzb(Map<String, String> map, Map<String, String> map2) {
        zzx.zzv(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zzb = zzb(entry);
                if (!(zzb == null || map2.containsKey(zzb))) {
                    map2.put(zzb, entry.getValue());
                }
            }
        }
    }

    private boolean zzhC() {
        return this.zzKL != null;
    }

    static String zzq(Activity activity) {
        zzx.zzv(activity);
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        CharSequence stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return !TextUtils.isEmpty(stringExtra) ? stringExtra : null;
    }

    public void enableAdvertisingIdCollection(boolean enabled) {
        this.zzKH = enabled;
    }

    public void enableAutoActivityTracking(boolean enabled) {
        this.zzKK.enableAutoActivityTracking(enabled);
    }

    public void enableExceptionReporting(boolean enable) {
        synchronized (this) {
            if (zzhC() == enable) {
                return;
            }
            if (enable) {
                this.zzKL = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), getContext());
                Thread.setDefaultUncaughtExceptionHandler(this.zzKL);
                zzaY("Uncaught exceptions will be reported to Google Analytics");
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zzKL.zzhv());
                zzaY("Uncaught exceptions will not be reported to Google Analytics");
            }
        }
    }

    public void send(Map<String, String> params) {
        long currentTimeMillis = zzid().currentTimeMillis();
        if (zzhu().getAppOptOut()) {
            zzaZ("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean isDryRunEnabled = zzhu().isDryRunEnabled();
        Map hashMap = new HashMap();
        zza(this.zzvs, hashMap);
        zza(params, hashMap);
        boolean zze = zzam.zze((String) this.zzvs.get("useSecure"), true);
        zzb(this.zzKI, hashMap);
        this.zzKI.clear();
        String str = (String) hashMap.get("t");
        if (TextUtils.isEmpty(str)) {
            zzie().zzh(hashMap, "Missing hit type parameter");
            return;
        }
        String str2 = (String) hashMap.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzie().zzh(hashMap, "Missing tracking id parameter");
            return;
        }
        boolean zzhD = zzhD();
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt((String) this.zzvs.get("&a")) + 1;
                if (parseInt >= Integer.MAX_VALUE) {
                    parseInt = 1;
                }
                this.zzvs.put("&a", Integer.toString(parseInt));
            }
        }
        zzig().zzf(new C06131(this, hashMap, zzhD, str, currentTimeMillis, isDryRunEnabled, zze, str2));
    }

    public void set(String key, String value) {
        zzx.zzb((Object) key, (Object) "Key should be non-null");
        if (!TextUtils.isEmpty(key)) {
            this.zzvs.put(key, value);
        }
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null && !uri.isOpaque()) {
            Object queryParameter = uri.getQueryParameter("referrer");
            if (!TextUtils.isEmpty(queryParameter)) {
                Uri parse = Uri.parse("http://hostname/?" + queryParameter);
                String queryParameter2 = parse.getQueryParameter("utm_id");
                if (queryParameter2 != null) {
                    this.zzKI.put("&ci", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("anid");
                if (queryParameter2 != null) {
                    this.zzKI.put("&anid", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_campaign");
                if (queryParameter2 != null) {
                    this.zzKI.put("&cn", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_content");
                if (queryParameter2 != null) {
                    this.zzKI.put("&cc", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_medium");
                if (queryParameter2 != null) {
                    this.zzKI.put("&cm", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_source");
                if (queryParameter2 != null) {
                    this.zzKI.put("&cs", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_term");
                if (queryParameter2 != null) {
                    this.zzKI.put("&ck", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("dclid");
                if (queryParameter2 != null) {
                    this.zzKI.put("&dclid", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("gclid");
                if (queryParameter2 != null) {
                    this.zzKI.put("&gclid", queryParameter2);
                }
                String queryParameter3 = parse.getQueryParameter("aclid");
                if (queryParameter3 != null) {
                    this.zzKI.put("&aclid", queryParameter3);
                }
            }
        }
    }

    public void setScreenName(String screenName) {
        set("&cd", screenName);
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.zzKK.setSessionTimeout(1000 * sessionTimeout);
    }

    void zza(zzal com_google_android_gms_analytics_internal_zzal) {
        zzaY("Loading Tracker config values");
        this.zzKM = com_google_android_gms_analytics_internal_zzal;
        if (this.zzKM.zzkM()) {
            String trackingId = this.zzKM.getTrackingId();
            set("&tid", trackingId);
            zza("trackingId loaded", trackingId);
        }
        if (this.zzKM.zzkN()) {
            trackingId = Double.toString(this.zzKM.zzkO());
            set("&sf", trackingId);
            zza("Sample frequency loaded", trackingId);
        }
        if (this.zzKM.zzkP()) {
            int sessionTimeout = this.zzKM.getSessionTimeout();
            setSessionTimeout((long) sessionTimeout);
            zza("Session timeout loaded", Integer.valueOf(sessionTimeout));
        }
        if (this.zzKM.zzkQ()) {
            boolean zzkR = this.zzKM.zzkR();
            enableAutoActivityTracking(zzkR);
            zza("Auto activity tracking loaded", Boolean.valueOf(zzkR));
        }
        if (this.zzKM.zzkS()) {
            zzkR = this.zzKM.zzkT();
            if (zzkR) {
                set("&aip", "1");
            }
            zza("Anonymize ip loaded", Boolean.valueOf(zzkR));
        }
        enableExceptionReporting(this.zzKM.zzkU());
    }

    protected void zzhB() {
        this.zzKK.zza();
        String zzjZ = zzhA().zzjZ();
        if (zzjZ != null) {
            set("&an", zzjZ);
        }
        zzjZ = zzhA().zzkb();
        if (zzjZ != null) {
            set("&av", zzjZ);
        }
    }

    boolean zzhD() {
        return this.zzKH;
    }
}
