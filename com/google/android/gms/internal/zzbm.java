package com.google.android.gms.internal;

import android.util.Base64OutputStream;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.PriorityQueue;
import loquendo.tts.engine.TTSConst;

public class zzbm {
    private final int zzse;
    private final int zzsf;
    private final int zzsg;
    private final zzbl zzsh;

    /* renamed from: com.google.android.gms.internal.zzbm.1 */
    class C08691 implements Comparator<String> {
        final /* synthetic */ zzbm zzsi;

        C08691(zzbm com_google_android_gms_internal_zzbm) {
            this.zzsi = com_google_android_gms_internal_zzbm;
        }

        public int compare(String s1, String s2) {
            return s2.length() - s1.length();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbm.2 */
    class C08702 implements Comparator<com.google.android.gms.internal.zzbp.zza> {
        final /* synthetic */ zzbm zzsi;

        C08702(zzbm com_google_android_gms_internal_zzbm) {
            this.zzsi = com_google_android_gms_internal_zzbm;
        }

        public /* synthetic */ int compare(Object x0, Object x1) {
            return zza((com.google.android.gms.internal.zzbp.zza) x0, (com.google.android.gms.internal.zzbp.zza) x1);
        }

        public int zza(com.google.android.gms.internal.zzbp.zza com_google_android_gms_internal_zzbp_zza, com.google.android.gms.internal.zzbp.zza com_google_android_gms_internal_zzbp_zza2) {
            return (int) (com_google_android_gms_internal_zzbp_zza.value - com_google_android_gms_internal_zzbp_zza2.value);
        }
    }

    static class zza {
        ByteArrayOutputStream zzsj;
        Base64OutputStream zzsk;

        public zza() {
            this.zzsj = new ByteArrayOutputStream(4096);
            this.zzsk = new Base64OutputStream(this.zzsj, 10);
        }

        public String toString() {
            String byteArrayOutputStream;
            try {
                this.zzsk.close();
            } catch (Throwable e) {
                zzb.zzb("HashManager: Unable to convert to Base64.", e);
            }
            try {
                this.zzsj.close();
                byteArrayOutputStream = this.zzsj.toString();
            } catch (Throwable e2) {
                zzb.zzb("HashManager: Unable to convert to Base64.", e2);
                byteArrayOutputStream = "";
            } finally {
                this.zzsj = null;
                this.zzsk = null;
            }
            return byteArrayOutputStream;
        }

        public void write(byte[] data) throws IOException {
            this.zzsk.write(data);
        }
    }

    public zzbm(int i) {
        this.zzsh = new zzbo();
        this.zzsf = i;
        this.zzse = 6;
        this.zzsg = 0;
    }

    private String zzA(String str) {
        String[] split = str.split("\n");
        if (split.length == 0) {
            return "";
        }
        zza zzcz = zzcz();
        Arrays.sort(split, new C08691(this));
        int i = 0;
        while (i < split.length && i < this.zzsf) {
            if (split[i].trim().length() != 0) {
                try {
                    zzcz.write(this.zzsh.zzz(split[i]));
                } catch (Throwable e) {
                    zzb.zzb("Error while writing hash to byteStream", e);
                }
            }
            i++;
        }
        return zzcz.toString();
    }

    String zzB(String str) {
        String[] split = str.split("\n");
        if (split.length == 0) {
            return "";
        }
        zza zzcz = zzcz();
        PriorityQueue priorityQueue = new PriorityQueue(this.zzsf, new C08702(this));
        for (String zzD : split) {
            String[] zzD2 = zzbn.zzD(zzD);
            if (zzD2.length >= this.zzse) {
                zzbp.zza(zzD2, this.zzsf, this.zzse, priorityQueue);
            }
        }
        Iterator it = priorityQueue.iterator();
        while (it.hasNext()) {
            try {
                zzcz.write(this.zzsh.zzz(((com.google.android.gms.internal.zzbp.zza) it.next()).zzsm));
            } catch (Throwable e) {
                zzb.zzb("Error while writing hash to byteStream", e);
            }
        }
        return zzcz.toString();
    }

    public String zza(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append(((String) it.next()).toLowerCase(Locale.US));
            stringBuffer.append('\n');
        }
        switch (this.zzsg) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return zzB(stringBuffer.toString());
            case TTSConst.TTSMULTILINE /*1*/:
                return zzA(stringBuffer.toString());
            default:
                return "";
        }
    }

    zza zzcz() {
        return new zza();
    }
}
