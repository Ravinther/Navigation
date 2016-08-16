package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class zzam extends zzal {
    private static AdvertisingIdClient zznq;
    private static CountDownLatch zznr;
    private static boolean zzns;
    private boolean zznt;

    class zza {
        private String zznu;
        private boolean zznv;
        final /* synthetic */ zzam zznw;

        public zza(zzam com_google_android_gms_internal_zzam, String str, boolean z) {
            this.zznw = com_google_android_gms_internal_zzam;
            this.zznu = str;
            this.zznv = z;
        }

        public String getId() {
            return this.zznu;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zznv;
        }
    }

    private static final class zzb implements Runnable {
        private Context zznx;

        public zzb(Context context) {
            this.zznx = context.getApplicationContext();
            if (this.zznx == null) {
                this.zznx = context;
            }
        }

        public void run() {
            try {
                AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(this.zznx);
                advertisingIdClient.start();
                synchronized (zzam.class) {
                    if (zzam.zznq == null) {
                        zzam.zznq = advertisingIdClient;
                    } else {
                        advertisingIdClient.finish();
                    }
                }
            } catch (GooglePlayServicesNotAvailableException e) {
                zzam.zzns = true;
            } catch (IOException e2) {
            } catch (GooglePlayServicesRepairableException e3) {
            }
            zzam.zznr.countDown();
        }
    }

    static {
        zznq = null;
        zznr = new CountDownLatch(1);
    }

    protected zzam(Context context, zzap com_google_android_gms_internal_zzap, zzaq com_google_android_gms_internal_zzaq, boolean z) {
        super(context, com_google_android_gms_internal_zzap, com_google_android_gms_internal_zzaq);
        this.zznt = z;
    }

    public static zzam zza(String str, Context context, boolean z) {
        zzap com_google_android_gms_internal_zzah = new zzah();
        zzal.zza(str, context, com_google_android_gms_internal_zzah);
        if (z) {
            synchronized (zzam.class) {
                if (zznq == null) {
                    new Thread(new zzb(context)).start();
                }
            }
        }
        return new zzam(context, com_google_android_gms_internal_zzah, new zzas(239), z);
    }

    zza zzY() throws IOException {
        synchronized (zzam.class) {
            try {
                zza com_google_android_gms_internal_zzam_zza;
                if (!zznr.await(2, TimeUnit.SECONDS)) {
                    com_google_android_gms_internal_zzam_zza = new zza(this, null, false);
                    return com_google_android_gms_internal_zzam_zza;
                } else if (zznq == null) {
                    com_google_android_gms_internal_zzam_zza = new zza(this, null, false);
                    return com_google_android_gms_internal_zzam_zza;
                } else {
                    Info info = zznq.getInfo();
                    return new zza(this, zzk(info.getId()), info.isLimitAdTrackingEnabled());
                }
            } catch (InterruptedException e) {
                return new zza(this, null, false);
            }
        }
    }

    protected void zzc(Context context) {
        super.zzc(context);
        try {
            if (zzns || !this.zznt) {
                zza(24, zzal.zze(context));
                return;
            }
            zza zzY = zzY();
            String id = zzY.getId();
            if (id != null) {
                zza(28, zzY.isLimitAdTrackingEnabled() ? 1 : 0);
                zza(26, 5);
                zza(24, id);
            }
        } catch (IOException e) {
        } catch (zza e2) {
        }
    }
}
