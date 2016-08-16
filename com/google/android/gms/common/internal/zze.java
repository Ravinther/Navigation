package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public abstract class zze {
    public static final zze zzacG;
    public static final zze zzacH;
    public static final zze zzacI;
    public static final zze zzacJ;
    public static final zze zzacK;
    public static final zze zzacL;
    public static final zze zzacM;
    public static final zze zzacN;
    public static final zze zzacO;
    public static final zze zzacP;
    public static final zze zzacQ;
    public static final zze zzacR;
    public static final zze zzacS;
    public static final zze zzacT;
    public static final zze zzacU;

    /* renamed from: com.google.android.gms.common.internal.zze.11 */
    static class AnonymousClass11 extends zze {
        final /* synthetic */ char zzada;

        AnonymousClass11(char c) {
            this.zzada = c;
        }

        public zze zza(zze com_google_android_gms_common_internal_zze) {
            return com_google_android_gms_common_internal_zze.zzd(this.zzada) ? com_google_android_gms_common_internal_zze : super.zza(com_google_android_gms_common_internal_zze);
        }

        public boolean zzd(char c) {
            return c == this.zzada;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.1 */
    static class C06491 extends zze {
        C06491() {
        }

        public boolean zzd(char c) {
            return Character.isDigit(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.2 */
    static class C06502 extends zze {
        final /* synthetic */ char zzacV;
        final /* synthetic */ char zzacW;

        C06502(char c, char c2) {
            this.zzacV = c;
            this.zzacW = c2;
        }

        public boolean zzd(char c) {
            return c == this.zzacV || c == this.zzacW;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.3 */
    static class C06513 extends zze {
        final /* synthetic */ char[] zzacX;

        C06513(char[] cArr) {
            this.zzacX = cArr;
        }

        public boolean zzd(char c) {
            return Arrays.binarySearch(this.zzacX, c) >= 0;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.4 */
    static class C06524 extends zze {
        final /* synthetic */ char zzacY;
        final /* synthetic */ char zzacZ;

        C06524(char c, char c2) {
            this.zzacY = c;
            this.zzacZ = c2;
        }

        public boolean zzd(char c) {
            return this.zzacY <= c && c <= this.zzacZ;
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.5 */
    static class C06535 extends zze {
        C06535() {
        }

        public boolean zzd(char c) {
            return Character.isLetter(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.6 */
    static class C06546 extends zze {
        C06546() {
        }

        public boolean zzd(char c) {
            return Character.isLetterOrDigit(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.7 */
    static class C06557 extends zze {
        C06557() {
        }

        public boolean zzd(char c) {
            return Character.isUpperCase(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.8 */
    static class C06568 extends zze {
        C06568() {
        }

        public boolean zzd(char c) {
            return Character.isLowerCase(c);
        }
    }

    /* renamed from: com.google.android.gms.common.internal.zze.9 */
    static class C06579 extends zze {
        C06579() {
        }

        public zze zza(zze com_google_android_gms_common_internal_zze) {
            zzx.zzv(com_google_android_gms_common_internal_zze);
            return this;
        }

        public boolean zzb(CharSequence charSequence) {
            zzx.zzv(charSequence);
            return true;
        }

        public boolean zzd(char c) {
            return true;
        }
    }

    private static class zza extends zze {
        List<zze> zzadb;

        zza(List<zze> list) {
            this.zzadb = list;
        }

        public zze zza(zze com_google_android_gms_common_internal_zze) {
            List arrayList = new ArrayList(this.zzadb);
            arrayList.add(zzx.zzv(com_google_android_gms_common_internal_zze));
            return new zza(arrayList);
        }

        public boolean zzd(char c) {
            for (zze zzd : this.zzadb) {
                if (zzd.zzd(c)) {
                    return true;
                }
            }
            return false;
        }
    }

    static {
        zzacG = zza((CharSequence) "\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000\u00a0\u180e\u202f").zza(zza('\u2000', '\u200a'));
        zzacH = zza((CharSequence) "\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000").zza(zza('\u2000', '\u2006')).zza(zza('\u2008', '\u200a'));
        zzacI = zza('\u0000', '\u007f');
        zze zza = zza('0', '9');
        zze com_google_android_gms_common_internal_zze = zza;
        for (char c : "\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".toCharArray()) {
            com_google_android_gms_common_internal_zze = com_google_android_gms_common_internal_zze.zza(zza(c, (char) (c + 9)));
        }
        zzacJ = com_google_android_gms_common_internal_zze;
        zzacK = zza('\t', '\r').zza(zza('\u001c', ' ')).zza(zzc('\u1680')).zza(zzc('\u180e')).zza(zza('\u2000', '\u2006')).zza(zza('\u2008', '\u200b')).zza(zza('\u2028', '\u2029')).zza(zzc('\u205f')).zza(zzc('\u3000'));
        zzacL = new C06491();
        zzacM = new C06535();
        zzacN = new C06546();
        zzacO = new C06557();
        zzacP = new C06568();
        zzacQ = zza('\u0000', '\u001f').zza(zza('\u007f', '\u009f'));
        zzacR = zza('\u0000', ' ').zza(zza('\u007f', '\u00a0')).zza(zzc('\u00ad')).zza(zza('\u0600', '\u0603')).zza(zza((CharSequence) "\u06dd\u070f\u1680\u17b4\u17b5\u180e")).zza(zza('\u2000', '\u200f')).zza(zza('\u2028', '\u202f')).zza(zza('\u205f', '\u2064')).zza(zza('\u206a', '\u206f')).zza(zzc('\u3000')).zza(zza('\ud800', '\uf8ff')).zza(zza((CharSequence) "\ufeff\ufff9\ufffa\ufffb"));
        zzacS = zza('\u0000', '\u04f9').zza(zzc('\u05be')).zza(zza('\u05d0', '\u05ea')).zza(zzc('\u05f3')).zza(zzc('\u05f4')).zza(zza('\u0600', '\u06ff')).zza(zza('\u0750', '\u077f')).zza(zza('\u0e00', '\u0e7f')).zza(zza('\u1e00', '\u20af')).zza(zza('\u2100', '\u213a')).zza(zza('\ufb50', '\ufdff')).zza(zza('\ufe70', '\ufeff')).zza(zza('\uff61', '\uffdc'));
        zzacT = new C06579();
        zzacU = new zze() {
            public zze zza(zze com_google_android_gms_common_internal_zze) {
                return (zze) zzx.zzv(com_google_android_gms_common_internal_zze);
            }

            public boolean zzb(CharSequence charSequence) {
                return charSequence.length() == 0;
            }

            public boolean zzd(char c) {
                return false;
            }
        };
    }

    public static zze zza(char c, char c2) {
        zzx.zzZ(c2 >= c);
        return new C06524(c, c2);
    }

    public static zze zza(CharSequence charSequence) {
        switch (charSequence.length()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return zzacU;
            case TTSConst.TTSMULTILINE /*1*/:
                return zzc(charSequence.charAt(0));
            case TTSConst.TTSPARAGRAPH /*2*/:
                return new C06502(charSequence.charAt(0), charSequence.charAt(1));
            default:
                char[] toCharArray = charSequence.toString().toCharArray();
                Arrays.sort(toCharArray);
                return new C06513(toCharArray);
        }
    }

    public static zze zzc(char c) {
        return new AnonymousClass11(c);
    }

    public zze zza(zze com_google_android_gms_common_internal_zze) {
        return new zza(Arrays.asList(new zze[]{this, (zze) zzx.zzv(com_google_android_gms_common_internal_zze)}));
    }

    public boolean zzb(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!zzd(charSequence.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean zzd(char c);
}
