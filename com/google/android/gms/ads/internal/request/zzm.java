package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzbb;
import com.google.android.gms.internal.zzbe;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzdg;
import com.google.android.gms.internal.zzdh;
import com.google.android.gms.internal.zzdl;
import com.google.android.gms.internal.zzdv;
import com.google.android.gms.internal.zzdv.zzd;
import com.google.android.gms.internal.zzea;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzgn;
import com.google.android.gms.internal.zzhq;
import com.google.android.gms.internal.zzip;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzm extends zzhq {
    static final long zzEu;
    private static boolean zzEv;
    private static zzdv zzEw;
    private static zzdh zzEx;
    private static zzdl zzEy;
    private static zzdg zzEz;
    private static final Object zzpm;
    private final Context mContext;
    private final Object zzCE;
    private final com.google.android.gms.ads.internal.request.zza.zza zzDp;
    private final com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza zzDq;
    private zzd zzEA;

    /* renamed from: com.google.android.gms.ads.internal.request.zzm.1 */
    class C05911 implements Runnable {
        final /* synthetic */ zzm zzEB;
        final /* synthetic */ com.google.android.gms.internal.zzhj.zza zzoA;

        C05911(zzm com_google_android_gms_ads_internal_request_zzm, com.google.android.gms.internal.zzhj.zza com_google_android_gms_internal_zzhj_zza) {
            this.zzEB = com_google_android_gms_ads_internal_request_zzm;
            this.zzoA = com_google_android_gms_internal_zzhj_zza;
        }

        public void run() {
            this.zzEB.zzDp.zza(this.zzoA);
            if (this.zzEB.zzEA != null) {
                this.zzEB.zzEA.release();
                this.zzEB.zzEA = null;
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzm.2 */
    class C05942 implements Runnable {
        final /* synthetic */ zzm zzEB;
        final /* synthetic */ JSONObject zzEC;
        final /* synthetic */ String zzED;

        /* renamed from: com.google.android.gms.ads.internal.request.zzm.2.1 */
        class C05921 implements com.google.android.gms.internal.zzij.zzc<zzbe> {
            final /* synthetic */ C05942 zzEE;

            C05921(C05942 c05942) {
                this.zzEE = c05942;
            }

            public void zzb(zzbe com_google_android_gms_internal_zzbe) {
                try {
                    com_google_android_gms_internal_zzbe.zza("AFMA_getAdapterLessMediationAd", this.zzEE.zzEC);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Error requesting an ad url", e);
                    zzm.zzEy.zzX(this.zzEE.zzED);
                }
            }

            public /* synthetic */ void zzc(Object obj) {
                zzb((zzbe) obj);
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.request.zzm.2.2 */
        class C05932 implements com.google.android.gms.internal.zzij.zza {
            final /* synthetic */ C05942 zzEE;

            C05932(C05942 c05942) {
                this.zzEE = c05942;
            }

            public void run() {
                zzm.zzEy.zzX(this.zzEE.zzED);
            }
        }

        C05942(zzm com_google_android_gms_ads_internal_request_zzm, JSONObject jSONObject, String str) {
            this.zzEB = com_google_android_gms_ads_internal_request_zzm;
            this.zzEC = jSONObject;
            this.zzED = str;
        }

        public void run() {
            this.zzEB.zzEA = zzm.zzEw.zzdL();
            this.zzEB.zzEA.zza(new C05921(this), new C05932(this));
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzm.3 */
    class C05953 implements Runnable {
        final /* synthetic */ zzm zzEB;

        C05953(zzm com_google_android_gms_ads_internal_request_zzm) {
            this.zzEB = com_google_android_gms_ads_internal_request_zzm;
        }

        public void run() {
            if (this.zzEB.zzEA != null) {
                this.zzEB.zzEA.release();
                this.zzEB.zzEA = null;
            }
        }
    }

    public static class zza implements com.google.android.gms.internal.zzdv.zzb<zzbb> {
        public void zza(zzbb com_google_android_gms_internal_zzbb) {
            zzm.zzd(com_google_android_gms_internal_zzbb);
        }

        public /* synthetic */ void zzc(Object obj) {
            zza((zzbb) obj);
        }
    }

    public static class zzb implements com.google.android.gms.internal.zzdv.zzb<zzbb> {
        public void zza(zzbb com_google_android_gms_internal_zzbb) {
            zzm.zzc(com_google_android_gms_internal_zzbb);
        }

        public /* synthetic */ void zzc(Object obj) {
            zza((zzbb) obj);
        }
    }

    public static class zzc implements zzdg {
        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            String str = (String) map.get("request_id");
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Invalid request: " + ((String) map.get("errors")));
            zzm.zzEy.zzX(str);
        }
    }

    static {
        zzEu = TimeUnit.SECONDS.toMillis(10);
        zzpm = new Object();
        zzEv = false;
        zzEw = null;
        zzEx = null;
        zzEy = null;
        zzEz = null;
    }

    public zzm(Context context, com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza, com.google.android.gms.ads.internal.request.zza.zza com_google_android_gms_ads_internal_request_zza_zza) {
        this.zzCE = new Object();
        this.zzDp = com_google_android_gms_ads_internal_request_zza_zza;
        this.mContext = context;
        this.zzDq = com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza;
        synchronized (zzpm) {
            if (!zzEv) {
                zzEy = new zzdl();
                zzEx = new zzdh(context.getApplicationContext(), com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza.zzqb);
                zzEz = new zzc();
                zzEw = new zzdv(this.mContext.getApplicationContext(), this.zzDq.zzqb, (String) zzby.zztW.get(), new zzb(), new zza());
                zzEv = true;
            }
        }
    }

    private JSONObject zza(AdRequestInfoParcel adRequestInfoParcel, String str) {
        Info advertisingIdInfo;
        Throwable e;
        Object obj;
        Map hashMap;
        JSONObject jSONObject = null;
        Bundle bundle = adRequestInfoParcel.zzDy.extras.getBundle("sdk_less_server_data");
        String string = adRequestInfoParcel.zzDy.extras.getString("sdk_less_network_id");
        if (bundle != null) {
            JSONObject zza = zzgn.zza(this.mContext, adRequestInfoParcel, zzp.zzbD().zzD(this.mContext), jSONObject, jSONObject, new zzbr((String) zzby.zztW.get()), jSONObject, jSONObject, new ArrayList());
            if (zza != null) {
                try {
                    advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
                } catch (IOException e2) {
                    e = e2;
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("network_id", string);
                    hashMap.put("request_param", zza);
                    hashMap.put(PoiFragment.ARG_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzp.zzbx().zzx(hashMap);
                    return jSONObject;
                } catch (IllegalStateException e3) {
                    e = e3;
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("network_id", string);
                    hashMap.put("request_param", zza);
                    hashMap.put(PoiFragment.ARG_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                        }
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzp.zzbx().zzx(hashMap);
                    return jSONObject;
                } catch (GooglePlayServicesNotAvailableException e4) {
                    e = e4;
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("network_id", string);
                    hashMap.put("request_param", zza);
                    hashMap.put(PoiFragment.ARG_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                        }
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzp.zzbx().zzx(hashMap);
                    return jSONObject;
                } catch (GooglePlayServicesRepairableException e5) {
                    e = e5;
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("network_id", string);
                    hashMap.put("request_param", zza);
                    hashMap.put(PoiFragment.ARG_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                        }
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzp.zzbx().zzx(hashMap);
                    return jSONObject;
                }
                hashMap = new HashMap();
                hashMap.put("request_id", str);
                hashMap.put("network_id", string);
                hashMap.put("request_param", zza);
                hashMap.put(PoiFragment.ARG_DATA, bundle);
                if (advertisingIdInfo != null) {
                    hashMap.put("adid", advertisingIdInfo.getId());
                    if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                    }
                    hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                }
                try {
                    jSONObject = zzp.zzbx().zzx(hashMap);
                } catch (JSONException e6) {
                }
            }
        }
        return jSONObject;
    }

    protected static void zzc(zzbb com_google_android_gms_internal_zzbb) {
        com_google_android_gms_internal_zzbb.zza("/loadAd", zzEy);
        com_google_android_gms_internal_zzbb.zza("/fetchHttpRequest", zzEx);
        com_google_android_gms_internal_zzbb.zza("/invalidRequest", zzEz);
    }

    protected static void zzd(zzbb com_google_android_gms_internal_zzbb) {
        com_google_android_gms_internal_zzbb.zzb("/loadAd", zzEy);
        com_google_android_gms_internal_zzbb.zzb("/fetchHttpRequest", zzEx);
        com_google_android_gms_internal_zzbb.zzb("/invalidRequest", zzEz);
    }

    private AdResponseParcel zzf(AdRequestInfoParcel adRequestInfoParcel) {
        String uuid = UUID.randomUUID().toString();
        JSONObject zza = zza(adRequestInfoParcel, uuid);
        if (zza == null) {
            return new AdResponseParcel(0);
        }
        long elapsedRealtime = zzp.zzbB().elapsedRealtime();
        Future zzW = zzEy.zzW(uuid);
        com.google.android.gms.ads.internal.util.client.zza.zzIy.post(new C05942(this, zza, uuid));
        try {
            JSONObject jSONObject = (JSONObject) zzW.get(zzEu - (zzp.zzbB().elapsedRealtime() - elapsedRealtime), TimeUnit.MILLISECONDS);
            if (jSONObject == null) {
                return new AdResponseParcel(-1);
            }
            AdResponseParcel zza2 = zzgn.zza(this.mContext, adRequestInfoParcel, jSONObject.toString());
            return (zza2.errorCode == -3 || !TextUtils.isEmpty(zza2.body)) ? zza2 : new AdResponseParcel(3);
        } catch (CancellationException e) {
            return new AdResponseParcel(-1);
        } catch (InterruptedException e2) {
            return new AdResponseParcel(-1);
        } catch (TimeoutException e3) {
            return new AdResponseParcel(2);
        } catch (ExecutionException e4) {
            return new AdResponseParcel(0);
        }
    }

    public void onStop() {
        synchronized (this.zzCE) {
            com.google.android.gms.ads.internal.util.client.zza.zzIy.post(new C05953(this));
        }
    }

    public void zzdG() {
        com.google.android.gms.ads.internal.util.client.zzb.zzaC("SdkLessAdLoaderBackgroundTask started.");
        AdRequestInfoParcel adRequestInfoParcel = new AdRequestInfoParcel(this.zzDq, null, null, -1);
        AdResponseParcel zzf = zzf(adRequestInfoParcel);
        AdRequestInfoParcel adRequestInfoParcel2 = adRequestInfoParcel;
        zzea com_google_android_gms_internal_zzea = null;
        AdSizeParcel adSizeParcel = null;
        com.google.android.gms.ads.internal.util.client.zza.zzIy.post(new C05911(this, new com.google.android.gms.internal.zzhj.zza(adRequestInfoParcel2, zzf, com_google_android_gms_internal_zzea, adSizeParcel, zzf.errorCode, zzp.zzbB().elapsedRealtime(), zzf.zzEb, null)));
    }
}
