package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

public class zza extends zzd {
    public static boolean zzLk;
    private Info zzLl;
    private final zzaj zzLm;
    private String zzLn;
    private boolean zzLo;
    private Object zzLp;

    zza(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzLo = false;
        this.zzLp = new Object();
        this.zzLm = new zzaj(com_google_android_gms_analytics_internal_zzf.zzid());
    }

    private boolean zza(Info info, Info info2) {
        String str = null;
        String id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        String zzjd = zzij().zzjd();
        synchronized (this.zzLp) {
            if (!this.zzLo) {
                this.zzLn = zzhT();
                this.zzLo = true;
            } else if (TextUtils.isEmpty(this.zzLn)) {
                if (info != null) {
                    str = info.getId();
                }
                if (str == null) {
                    boolean zzaX = zzaX(id + zzjd);
                    return zzaX;
                }
                this.zzLn = zzaW(str + zzjd);
            }
            Object zzaW = zzaW(id + zzjd);
            if (TextUtils.isEmpty(zzaW)) {
                return false;
            } else if (zzaW.equals(this.zzLn)) {
                return true;
            } else {
                if (TextUtils.isEmpty(this.zzLn)) {
                    str = zzjd;
                } else {
                    zzaY("Resetting the client id because Advertising Id changed.");
                    str = zzij().zzje();
                    zza("New client Id", str);
                }
                zzaX = zzaX(id + str);
                return zzaX;
            }
        }
    }

    private static String zzaW(String str) {
        if (zzam.zzbq("MD5") == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzam.zzbq("MD5").digest(str.getBytes()))});
    }

    private boolean zzaX(String str) {
        try {
            String zzaW = zzaW(str);
            zzaY("Storing hashed adid.");
            FileOutputStream openFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            openFileOutput.write(zzaW.getBytes());
            openFileOutput.close();
            this.zzLn = zzaW;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    private synchronized Info zzhR() {
        if (this.zzLm.zzv(1000)) {
            this.zzLm.start();
            Info zzhS = zzhS();
            if (zza(this.zzLl, zzhS)) {
                this.zzLl = zzhS;
            } else {
                zzbc("Failed to reset client id on adid change. Not using adid");
                this.zzLl = new Info("", false);
            }
        }
        return this.zzLl;
    }

    protected void zzhB() {
    }

    public boolean zzhM() {
        zzio();
        Info zzhR = zzhR();
        return (zzhR == null || zzhR.isLimitAdTrackingEnabled()) ? false : true;
    }

    public String zzhQ() {
        zzio();
        Info zzhR = zzhR();
        CharSequence id = zzhR != null ? zzhR.getId() : null;
        return TextUtils.isEmpty(id) ? null : id;
    }

    protected Info zzhS() {
        Info info = null;
        try {
            info = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException e) {
            zzbb("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
        } catch (Throwable th) {
            if (!zzLk) {
                zzLk = true;
                zzd("Error getting advertiser id", th);
            }
        }
        return info;
    }

    protected String zzhT() {
        Object obj;
        String str = null;
        try {
            FileInputStream openFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                zzbb("Hash file seems corrupted, deleting it.");
                openFileInput.close();
                getContext().deleteFile("gaClientIdData");
                return null;
            } else if (read <= 0) {
                zzaY("Hash file is empty.");
                openFileInput.close();
                return null;
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                    return str2;
                } catch (FileNotFoundException e) {
                    return str2;
                } catch (IOException e2) {
                    IOException iOException = e2;
                    str = str2;
                    IOException iOException2 = iOException;
                    zzd("Error reading Hash file, deleting it", obj);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
            }
        } catch (FileNotFoundException e3) {
            return null;
        } catch (IOException e4) {
            obj = e4;
            zzd("Error reading Hash file, deleting it", obj);
            getContext().deleteFile("gaClientIdData");
            return str;
        }
    }
}
