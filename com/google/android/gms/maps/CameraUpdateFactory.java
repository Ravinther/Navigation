package com.google.android.gms.maps;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;

public final class CameraUpdateFactory {
    private static ICameraUpdateFactoryDelegate zzaFo;

    public static void zza(ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate) {
        zzaFo = (ICameraUpdateFactoryDelegate) zzx.zzv(iCameraUpdateFactoryDelegate);
    }
}
