package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.internal.zzc.zza;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import loquendo.tts.engine.TTSConst;

public class zzy {
    private static Context zzaGM;
    private static zzc zzaGN;

    private static Context getRemoteContext(Context context) {
        if (zzaGM == null) {
            if (zzwZ()) {
                zzaGM = context.getApplicationContext();
            } else {
                zzaGM = GooglePlayServicesUtil.getRemoteContext(context);
            }
        }
        return zzaGM;
    }

    private static <T> T zza(ClassLoader classLoader, String str) {
        try {
            return zzc(((ClassLoader) zzx.zzv(classLoader)).loadClass(str));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find dynamic class " + str);
        }
    }

    public static zzc zzaF(Context context) throws GooglePlayServicesNotAvailableException {
        zzx.zzv(context);
        if (zzaGN != null) {
            return zzaGN;
        }
        zzaG(context);
        zzaGN = zzaH(context);
        try {
            zzaGN.zzd(zze.zzx(getRemoteContext(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return zzaGN;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    private static void zzaG(Context context) throws GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (isGooglePlayServicesAvailable) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            default:
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
    }

    private static zzc zzaH(Context context) {
        if (zzwZ()) {
            Log.i(zzy.class.getSimpleName(), "Making Creator statically");
            return (zzc) zzc(zzxa());
        }
        Log.i(zzy.class.getSimpleName(), "Making Creator dynamically");
        return zza.zzck((IBinder) zza(getRemoteContext(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
    }

    private static <T> T zzc(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("Unable to instantiate the dynamic class " + cls.getName());
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unable to call the default constructor of " + cls.getName());
        }
    }

    public static boolean zzwZ() {
        return false;
    }

    private static Class<?> zzxa() {
        try {
            return Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
