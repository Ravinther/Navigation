package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzaa extends zzak {
    private static final String ID;
    private final Context mContext;

    static {
        ID = zzad.DEVICE_ID.toString();
    }

    public zzaa(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public zza zzG(Map<String, zza> map) {
        String zzaN = zzaN(this.mContext);
        return zzaN == null ? zzdf.zzBg() : zzdf.zzK(zzaN);
    }

    protected String zzaN(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    public boolean zzzx() {
        return true;
    }
}
