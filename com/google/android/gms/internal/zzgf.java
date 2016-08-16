package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.internal.formats.zzf;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.ads.internal.zzn;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.dynamic.zze;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzgf implements Callable<zzhj> {
    private static final long zzCS;
    private final Context mContext;
    private final com.google.android.gms.internal.zzhj.zza zzCF;
    private int zzCI;
    private final zzhy zzCT;
    private final zzn zzCU;
    private final zzbc zzCV;
    private boolean zzCW;
    private List<String> zzCX;
    private final Object zzpc;
    private final zzan zzwh;

    /* renamed from: com.google.android.gms.internal.zzgf.1 */
    class C09251 implements zzdg {
        final /* synthetic */ zzbb zzCY;
        final /* synthetic */ zzb zzCZ;
        final /* synthetic */ zzie zzDa;
        final /* synthetic */ zzgf zzDb;

        C09251(zzgf com_google_android_gms_internal_zzgf, zzbb com_google_android_gms_internal_zzbb, zzb com_google_android_gms_internal_zzgf_zzb, zzie com_google_android_gms_internal_zzie) {
            this.zzDb = com_google_android_gms_internal_zzgf;
            this.zzCY = com_google_android_gms_internal_zzbb;
            this.zzCZ = com_google_android_gms_internal_zzgf_zzb;
            this.zzDa = com_google_android_gms_internal_zzie;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            this.zzCY.zzb("/nativeAdPreProcess", this.zzCZ.zzDk);
            try {
                String str = (String) map.get("success");
                if (!TextUtils.isEmpty(str)) {
                    this.zzDa.zzf(new JSONObject(str).getJSONArray("ads").getJSONObject(0));
                    return;
                }
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Malformed native JSON response.", e);
            }
            this.zzDb.zzB(0);
            zzx.zza(this.zzDb.zzft(), "Unable to set the ad state error!");
            this.zzDa.zzf(null);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.2 */
    class C09262 implements Runnable {
        final /* synthetic */ zzgf zzDb;
        final /* synthetic */ zzie zzDc;
        final /* synthetic */ String zzDd;

        C09262(zzgf com_google_android_gms_internal_zzgf, zzie com_google_android_gms_internal_zzie, String str) {
            this.zzDb = com_google_android_gms_internal_zzgf;
            this.zzDc = com_google_android_gms_internal_zzie;
            this.zzDd = str;
        }

        public void run() {
            this.zzDc.zzf(this.zzDb.zzCU.zzbq().get(this.zzDd));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.3 */
    class C09273 implements zzdg {
        final /* synthetic */ zzgf zzDb;
        final /* synthetic */ zzf zzDe;

        C09273(zzgf com_google_android_gms_internal_zzgf, zzf com_google_android_gms_ads_internal_formats_zzf) {
            this.zzDb = com_google_android_gms_internal_zzgf;
            this.zzDe = com_google_android_gms_ads_internal_formats_zzf;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            this.zzDb.zzb(this.zzDe, (String) map.get("asset"));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.4 */
    class C09284 implements com.google.android.gms.internal.zzig.zza<zzc, com.google.android.gms.ads.internal.formats.zza> {
        final /* synthetic */ zzgf zzDb;
        final /* synthetic */ String zzDf;
        final /* synthetic */ Integer zzDg;
        final /* synthetic */ Integer zzDh;
        final /* synthetic */ int zzDi;

        C09284(zzgf com_google_android_gms_internal_zzgf, String str, Integer num, Integer num2, int i) {
            this.zzDb = com_google_android_gms_internal_zzgf;
            this.zzDf = str;
            this.zzDg = num;
            this.zzDh = num2;
            this.zzDi = i;
        }

        public com.google.android.gms.ads.internal.formats.zza zza(zzc com_google_android_gms_ads_internal_formats_zzc) {
            com.google.android.gms.ads.internal.formats.zza com_google_android_gms_ads_internal_formats_zza;
            if (com_google_android_gms_ads_internal_formats_zzc != null) {
                try {
                    if (!TextUtils.isEmpty(this.zzDf)) {
                        com_google_android_gms_ads_internal_formats_zza = new com.google.android.gms.ads.internal.formats.zza(this.zzDf, (Drawable) zze.zzp(com_google_android_gms_ads_internal_formats_zzc.zzdr()), this.zzDg, this.zzDh, this.zzDi > 0 ? Integer.valueOf(this.zzDi) : null);
                        return com_google_android_gms_ads_internal_formats_zza;
                    }
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Could not get attribution icon", e);
                    return null;
                }
            }
            com_google_android_gms_ads_internal_formats_zza = null;
            return com_google_android_gms_ads_internal_formats_zza;
        }

        public /* synthetic */ Object zze(Object obj) {
            return zza((zzc) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.5 */
    class C09295 implements com.google.android.gms.internal.zzhy.zza<zzc> {
        final /* synthetic */ zzgf zzDb;
        final /* synthetic */ boolean zzDj;
        final /* synthetic */ String zzzF;

        C09295(zzgf com_google_android_gms_internal_zzgf, boolean z, String str) {
            this.zzDb = com_google_android_gms_internal_zzgf;
            this.zzDj = z;
            this.zzzF = str;
        }

        public zzc zzfu() {
            this.zzDb.zza(2, this.zzDj);
            return null;
        }

        public /* synthetic */ Object zzfv() {
            return zzfu();
        }

        public zzc zzg(InputStream inputStream) {
            byte[] zzk;
            try {
                zzk = zzlr.zzk(inputStream);
            } catch (IOException e) {
                zzk = null;
            }
            if (zzk == null) {
                this.zzDb.zza(2, this.zzDj);
                return null;
            }
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(zzk, 0, zzk.length);
            if (decodeByteArray != null) {
                return new zzc(new BitmapDrawable(Resources.getSystem(), decodeByteArray), Uri.parse(this.zzzF));
            }
            this.zzDb.zza(2, this.zzDj);
            return null;
        }

        public /* synthetic */ Object zzh(InputStream inputStream) {
            return zzg(inputStream);
        }
    }

    public interface zza<T extends com.google.android.gms.ads.internal.formats.zzh.zza> {
        T zza(zzgf com_google_android_gms_internal_zzgf, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException;
    }

    class zzb {
        final /* synthetic */ zzgf zzDb;
        public zzdg zzDk;

        zzb(zzgf com_google_android_gms_internal_zzgf) {
            this.zzDb = com_google_android_gms_internal_zzgf;
        }
    }

    static {
        zzCS = TimeUnit.SECONDS.toMillis(60);
    }

    public zzgf(Context context, zzn com_google_android_gms_ads_internal_zzn, zzbc com_google_android_gms_internal_zzbc, zzhy com_google_android_gms_internal_zzhy, zzan com_google_android_gms_internal_zzan, com.google.android.gms.internal.zzhj.zza com_google_android_gms_internal_zzhj_zza) {
        this.zzpc = new Object();
        this.mContext = context;
        this.zzCU = com_google_android_gms_ads_internal_zzn;
        this.zzCT = com_google_android_gms_internal_zzhy;
        this.zzCV = com_google_android_gms_internal_zzbc;
        this.zzCF = com_google_android_gms_internal_zzhj_zza;
        this.zzwh = com_google_android_gms_internal_zzan;
        this.zzCW = false;
        this.zzCI = -2;
        this.zzCX = null;
    }

    private com.google.android.gms.ads.internal.formats.zzh.zza zza(zzbb com_google_android_gms_internal_zzbb, zza com_google_android_gms_internal_zzgf_zza, JSONObject jSONObject) throws ExecutionException, InterruptedException, JSONException {
        if (zzft()) {
            return null;
        }
        String[] zzc = zzc(jSONObject.getJSONObject("tracking_urls_and_actions"), "impression_tracking_urls");
        this.zzCX = zzc == null ? null : Arrays.asList(zzc);
        com.google.android.gms.ads.internal.formats.zzh.zza zza = com_google_android_gms_internal_zzgf_zza.zza(this, jSONObject);
        if (zza == null) {
            com.google.android.gms.ads.internal.util.client.zzb.m1444e("Failed to retrieve ad assets.");
            return null;
        }
        zza.zza(new zzh(this.mContext, this.zzCU, com_google_android_gms_internal_zzbb, this.zzwh, jSONObject, zza, this.zzCF.zzGL.zzqb));
        return zza;
    }

    private zzhj zza(com.google.android.gms.ads.internal.formats.zzh.zza com_google_android_gms_ads_internal_formats_zzh_zza) {
        int i;
        synchronized (this.zzpc) {
            i = this.zzCI;
            if (com_google_android_gms_ads_internal_formats_zzh_zza == null && this.zzCI == -2) {
                i = 0;
            }
        }
        return new zzhj(this.zzCF.zzGL.zzDy, null, this.zzCF.zzGM.zzyw, i, this.zzCF.zzGM.zzyx, this.zzCX, this.zzCF.zzGM.orientation, this.zzCF.zzGM.zzyA, this.zzCF.zzGL.zzDB, false, null, null, null, null, null, 0, this.zzCF.zzqf, this.zzCF.zzGM.zzDW, this.zzCF.zzGI, this.zzCF.zzGJ, this.zzCF.zzGM.zzEc, this.zzCF.zzGF, i != -2 ? null : com_google_android_gms_ads_internal_formats_zzh_zza, this.zzCF.zzGL.zzDO);
    }

    private zzih<zzc> zza(JSONObject jSONObject, boolean z, boolean z2) throws JSONException {
        String string = z ? jSONObject.getString("url") : jSONObject.optString("url");
        if (!TextUtils.isEmpty(string)) {
            return z2 ? new zzif(new zzc(null, Uri.parse(string))) : this.zzCT.zza(string, new C09295(this, z, string));
        } else {
            zza(0, z);
            return new zzif(null);
        }
    }

    private void zza(com.google.android.gms.ads.internal.formats.zzh.zza com_google_android_gms_ads_internal_formats_zzh_zza, zzbb com_google_android_gms_internal_zzbb) {
        if (com_google_android_gms_ads_internal_formats_zzh_zza instanceof zzf) {
            zzf com_google_android_gms_ads_internal_formats_zzf = (zzf) com_google_android_gms_ads_internal_formats_zzh_zza;
            zzb com_google_android_gms_internal_zzgf_zzb = new zzb(this);
            zzdg c09273 = new C09273(this, com_google_android_gms_ads_internal_formats_zzf);
            com_google_android_gms_internal_zzgf_zzb.zzDk = c09273;
            com_google_android_gms_internal_zzbb.zza("/nativeAdCustomClick", c09273);
        }
    }

    private Integer zzb(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            return Integer.valueOf(Color.rgb(jSONObject2.getInt("r"), jSONObject2.getInt("g"), jSONObject2.getInt("b")));
        } catch (JSONException e) {
            return null;
        }
    }

    private JSONObject zzb(zzbb com_google_android_gms_internal_zzbb) throws TimeoutException, JSONException {
        if (zzft()) {
            return null;
        }
        zzie com_google_android_gms_internal_zzie = new zzie();
        zzb com_google_android_gms_internal_zzgf_zzb = new zzb(this);
        zzdg c09251 = new C09251(this, com_google_android_gms_internal_zzbb, com_google_android_gms_internal_zzgf_zzb, com_google_android_gms_internal_zzie);
        com_google_android_gms_internal_zzgf_zzb.zzDk = c09251;
        com_google_android_gms_internal_zzbb.zza("/nativeAdPreProcess", c09251);
        com_google_android_gms_internal_zzbb.zza("google.afma.nativeAds.preProcessJsonGmsg", new JSONObject(this.zzCF.zzGM.body));
        return (JSONObject) com_google_android_gms_internal_zzie.get(zzCS, TimeUnit.MILLISECONDS);
    }

    private void zzb(zzcr com_google_android_gms_internal_zzcr, String str) {
        try {
            zzcv zzr = this.zzCU.zzr(com_google_android_gms_internal_zzcr.getCustomTemplateId());
            if (zzr != null) {
                zzr.zza(com_google_android_gms_internal_zzcr, str);
            }
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call onCustomClick for asset " + str + ".", e);
        }
    }

    private String[] zzc(JSONObject jSONObject, String str) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        String[] strArr = new String[optJSONArray.length()];
        for (int i = 0; i < optJSONArray.length(); i++) {
            strArr[i] = optJSONArray.getString(i);
        }
        return strArr;
    }

    private zzbb zzfs() throws CancellationException, ExecutionException, InterruptedException, TimeoutException {
        if (zzft()) {
            return null;
        }
        zzbb com_google_android_gms_internal_zzbb = (zzbb) this.zzCV.zza(this.mContext, this.zzCF.zzGL.zzqb, (this.zzCF.zzGM.zzAT.indexOf("https") == 0 ? "https:" : "http:") + ((String) zzby.zzuU.get()), this.zzwh).get(zzCS, TimeUnit.MILLISECONDS);
        com_google_android_gms_internal_zzbb.zza(this.zzCU, this.zzCU, this.zzCU, this.zzCU, false, null, null, null, null);
        return com_google_android_gms_internal_zzbb;
    }

    public /* synthetic */ Object call() throws Exception {
        return zzfr();
    }

    public void zzB(int i) {
        synchronized (this.zzpc) {
            this.zzCW = true;
            this.zzCI = i;
        }
    }

    public zzih<zzc> zza(JSONObject jSONObject, String str, boolean z, boolean z2) throws JSONException {
        JSONObject jSONObject2 = z ? jSONObject.getJSONObject(str) : jSONObject.optJSONObject(str);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, z, z2);
    }

    public List<zzih<zzc>> zza(JSONObject jSONObject, String str, boolean z, boolean z2, boolean z3) throws JSONException {
        JSONArray jSONArray = z ? jSONObject.getJSONArray(str) : jSONObject.optJSONArray(str);
        List<zzih<zzc>> arrayList = new ArrayList();
        if (jSONArray == null || jSONArray.length() == 0) {
            zza(0, z);
            return arrayList;
        }
        int length = z3 ? jSONArray.length() : 1;
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            arrayList.add(zza(jSONObject2, z, z2));
        }
        return arrayList;
    }

    public Future<zzc> zza(JSONObject jSONObject, String str, boolean z) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        boolean optBoolean = jSONObject2.optBoolean("require", true);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, optBoolean, z);
    }

    public void zza(int i, boolean z) {
        if (z) {
            zzB(i);
        }
    }

    protected zza zzd(JSONObject jSONObject) throws JSONException, TimeoutException {
        if (zzft()) {
            return null;
        }
        String string = jSONObject.getString("template_id");
        boolean z = this.zzCF.zzGL.zzqt != null ? this.zzCF.zzGL.zzqt.zzwn : false;
        boolean z2 = this.zzCF.zzGL.zzqt != null ? this.zzCF.zzGL.zzqt.zzwp : false;
        if ("2".equals(string)) {
            return new zzgg(z, z2);
        }
        if ("1".equals(string)) {
            return new zzgh(z, z2);
        }
        if ("3".equals(string)) {
            String string2 = jSONObject.getString("custom_template_id");
            zzie com_google_android_gms_internal_zzie = new zzie();
            zzhu.zzHK.post(new C09262(this, com_google_android_gms_internal_zzie, string2));
            if (com_google_android_gms_internal_zzie.get(zzCS, TimeUnit.MILLISECONDS) != null) {
                return new zzgi(z);
            }
            com.google.android.gms.ads.internal.util.client.zzb.m1444e("No handler for custom template: " + jSONObject.getString("custom_template_id"));
        } else {
            zzB(0);
        }
        return null;
    }

    public zzih<com.google.android.gms.ads.internal.formats.zza> zze(JSONObject jSONObject) throws JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject("attribution");
        if (optJSONObject == null) {
            return new zzif(null);
        }
        String optString = optJSONObject.optString("text");
        int optInt = optJSONObject.optInt("text_size", -1);
        Integer zzb = zzb(optJSONObject, "text_color");
        return zzig.zza(zza(optJSONObject, "image", false, false), new C09284(this, optString, zzb(optJSONObject, "bg_color"), zzb, optInt));
    }

    public zzhj zzfr() {
        try {
            zzbb zzfs = zzfs();
            JSONObject zzb = zzb(zzfs);
            com.google.android.gms.ads.internal.formats.zzh.zza zza = zza(zzfs, zzd(zzb), zzb);
            zza(zza, zzfs);
            return zza(zza);
        } catch (CancellationException e) {
            if (!this.zzCW) {
                zzB(0);
            }
            return zza(null);
        } catch (ExecutionException e2) {
            if (this.zzCW) {
                zzB(0);
            }
            return zza(null);
        } catch (InterruptedException e3) {
            if (this.zzCW) {
                zzB(0);
            }
            return zza(null);
        } catch (Throwable e4) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Malformed native JSON response.", e4);
            if (this.zzCW) {
                zzB(0);
            }
            return zza(null);
        } catch (Throwable e42) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Timeout when loading native ad.", e42);
            if (this.zzCW) {
                zzB(0);
            }
            return zza(null);
        }
    }

    public boolean zzft() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzCW;
        }
        return z;
    }
}
