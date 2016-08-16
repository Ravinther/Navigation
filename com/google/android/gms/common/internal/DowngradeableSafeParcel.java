package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public abstract class DowngradeableSafeParcel implements SafeParcelable {
    private static final Object zzadj;
    private static ClassLoader zzadk;
    private static Integer zzadl;
    private boolean zzadm;

    static {
        zzadj = new Object();
        zzadk = null;
        zzadl = null;
    }

    public DowngradeableSafeParcel() {
        this.zzadm = false;
    }
}
