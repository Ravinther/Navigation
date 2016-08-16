package com.google.android.gms.maps;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;

public final class GoogleMap {
    private final IGoogleMapDelegate zzaFp;

    protected GoogleMap(IGoogleMapDelegate map) {
        this.zzaFp = (IGoogleMapDelegate) zzx.zzv(map);
    }
}
