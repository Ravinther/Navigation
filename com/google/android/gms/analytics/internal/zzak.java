package com.google.android.gms.analytics.internal;

public class zzak extends zzq<zzal> {

    private static class zza extends zzc implements com.google.android.gms.analytics.internal.zzq.zza<zzal> {
        private final zzal zzOD;

        public zza(zzf com_google_android_gms_analytics_internal_zzf) {
            super(com_google_android_gms_analytics_internal_zzf);
            this.zzOD = new zzal();
        }

        public void zzc(String str, boolean z) {
            int i = 1;
            zzal com_google_android_gms_analytics_internal_zzal;
            if ("ga_autoActivityTracking".equals(str)) {
                com_google_android_gms_analytics_internal_zzal = this.zzOD;
                if (!z) {
                    i = 0;
                }
                com_google_android_gms_analytics_internal_zzal.zzOG = i;
            } else if ("ga_anonymizeIp".equals(str)) {
                com_google_android_gms_analytics_internal_zzal = this.zzOD;
                if (!z) {
                    i = 0;
                }
                com_google_android_gms_analytics_internal_zzal.zzOH = i;
            } else if ("ga_reportUncaughtExceptions".equals(str)) {
                com_google_android_gms_analytics_internal_zzal = this.zzOD;
                if (!z) {
                    i = 0;
                }
                com_google_android_gms_analytics_internal_zzal.zzOI = i;
            } else {
                zzd("bool configuration name not recognized", str);
            }
        }

        public void zzd(String str, int i) {
            if ("ga_sessionTimeout".equals(str)) {
                this.zzOD.zzOF = i;
            } else {
                zzd("int configuration name not recognized", str);
            }
        }

        public /* synthetic */ zzp zzjj() {
            return zzkL();
        }

        public zzal zzkL() {
            return this.zzOD;
        }

        public void zzl(String str, String str2) {
            this.zzOD.zzOJ.put(str, str2);
        }

        public void zzm(String str, String str2) {
            if ("ga_trackingId".equals(str)) {
                this.zzOD.zztw = str2;
            } else if ("ga_sampleFrequency".equals(str)) {
                try {
                    this.zzOD.zzOE = Double.parseDouble(str2);
                } catch (NumberFormatException e) {
                    zzc("Error parsing ga_sampleFrequency value", str2, e);
                }
            } else {
                zzd("string configuration name not recognized", str);
            }
        }
    }

    public zzak(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf, new zza(com_google_android_gms_analytics_internal_zzf));
    }
}
