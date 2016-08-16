package com.google.android.gms.location.places.internal;

import android.os.Handler;
import android.util.Log;
import com.sygic.aura.poi.detail.PoiDetailFragment;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class zzo {
    private static final String TAG;
    private static final long zzaEN;
    private final Handler mHandler;
    private final Runnable zzaEP;
    private ArrayList<String> zzaEQ;
    private ArrayList<String> zzaER;
    private final Object zzpc;

    static {
        TAG = zzo.class.getSimpleName();
        zzaEN = TimeUnit.SECONDS.toMillis(1);
    }

    public void zzC(String str, String str2) {
        synchronized (this.zzpc) {
            if (this.zzaEQ == null) {
                this.zzaEQ = new ArrayList();
                this.zzaER = new ArrayList();
                this.mHandler.postDelayed(this.zzaEP, zzaEN);
            }
            this.zzaEQ.add(str);
            this.zzaER.add(str2);
            if (this.zzaEQ.size() >= PoiDetailFragment.MIN_STREET_VIEW_BYTES) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Event buffer full, flushing");
                }
                this.zzaEP.run();
                this.mHandler.removeCallbacks(this.zzaEP);
                return;
            }
        }
    }
}
