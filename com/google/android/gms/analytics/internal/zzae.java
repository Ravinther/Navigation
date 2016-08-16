package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

@Deprecated
public class zzae {
    private static volatile Logger zzOk;

    static {
        setLogger(new zzs());
    }

    public static Logger getLogger() {
        return zzOk;
    }

    public static void setLogger(Logger logger) {
        zzOk = logger;
    }

    public static void m1446v(String msg) {
        zzaf zzkq = zzaf.zzkq();
        if (zzkq != null) {
            zzkq.zzaY(msg);
        } else if (zzM(0)) {
            Log.v((String) zzy.zzNa.get(), msg);
        }
        Logger logger = zzOk;
        if (logger != null) {
            logger.verbose(msg);
        }
    }

    public static boolean zzM(int i) {
        return getLogger() != null && getLogger().getLogLevel() <= i;
    }

    public static void zzaD(String str) {
        zzaf zzkq = zzaf.zzkq();
        if (zzkq != null) {
            zzkq.zzba(str);
        } else if (zzM(1)) {
            Log.i((String) zzy.zzNa.get(), str);
        }
        Logger logger = zzOk;
        if (logger != null) {
            logger.info(str);
        }
    }

    public static void zzaE(String str) {
        zzaf zzkq = zzaf.zzkq();
        if (zzkq != null) {
            zzkq.zzbb(str);
        } else if (zzM(2)) {
            Log.w((String) zzy.zzNa.get(), str);
        }
        Logger logger = zzOk;
        if (logger != null) {
            logger.warn(str);
        }
    }

    public static void zzf(String str, Object obj) {
        zzaf zzkq = zzaf.zzkq();
        if (zzkq != null) {
            zzkq.zze(str, obj);
        } else if (zzM(3)) {
            Log.e((String) zzy.zzNa.get(), obj != null ? str + ":" + obj : str);
        }
        Logger logger = zzOk;
        if (logger != null) {
            logger.error(str);
        }
    }
}
