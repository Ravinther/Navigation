package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzp;
import com.sygic.aura.settings.fragments.SettingsFragment;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzdh implements zzdg {
    private final Context mContext;
    private final VersionInfoParcel zzpa;

    /* renamed from: com.google.android.gms.internal.zzdh.1 */
    class C08871 implements Runnable {
        final /* synthetic */ zzip zzwU;
        final /* synthetic */ zzdh zzwV;
        final /* synthetic */ Map zzwl;

        /* renamed from: com.google.android.gms.internal.zzdh.1.1 */
        class C08861 implements Runnable {
            final /* synthetic */ JSONObject zzwW;
            final /* synthetic */ C08871 zzwX;

            C08861(C08871 c08871, JSONObject jSONObject) {
                this.zzwX = c08871;
                this.zzwW = jSONObject;
            }

            public void run() {
                this.zzwX.zzwU.zzb("fetchHttpRequestCompleted", this.zzwW);
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Dispatched http response.");
            }
        }

        C08871(zzdh com_google_android_gms_internal_zzdh, Map map, zzip com_google_android_gms_internal_zzip) {
            this.zzwV = com_google_android_gms_internal_zzdh;
            this.zzwl = map;
            this.zzwU = com_google_android_gms_internal_zzip;
        }

        public void run() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Received Http request.");
            JSONObject zzV = this.zzwV.zzV((String) this.zzwl.get("http_request"));
            if (zzV == null) {
                com.google.android.gms.ads.internal.util.client.zzb.m1444e("Response should not be null.");
            } else {
                zzhu.zzHK.post(new C08861(this, zzV));
            }
        }
    }

    @zzgk
    static class zza {
        private final String mValue;
        private final String zztP;

        public zza(String str, String str2) {
            this.zztP = str;
            this.mValue = str2;
        }

        public String getKey() {
            return this.zztP;
        }

        public String getValue() {
            return this.mValue;
        }
    }

    @zzgk
    static class zzb {
        private final String zzwY;
        private final URL zzwZ;
        private final ArrayList<zza> zzxa;
        private final String zzxb;

        public zzb(String str, URL url, ArrayList<zza> arrayList, String str2) {
            this.zzwY = str;
            this.zzwZ = url;
            if (arrayList == null) {
                this.zzxa = new ArrayList();
            } else {
                this.zzxa = arrayList;
            }
            this.zzxb = str2;
        }

        public String zzdA() {
            return this.zzwY;
        }

        public URL zzdB() {
            return this.zzwZ;
        }

        public ArrayList<zza> zzdC() {
            return this.zzxa;
        }

        public String zzdD() {
            return this.zzxb;
        }
    }

    @zzgk
    class zzc {
        final /* synthetic */ zzdh zzwV;
        private final zzd zzxc;
        private final boolean zzxd;
        private final String zzxe;

        public zzc(zzdh com_google_android_gms_internal_zzdh, boolean z, zzd com_google_android_gms_internal_zzdh_zzd, String str) {
            this.zzwV = com_google_android_gms_internal_zzdh;
            this.zzxd = z;
            this.zzxc = com_google_android_gms_internal_zzdh_zzd;
            this.zzxe = str;
        }

        public String getReason() {
            return this.zzxe;
        }

        public boolean isSuccess() {
            return this.zzxd;
        }

        public zzd zzdE() {
            return this.zzxc;
        }
    }

    @zzgk
    static class zzd {
        private final String zzvM;
        private final String zzwY;
        private final int zzxf;
        private final List<zza> zzxg;

        public zzd(String str, int i, List<zza> list, String str2) {
            this.zzwY = str;
            this.zzxf = i;
            if (list == null) {
                this.zzxg = new ArrayList();
            } else {
                this.zzxg = list;
            }
            this.zzvM = str2;
        }

        public String getBody() {
            return this.zzvM;
        }

        public int getResponseCode() {
            return this.zzxf;
        }

        public String zzdA() {
            return this.zzwY;
        }

        public Iterable<zza> zzdF() {
            return this.zzxg;
        }
    }

    public zzdh(Context context, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzpa = versionInfoParcel;
    }

    public JSONObject zzV(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject();
            Object obj = "";
            try {
                obj = jSONObject.optString("http_request_id");
                zzc zza = zza(zzb(jSONObject));
                if (zza.isSuccess()) {
                    jSONObject2.put("response", zza(zza.zzdE()));
                    jSONObject2.put("success", true);
                    return jSONObject2;
                }
                jSONObject2.put("response", new JSONObject().put("http_request_id", obj));
                jSONObject2.put("success", false);
                jSONObject2.put("reason", zza.getReason());
                return jSONObject2;
            } catch (Exception e) {
                try {
                    jSONObject2.put("response", new JSONObject().put("http_request_id", obj));
                    jSONObject2.put("success", false);
                    jSONObject2.put("reason", e.toString());
                    return jSONObject2;
                } catch (JSONException e2) {
                    return jSONObject2;
                }
            }
        } catch (JSONException e3) {
            com.google.android.gms.ads.internal.util.client.zzb.m1444e("The request is not a valid JSON.");
            try {
                return new JSONObject().put("success", false);
            } catch (JSONException e4) {
                return new JSONObject();
            }
        }
    }

    protected zzc zza(zzb com_google_android_gms_internal_zzdh_zzb) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) com_google_android_gms_internal_zzdh_zzb.zzdB().openConnection();
            zzp.zzbx().zza(this.mContext, this.zzpa.zzIz, false, httpURLConnection);
            Iterator it = com_google_android_gms_internal_zzdh_zzb.zzdC().iterator();
            while (it.hasNext()) {
                zza com_google_android_gms_internal_zzdh_zza = (zza) it.next();
                httpURLConnection.addRequestProperty(com_google_android_gms_internal_zzdh_zza.getKey(), com_google_android_gms_internal_zzdh_zza.getValue());
            }
            if (!TextUtils.isEmpty(com_google_android_gms_internal_zzdh_zzb.zzdD())) {
                httpURLConnection.setDoOutput(true);
                byte[] bytes = com_google_android_gms_internal_zzdh_zzb.zzdD().getBytes();
                httpURLConnection.setFixedLengthStreamingMode(bytes.length);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
            }
            List arrayList = new ArrayList();
            if (httpURLConnection.getHeaderFields() != null) {
                for (Entry entry : httpURLConnection.getHeaderFields().entrySet()) {
                    for (String com_google_android_gms_internal_zzdh_zza2 : (List) entry.getValue()) {
                        arrayList.add(new zza((String) entry.getKey(), com_google_android_gms_internal_zzdh_zza2));
                    }
                }
            }
            return new zzc(this, true, new zzd(com_google_android_gms_internal_zzdh_zzb.zzdA(), httpURLConnection.getResponseCode(), arrayList, zzp.zzbx().zza(new InputStreamReader(httpURLConnection.getInputStream()))), null);
        } catch (Exception e) {
            return new zzc(this, false, null, e.toString());
        }
    }

    protected JSONObject zza(zzd com_google_android_gms_internal_zzdh_zzd) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("http_request_id", com_google_android_gms_internal_zzdh_zzd.zzdA());
            if (com_google_android_gms_internal_zzdh_zzd.getBody() != null) {
                jSONObject.put("body", com_google_android_gms_internal_zzdh_zzd.getBody());
            }
            JSONArray jSONArray = new JSONArray();
            for (zza com_google_android_gms_internal_zzdh_zza : com_google_android_gms_internal_zzdh_zzd.zzdF()) {
                jSONArray.put(new JSONObject().put(SettingsFragment.ARG_KEY, com_google_android_gms_internal_zzdh_zza.getKey()).put("value", com_google_android_gms_internal_zzdh_zza.getValue()));
            }
            jSONObject.put("headers", jSONArray);
            jSONObject.put("response_code", com_google_android_gms_internal_zzdh_zzd.getResponseCode());
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing JSON for http response.", e);
        }
        return jSONObject;
    }

    public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
        zzht.zza(new C08871(this, map, com_google_android_gms_internal_zzip));
    }

    protected zzb zzb(JSONObject jSONObject) {
        URL url;
        String optString = jSONObject.optString("http_request_id");
        String optString2 = jSONObject.optString("url");
        String optString3 = jSONObject.optString("post_body", null);
        try {
            url = new URL(optString2);
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing http request.", e);
            url = null;
        }
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("headers");
        if (optJSONArray == null) {
            optJSONArray = new JSONArray();
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(new zza(optJSONObject.optString(SettingsFragment.ARG_KEY), optJSONObject.optString("value")));
            }
        }
        return new zzb(optString, url, arrayList, optString3);
    }
}
