package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class zzg implements Creator<WakeLockEvent> {
    static void zza(WakeLockEvent wakeLockEvent, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, wakeLockEvent.mVersionCode);
        zzb.zza(parcel, 2, wakeLockEvent.getTimeMillis());
        zzb.zza(parcel, 4, wakeLockEvent.zzpE(), false);
        zzb.zzc(parcel, 5, wakeLockEvent.zzpG());
        zzb.zzb(parcel, 6, wakeLockEvent.zzpH(), false);
        zzb.zza(parcel, 8, wakeLockEvent.zzpC());
        zzb.zza(parcel, 10, wakeLockEvent.zzpF(), false);
        zzb.zzc(parcel, 11, wakeLockEvent.getEventType());
        zzb.zza(parcel, 12, wakeLockEvent.zzpA(), false);
        zzb.zza(parcel, 13, wakeLockEvent.zzpJ(), false);
        zzb.zzc(parcel, 14, wakeLockEvent.zzpI());
        zzb.zza(parcel, 15, wakeLockEvent.zzpK());
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzav(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzbU(x0);
    }

    public WakeLockEvent zzav(Parcel parcel) {
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        long j = 0;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        List list = null;
        String str2 = null;
        long j2 = 0;
        int i4 = 0;
        String str3 = null;
        String str4 = null;
        float f = 0.0f;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    list = zza.zzC(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    j2 = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    str3 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    str4 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    i4 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                    f = zza.zzl(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new WakeLockEvent(i, j, i2, str, i3, list, str2, j2, i4, str3, str4, f);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public WakeLockEvent[] zzbU(int i) {
        return new WakeLockEvent[i];
    }
}
