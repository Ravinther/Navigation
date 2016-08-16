package com.google.android.gms.analytics.internal;

public class zzz extends zzq<zzaa> {

    private static class zza implements com.google.android.gms.analytics.internal.zzq.zza<zzaa> {
        private final zzf zzLy;
        private final zzaa zzNS;

        public zza(zzf com_google_android_gms_analytics_internal_zzf) {
            this.zzLy = com_google_android_gms_analytics_internal_zzf;
            this.zzNS = new zzaa();
        }

        public void zzc(String str, boolean z) {
            if ("ga_dryRun".equals(str)) {
                this.zzNS.zzNX = z ? 1 : 0;
                return;
            }
            this.zzLy.zzie().zzd("Bool xml configuration name not recognized", str);
        }

        public void zzd(String str, int i) {
            if ("ga_dispatchPeriod".equals(str)) {
                this.zzNS.zzNW = i;
            } else {
                this.zzLy.zzie().zzd("Int xml configuration name not recognized", str);
            }
        }

        public zzaa zzjX() {
            return this.zzNS;
        }

        public /* synthetic */ zzp zzjj() {
            return zzjX();
        }

        public void zzl(String str, String str2) {
        }

        public void zzm(String str, String str2) {
            if ("ga_appName".equals(str)) {
                this.zzNS.zzNT = str2;
            } else if ("ga_appVersion".equals(str)) {
                this.zzNS.zzNU = str2;
            } else if ("ga_logLevel".equals(str)) {
                this.zzNS.zzNV = str2;
            } else {
                this.zzLy.zzie().zzd("String xml configuration name not recognized", str);
            }
        }
    }

    public zzz(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf, new zza(com_google_android_gms_analytics_internal_zzf));
    }
}
