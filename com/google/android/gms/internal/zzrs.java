package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import loquendo.tts.engine.TTSConst;

public class zzrs<M extends zzrr<M>, T> {
    public final int tag;
    protected final int type;
    protected final Class<T> zzbcb;
    protected final boolean zzbcc;

    int zzS(Object obj) {
        return this.zzbcc ? zzT(obj) : zzU(obj);
    }

    protected int zzT(Object obj) {
        int i = 0;
        int length = Array.getLength(obj);
        for (int i2 = 0; i2 < length; i2++) {
            if (Array.get(obj, i2) != null) {
                i += zzU(Array.get(obj, i2));
            }
        }
        return i;
    }

    protected int zzU(Object obj) {
        int zzlE = zzsa.zzlE(this.tag);
        switch (this.type) {
            case TTSConst.TTSEVT_RESUME /*10*/:
                return zzrq.zzb(zzlE, (zzrx) obj);
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return zzrq.zzc(zzlE, (zzrx) obj);
            default:
                throw new IllegalArgumentException("Unknown type " + this.type);
        }
    }

    void zza(Object obj, zzrq com_google_android_gms_internal_zzrq) throws IOException {
        if (this.zzbcc) {
            zzc(obj, com_google_android_gms_internal_zzrq);
        } else {
            zzb(obj, com_google_android_gms_internal_zzrq);
        }
    }

    protected void zzb(Object obj, zzrq com_google_android_gms_internal_zzrq) {
        try {
            com_google_android_gms_internal_zzrq.zzlw(this.tag);
            switch (this.type) {
                case TTSConst.TTSEVT_RESUME /*10*/:
                    zzrx com_google_android_gms_internal_zzrx = (zzrx) obj;
                    int zzlE = zzsa.zzlE(this.tag);
                    com_google_android_gms_internal_zzrq.zzb(com_google_android_gms_internal_zzrx);
                    com_google_android_gms_internal_zzrq.zzD(zzlE, 4);
                    return;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    com_google_android_gms_internal_zzrq.zzc((zzrx) obj);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        throw new IllegalStateException(e);
    }

    protected void zzc(Object obj, zzrq com_google_android_gms_internal_zzrq) {
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                zzb(obj2, com_google_android_gms_internal_zzrq);
            }
        }
    }
}
