package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.CameraPosition;
import loquendo.tts.engine.TTSConst;

public class zza implements Creator<GoogleMapOptions> {
    static void zza(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, googleMapOptions.getVersionCode());
        zzb.zza(parcel, 2, googleMapOptions.zzwE());
        zzb.zza(parcel, 3, googleMapOptions.zzwF());
        zzb.zzc(parcel, 4, googleMapOptions.getMapType());
        zzb.zza(parcel, 5, googleMapOptions.getCamera(), i, false);
        zzb.zza(parcel, 6, googleMapOptions.zzwG());
        zzb.zza(parcel, 7, googleMapOptions.zzwH());
        zzb.zza(parcel, 8, googleMapOptions.zzwI());
        zzb.zza(parcel, 9, googleMapOptions.zzwJ());
        zzb.zza(parcel, 10, googleMapOptions.zzwK());
        zzb.zza(parcel, 11, googleMapOptions.zzwL());
        zzb.zza(parcel, 12, googleMapOptions.zzwM());
        zzb.zza(parcel, 14, googleMapOptions.zzwN());
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzeU(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhu(x0);
    }

    public GoogleMapOptions zzeU(Parcel parcel) {
        int zzaj = com.google.android.gms.common.internal.safeparcel.zza.zzaj(parcel);
        int i = 0;
        byte b = (byte) -1;
        byte b2 = (byte) -1;
        int i2 = 0;
        CameraPosition cameraPosition = null;
        byte b3 = (byte) -1;
        byte b4 = (byte) -1;
        byte b5 = (byte) -1;
        byte b6 = (byte) -1;
        byte b7 = (byte) -1;
        byte b8 = (byte) -1;
        byte b9 = (byte) -1;
        byte b10 = (byte) -1;
        while (parcel.dataPosition() < zzaj) {
            int zzai = com.google.android.gms.common.internal.safeparcel.zza.zzai(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    b = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    b2 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    cameraPosition = (CameraPosition) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, zzai, CameraPosition.CREATOR);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    b3 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    b4 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    b5 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    b6 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    b7 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    b8 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    b9 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    b10 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, zzai);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new GoogleMapOptions(i, b, b2, i2, cameraPosition, b3, b4, b5, b6, b7, b8, b9, b10);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public GoogleMapOptions[] zzhu(int i) {
        return new GoogleMapOptions[i];
    }
}
