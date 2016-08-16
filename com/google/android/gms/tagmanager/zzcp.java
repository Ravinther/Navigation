package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzqp;
import com.google.android.gms.internal.zzqp.zze;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

class zzcp {
    private static final zzbw<com.google.android.gms.internal.zzag.zza> zzaRy;
    private final DataLayer zzaOT;
    private final zzah zzaRA;
    private final Map<String, zzak> zzaRB;
    private final Map<String, zzak> zzaRC;
    private final Map<String, zzak> zzaRD;
    private final zzl<com.google.android.gms.internal.zzqp.zza, zzbw<com.google.android.gms.internal.zzag.zza>> zzaRE;
    private final zzl<String, zzb> zzaRF;
    private final Set<zze> zzaRG;
    private final Map<String, zzc> zzaRH;
    private volatile String zzaRI;
    private int zzaRJ;
    private final com.google.android.gms.internal.zzqp.zzc zzaRz;

    /* renamed from: com.google.android.gms.tagmanager.zzcp.1 */
    class C10151 implements com.google.android.gms.tagmanager.zzm.zza<com.google.android.gms.internal.zzqp.zza, zzbw<com.google.android.gms.internal.zzag.zza>> {
        final /* synthetic */ zzcp zzaRK;

        C10151(zzcp com_google_android_gms_tagmanager_zzcp) {
            this.zzaRK = com_google_android_gms_tagmanager_zzcp;
        }

        public /* synthetic */ int sizeOf(Object x0, Object x1) {
            return zza((com.google.android.gms.internal.zzqp.zza) x0, (zzbw) x1);
        }

        public int zza(com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza, zzbw<com.google.android.gms.internal.zzag.zza> com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza) {
            return ((com.google.android.gms.internal.zzag.zza) com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza.getObject()).zzDw();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcp.2 */
    class C10162 implements com.google.android.gms.tagmanager.zzm.zza<String, zzb> {
        final /* synthetic */ zzcp zzaRK;

        C10162(zzcp com_google_android_gms_tagmanager_zzcp) {
            this.zzaRK = com_google_android_gms_tagmanager_zzcp;
        }

        public /* synthetic */ int sizeOf(Object x0, Object x1) {
            return zza((String) x0, (zzb) x1);
        }

        public int zza(String str, zzb com_google_android_gms_tagmanager_zzcp_zzb) {
            return str.length() + com_google_android_gms_tagmanager_zzcp_zzb.getSize();
        }
    }

    interface zza {
        void zza(zze com_google_android_gms_internal_zzqp_zze, Set<com.google.android.gms.internal.zzqp.zza> set, Set<com.google.android.gms.internal.zzqp.zza> set2, zzck com_google_android_gms_tagmanager_zzck);
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcp.3 */
    class C10173 implements zza {
        final /* synthetic */ zzcp zzaRK;
        final /* synthetic */ Map zzaRL;
        final /* synthetic */ Map zzaRM;
        final /* synthetic */ Map zzaRN;
        final /* synthetic */ Map zzaRO;

        C10173(zzcp com_google_android_gms_tagmanager_zzcp, Map map, Map map2, Map map3, Map map4) {
            this.zzaRK = com_google_android_gms_tagmanager_zzcp;
            this.zzaRL = map;
            this.zzaRM = map2;
            this.zzaRN = map3;
            this.zzaRO = map4;
        }

        public void zza(zze com_google_android_gms_internal_zzqp_zze, Set<com.google.android.gms.internal.zzqp.zza> set, Set<com.google.android.gms.internal.zzqp.zza> set2, zzck com_google_android_gms_tagmanager_zzck) {
            List list = (List) this.zzaRL.get(com_google_android_gms_internal_zzqp_zze);
            List list2 = (List) this.zzaRM.get(com_google_android_gms_internal_zzqp_zze);
            if (list != null) {
                set.addAll(list);
                com_google_android_gms_tagmanager_zzck.zzAk().zzc(list, list2);
            }
            list = (List) this.zzaRN.get(com_google_android_gms_internal_zzqp_zze);
            list2 = (List) this.zzaRO.get(com_google_android_gms_internal_zzqp_zze);
            if (list != null) {
                set2.addAll(list);
                com_google_android_gms_tagmanager_zzck.zzAl().zzc(list, list2);
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcp.4 */
    class C10184 implements zza {
        final /* synthetic */ zzcp zzaRK;

        C10184(zzcp com_google_android_gms_tagmanager_zzcp) {
            this.zzaRK = com_google_android_gms_tagmanager_zzcp;
        }

        public void zza(zze com_google_android_gms_internal_zzqp_zze, Set<com.google.android.gms.internal.zzqp.zza> set, Set<com.google.android.gms.internal.zzqp.zza> set2, zzck com_google_android_gms_tagmanager_zzck) {
            set.addAll(com_google_android_gms_internal_zzqp_zze.zzBM());
            set2.addAll(com_google_android_gms_internal_zzqp_zze.zzBN());
            com_google_android_gms_tagmanager_zzck.zzAm().zzc(com_google_android_gms_internal_zzqp_zze.zzBM(), com_google_android_gms_internal_zzqp_zze.zzBR());
            com_google_android_gms_tagmanager_zzck.zzAn().zzc(com_google_android_gms_internal_zzqp_zze.zzBN(), com_google_android_gms_internal_zzqp_zze.zzBS());
        }
    }

    private static class zzb {
        private zzbw<com.google.android.gms.internal.zzag.zza> zzaRP;
        private com.google.android.gms.internal.zzag.zza zzaRQ;

        public zzb(zzbw<com.google.android.gms.internal.zzag.zza> com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza, com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza) {
            this.zzaRP = com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza;
            this.zzaRQ = com_google_android_gms_internal_zzag_zza;
        }

        public int getSize() {
            return (this.zzaRQ == null ? 0 : this.zzaRQ.zzDw()) + ((com.google.android.gms.internal.zzag.zza) this.zzaRP.getObject()).zzDw();
        }

        public zzbw<com.google.android.gms.internal.zzag.zza> zzAH() {
            return this.zzaRP;
        }

        public com.google.android.gms.internal.zzag.zza zzAI() {
            return this.zzaRQ;
        }
    }

    private static class zzc {
        private final Set<zze> zzaRG;
        private final Map<zze, List<com.google.android.gms.internal.zzqp.zza>> zzaRR;
        private final Map<zze, List<com.google.android.gms.internal.zzqp.zza>> zzaRS;
        private final Map<zze, List<String>> zzaRT;
        private final Map<zze, List<String>> zzaRU;
        private com.google.android.gms.internal.zzqp.zza zzaRV;

        public zzc() {
            this.zzaRG = new HashSet();
            this.zzaRR = new HashMap();
            this.zzaRT = new HashMap();
            this.zzaRS = new HashMap();
            this.zzaRU = new HashMap();
        }

        public Set<zze> zzAJ() {
            return this.zzaRG;
        }

        public Map<zze, List<com.google.android.gms.internal.zzqp.zza>> zzAK() {
            return this.zzaRR;
        }

        public Map<zze, List<String>> zzAL() {
            return this.zzaRT;
        }

        public Map<zze, List<String>> zzAM() {
            return this.zzaRU;
        }

        public Map<zze, List<com.google.android.gms.internal.zzqp.zza>> zzAN() {
            return this.zzaRS;
        }

        public com.google.android.gms.internal.zzqp.zza zzAO() {
            return this.zzaRV;
        }

        public void zza(zze com_google_android_gms_internal_zzqp_zze) {
            this.zzaRG.add(com_google_android_gms_internal_zzqp_zze);
        }

        public void zza(zze com_google_android_gms_internal_zzqp_zze, com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza) {
            List list = (List) this.zzaRR.get(com_google_android_gms_internal_zzqp_zze);
            if (list == null) {
                list = new ArrayList();
                this.zzaRR.put(com_google_android_gms_internal_zzqp_zze, list);
            }
            list.add(com_google_android_gms_internal_zzqp_zza);
        }

        public void zza(zze com_google_android_gms_internal_zzqp_zze, String str) {
            List list = (List) this.zzaRT.get(com_google_android_gms_internal_zzqp_zze);
            if (list == null) {
                list = new ArrayList();
                this.zzaRT.put(com_google_android_gms_internal_zzqp_zze, list);
            }
            list.add(str);
        }

        public void zzb(com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza) {
            this.zzaRV = com_google_android_gms_internal_zzqp_zza;
        }

        public void zzb(zze com_google_android_gms_internal_zzqp_zze, com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza) {
            List list = (List) this.zzaRS.get(com_google_android_gms_internal_zzqp_zze);
            if (list == null) {
                list = new ArrayList();
                this.zzaRS.put(com_google_android_gms_internal_zzqp_zze, list);
            }
            list.add(com_google_android_gms_internal_zzqp_zza);
        }

        public void zzb(zze com_google_android_gms_internal_zzqp_zze, String str) {
            List list = (List) this.zzaRU.get(com_google_android_gms_internal_zzqp_zze);
            if (list == null) {
                list = new ArrayList();
                this.zzaRU.put(com_google_android_gms_internal_zzqp_zze, list);
            }
            list.add(str);
        }
    }

    static {
        zzaRy = new zzbw(zzdf.zzBg(), true);
    }

    public zzcp(Context context, com.google.android.gms.internal.zzqp.zzc com_google_android_gms_internal_zzqp_zzc, DataLayer dataLayer, com.google.android.gms.tagmanager.zzt.zza com_google_android_gms_tagmanager_zzt_zza, com.google.android.gms.tagmanager.zzt.zza com_google_android_gms_tagmanager_zzt_zza2, zzah com_google_android_gms_tagmanager_zzah) {
        if (com_google_android_gms_internal_zzqp_zzc == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.zzaRz = com_google_android_gms_internal_zzqp_zzc;
        this.zzaRG = new HashSet(com_google_android_gms_internal_zzqp_zzc.zzBG());
        this.zzaOT = dataLayer;
        this.zzaRA = com_google_android_gms_tagmanager_zzah;
        this.zzaRE = new zzm().zza(1048576, new C10151(this));
        this.zzaRF = new zzm().zza(1048576, new C10162(this));
        this.zzaRB = new HashMap();
        zzb(new zzj(context));
        zzb(new zzt(com_google_android_gms_tagmanager_zzt_zza2));
        zzb(new zzx(dataLayer));
        zzb(new zzdg(context, dataLayer));
        zzb(new zzdb(context, dataLayer));
        this.zzaRC = new HashMap();
        zzc(new zzr());
        zzc(new zzae());
        zzc(new zzaf());
        zzc(new zzam());
        zzc(new zzan());
        zzc(new zzbc());
        zzc(new zzbd());
        zzc(new zzcf());
        zzc(new zzcy());
        this.zzaRD = new HashMap();
        zza(new zzb(context));
        zza(new zzc(context));
        zza(new zze(context));
        zza(new zzf(context));
        zza(new zzg(context));
        zza(new zzh(context));
        zza(new zzi(context));
        zza(new zzn());
        zza(new zzq(this.zzaRz.getVersion()));
        zza(new zzt(com_google_android_gms_tagmanager_zzt_zza));
        zza(new zzv(dataLayer));
        zza(new zzaa(context));
        zza(new zzab());
        zza(new zzad());
        zza(new zzai(this));
        zza(new zzao());
        zza(new zzap());
        zza(new zzaw(context));
        zza(new zzay());
        zza(new zzbb());
        zza(new zzbi());
        zza(new zzbk(context));
        zza(new zzbx());
        zza(new zzbz());
        zza(new zzcc());
        zza(new zzce());
        zza(new zzcg(context));
        zza(new zzcq());
        zza(new zzcr());
        zza(new zzda());
        zza(new zzdh());
        this.zzaRH = new HashMap();
        for (zze com_google_android_gms_internal_zzqp_zze : this.zzaRG) {
            if (com_google_android_gms_tagmanager_zzah.zzAb()) {
                zza(com_google_android_gms_internal_zzqp_zze.zzBO(), com_google_android_gms_internal_zzqp_zze.zzBP(), "add macro");
                zza(com_google_android_gms_internal_zzqp_zze.zzBT(), com_google_android_gms_internal_zzqp_zze.zzBQ(), "remove macro");
                zza(com_google_android_gms_internal_zzqp_zze.zzBM(), com_google_android_gms_internal_zzqp_zze.zzBR(), "add tag");
                zza(com_google_android_gms_internal_zzqp_zze.zzBN(), com_google_android_gms_internal_zzqp_zze.zzBS(), "remove tag");
            }
            int i = 0;
            while (i < com_google_android_gms_internal_zzqp_zze.zzBO().size()) {
                com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza = (com.google.android.gms.internal.zzqp.zza) com_google_android_gms_internal_zzqp_zze.zzBO().get(i);
                String str = "Unknown";
                if (com_google_android_gms_tagmanager_zzah.zzAb() && i < com_google_android_gms_internal_zzqp_zze.zzBP().size()) {
                    str = (String) com_google_android_gms_internal_zzqp_zze.zzBP().get(i);
                }
                zzc zzi = zzi(this.zzaRH, zza(com_google_android_gms_internal_zzqp_zza));
                zzi.zza(com_google_android_gms_internal_zzqp_zze);
                zzi.zza(com_google_android_gms_internal_zzqp_zze, com_google_android_gms_internal_zzqp_zza);
                zzi.zza(com_google_android_gms_internal_zzqp_zze, str);
                i++;
            }
            i = 0;
            while (i < com_google_android_gms_internal_zzqp_zze.zzBT().size()) {
                com_google_android_gms_internal_zzqp_zza = (com.google.android.gms.internal.zzqp.zza) com_google_android_gms_internal_zzqp_zze.zzBT().get(i);
                str = "Unknown";
                if (com_google_android_gms_tagmanager_zzah.zzAb() && i < com_google_android_gms_internal_zzqp_zze.zzBQ().size()) {
                    str = (String) com_google_android_gms_internal_zzqp_zze.zzBQ().get(i);
                }
                zzi = zzi(this.zzaRH, zza(com_google_android_gms_internal_zzqp_zza));
                zzi.zza(com_google_android_gms_internal_zzqp_zze);
                zzi.zzb(com_google_android_gms_internal_zzqp_zze, com_google_android_gms_internal_zzqp_zza);
                zzi.zzb(com_google_android_gms_internal_zzqp_zze, str);
                i++;
            }
        }
        for (Entry entry : this.zzaRz.zzBH().entrySet()) {
            for (com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza2 : (List) entry.getValue()) {
                if (!zzdf.zzk((com.google.android.gms.internal.zzag.zza) com_google_android_gms_internal_zzqp_zza2.zzBD().get(zzae.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    zzi(this.zzaRH, (String) entry.getKey()).zzb(com_google_android_gms_internal_zzqp_zza2);
                }
            }
        }
    }

    private String zzAG() {
        if (this.zzaRJ <= 1) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.zzaRJ));
        for (int i = 2; i < this.zzaRJ; i++) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(": ");
        return stringBuilder.toString();
    }

    private zzbw<com.google.android.gms.internal.zzag.zza> zza(com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza, Set<String> set, zzdi com_google_android_gms_tagmanager_zzdi) {
        if (!com_google_android_gms_internal_zzag_zza.zzje) {
            return new zzbw(com_google_android_gms_internal_zzag_zza, true);
        }
        com.google.android.gms.internal.zzag.zza zzo;
        int i;
        zzbw zza;
        switch (com_google_android_gms_internal_zzag_zza.type) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                zzo = zzqp.zzo(com_google_android_gms_internal_zzag_zza);
                zzo.zziV = new com.google.android.gms.internal.zzag.zza[com_google_android_gms_internal_zzag_zza.zziV.length];
                for (i = 0; i < com_google_android_gms_internal_zzag_zza.zziV.length; i++) {
                    zza = zza(com_google_android_gms_internal_zzag_zza.zziV[i], (Set) set, com_google_android_gms_tagmanager_zzdi.zzjf(i));
                    if (zza == zzaRy) {
                        return zzaRy;
                    }
                    zzo.zziV[i] = (com.google.android.gms.internal.zzag.zza) zza.getObject();
                }
                return new zzbw(zzo, false);
            case TTSConst.TTSUNICODE /*3*/:
                zzo = zzqp.zzo(com_google_android_gms_internal_zzag_zza);
                if (com_google_android_gms_internal_zzag_zza.zziW.length != com_google_android_gms_internal_zzag_zza.zziX.length) {
                    zzbg.m1447e("Invalid serving value: " + com_google_android_gms_internal_zzag_zza.toString());
                    return zzaRy;
                }
                zzo.zziW = new com.google.android.gms.internal.zzag.zza[com_google_android_gms_internal_zzag_zza.zziW.length];
                zzo.zziX = new com.google.android.gms.internal.zzag.zza[com_google_android_gms_internal_zzag_zza.zziW.length];
                for (i = 0; i < com_google_android_gms_internal_zzag_zza.zziW.length; i++) {
                    zza = zza(com_google_android_gms_internal_zzag_zza.zziW[i], (Set) set, com_google_android_gms_tagmanager_zzdi.zzjg(i));
                    zzbw zza2 = zza(com_google_android_gms_internal_zzag_zza.zziX[i], (Set) set, com_google_android_gms_tagmanager_zzdi.zzjh(i));
                    if (zza == zzaRy || zza2 == zzaRy) {
                        return zzaRy;
                    }
                    zzo.zziW[i] = (com.google.android.gms.internal.zzag.zza) zza.getObject();
                    zzo.zziX[i] = (com.google.android.gms.internal.zzag.zza) zza2.getObject();
                }
                return new zzbw(zzo, false);
            case TTSConst.TTSXML /*4*/:
                if (set.contains(com_google_android_gms_internal_zzag_zza.zziY)) {
                    zzbg.m1447e("Macro cycle detected.  Current macro reference: " + com_google_android_gms_internal_zzag_zza.zziY + "." + "  Previous macro references: " + set.toString() + ".");
                    return zzaRy;
                }
                set.add(com_google_android_gms_internal_zzag_zza.zziY);
                zzbw<com.google.android.gms.internal.zzag.zza> zza3 = zzdj.zza(zza(com_google_android_gms_internal_zzag_zza.zziY, (Set) set, com_google_android_gms_tagmanager_zzdi.zzAp()), com_google_android_gms_internal_zzag_zza.zzjd);
                set.remove(com_google_android_gms_internal_zzag_zza.zziY);
                return zza3;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                zzo = zzqp.zzo(com_google_android_gms_internal_zzag_zza);
                zzo.zzjc = new com.google.android.gms.internal.zzag.zza[com_google_android_gms_internal_zzag_zza.zzjc.length];
                for (i = 0; i < com_google_android_gms_internal_zzag_zza.zzjc.length; i++) {
                    zza = zza(com_google_android_gms_internal_zzag_zza.zzjc[i], (Set) set, com_google_android_gms_tagmanager_zzdi.zzji(i));
                    if (zza == zzaRy) {
                        return zzaRy;
                    }
                    zzo.zzjc[i] = (com.google.android.gms.internal.zzag.zza) zza.getObject();
                }
                return new zzbw(zzo, false);
            default:
                zzbg.m1447e("Unknown type: " + com_google_android_gms_internal_zzag_zza.type);
                return zzaRy;
        }
    }

    private zzbw<com.google.android.gms.internal.zzag.zza> zza(String str, Set<String> set, zzbj com_google_android_gms_tagmanager_zzbj) {
        this.zzaRJ++;
        zzb com_google_android_gms_tagmanager_zzcp_zzb = (zzb) this.zzaRF.get(str);
        if (com_google_android_gms_tagmanager_zzcp_zzb == null || this.zzaRA.zzAb()) {
            zzc com_google_android_gms_tagmanager_zzcp_zzc = (zzc) this.zzaRH.get(str);
            if (com_google_android_gms_tagmanager_zzcp_zzc == null) {
                zzbg.m1447e(zzAG() + "Invalid macro: " + str);
                this.zzaRJ--;
                return zzaRy;
            }
            com.google.android.gms.internal.zzqp.zza zzAO;
            zzbw zza = zza(str, com_google_android_gms_tagmanager_zzcp_zzc.zzAJ(), com_google_android_gms_tagmanager_zzcp_zzc.zzAK(), com_google_android_gms_tagmanager_zzcp_zzc.zzAL(), com_google_android_gms_tagmanager_zzcp_zzc.zzAN(), com_google_android_gms_tagmanager_zzcp_zzc.zzAM(), set, com_google_android_gms_tagmanager_zzbj.zzzR());
            if (((Set) zza.getObject()).isEmpty()) {
                zzAO = com_google_android_gms_tagmanager_zzcp_zzc.zzAO();
            } else {
                if (((Set) zza.getObject()).size() > 1) {
                    zzbg.zzaE(zzAG() + "Multiple macros active for macroName " + str);
                }
                zzAO = (com.google.android.gms.internal.zzqp.zza) ((Set) zza.getObject()).iterator().next();
            }
            if (zzAO == null) {
                this.zzaRJ--;
                return zzaRy;
            }
            zzbw zza2 = zza(this.zzaRD, zzAO, (Set) set, com_google_android_gms_tagmanager_zzbj.zzAh());
            boolean z = zza.zzAq() && zza2.zzAq();
            zzbw<com.google.android.gms.internal.zzag.zza> com_google_android_gms_tagmanager_zzbw = zza2 == zzaRy ? zzaRy : new zzbw(zza2.getObject(), z);
            com.google.android.gms.internal.zzag.zza zzAI = zzAO.zzAI();
            if (com_google_android_gms_tagmanager_zzbw.zzAq()) {
                this.zzaRF.zzf(str, new zzb(com_google_android_gms_tagmanager_zzbw, zzAI));
            }
            zza(zzAI, (Set) set);
            this.zzaRJ--;
            return com_google_android_gms_tagmanager_zzbw;
        }
        zza(com_google_android_gms_tagmanager_zzcp_zzb.zzAI(), (Set) set);
        this.zzaRJ--;
        return com_google_android_gms_tagmanager_zzcp_zzb.zzAH();
    }

    private zzbw<com.google.android.gms.internal.zzag.zza> zza(Map<String, zzak> map, com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza, Set<String> set, zzch com_google_android_gms_tagmanager_zzch) {
        boolean z = true;
        com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza = (com.google.android.gms.internal.zzag.zza) com_google_android_gms_internal_zzqp_zza.zzBD().get(zzae.FUNCTION.toString());
        if (com_google_android_gms_internal_zzag_zza == null) {
            zzbg.m1447e("No function id in properties");
            return zzaRy;
        }
        String str = com_google_android_gms_internal_zzag_zza.zziZ;
        zzak com_google_android_gms_tagmanager_zzak = (zzak) map.get(str);
        if (com_google_android_gms_tagmanager_zzak == null) {
            zzbg.m1447e(str + " has no backing implementation.");
            return zzaRy;
        }
        zzbw<com.google.android.gms.internal.zzag.zza> com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza = (zzbw) this.zzaRE.get(com_google_android_gms_internal_zzqp_zza);
        if (com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza != null && !this.zzaRA.zzAb()) {
            return com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza;
        }
        Map hashMap = new HashMap();
        boolean z2 = true;
        for (Entry entry : com_google_android_gms_internal_zzqp_zza.zzBD().entrySet()) {
            zzbw zza = zza((com.google.android.gms.internal.zzag.zza) entry.getValue(), (Set) set, com_google_android_gms_tagmanager_zzch.zzeO((String) entry.getKey()).zze((com.google.android.gms.internal.zzag.zza) entry.getValue()));
            if (zza == zzaRy) {
                return zzaRy;
            }
            boolean z3;
            if (zza.zzAq()) {
                com_google_android_gms_internal_zzqp_zza.zza((String) entry.getKey(), (com.google.android.gms.internal.zzag.zza) zza.getObject());
                z3 = z2;
            } else {
                z3 = false;
            }
            hashMap.put(entry.getKey(), zza.getObject());
            z2 = z3;
        }
        if (com_google_android_gms_tagmanager_zzak.zzf(hashMap.keySet())) {
            if (!(z2 && com_google_android_gms_tagmanager_zzak.zzzx())) {
                z = false;
            }
            com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza = new zzbw(com_google_android_gms_tagmanager_zzak.zzG(hashMap), z);
            if (z) {
                this.zzaRE.zzf(com_google_android_gms_internal_zzqp_zza, com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza);
            }
            com_google_android_gms_tagmanager_zzch.zzd((com.google.android.gms.internal.zzag.zza) com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza.getObject());
            return com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza;
        }
        zzbg.m1447e("Incorrect keys for function " + str + " required " + com_google_android_gms_tagmanager_zzak.zzAd() + " had " + hashMap.keySet());
        return zzaRy;
    }

    private zzbw<Set<com.google.android.gms.internal.zzqp.zza>> zza(Set<zze> set, Set<String> set2, zza com_google_android_gms_tagmanager_zzcp_zza, zzco com_google_android_gms_tagmanager_zzco) {
        Set hashSet = new HashSet();
        Collection hashSet2 = new HashSet();
        boolean z = true;
        for (zze com_google_android_gms_internal_zzqp_zze : set) {
            zzck zzAo = com_google_android_gms_tagmanager_zzco.zzAo();
            zzbw zza = zza(com_google_android_gms_internal_zzqp_zze, (Set) set2, zzAo);
            if (((Boolean) zza.getObject()).booleanValue()) {
                com_google_android_gms_tagmanager_zzcp_zza.zza(com_google_android_gms_internal_zzqp_zze, hashSet, hashSet2, zzAo);
            }
            boolean z2 = z && zza.zzAq();
            z = z2;
        }
        hashSet.removeAll(hashSet2);
        com_google_android_gms_tagmanager_zzco.zzg(hashSet);
        return new zzbw(hashSet, z);
    }

    private static String zza(com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza) {
        return zzdf.zzg((com.google.android.gms.internal.zzag.zza) com_google_android_gms_internal_zzqp_zza.zzBD().get(zzae.INSTANCE_NAME.toString()));
    }

    private void zza(com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza, Set<String> set) {
        if (com_google_android_gms_internal_zzag_zza != null) {
            zzbw zza = zza(com_google_android_gms_internal_zzag_zza, (Set) set, new zzbu());
            if (zza != zzaRy) {
                Object zzl = zzdf.zzl((com.google.android.gms.internal.zzag.zza) zza.getObject());
                if (zzl instanceof Map) {
                    this.zzaOT.push((Map) zzl);
                } else if (zzl instanceof List) {
                    for (Object zzl2 : (List) zzl2) {
                        if (zzl2 instanceof Map) {
                            this.zzaOT.push((Map) zzl2);
                        } else {
                            zzbg.zzaE("pushAfterEvaluate: value not a Map");
                        }
                    }
                } else {
                    zzbg.zzaE("pushAfterEvaluate: value not a Map or List");
                }
            }
        }
    }

    private static void zza(List<com.google.android.gms.internal.zzqp.zza> list, List<String> list2, String str) {
        if (list.size() != list2.size()) {
            zzbg.zzaD("Invalid resource: imbalance of rule names of functions for " + str + " operation. Using default rule name instead");
        }
    }

    private static void zza(Map<String, zzak> map, zzak com_google_android_gms_tagmanager_zzak) {
        if (map.containsKey(com_google_android_gms_tagmanager_zzak.zzAc())) {
            throw new IllegalArgumentException("Duplicate function type name: " + com_google_android_gms_tagmanager_zzak.zzAc());
        }
        map.put(com_google_android_gms_tagmanager_zzak.zzAc(), com_google_android_gms_tagmanager_zzak);
    }

    private static zzc zzi(Map<String, zzc> map, String str) {
        zzc com_google_android_gms_tagmanager_zzcp_zzc = (zzc) map.get(str);
        if (com_google_android_gms_tagmanager_zzcp_zzc != null) {
            return com_google_android_gms_tagmanager_zzcp_zzc;
        }
        com_google_android_gms_tagmanager_zzcp_zzc = new zzc();
        map.put(str, com_google_android_gms_tagmanager_zzcp_zzc);
        return com_google_android_gms_tagmanager_zzcp_zzc;
    }

    synchronized String zzAF() {
        return this.zzaRI;
    }

    zzbw<Boolean> zza(com.google.android.gms.internal.zzqp.zza com_google_android_gms_internal_zzqp_zza, Set<String> set, zzch com_google_android_gms_tagmanager_zzch) {
        zzbw zza = zza(this.zzaRC, com_google_android_gms_internal_zzqp_zza, (Set) set, com_google_android_gms_tagmanager_zzch);
        Boolean zzk = zzdf.zzk((com.google.android.gms.internal.zzag.zza) zza.getObject());
        com_google_android_gms_tagmanager_zzch.zzd(zzdf.zzK(zzk));
        return new zzbw(zzk, zza.zzAq());
    }

    zzbw<Boolean> zza(zze com_google_android_gms_internal_zzqp_zze, Set<String> set, zzck com_google_android_gms_tagmanager_zzck) {
        boolean z = true;
        for (com.google.android.gms.internal.zzqp.zza zza : com_google_android_gms_internal_zzqp_zze.zzBL()) {
            zzbw zza2 = zza(zza, (Set) set, com_google_android_gms_tagmanager_zzck.zzAi());
            if (((Boolean) zza2.getObject()).booleanValue()) {
                com_google_android_gms_tagmanager_zzck.zzf(zzdf.zzK(Boolean.valueOf(false)));
                return new zzbw(Boolean.valueOf(false), zza2.zzAq());
            }
            boolean z2 = z && zza2.zzAq();
            z = z2;
        }
        for (com.google.android.gms.internal.zzqp.zza zza3 : com_google_android_gms_internal_zzqp_zze.zzBK()) {
            zza2 = zza(zza3, (Set) set, com_google_android_gms_tagmanager_zzck.zzAj());
            if (((Boolean) zza2.getObject()).booleanValue()) {
                z = z && zza2.zzAq();
            } else {
                com_google_android_gms_tagmanager_zzck.zzf(zzdf.zzK(Boolean.valueOf(false)));
                return new zzbw(Boolean.valueOf(false), zza2.zzAq());
            }
        }
        com_google_android_gms_tagmanager_zzck.zzf(zzdf.zzK(Boolean.valueOf(true)));
        return new zzbw(Boolean.valueOf(true), z);
    }

    zzbw<Set<com.google.android.gms.internal.zzqp.zza>> zza(String str, Set<zze> set, Map<zze, List<com.google.android.gms.internal.zzqp.zza>> map, Map<zze, List<String>> map2, Map<zze, List<com.google.android.gms.internal.zzqp.zza>> map3, Map<zze, List<String>> map4, Set<String> set2, zzco com_google_android_gms_tagmanager_zzco) {
        return zza((Set) set, (Set) set2, new C10173(this, map, map2, map3, map4), com_google_android_gms_tagmanager_zzco);
    }

    zzbw<Set<com.google.android.gms.internal.zzqp.zza>> zza(Set<zze> set, zzco com_google_android_gms_tagmanager_zzco) {
        return zza((Set) set, new HashSet(), new C10184(this), com_google_android_gms_tagmanager_zzco);
    }

    void zza(zzak com_google_android_gms_tagmanager_zzak) {
        zza(this.zzaRD, com_google_android_gms_tagmanager_zzak);
    }

    void zzb(zzak com_google_android_gms_tagmanager_zzak) {
        zza(this.zzaRB, com_google_android_gms_tagmanager_zzak);
    }

    void zzc(zzak com_google_android_gms_tagmanager_zzak) {
        zza(this.zzaRC, com_google_android_gms_tagmanager_zzak);
    }

    public zzbw<com.google.android.gms.internal.zzag.zza> zzeS(String str) {
        this.zzaRJ = 0;
        zzag zzeI = this.zzaRA.zzeI(str);
        zzbw<com.google.android.gms.internal.zzag.zza> zza = zza(str, new HashSet(), zzeI.zzzY());
        zzeI.zzAa();
        return zza;
    }

    synchronized void zzeT(String str) {
        this.zzaRI = str;
    }

    public synchronized void zzew(String str) {
        zzeT(str);
        zzag zzeJ = this.zzaRA.zzeJ(str);
        zzu zzzZ = zzeJ.zzzZ();
        for (com.google.android.gms.internal.zzqp.zza zza : (Set) zza(this.zzaRG, zzzZ.zzzR()).getObject()) {
            zza(this.zzaRB, zza, new HashSet(), zzzZ.zzzQ());
        }
        zzeJ.zzAa();
        zzeT(null);
    }
}
