package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

class zzay extends zzak {
    private static final String ID;
    private static final String zzaPY;
    private static final String zzaQq;
    private static final String zzaQr;
    private static final String zzaQs;

    /* renamed from: com.google.android.gms.tagmanager.zzay.1 */
    static /* synthetic */ class C10111 {
        static final /* synthetic */ int[] zzaQt;

        static {
            zzaQt = new int[zza.values().length];
            try {
                zzaQt[zza.URL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                zzaQt[zza.BACKSLASH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                zzaQt[zza.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private enum zza {
        NONE,
        URL,
        BACKSLASH
    }

    static {
        ID = zzad.JOINER.toString();
        zzaPY = zzae.ARG0.toString();
        zzaQq = zzae.ITEM_SEPARATOR.toString();
        zzaQr = zzae.KEY_VALUE_SEPARATOR.toString();
        zzaQs = zzae.ESCAPE.toString();
    }

    public zzay() {
        super(ID, zzaPY);
    }

    private String zza(String str, zza com_google_android_gms_tagmanager_zzay_zza, Set<Character> set) {
        switch (C10111.zzaQt[com_google_android_gms_tagmanager_zzay_zza.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                try {
                    return zzdj.zzff(str);
                } catch (Throwable e) {
                    zzbg.zzb("Joiner: unsupported encoding", e);
                    return str;
                }
            case TTSConst.TTSPARAGRAPH /*2*/:
                String replace = str.replace("\\", "\\\\");
                String str2 = replace;
                for (Character ch : set) {
                    CharSequence ch2 = ch.toString();
                    str2 = str2.replace(ch2, "\\" + ch2);
                }
                return str2;
            default:
                return str;
        }
    }

    private void zza(StringBuilder stringBuilder, String str, zza com_google_android_gms_tagmanager_zzay_zza, Set<Character> set) {
        stringBuilder.append(zza(str, com_google_android_gms_tagmanager_zzay_zza, set));
    }

    private void zza(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    public com.google.android.gms.internal.zzag.zza zzG(Map<String, com.google.android.gms.internal.zzag.zza> map) {
        com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza = (com.google.android.gms.internal.zzag.zza) map.get(zzaPY);
        if (com_google_android_gms_internal_zzag_zza == null) {
            return zzdf.zzBg();
        }
        zza com_google_android_gms_tagmanager_zzay_zza;
        Set set;
        com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza2 = (com.google.android.gms.internal.zzag.zza) map.get(zzaQq);
        String zzg = com_google_android_gms_internal_zzag_zza2 != null ? zzdf.zzg(com_google_android_gms_internal_zzag_zza2) : "";
        com_google_android_gms_internal_zzag_zza2 = (com.google.android.gms.internal.zzag.zza) map.get(zzaQr);
        String zzg2 = com_google_android_gms_internal_zzag_zza2 != null ? zzdf.zzg(com_google_android_gms_internal_zzag_zza2) : "=";
        zza com_google_android_gms_tagmanager_zzay_zza2 = zza.NONE;
        com_google_android_gms_internal_zzag_zza2 = (com.google.android.gms.internal.zzag.zza) map.get(zzaQs);
        if (com_google_android_gms_internal_zzag_zza2 != null) {
            String zzg3 = zzdf.zzg(com_google_android_gms_internal_zzag_zza2);
            if ("url".equals(zzg3)) {
                com_google_android_gms_tagmanager_zzay_zza = zza.URL;
                set = null;
            } else if ("backslash".equals(zzg3)) {
                com_google_android_gms_tagmanager_zzay_zza = zza.BACKSLASH;
                set = new HashSet();
                zza(set, zzg);
                zza(set, zzg2);
                set.remove(Character.valueOf('\\'));
            } else {
                zzbg.m1447e("Joiner: unsupported escape type: " + zzg3);
                return zzdf.zzBg();
            }
        }
        set = null;
        com_google_android_gms_tagmanager_zzay_zza = com_google_android_gms_tagmanager_zzay_zza2;
        StringBuilder stringBuilder = new StringBuilder();
        switch (com_google_android_gms_internal_zzag_zza.type) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                Object obj = 1;
                com.google.android.gms.internal.zzag.zza[] com_google_android_gms_internal_zzag_zzaArr = com_google_android_gms_internal_zzag_zza.zziV;
                int length = com_google_android_gms_internal_zzag_zzaArr.length;
                int i = 0;
                while (i < length) {
                    com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza3 = com_google_android_gms_internal_zzag_zzaArr[i];
                    if (obj == null) {
                        stringBuilder.append(zzg);
                    }
                    zza(stringBuilder, zzdf.zzg(com_google_android_gms_internal_zzag_zza3), com_google_android_gms_tagmanager_zzay_zza, set);
                    i++;
                    obj = null;
                }
                break;
            case TTSConst.TTSUNICODE /*3*/:
                for (int i2 = 0; i2 < com_google_android_gms_internal_zzag_zza.zziW.length; i2++) {
                    if (i2 > 0) {
                        stringBuilder.append(zzg);
                    }
                    String zzg4 = zzdf.zzg(com_google_android_gms_internal_zzag_zza.zziW[i2]);
                    String zzg5 = zzdf.zzg(com_google_android_gms_internal_zzag_zza.zziX[i2]);
                    zza(stringBuilder, zzg4, com_google_android_gms_tagmanager_zzay_zza, set);
                    stringBuilder.append(zzg2);
                    zza(stringBuilder, zzg5, com_google_android_gms_tagmanager_zzay_zza, set);
                }
                break;
            default:
                zza(stringBuilder, zzdf.zzg(com_google_android_gms_internal_zzag_zza), com_google_android_gms_tagmanager_zzay_zza, set);
                break;
        }
        return zzdf.zzK(stringBuilder.toString());
    }

    public boolean zzzx() {
        return true;
    }
}
