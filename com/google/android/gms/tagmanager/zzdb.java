package com.google.android.gms.tagmanager;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class zzdb extends zzak {
    private static final String ID;
    private static final String NAME;
    private static final String zzaSA;
    private static final String zzaSy;
    private static final String zzaSz;
    private final Context mContext;
    private Handler mHandler;
    private DataLayer zzaOT;
    private boolean zzaSB;
    private boolean zzaSC;
    private final HandlerThread zzaSD;
    private final Set<String> zzaSE;

    private final class zza implements Runnable {
        private final long zzOC;
        private final long zzaBW;
        private final String zzaSF;
        private final String zzaSG;
        private final long zzaSH;
        private long zzaSI;
        final /* synthetic */ zzdb zzaSJ;

        zza(zzdb com_google_android_gms_tagmanager_zzdb, String str, String str2, long j, long j2) {
            this.zzaSJ = com_google_android_gms_tagmanager_zzdb;
            this.zzaSF = str;
            this.zzaSG = str2;
            this.zzaBW = j;
            this.zzaSH = j2;
            this.zzOC = System.currentTimeMillis();
        }

        public void run() {
            if (this.zzaSH <= 0 || this.zzaSI < this.zzaSH) {
                this.zzaSI++;
                if (zzcu()) {
                    long currentTimeMillis = System.currentTimeMillis();
                    this.zzaSJ.zzaOT.push(DataLayer.mapOf("event", this.zzaSF, "gtm.timerInterval", String.valueOf(this.zzaBW), "gtm.timerLimit", String.valueOf(this.zzaSH), "gtm.timerStartTime", String.valueOf(this.zzOC), "gtm.timerCurrentTime", String.valueOf(currentTimeMillis), "gtm.timerElapsedTime", String.valueOf(currentTimeMillis - this.zzOC), "gtm.timerEventNumber", String.valueOf(this.zzaSI), "gtm.triggers", this.zzaSG));
                }
                this.zzaSJ.mHandler.postDelayed(this, this.zzaBW);
            } else if (!"0".equals(this.zzaSG)) {
                this.zzaSJ.zzaSE.remove(this.zzaSG);
            }
        }

        protected boolean zzcu() {
            if (this.zzaSJ.zzaSC) {
                return this.zzaSJ.zzaSB;
            }
            KeyguardManager keyguardManager = (KeyguardManager) this.zzaSJ.mContext.getSystemService("keyguard");
            PowerManager powerManager = (PowerManager) this.zzaSJ.mContext.getSystemService("power");
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) this.zzaSJ.mContext.getSystemService("activity")).getRunningAppProcesses()) {
                if (Process.myPid() == runningAppProcessInfo.pid && runningAppProcessInfo.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode() && powerManager.isScreenOn()) {
                    return true;
                }
            }
            return false;
        }
    }

    static {
        ID = zzad.TIMER_LISTENER.toString();
        NAME = zzae.NAME.toString();
        zzaSy = zzae.INTERVAL.toString();
        zzaSz = zzae.LIMIT.toString();
        zzaSA = zzae.UNIQUE_TRIGGER_ID.toString();
    }

    public zzdb(Context context, DataLayer dataLayer) {
        super(ID, zzaSy, NAME);
        this.zzaSE = new HashSet();
        this.mContext = context;
        this.zzaOT = dataLayer;
        this.zzaSD = new HandlerThread("Google GTM SDK Timer", 10);
        this.zzaSD.start();
        this.mHandler = new Handler(this.zzaSD.getLooper());
    }

    public com.google.android.gms.internal.zzag.zza zzG(Map<String, com.google.android.gms.internal.zzag.zza> map) {
        long parseLong;
        long parseLong2;
        Object zzg = zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(NAME));
        String zzg2 = zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(zzaSA));
        String zzg3 = zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(zzaSy));
        String zzg4 = zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(zzaSz));
        try {
            parseLong = Long.parseLong(zzg3);
        } catch (NumberFormatException e) {
            parseLong = 0;
        }
        try {
            parseLong2 = Long.parseLong(zzg4);
        } catch (NumberFormatException e2) {
            parseLong2 = 0;
        }
        if (parseLong > 0 && !TextUtils.isEmpty(zzg)) {
            if (zzg2 == null || zzg2.isEmpty()) {
                zzg2 = "0";
            }
            if (!this.zzaSE.contains(zzg2)) {
                if (!"0".equals(zzg2)) {
                    this.zzaSE.add(zzg2);
                }
                this.mHandler.postDelayed(new zza(this, zzg, zzg2, parseLong, parseLong2), parseLong);
            }
        }
        return zzdf.zzBg();
    }

    public boolean zzzx() {
        return false;
    }
}
