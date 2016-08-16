package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.sygic.aura.C1090R;
import loquendo.tts.engine.TTSConst;

public class LocationRequestCreator implements Creator<LocationRequest> {
    static void zza(LocationRequest locationRequest, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, locationRequest.mPriority);
        zzb.zzc(parcel, 1000, locationRequest.getVersionCode());
        zzb.zza(parcel, 2, locationRequest.zzaBW);
        zzb.zza(parcel, 3, locationRequest.zzaBX);
        zzb.zza(parcel, 4, locationRequest.zzaqs);
        zzb.zza(parcel, 5, locationRequest.zzaBB);
        zzb.zzc(parcel, 6, locationRequest.zzaBY);
        zzb.zza(parcel, 7, locationRequest.zzaBZ);
        zzb.zza(parcel, 8, locationRequest.zzaCa);
        zzb.zzH(parcel, zzak);
    }

    public LocationRequest createFromParcel(Parcel parcel) {
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        int i2 = C1090R.styleable.Theme_editTextStyle;
        long j = 3600000;
        long j2 = 600000;
        boolean z = false;
        long j3 = Long.MAX_VALUE;
        int i3 = Integer.MAX_VALUE;
        float f = 0.0f;
        long j4 = 0;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i2 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    j = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    j2 = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    j3 = zza.zzi(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    i3 = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    f = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    j4 = zza.zzi(parcel, zzai);
                    break;
                case 1000:
                    i = zza.zzg(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new LocationRequest(i, i2, j, j2, z, j3, i3, f, j4);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public LocationRequest[] newArray(int size) {
        return new LocationRequest[size];
    }
}
