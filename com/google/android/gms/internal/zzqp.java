package com.google.android.gms.internal;

import com.google.android.gms.tagmanager.zzdf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zzqp {

    public static class zza {
        private final com.google.android.gms.internal.zzag.zza zzaRQ;
        private final Map<String, com.google.android.gms.internal.zzag.zza> zzaTS;

        private zza(Map<String, com.google.android.gms.internal.zzag.zza> map, com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza) {
            this.zzaTS = map;
            this.zzaRQ = com_google_android_gms_internal_zzag_zza;
        }

        public static zzb zzBC() {
            return new zzb();
        }

        public String toString() {
            return "Properties: " + zzBD() + " pushAfterEvaluate: " + this.zzaRQ;
        }

        public com.google.android.gms.internal.zzag.zza zzAI() {
            return this.zzaRQ;
        }

        public Map<String, com.google.android.gms.internal.zzag.zza> zzBD() {
            return Collections.unmodifiableMap(this.zzaTS);
        }

        public void zza(String str, com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza) {
            this.zzaTS.put(str, com_google_android_gms_internal_zzag_zza);
        }
    }

    public static class zzb {
        private com.google.android.gms.internal.zzag.zza zzaRQ;
        private final Map<String, com.google.android.gms.internal.zzag.zza> zzaTS;

        private zzb() {
            this.zzaTS = new HashMap();
        }

        public zza zzBE() {
            return new zza(this.zzaRQ, null);
        }

        public zzb zzb(String str, com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza) {
            this.zzaTS.put(str, com_google_android_gms_internal_zzag_zza);
            return this;
        }

        public zzb zzq(com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza) {
            this.zzaRQ = com_google_android_gms_internal_zzag_zza;
            return this;
        }
    }

    public static class zzc {
        private final String zzWs;
        private final List<zze> zzaTT;
        private final Map<String, List<zza>> zzaTU;
        private final int zzaTV;

        private zzc(List<zze> list, Map<String, List<zza>> map, String str, int i) {
            this.zzaTT = Collections.unmodifiableList(list);
            this.zzaTU = Collections.unmodifiableMap(map);
            this.zzWs = str;
            this.zzaTV = i;
        }

        public static zzd zzBF() {
            return new zzd();
        }

        public String getVersion() {
            return this.zzWs;
        }

        public String toString() {
            return "Rules: " + zzBG() + "  Macros: " + this.zzaTU;
        }

        public List<zze> zzBG() {
            return this.zzaTT;
        }

        public Map<String, List<zza>> zzBH() {
            return this.zzaTU;
        }
    }

    public static class zzd {
        private String zzWs;
        private final List<zze> zzaTT;
        private final Map<String, List<zza>> zzaTU;
        private int zzaTV;

        private zzd() {
            this.zzaTT = new ArrayList();
            this.zzaTU = new HashMap();
            this.zzWs = "";
            this.zzaTV = 0;
        }

        public zzc zzBI() {
            return new zzc(this.zzaTU, this.zzWs, this.zzaTV, null);
        }

        public zzd zzb(zze com_google_android_gms_internal_zzqp_zze) {
            this.zzaTT.add(com_google_android_gms_internal_zzqp_zze);
            return this;
        }

        public zzd zzc(zza com_google_android_gms_internal_zzqp_zza) {
            String zzg = zzdf.zzg((com.google.android.gms.internal.zzag.zza) com_google_android_gms_internal_zzqp_zza.zzBD().get(zzae.INSTANCE_NAME.toString()));
            List list = (List) this.zzaTU.get(zzg);
            if (list == null) {
                list = new ArrayList();
                this.zzaTU.put(zzg, list);
            }
            list.add(com_google_android_gms_internal_zzqp_zza);
            return this;
        }

        public zzd zzfk(String str) {
            this.zzWs = str;
            return this;
        }

        public zzd zzjm(int i) {
            this.zzaTV = i;
            return this;
        }
    }

    public static class zze {
        private final List<zza> zzaTW;
        private final List<zza> zzaTX;
        private final List<zza> zzaTY;
        private final List<zza> zzaTZ;
        private final List<zza> zzaUa;
        private final List<zza> zzaUb;
        private final List<String> zzaUc;
        private final List<String> zzaUd;
        private final List<String> zzaUe;
        private final List<String> zzaUf;

        private zze(List<zza> list, List<zza> list2, List<zza> list3, List<zza> list4, List<zza> list5, List<zza> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.zzaTW = Collections.unmodifiableList(list);
            this.zzaTX = Collections.unmodifiableList(list2);
            this.zzaTY = Collections.unmodifiableList(list3);
            this.zzaTZ = Collections.unmodifiableList(list4);
            this.zzaUa = Collections.unmodifiableList(list5);
            this.zzaUb = Collections.unmodifiableList(list6);
            this.zzaUc = Collections.unmodifiableList(list7);
            this.zzaUd = Collections.unmodifiableList(list8);
            this.zzaUe = Collections.unmodifiableList(list9);
            this.zzaUf = Collections.unmodifiableList(list10);
        }

        public static zzf zzBJ() {
            return new zzf();
        }

        public String toString() {
            return "Positive predicates: " + zzBK() + "  Negative predicates: " + zzBL() + "  Add tags: " + zzBM() + "  Remove tags: " + zzBN() + "  Add macros: " + zzBO() + "  Remove macros: " + zzBT();
        }

        public List<zza> zzBK() {
            return this.zzaTW;
        }

        public List<zza> zzBL() {
            return this.zzaTX;
        }

        public List<zza> zzBM() {
            return this.zzaTY;
        }

        public List<zza> zzBN() {
            return this.zzaTZ;
        }

        public List<zza> zzBO() {
            return this.zzaUa;
        }

        public List<String> zzBP() {
            return this.zzaUc;
        }

        public List<String> zzBQ() {
            return this.zzaUd;
        }

        public List<String> zzBR() {
            return this.zzaUe;
        }

        public List<String> zzBS() {
            return this.zzaUf;
        }

        public List<zza> zzBT() {
            return this.zzaUb;
        }
    }

    public static class zzf {
        private final List<zza> zzaTW;
        private final List<zza> zzaTX;
        private final List<zza> zzaTY;
        private final List<zza> zzaTZ;
        private final List<zza> zzaUa;
        private final List<zza> zzaUb;
        private final List<String> zzaUc;
        private final List<String> zzaUd;
        private final List<String> zzaUe;
        private final List<String> zzaUf;

        private zzf() {
            this.zzaTW = new ArrayList();
            this.zzaTX = new ArrayList();
            this.zzaTY = new ArrayList();
            this.zzaTZ = new ArrayList();
            this.zzaUa = new ArrayList();
            this.zzaUb = new ArrayList();
            this.zzaUc = new ArrayList();
            this.zzaUd = new ArrayList();
            this.zzaUe = new ArrayList();
            this.zzaUf = new ArrayList();
        }

        public zze zzBU() {
            return new zze(this.zzaTX, this.zzaTY, this.zzaTZ, this.zzaUa, this.zzaUb, this.zzaUc, this.zzaUd, this.zzaUe, this.zzaUf, null);
        }

        public zzf zzd(zza com_google_android_gms_internal_zzqp_zza) {
            this.zzaTW.add(com_google_android_gms_internal_zzqp_zza);
            return this;
        }

        public zzf zze(zza com_google_android_gms_internal_zzqp_zza) {
            this.zzaTX.add(com_google_android_gms_internal_zzqp_zza);
            return this;
        }

        public zzf zzf(zza com_google_android_gms_internal_zzqp_zza) {
            this.zzaTY.add(com_google_android_gms_internal_zzqp_zza);
            return this;
        }

        public zzf zzfl(String str) {
            this.zzaUe.add(str);
            return this;
        }

        public zzf zzfm(String str) {
            this.zzaUf.add(str);
            return this;
        }

        public zzf zzfn(String str) {
            this.zzaUc.add(str);
            return this;
        }

        public zzf zzfo(String str) {
            this.zzaUd.add(str);
            return this;
        }

        public zzf zzg(zza com_google_android_gms_internal_zzqp_zza) {
            this.zzaTZ.add(com_google_android_gms_internal_zzqp_zza);
            return this;
        }

        public zzf zzh(zza com_google_android_gms_internal_zzqp_zza) {
            this.zzaUa.add(com_google_android_gms_internal_zzqp_zza);
            return this;
        }

        public zzf zzi(zza com_google_android_gms_internal_zzqp_zza) {
            this.zzaUb.add(com_google_android_gms_internal_zzqp_zza);
            return this;
        }
    }

    public static class zzg extends Exception {
        public zzg(String str) {
            super(str);
        }
    }

    public static com.google.android.gms.internal.zzag.zza zzo(com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza) {
        com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza2 = new com.google.android.gms.internal.zzag.zza();
        com_google_android_gms_internal_zzag_zza2.type = com_google_android_gms_internal_zzag_zza.type;
        com_google_android_gms_internal_zzag_zza2.zzjd = (int[]) com_google_android_gms_internal_zzag_zza.zzjd.clone();
        if (com_google_android_gms_internal_zzag_zza.zzje) {
            com_google_android_gms_internal_zzag_zza2.zzje = com_google_android_gms_internal_zzag_zza.zzje;
        }
        return com_google_android_gms_internal_zzag_zza2;
    }
}
