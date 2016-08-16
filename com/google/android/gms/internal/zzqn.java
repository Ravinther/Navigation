package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.List;

public class zzqn {
    private final List<zzqi> zzaTH;

    public zzqn() {
        this.zzaTH = new ArrayList();
    }

    public String getId() {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (zzqi com_google_android_gms_internal_zzqi : this.zzaTH) {
            if (obj != null) {
                obj = null;
            } else {
                stringBuilder.append("#");
            }
            stringBuilder.append(com_google_android_gms_internal_zzqi.getContainerId());
        }
        return stringBuilder.toString();
    }

    public List<zzqi> zzBv() {
        return this.zzaTH;
    }

    public zzqn zzb(zzqi com_google_android_gms_internal_zzqi) throws IllegalArgumentException {
        zzx.zzv(com_google_android_gms_internal_zzqi);
        for (zzqi containerId : this.zzaTH) {
            if (containerId.getContainerId().equals(com_google_android_gms_internal_zzqi.getContainerId())) {
                throw new IllegalArgumentException("The container is already being requested. " + com_google_android_gms_internal_zzqi.getContainerId());
            }
        }
        this.zzaTH.add(com_google_android_gms_internal_zzqi);
        return this;
    }
}
