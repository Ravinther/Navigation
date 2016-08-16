package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzaw extends zzak {
    private static final String ID;
    private static final String zzaOJ;
    private final Context context;

    static {
        ID = zzad.INSTALL_REFERRER.toString();
        zzaOJ = zzae.COMPONENT.toString();
    }

    public zzaw(Context context) {
        super(ID, new String[0]);
        this.context = context;
    }

    public zza zzG(Map<String, zza> map) {
        String zzk = zzax.zzk(this.context, ((zza) map.get(zzaOJ)) != null ? zzdf.zzg((zza) map.get(zzaOJ)) : null);
        return zzk != null ? zzdf.zzK(zzk) : zzdf.zzBg();
    }

    public boolean zzzx() {
        return true;
    }
}
