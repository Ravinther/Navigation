package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzdv.zzd;
import java.util.Map;
import java.util.concurrent.Future;

@zzgk
public final class zzgo {
    private String zzBm;
    private String zzEZ;
    private zzie<zzgq> zzFa;
    zzd zzFb;
    public final zzdg zzFc;
    public final zzdg zzFd;
    zzip zzoL;
    private final Object zzpc;

    /* renamed from: com.google.android.gms.internal.zzgo.1 */
    class C09381 implements zzdg {
        final /* synthetic */ zzgo zzFe;

        C09381(zzgo com_google_android_gms_internal_zzgo) {
            this.zzFe = com_google_android_gms_internal_zzgo;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            synchronized (this.zzFe.zzpc) {
                if (this.zzFe.zzFa.isDone()) {
                } else if (this.zzFe.zzBm.equals(map.get("request_id"))) {
                    zzgq com_google_android_gms_internal_zzgq = new zzgq(1, map);
                    zzb.zzaE("Invalid " + com_google_android_gms_internal_zzgq.getType() + " request error: " + com_google_android_gms_internal_zzgq.zzfK());
                    this.zzFe.zzFa.zzf(com_google_android_gms_internal_zzgq);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgo.2 */
    class C09392 implements zzdg {
        final /* synthetic */ zzgo zzFe;

        C09392(zzgo com_google_android_gms_internal_zzgo) {
            this.zzFe = com_google_android_gms_internal_zzgo;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            synchronized (this.zzFe.zzpc) {
                if (this.zzFe.zzFa.isDone()) {
                    return;
                }
                zzgq com_google_android_gms_internal_zzgq = new zzgq(-2, map);
                if (this.zzFe.zzBm.equals(com_google_android_gms_internal_zzgq.getRequestId())) {
                    String url = com_google_android_gms_internal_zzgq.getUrl();
                    if (url == null) {
                        zzb.zzaE("URL missing in loadAdUrl GMSG.");
                        return;
                    }
                    if (url.contains("%40mediation_adapters%40")) {
                        String replaceAll = url.replaceAll("%40mediation_adapters%40", zzhp.zza(com_google_android_gms_internal_zzip.getContext(), (String) map.get("check_adapters"), this.zzFe.zzEZ));
                        com_google_android_gms_internal_zzgq.setUrl(replaceAll);
                        zzb.m1445v("Ad request URL modified to " + replaceAll);
                    }
                    this.zzFe.zzFa.zzf(com_google_android_gms_internal_zzgq);
                    return;
                }
                zzb.zzaE(com_google_android_gms_internal_zzgq.getRequestId() + " ==== " + this.zzFe.zzBm);
            }
        }
    }

    public zzgo(String str, String str2) {
        this.zzpc = new Object();
        this.zzFa = new zzie();
        this.zzFc = new C09381(this);
        this.zzFd = new C09392(this);
        this.zzEZ = str2;
        this.zzBm = str;
    }

    public void zzb(zzd com_google_android_gms_internal_zzdv_zzd) {
        this.zzFb = com_google_android_gms_internal_zzdv_zzd;
    }

    public void zze(zzip com_google_android_gms_internal_zzip) {
        this.zzoL = com_google_android_gms_internal_zzip;
    }

    public zzd zzfH() {
        return this.zzFb;
    }

    public Future<zzgq> zzfI() {
        return this.zzFa;
    }

    public void zzfJ() {
        if (this.zzoL != null) {
            this.zzoL.destroy();
            this.zzoL = null;
        }
    }
}
