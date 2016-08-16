package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzc implements Creator<LogEvent> {
    static void zza(LogEvent logEvent, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, logEvent.versionCode);
        zzb.zza(parcel, 2, logEvent.zzaKG);
        zzb.zza(parcel, 3, logEvent.tag, false);
        zzb.zza(parcel, 4, logEvent.zzaKI, false);
        zzb.zza(parcel, 5, logEvent.zzaKJ, false);
        zzb.zza(parcel, 6, logEvent.zzaKH);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzfP(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zziD(x0);
    }

    public LogEvent zzfP(Parcel parcel) {
        long j = 0;
        Bundle bundle = null;
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        byte[] bArr = null;
        String str = null;
        long j2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    j2 = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    bArr = zza.zzr(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    bundle = zza.zzq(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new LogEvent(i, j2, j, str, bArr, bundle);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public LogEvent[] zziD(int i) {
        return new LogEvent[i];
    }
}
