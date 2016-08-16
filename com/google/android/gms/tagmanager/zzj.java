package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzj extends zzdd {
    private static final String ID;
    private static final String URL;
    private static final String zzaOL;
    private static final String zzaOM;
    static final String zzaON;
    private static final Set<String> zzaOO;
    private final Context mContext;
    private final zza zzaOP;

    public interface zza {
        zzar zzzy();
    }

    /* renamed from: com.google.android.gms.tagmanager.zzj.1 */
    class C10231 implements zza {
        final /* synthetic */ Context zzrn;

        C10231(Context context) {
            this.zzrn = context;
        }

        public zzar zzzy() {
            return zzz.zzaM(this.zzrn);
        }
    }

    static {
        ID = zzad.ARBITRARY_PIXEL.toString();
        URL = zzae.URL.toString();
        zzaOL = zzae.ADDITIONAL_PARAMS.toString();
        zzaOM = zzae.UNREPEATABLE.toString();
        zzaON = "gtm_" + ID + "_unrepeatable";
        zzaOO = new HashSet();
    }

    public zzj(Context context) {
        this(context, new C10231(context));
    }

    zzj(Context context, zza com_google_android_gms_tagmanager_zzj_zza) {
        super(ID, URL);
        this.zzaOP = com_google_android_gms_tagmanager_zzj_zza;
        this.mContext = context;
    }

    private synchronized boolean zzeq(String str) {
        boolean z = true;
        synchronized (this) {
            if (!zzes(str)) {
                if (zzer(str)) {
                    zzaOO.add(str);
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    public void zzI(Map<String, com.google.android.gms.internal.zzag.zza> map) {
        String zzg = map.get(zzaOM) != null ? zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(zzaOM)) : null;
        if (zzg == null || !zzeq(zzg)) {
            Builder buildUpon = Uri.parse(zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(URL))).buildUpon();
            com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza = (com.google.android.gms.internal.zzag.zza) map.get(zzaOL);
            if (com_google_android_gms_internal_zzag_zza != null) {
                Object zzl = zzdf.zzl(com_google_android_gms_internal_zzag_zza);
                if (zzl instanceof List) {
                    for (Object zzl2 : (List) zzl2) {
                        if (zzl2 instanceof Map) {
                            for (Entry entry : ((Map) zzl2).entrySet()) {
                                buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                            }
                        } else {
                            zzbg.m1447e("ArbitraryPixel: additional params contains non-map: not sending partial hit: " + buildUpon.build().toString());
                            return;
                        }
                    }
                }
                zzbg.m1447e("ArbitraryPixel: additional params not a list: not sending partial hit: " + buildUpon.build().toString());
                return;
            }
            String uri = buildUpon.build().toString();
            this.zzaOP.zzzy().zzeH(uri);
            zzbg.m1448v("ArbitraryPixel: url = " + uri);
            if (zzg != null) {
                synchronized (zzj.class) {
                    zzaOO.add(zzg);
                    zzcv.zzb(this.mContext, zzaON, zzg, "true");
                }
            }
        }
    }

    boolean zzer(String str) {
        return this.mContext.getSharedPreferences(zzaON, 0).contains(str);
    }

    boolean zzes(String str) {
        return zzaOO.contains(str);
    }
}
