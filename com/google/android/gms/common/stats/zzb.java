package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.stats.zzc.zza;
import com.google.android.gms.internal.zzll;
import com.google.android.gms.internal.zzlw;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class zzb {
    private static final Object zzadT;
    private static Integer zzafB;
    private static zzb zzafu;
    private static final ComponentName zzafz;
    private zze zzafA;
    private final List<String> zzafv;
    private final List<String> zzafw;
    private final List<String> zzafx;
    private final List<String> zzafy;

    static {
        zzadT = new Object();
        zzafz = new ComponentName("com.google.android.gms", "com.google.android.gms.common.stats.GmsCoreStatsService");
    }

    private zzb() {
        if (getLogLevel() == zzd.LOG_LEVEL_OFF) {
            this.zzafv = Collections.EMPTY_LIST;
            this.zzafw = Collections.EMPTY_LIST;
            this.zzafx = Collections.EMPTY_LIST;
            this.zzafy = Collections.EMPTY_LIST;
            return;
        }
        String str = (String) zza.zzafE.get();
        this.zzafv = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        str = (String) zza.zzafF.get();
        this.zzafw = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        str = (String) zza.zzafG.get();
        this.zzafx = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        str = (String) zza.zzafH.get();
        this.zzafy = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        this.zzafA = new zze(1024, ((Long) zza.zzafI.get()).longValue());
    }

    private static int getLogLevel() {
        if (zzafB == null) {
            try {
                zzafB = Integer.valueOf(zzll.zzjk() ? ((Integer) zza.zzafD.get()).intValue() : zzd.LOG_LEVEL_OFF);
            } catch (SecurityException e) {
                zzafB = Integer.valueOf(zzd.LOG_LEVEL_OFF);
            }
        }
        return zzafB.intValue();
    }

    private void zza(Context context, ServiceConnection serviceConnection, String str, Intent intent, int i) {
        if (zzd.zzacF) {
            String zzb = zzb(serviceConnection);
            if (zza(context, str, intent, zzb, i)) {
                Parcelable connectionEvent;
                long currentTimeMillis = System.currentTimeMillis();
                String str2 = null;
                if ((getLogLevel() & zzd.zzafM) != 0) {
                    str2 = zzlw.zzm(3, 5);
                }
                long j = 0;
                if ((getLogLevel() & zzd.zzafO) != 0) {
                    j = Debug.getNativeHeapAllocatedSize();
                }
                if (i == 1 || i == 4) {
                    connectionEvent = new ConnectionEvent(currentTimeMillis, i, null, null, null, null, str2, zzb, SystemClock.elapsedRealtime(), j);
                } else {
                    ServiceInfo zzc = zzc(context, intent);
                    connectionEvent = new ConnectionEvent(currentTimeMillis, i, zzlw.zzap(context), str, zzc.processName, zzc.name, str2, zzb, SystemClock.elapsedRealtime(), j);
                }
                context.startService(new Intent().setComponent(zzafz).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", connectionEvent));
            }
        }
    }

    private boolean zza(Context context, String str, Intent intent, String str2, int i) {
        int logLevel = getLogLevel();
        if (logLevel == zzd.LOG_LEVEL_OFF || this.zzafA == null) {
            return false;
        }
        if (i == 4 || i == 1) {
            return this.zzafA.zzcz(str2);
        } else {
            ServiceInfo zzc = zzc(context, intent);
            if (zzc == null) {
                Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", new Object[]{str, intent.toUri(0)}));
                return false;
            }
            String zzap = zzlw.zzap(context);
            String str3 = zzc.processName;
            String str4 = zzc.name;
            if (this.zzafv.contains(zzap) || this.zzafw.contains(str) || this.zzafx.contains(str3) || this.zzafy.contains(str4)) {
                return false;
            }
            if (str3.equals(zzap) && (logLevel & zzd.zzafN) != 0) {
                return false;
            }
            this.zzafA.zzcy(str2);
            return true;
        }
    }

    private String zzb(ServiceConnection serviceConnection) {
        return String.valueOf((Process.myPid() << 32) | System.identityHashCode(serviceConnection));
    }

    private boolean zzb(Context context, Intent intent) {
        ComponentName component = intent.getComponent();
        return (component == null || (zzd.zzacF && "com.google.android.gms".equals(component.getPackageName()))) ? false : zzll.zzi(context, component.getPackageName());
    }

    private static ServiceInfo zzc(Context context, Intent intent) {
        List queryIntentServices = context.getPackageManager().queryIntentServices(intent, 128);
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", new Object[]{intent.toUri(0), zzlw.zzm(3, 20)}));
            return null;
        }
        if (queryIntentServices.size() > 1) {
            Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", new Object[]{intent.toUri(0), zzlw.zzm(3, 20)}));
            Iterator it = queryIntentServices.iterator();
            if (it.hasNext()) {
                Log.w("ConnectionTracker", ((ResolveInfo) it.next()).serviceInfo.name);
                return null;
            }
        }
        return ((ResolveInfo) queryIntentServices.get(0)).serviceInfo;
    }

    public static zzb zzpD() {
        synchronized (zzadT) {
            if (zzafu == null) {
                zzafu = new zzb();
            }
        }
        return zzafu;
    }

    public void zza(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
        zza(context, serviceConnection, null, null, 1);
    }

    public void zza(Context context, ServiceConnection serviceConnection, String str, Intent intent) {
        zza(context, serviceConnection, str, intent, 3);
    }

    public boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int i) {
        return zza(context, context.getClass().getName(), intent, serviceConnection, i);
    }

    public boolean zza(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i) {
        if (zzb(context, intent)) {
            Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        boolean bindService = context.bindService(intent, serviceConnection, i);
        if (bindService) {
            zza(context, serviceConnection, str, intent, 2);
        }
        return bindService;
    }

    public void zzb(Context context, ServiceConnection serviceConnection) {
        zza(context, serviceConnection, null, null, 4);
    }
}
