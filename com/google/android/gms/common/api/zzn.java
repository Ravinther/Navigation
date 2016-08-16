package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzn implements Creator<Status> {
    static void zza(Status status, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, status.getStatusCode());
        zzb.zzc(parcel, 1000, status.getVersionCode());
        zzb.zza(parcel, 2, status.getStatusMessage(), false);
        zzb.zza(parcel, 3, status.zznH(), i, false);
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzY(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzbh(x0);
    }

    public Status zzY(Parcel parcel) {
        PendingIntent pendingIntent = null;
        int i = 0;
        int zzaj = zza.zzaj(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    pendingIntent = (PendingIntent) zza.zza(parcel, zzai, PendingIntent.CREATOR);
                    break;
                case 1000:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new Status(i2, i, str, pendingIntent);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public Status[] zzbh(int i) {
        return new Status[i];
    }
}
