package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import loquendo.tts.engine.TTSConst;

public class zzf implements Creator<MarkerOptions> {
    static void zza(MarkerOptions markerOptions, Parcel parcel, int i) {
        int zzak = zzb.zzak(parcel);
        zzb.zzc(parcel, 1, markerOptions.getVersionCode());
        zzb.zza(parcel, 2, markerOptions.getPosition(), i, false);
        zzb.zza(parcel, 3, markerOptions.getTitle(), false);
        zzb.zza(parcel, 4, markerOptions.getSnippet(), false);
        zzb.zza(parcel, 5, markerOptions.zzxd(), false);
        zzb.zza(parcel, 6, markerOptions.getAnchorU());
        zzb.zza(parcel, 7, markerOptions.getAnchorV());
        zzb.zza(parcel, 8, markerOptions.isDraggable());
        zzb.zza(parcel, 9, markerOptions.isVisible());
        zzb.zza(parcel, 10, markerOptions.isFlat());
        zzb.zza(parcel, 11, markerOptions.getRotation());
        zzb.zza(parcel, 12, markerOptions.getInfoWindowAnchorU());
        zzb.zza(parcel, 13, markerOptions.getInfoWindowAnchorV());
        zzb.zza(parcel, 14, markerOptions.getAlpha());
        zzb.zzH(parcel, zzak);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzfb(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzhB(x0);
    }

    public MarkerOptions zzfb(Parcel parcel) {
        int zzaj = zza.zzaj(parcel);
        int i = 0;
        LatLng latLng = null;
        String str = null;
        String str2 = null;
        IBinder iBinder = null;
        float f = 0.0f;
        float f2 = 0.0f;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        float f3 = 0.0f;
        float f4 = 0.5f;
        float f5 = 0.0f;
        float f6 = 1.0f;
        while (parcel.dataPosition() < zzaj) {
            int zzai = zza.zzai(parcel);
            switch (zza.zzbH(zzai)) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i = zza.zzg(parcel, zzai);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    latLng = (LatLng) zza.zza(parcel, zzai, LatLng.CREATOR);
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    str = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSXML /*4*/:
                    str2 = zza.zzo(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    iBinder = zza.zzp(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    f = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    f2 = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    z = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    z2 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    z3 = zza.zzc(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    f3 = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    f4 = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    f5 = zza.zzl(parcel, zzai);
                    break;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    f6 = zza.zzl(parcel, zzai);
                    break;
                default:
                    zza.zzb(parcel, zzai);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaj) {
            return new MarkerOptions(i, latLng, str, str2, iBinder, f, f2, z, z2, z3, f3, f4, f5, f6);
        }
        throw new zza.zza("Overread allowed size end=" + zzaj, parcel);
    }

    public MarkerOptions[] zzhB(int i) {
        return new MarkerOptions[i];
    }
}
