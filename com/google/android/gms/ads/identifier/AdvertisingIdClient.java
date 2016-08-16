package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.internal.zzav;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import loquendo.tts.engine.TTSConst;

public class AdvertisingIdClient {
    private static boolean zzog;
    private final Context mContext;
    com.google.android.gms.common.zza zzoa;
    zzav zzob;
    boolean zzoc;
    Object zzod;
    zza zzoe;
    final long zzof;

    public static final class Info {
        private final String zzol;
        private final boolean zzom;

        public Info(String advertisingId, boolean limitAdTrackingEnabled) {
            this.zzol = advertisingId;
            this.zzom = limitAdTrackingEnabled;
        }

        public String getId() {
            return this.zzol;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzom;
        }

        public String toString() {
            return "{" + this.zzol + "}" + this.zzom;
        }
    }

    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzoh;
        private long zzoi;
        CountDownLatch zzoj;
        boolean zzok;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzoh = new WeakReference(advertisingIdClient);
            this.zzoi = j;
            this.zzoj = new CountDownLatch(1);
            this.zzok = false;
            start();
        }

        private void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.zzoh.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzok = true;
            }
        }

        public void cancel() {
            this.zzoj.countDown();
        }

        public void run() {
            try {
                if (!this.zzoj.await(this.zzoi, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException e) {
                disconnect();
            }
        }

        public boolean zzaK() {
            return this.zzok;
        }
    }

    static {
        zzog = false;
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000);
    }

    public AdvertisingIdClient(Context context, long timeoutInMillis) {
        this.zzod = new Object();
        zzx.zzv(context);
        this.mContext = context;
        this.zzoc = false;
        this.zzof = timeoutInMillis;
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1);
        try {
            advertisingIdClient.zzb(false);
            Info info = advertisingIdClient.getInfo();
            return info;
        } finally {
            advertisingIdClient.finish();
        }
    }

    static zzav zza(Context context, com.google.android.gms.common.zza com_google_android_gms_common_zza) throws IOException {
        try {
            return com.google.android.gms.internal.zzav.zza.zzb(com_google_android_gms_common_zza.zzmS());
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            IOException iOException = new IOException(th);
        }
    }

    private void zzaJ() {
        synchronized (this.zzod) {
            if (this.zzoe != null) {
                this.zzoe.cancel();
                try {
                    this.zzoe.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzof > 0) {
                this.zzoe = new zza(this, this.zzof);
            }
        }
    }

    static com.google.android.gms.common.zza zzo(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            if (zzog) {
                Log.d("Ads", "Skipping gmscore version check");
                switch (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)) {
                    case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        break;
                    default:
                        throw new IOException("Google Play services not available");
                }
            }
            try {
                GooglePlayServicesUtil.zzaa(context);
            } catch (Throwable e) {
                throw new IOException(e);
            }
            ServiceConnection com_google_android_gms_common_zza = new com.google.android.gms.common.zza();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (zzb.zzpD().zza(context, intent, com_google_android_gms_common_zza, 1)) {
                    return com_google_android_gms_common_zza;
                }
                throw new IOException("Connection failure");
            } catch (Throwable e2) {
                IOException iOException = new IOException(e2);
            }
        } catch (NameNotFoundException e3) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public void finish() {
        zzx.zzci("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext == null || this.zzoa == null) {
                return;
            }
            try {
                if (this.zzoc) {
                    zzb.zzpD().zza(this.mContext, this.zzoa);
                }
            } catch (Throwable e) {
                Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", e);
            }
            this.zzoc = false;
            this.zzob = null;
            this.zzoa = null;
        }
    }

    public Info getInfo() throws IOException {
        Info info;
        zzx.zzci("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzoc) {
                synchronized (this.zzod) {
                    if (this.zzoe == null || !this.zzoe.zzaK()) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    zzb(false);
                    if (!this.zzoc) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (Throwable e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Throwable e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzx.zzv(this.zzoa);
            zzx.zzv(this.zzob);
            info = new Info(this.zzob.getId(), this.zzob.zzc(true));
        }
        zzaJ();
        return info;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzb(true);
    }

    protected void zzb(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzx.zzci("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzoc) {
                finish();
            }
            this.zzoa = zzo(this.mContext);
            this.zzob = zza(this.mContext, this.zzoa);
            this.zzoc = true;
            if (z) {
                zzaJ();
            }
        }
    }
}
