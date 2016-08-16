package com.google.android.gms.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqx.zza;
import com.google.android.gms.tagmanager.zzbg;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class zzqy implements Runnable {
    private final Context mContext;
    private final zzqw zzaRm;
    private final zzqn zzaUo;
    private final zzqx zzaUv;
    private final zzqs zzaUw;

    public zzqy(Context context, zzqn com_google_android_gms_internal_zzqn, zzqx com_google_android_gms_internal_zzqx) {
        this(context, com_google_android_gms_internal_zzqn, com_google_android_gms_internal_zzqx, new zzqw(), new zzqs());
    }

    zzqy(Context context, zzqn com_google_android_gms_internal_zzqn, zzqx com_google_android_gms_internal_zzqx, zzqw com_google_android_gms_internal_zzqw, zzqs com_google_android_gms_internal_zzqs) {
        zzx.zzv(context);
        zzx.zzv(com_google_android_gms_internal_zzqx);
        this.mContext = context;
        this.zzaUo = com_google_android_gms_internal_zzqn;
        this.zzaUv = com_google_android_gms_internal_zzqx;
        this.zzaRm = com_google_android_gms_internal_zzqw;
        this.zzaUw = com_google_android_gms_internal_zzqs;
    }

    public zzqy(Context context, zzqn com_google_android_gms_internal_zzqn, zzqx com_google_android_gms_internal_zzqx, String str) {
        this(context, com_google_android_gms_internal_zzqn, com_google_android_gms_internal_zzqx, new zzqw(), new zzqs());
        this.zzaUw.zzfj(str);
    }

    public void run() {
        zzeP();
    }

    boolean zzBY() {
        if (!zzbf("android.permission.INTERNET")) {
            zzbg.m1447e("Missing android.permission.INTERNET. Please add the following declaration to your AndroidManifest.xml: <uses-permission android:name=\"android.permission.INTERNET\" />");
            return false;
        } else if (zzbf("android.permission.ACCESS_NETWORK_STATE")) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
            zzbg.zzaE("NetworkLoader: No network connectivity - Offline");
            return false;
        } else {
            zzbg.m1447e("Missing android.permission.ACCESS_NETWORK_STATE. Please add the following declaration to your AndroidManifest.xml: <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />");
            return false;
        }
    }

    boolean zzbf(String str) {
        return this.mContext.getPackageManager().checkPermission(str, this.mContext.getPackageName()) == 0;
    }

    void zzeP() {
        String zzt;
        if (zzBY()) {
            zzbg.m1448v("NetworkLoader: Starting to load resource from Network.");
            zzqv zzBW = this.zzaRm.zzBW();
            try {
                zzt = this.zzaUw.zzt(this.zzaUo.zzBv());
                InputStream zzfs = zzBW.zzfs(zzt);
                try {
                    OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    zzlr.zza(zzfs, byteArrayOutputStream);
                    this.zzaUv.zzu(byteArrayOutputStream.toByteArray());
                    zzbg.m1448v("NetworkLoader: Resource loaded.");
                } catch (Throwable e) {
                    zzbg.zzb("NetworkLoader: Error when parsing downloaded resources from url: " + zzt + " " + e.getMessage(), e);
                    this.zzaUv.zza(zza.SERVER_ERROR);
                    zzBW.close();
                }
            } catch (FileNotFoundException e2) {
                zzbg.m1447e("NetworkLoader: No data is retrieved from the given url: " + zzt);
                this.zzaUv.zza(zza.SERVER_ERROR);
            } catch (Throwable e3) {
                zzbg.zzb("NetworkLoader: Error when loading resource from url: " + zzt + " " + e3.getMessage(), e3);
                this.zzaUv.zza(zza.IO_ERROR);
            } finally {
                zzBW.close();
            }
        } else {
            this.zzaUv.zza(zza.NOT_AVAILABLE);
        }
    }
}
