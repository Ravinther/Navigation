package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks;

public final class zze implements Optional {
    public static final zze zzaOd;
    private final String zzRU;
    private final boolean zzaOe;
    private final boolean zzaOf;
    private final ServerAuthCodeCallbacks zzaOg;

    public static final class zza {
        private String zzaLe;
        private boolean zzaOh;
        private boolean zzaOi;
        private ServerAuthCodeCallbacks zzaOj;

        public zze zzzr() {
            return new zze(this.zzaOi, this.zzaLe, this.zzaOj, null);
        }
    }

    static {
        zzaOd = new zza().zzzr();
    }

    private zze(boolean z, boolean z2, String str, ServerAuthCodeCallbacks serverAuthCodeCallbacks) {
        this.zzaOe = z;
        this.zzaOf = z2;
        this.zzRU = str;
        this.zzaOg = serverAuthCodeCallbacks;
    }

    public String zzlG() {
        return this.zzRU;
    }

    public boolean zzzo() {
        return this.zzaOe;
    }

    public boolean zzzp() {
        return this.zzaOf;
    }

    public ServerAuthCodeCallbacks zzzq() {
        return this.zzaOg;
    }
}
