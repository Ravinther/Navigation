package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import org.json.JSONObject;

@zzgk
public class zzez {
    private final boolean zzzI;
    private final boolean zzzJ;
    private final boolean zzzK;
    private final boolean zzzL;
    private final boolean zzzM;

    public static final class zza {
        private boolean zzzI;
        private boolean zzzJ;
        private boolean zzzK;
        private boolean zzzL;
        private boolean zzzM;

        public zzez zzea() {
            return new zzez();
        }

        public zza zzo(boolean z) {
            this.zzzI = z;
            return this;
        }

        public zza zzp(boolean z) {
            this.zzzJ = z;
            return this;
        }

        public zza zzq(boolean z) {
            this.zzzK = z;
            return this;
        }

        public zza zzr(boolean z) {
            this.zzzL = z;
            return this;
        }

        public zza zzs(boolean z) {
            this.zzzM = z;
            return this;
        }
    }

    private zzez(zza com_google_android_gms_internal_zzez_zza) {
        this.zzzI = com_google_android_gms_internal_zzez_zza.zzzI;
        this.zzzJ = com_google_android_gms_internal_zzez_zza.zzzJ;
        this.zzzK = com_google_android_gms_internal_zzez_zza.zzzK;
        this.zzzL = com_google_android_gms_internal_zzez_zza.zzzL;
        this.zzzM = com_google_android_gms_internal_zzez_zza.zzzM;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject().put("sms", this.zzzI).put("tel", this.zzzJ).put("calendar", this.zzzK).put("storePicture", this.zzzL).put("inlineVideo", this.zzzM);
        } catch (Throwable e) {
            zzb.zzb("Error occured while obtaining the MRAID capabilities.", e);
            return null;
        }
    }
}
