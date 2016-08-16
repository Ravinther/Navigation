package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzkq;

public final class zzy {
    public static zza<Boolean> zzMY;
    public static zza<Boolean> zzMZ;
    public static zza<Integer> zzNA;
    public static zza<Long> zzNB;
    public static zza<Integer> zzNC;
    public static zza<Integer> zzND;
    public static zza<Long> zzNE;
    public static zza<String> zzNF;
    public static zza<Integer> zzNG;
    public static zza<Boolean> zzNH;
    public static zza<Long> zzNI;
    public static zza<Long> zzNJ;
    public static zza<Long> zzNK;
    public static zza<Long> zzNL;
    public static zza<Long> zzNM;
    public static zza<Long> zzNN;
    public static zza<Long> zzNO;
    public static zza<String> zzNa;
    public static zza<Long> zzNb;
    public static zza<Float> zzNc;
    public static zza<Integer> zzNd;
    public static zza<Integer> zzNe;
    public static zza<Integer> zzNf;
    public static zza<Long> zzNg;
    public static zza<Long> zzNh;
    public static zza<Long> zzNi;
    public static zza<Long> zzNj;
    public static zza<Long> zzNk;
    public static zza<Long> zzNl;
    public static zza<Integer> zzNm;
    public static zza<Integer> zzNn;
    public static zza<String> zzNo;
    public static zza<String> zzNp;
    public static zza<String> zzNq;
    public static zza<String> zzNr;
    public static zza<Integer> zzNs;
    public static zza<String> zzNt;
    public static zza<String> zzNu;
    public static zza<Integer> zzNv;
    public static zza<Integer> zzNw;
    public static zza<Integer> zzNx;
    public static zza<Integer> zzNy;
    public static zza<String> zzNz;

    public static final class zza<V> {
        private final V zzNP;
        private final zzkq<V> zzNQ;
        private V zzNR;

        private zza(zzkq<V> com_google_android_gms_internal_zzkq_V, V v) {
            zzx.zzv(com_google_android_gms_internal_zzkq_V);
            this.zzNQ = com_google_android_gms_internal_zzkq_V;
            this.zzNP = v;
        }

        static zza<Float> zza(String str, float f) {
            return zza(str, f, f);
        }

        static zza<Float> zza(String str, float f, float f2) {
            return new zza(zzkq.zza(str, Float.valueOf(f2)), Float.valueOf(f));
        }

        static zza<Integer> zza(String str, int i, int i2) {
            return new zza(zzkq.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
        }

        static zza<Long> zza(String str, long j, long j2) {
            return new zza(zzkq.zza(str, Long.valueOf(j2)), Long.valueOf(j));
        }

        static zza<Boolean> zza(String str, boolean z, boolean z2) {
            return new zza(zzkq.zzg(str, z2), Boolean.valueOf(z));
        }

        static zza<Long> zzc(String str, long j) {
            return zza(str, j, j);
        }

        static zza<String> zzd(String str, String str2, String str3) {
            return new zza(zzkq.zzu(str, str3), str2);
        }

        static zza<Boolean> zzd(String str, boolean z) {
            return zza(str, z, z);
        }

        static zza<Integer> zze(String str, int i) {
            return zza(str, i, i);
        }

        static zza<String> zzn(String str, String str2) {
            return zzd(str, str2, str2);
        }

        public V get() {
            return this.zzNR != null ? this.zzNR : (zzd.zzacF && zzkq.isInitialized()) ? this.zzNQ.zznN() : this.zzNP;
        }
    }

    static {
        zzMY = zza.zzd("analytics.service_enabled", false);
        zzMZ = zza.zzd("analytics.service_client_enabled", true);
        zzNa = zza.zzd("analytics.log_tag", "GAv4", "GAv4-SVC");
        zzNb = zza.zzc("analytics.max_tokens", 60);
        zzNc = zza.zza("analytics.tokens_per_sec", 0.5f);
        zzNd = zza.zza("analytics.max_stored_hits", 2000, 20000);
        zzNe = zza.zze("analytics.max_stored_hits_per_app", 2000);
        zzNf = zza.zze("analytics.max_stored_properties_per_app", 100);
        zzNg = zza.zza("analytics.local_dispatch_millis", 1800000, 120000);
        zzNh = zza.zza("analytics.initial_local_dispatch_millis", 5000, 5000);
        zzNi = zza.zzc("analytics.min_local_dispatch_millis", 120000);
        zzNj = zza.zzc("analytics.max_local_dispatch_millis", 7200000);
        zzNk = zza.zzc("analytics.dispatch_alarm_millis", 7200000);
        zzNl = zza.zzc("analytics.max_dispatch_alarm_millis", 32400000);
        zzNm = zza.zze("analytics.max_hits_per_dispatch", 20);
        zzNn = zza.zze("analytics.max_hits_per_batch", 20);
        zzNo = zza.zzn("analytics.insecure_host", "http://www.google-analytics.com");
        zzNp = zza.zzn("analytics.secure_host", "https://ssl.google-analytics.com");
        zzNq = zza.zzn("analytics.simple_endpoint", "/collect");
        zzNr = zza.zzn("analytics.batching_endpoint", "/batch");
        zzNs = zza.zze("analytics.max_get_length", 2036);
        zzNt = zza.zzd("analytics.batching_strategy.k", zzm.BATCH_BY_COUNT.name(), zzm.BATCH_BY_COUNT.name());
        zzNu = zza.zzn("analytics.compression_strategy.k", zzo.GZIP.name());
        zzNv = zza.zze("analytics.max_hits_per_request.k", 20);
        zzNw = zza.zze("analytics.max_hit_length.k", 8192);
        zzNx = zza.zze("analytics.max_post_length.k", 8192);
        zzNy = zza.zze("analytics.max_batch_post_length", 8192);
        zzNz = zza.zzn("analytics.fallback_responses.k", "404,502");
        zzNA = zza.zze("analytics.batch_retry_interval.seconds.k", 3600);
        zzNB = zza.zzc("analytics.service_monitor_interval", 86400000);
        zzNC = zza.zze("analytics.http_connection.connect_timeout_millis", 60000);
        zzND = zza.zze("analytics.http_connection.read_timeout_millis", 61000);
        zzNE = zza.zzc("analytics.campaigns.time_limit", 86400000);
        zzNF = zza.zzn("analytics.first_party_experiment_id", "");
        zzNG = zza.zze("analytics.first_party_experiment_variant", 0);
        zzNH = zza.zzd("analytics.test.disable_receiver", false);
        zzNI = zza.zza("analytics.service_client.idle_disconnect_millis", 10000, 10000);
        zzNJ = zza.zzc("analytics.service_client.connect_timeout_millis", 5000);
        zzNK = zza.zzc("analytics.service_client.second_connect_delay_millis", 5000);
        zzNL = zza.zzc("analytics.service_client.unexpected_reconnect_millis", 60000);
        zzNM = zza.zzc("analytics.service_client.reconnect_throttle_millis", 1800000);
        zzNN = zza.zzc("analytics.monitoring.sample_period_millis", 86400000);
        zzNO = zza.zzc("analytics.initialization_warning_threshold", 5000);
    }
}
