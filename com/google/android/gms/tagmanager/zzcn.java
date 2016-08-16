package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class zzcn implements zzf {
    private final Context mContext;
    private final String zzaOS;
    private final ExecutorService zzaRv;

    zzcn(Context context, String str) {
        this.mContext = context;
        this.zzaOS = str;
        this.zzaRv = Executors.newSingleThreadExecutor();
    }

    public synchronized void release() {
        this.zzaRv.shutdown();
    }
}
