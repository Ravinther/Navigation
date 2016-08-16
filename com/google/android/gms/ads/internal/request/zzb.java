package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.zzm;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzan;
import com.google.android.gms.internal.zzbk;
import com.google.android.gms.internal.zzea;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhq;
import com.google.android.gms.internal.zzhu;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzb extends zzhq implements com.google.android.gms.ads.internal.request.zzc.zza {
    private final Context mContext;
    private final Object zzCE;
    AdResponseParcel zzCG;
    private final com.google.android.gms.ads.internal.request.zza.zza zzDp;
    private final com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza zzDq;
    zzhq zzDr;
    Object zzpc;
    private final zzan zzwh;
    zzea zzye;

    /* renamed from: com.google.android.gms.ads.internal.request.zzb.1 */
    class C05891 implements Runnable {
        final /* synthetic */ zzb zzDs;

        C05891(zzb com_google_android_gms_ads_internal_request_zzb) {
            this.zzDs = com_google_android_gms_ads_internal_request_zzb;
        }

        public void run() {
            this.zzDs.onStop();
        }
    }

    @zzgk
    static final class zza extends Exception {
        private final int zzCI;

        public zza(String str, int i) {
            super(str);
            this.zzCI = i;
        }

        public int getErrorCode() {
            return this.zzCI;
        }
    }

    public zzb(Context context, com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza, zzan com_google_android_gms_internal_zzan, com.google.android.gms.ads.internal.request.zza.zza com_google_android_gms_ads_internal_request_zza_zza) {
        this.zzCE = new Object();
        this.zzpc = new Object();
        this.zzDp = com_google_android_gms_ads_internal_request_zza_zza;
        this.mContext = context;
        this.zzDq = com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza;
        this.zzwh = com_google_android_gms_internal_zzan;
    }

    public void onStop() {
        synchronized (this.zzCE) {
            if (this.zzDr != null) {
                this.zzDr.cancel();
            }
        }
    }

    zzhq zzb(AdRequestInfoParcel adRequestInfoParcel) {
        return zzc.zza(this.mContext, adRequestInfoParcel, this);
    }

    public void zzb(AdResponseParcel adResponseParcel) {
        synchronized (this.zzpc) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Received ad response.");
            this.zzCG = adResponseParcel;
            this.zzpc.notify();
        }
    }

    protected AdSizeParcel zzc(AdRequestInfoParcel adRequestInfoParcel) throws zza {
        if (this.zzCG.zzEa == null) {
            throw new zza("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] split = this.zzCG.zzEa.split("x");
        if (split.length != 2) {
            throw new zza("Invalid ad size format from the ad response: " + this.zzCG.zzEa, 0);
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            for (AdSizeParcel adSizeParcel : adRequestInfoParcel.zzqf.zzsI) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i = adSizeParcel.width == -1 ? (int) (((float) adSizeParcel.widthPixels) / f) : adSizeParcel.width;
                int i2 = adSizeParcel.height == -2 ? (int) (((float) adSizeParcel.heightPixels) / f) : adSizeParcel.height;
                if (parseInt == i && parseInt2 == i2) {
                    return new AdSizeParcel(adSizeParcel, adRequestInfoParcel.zzqf.zzsI);
                }
            }
            throw new zza("The ad size from the ad response was not one of the requested sizes: " + this.zzCG.zzEa, 0);
        } catch (NumberFormatException e) {
            throw new zza("Invalid ad size number from the ad response: " + this.zzCG.zzEa, 0);
        }
    }

    public void zzdG() {
        zza e;
        synchronized (this.zzpc) {
            long j;
            AdSizeParcel adSizeParcel;
            JSONObject jSONObject;
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("AdLoaderBackgroundTask started.");
            String zzb = this.zzwh.zzab().zzb(this.mContext);
            String clientId = zzm.zzq(this.mContext).getClientId();
            long elapsedRealtime = zzp.zzbB().elapsedRealtime();
            AdRequestInfoParcel adRequestInfoParcel = new AdRequestInfoParcel(this.zzDq, zzb, clientId, elapsedRealtime);
            int i = -2;
            long j2 = -1;
            AdSizeParcel zzc;
            try {
                zzhq zzb2 = zzb(adRequestInfoParcel);
                synchronized (this.zzCE) {
                    this.zzDr = zzb2;
                    if (this.zzDr == null) {
                        throw new zza("Could not start the ad request service.", 0);
                    }
                }
                zzi(elapsedRealtime);
                j2 = zzp.zzbB().elapsedRealtime();
                zzfw();
                zzc = adRequestInfoParcel.zzqf.zzsI != null ? zzc(adRequestInfoParcel) : null;
                try {
                    zzw(this.zzCG.zzEh);
                    j = j2;
                    adSizeParcel = zzc;
                } catch (zza e2) {
                    e = e2;
                    i = e.getErrorCode();
                    if (i == 3 || i == -1) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaD(e.getMessage());
                    } else {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaE(e.getMessage());
                    }
                    if (this.zzCG == null) {
                        this.zzCG = new AdResponseParcel(i);
                    } else {
                        this.zzCG = new AdResponseParcel(i, this.zzCG.zzyA);
                    }
                    zzhu.zzHK.post(new C05891(this));
                    j = j2;
                    adSizeParcel = zzc;
                    if (!TextUtils.isEmpty(this.zzCG.zzEf)) {
                        try {
                            jSONObject = new JSONObject(this.zzCG.zzEf);
                        } catch (Throwable e3) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error parsing the JSON for Active View.", e3);
                        }
                        this.zzDp.zza(new com.google.android.gms.internal.zzhj.zza(adRequestInfoParcel, this.zzCG, this.zzye, adSizeParcel, i, j, this.zzCG.zzEb, jSONObject));
                    }
                    jSONObject = null;
                    this.zzDp.zza(new com.google.android.gms.internal.zzhj.zza(adRequestInfoParcel, this.zzCG, this.zzye, adSizeParcel, i, j, this.zzCG.zzEb, jSONObject));
                }
            } catch (zza e4) {
                e = e4;
                zzc = null;
            }
            if (TextUtils.isEmpty(this.zzCG.zzEf)) {
                jSONObject = new JSONObject(this.zzCG.zzEf);
                this.zzDp.zza(new com.google.android.gms.internal.zzhj.zza(adRequestInfoParcel, this.zzCG, this.zzye, adSizeParcel, i, j, this.zzCG.zzEb, jSONObject));
            }
            jSONObject = null;
            this.zzDp.zza(new com.google.android.gms.internal.zzhj.zza(adRequestInfoParcel, this.zzCG, this.zzye, adSizeParcel, i, j, this.zzCG.zzEb, jSONObject));
        }
    }

    protected boolean zze(long j) throws zza {
        long elapsedRealtime = 60000 - (zzp.zzbB().elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzpc.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            throw new zza("Ad request cancelled.", -1);
        }
    }

    protected void zzfw() throws zza {
        if (this.zzCG.errorCode != -3) {
            if (TextUtils.isEmpty(this.zzCG.body)) {
                throw new zza("No fill from ad server.", 3);
            }
            zzp.zzbA().zza(this.mContext, this.zzCG.zzDG);
            if (this.zzCG.zzDX) {
                try {
                    this.zzye = new zzea(this.zzCG.body);
                } catch (JSONException e) {
                    throw new zza("Could not parse mediation config: " + this.zzCG.body, 0);
                }
            }
        }
    }

    protected void zzi(long j) throws zza {
        while (zze(j)) {
            if (this.zzCG != null) {
                synchronized (this.zzCE) {
                    this.zzDr = null;
                }
                if (this.zzCG.errorCode != -2 && this.zzCG.errorCode != -3) {
                    throw new zza("There was a problem getting an ad response. ErrorCode: " + this.zzCG.errorCode, this.zzCG.errorCode);
                }
                return;
            }
        }
        throw new zza("Timed out waiting for ad response.", 2);
    }

    protected void zzw(boolean z) {
        zzp.zzbA().zzA(z);
        zzbk zzF = zzp.zzbA().zzF(this.mContext);
        if (zzF != null && !zzF.isAlive()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("start fetching content...");
            zzF.zzct();
        }
    }
}
