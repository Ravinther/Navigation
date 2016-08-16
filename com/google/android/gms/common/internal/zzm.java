package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

final class zzm extends zzl implements Callback {
    private final Handler mHandler;
    private final HashMap<zza, zzb> zzadV;
    private final com.google.android.gms.common.stats.zzb zzadW;
    private final long zzadX;
    private final Context zzqO;

    private static final class zza {
        private final String zzOj;
        private final ComponentName zzadY;

        public zza(String str) {
            this.zzOj = zzx.zzcs(str);
            this.zzadY = null;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_common_internal_zzm_zza = (zza) o;
            return zzw.equal(this.zzOj, com_google_android_gms_common_internal_zzm_zza.zzOj) && zzw.equal(this.zzadY, com_google_android_gms_common_internal_zzm_zza.zzadY);
        }

        public int hashCode() {
            return zzw.hashCode(this.zzOj, this.zzadY);
        }

        public String toString() {
            return this.zzOj == null ? this.zzadY.flattenToString() : this.zzOj;
        }

        public Intent zzoK() {
            return this.zzOj != null ? new Intent(this.zzOj).setPackage("com.google.android.gms") : new Intent().setComponent(this.zzadY);
        }
    }

    private final class zzb {
        private int mState;
        private IBinder zzacE;
        private ComponentName zzadY;
        private final zza zzadZ;
        private final Set<ServiceConnection> zzaea;
        private boolean zzaeb;
        private final zza zzaec;
        final /* synthetic */ zzm zzaed;

        public class zza implements ServiceConnection {
            final /* synthetic */ zzb zzaee;

            public zza(zzb com_google_android_gms_common_internal_zzm_zzb) {
                this.zzaee = com_google_android_gms_common_internal_zzm_zzb;
            }

            public void onServiceConnected(ComponentName component, IBinder binder) {
                synchronized (this.zzaee.zzaed.zzadV) {
                    this.zzaee.zzacE = binder;
                    this.zzaee.zzadY = component;
                    for (ServiceConnection onServiceConnected : this.zzaee.zzaea) {
                        onServiceConnected.onServiceConnected(component, binder);
                    }
                    this.zzaee.mState = 1;
                }
            }

            public void onServiceDisconnected(ComponentName component) {
                synchronized (this.zzaee.zzaed.zzadV) {
                    this.zzaee.zzacE = null;
                    this.zzaee.zzadY = component;
                    for (ServiceConnection onServiceDisconnected : this.zzaee.zzaea) {
                        onServiceDisconnected.onServiceDisconnected(component);
                    }
                    this.zzaee.mState = 2;
                }
            }
        }

        public zzb(zzm com_google_android_gms_common_internal_zzm, zza com_google_android_gms_common_internal_zzm_zza) {
            this.zzaed = com_google_android_gms_common_internal_zzm;
            this.zzaec = com_google_android_gms_common_internal_zzm_zza;
            this.zzadZ = new zza(this);
            this.zzaea = new HashSet();
            this.mState = 2;
        }

        public IBinder getBinder() {
            return this.zzacE;
        }

        public ComponentName getComponentName() {
            return this.zzadY;
        }

        public int getState() {
            return this.mState;
        }

        public boolean isBound() {
            return this.zzaeb;
        }

        public void zza(ServiceConnection serviceConnection, String str) {
            this.zzaed.zzadW.zza(this.zzaed.zzqO, serviceConnection, str, this.zzaec.zzoK());
            this.zzaea.add(serviceConnection);
        }

        public boolean zza(ServiceConnection serviceConnection) {
            return this.zzaea.contains(serviceConnection);
        }

        public void zzb(ServiceConnection serviceConnection, String str) {
            this.zzaed.zzadW.zzb(this.zzaed.zzqO, serviceConnection);
            this.zzaea.remove(serviceConnection);
        }

        public void zzcl(String str) {
            this.zzaeb = this.zzaed.zzadW.zza(this.zzaed.zzqO, str, this.zzaec.zzoK(), this.zzadZ, 129);
            if (this.zzaeb) {
                this.mState = 3;
                return;
            }
            try {
                this.zzaed.zzadW.zza(this.zzaed.zzqO, this.zzadZ);
            } catch (IllegalArgumentException e) {
            }
        }

        public void zzcm(String str) {
            this.zzaed.zzadW.zza(this.zzaed.zzqO, this.zzadZ);
            this.zzaeb = false;
            this.mState = 2;
        }

        public boolean zzoL() {
            return this.zzaea.isEmpty();
        }
    }

    zzm(Context context) {
        this.zzadV = new HashMap();
        this.zzqO = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.zzadW = com.google.android.gms.common.stats.zzb.zzpD();
        this.zzadX = 5000;
    }

    private boolean zza(zza com_google_android_gms_common_internal_zzm_zza, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzx.zzb((Object) serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzadV) {
            zzb com_google_android_gms_common_internal_zzm_zzb = (zzb) this.zzadV.get(com_google_android_gms_common_internal_zzm_zza);
            if (com_google_android_gms_common_internal_zzm_zzb != null) {
                this.mHandler.removeMessages(0, com_google_android_gms_common_internal_zzm_zzb);
                if (!com_google_android_gms_common_internal_zzm_zzb.zza(serviceConnection)) {
                    com_google_android_gms_common_internal_zzm_zzb.zza(serviceConnection, str);
                    switch (com_google_android_gms_common_internal_zzm_zzb.getState()) {
                        case TTSConst.TTSMULTILINE /*1*/:
                            serviceConnection.onServiceConnected(com_google_android_gms_common_internal_zzm_zzb.getComponentName(), com_google_android_gms_common_internal_zzm_zzb.getBinder());
                            break;
                        case TTSConst.TTSPARAGRAPH /*2*/:
                            com_google_android_gms_common_internal_zzm_zzb.zzcl(str);
                            break;
                        default:
                            break;
                    }
                }
                throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  config=" + com_google_android_gms_common_internal_zzm_zza);
            }
            com_google_android_gms_common_internal_zzm_zzb = new zzb(this, com_google_android_gms_common_internal_zzm_zza);
            com_google_android_gms_common_internal_zzm_zzb.zza(serviceConnection, str);
            com_google_android_gms_common_internal_zzm_zzb.zzcl(str);
            this.zzadV.put(com_google_android_gms_common_internal_zzm_zza, com_google_android_gms_common_internal_zzm_zzb);
            isBound = com_google_android_gms_common_internal_zzm_zzb.isBound();
        }
        return isBound;
    }

    private void zzb(zza com_google_android_gms_common_internal_zzm_zza, ServiceConnection serviceConnection, String str) {
        zzx.zzb((Object) serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzadV) {
            zzb com_google_android_gms_common_internal_zzm_zzb = (zzb) this.zzadV.get(com_google_android_gms_common_internal_zzm_zza);
            if (com_google_android_gms_common_internal_zzm_zzb == null) {
                throw new IllegalStateException("Nonexistent connection status for service config: " + com_google_android_gms_common_internal_zzm_zza);
            } else if (com_google_android_gms_common_internal_zzm_zzb.zza(serviceConnection)) {
                com_google_android_gms_common_internal_zzm_zzb.zzb(serviceConnection, str);
                if (com_google_android_gms_common_internal_zzm_zzb.zzoL()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, com_google_android_gms_common_internal_zzm_zzb), this.zzadX);
                }
            } else {
                throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  config=" + com_google_android_gms_common_internal_zzm_zza);
            }
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                zzb com_google_android_gms_common_internal_zzm_zzb = (zzb) msg.obj;
                synchronized (this.zzadV) {
                    if (com_google_android_gms_common_internal_zzm_zzb.zzoL()) {
                        if (com_google_android_gms_common_internal_zzm_zzb.isBound()) {
                            com_google_android_gms_common_internal_zzm_zzb.zzcm("GmsClientSupervisor");
                        }
                        this.zzadV.remove(com_google_android_gms_common_internal_zzm_zzb.zzaec);
                    }
                    break;
                }
                return true;
            default:
                return false;
        }
    }

    public boolean zza(String str, ServiceConnection serviceConnection, String str2) {
        return zza(new zza(str), serviceConnection, str2);
    }

    public void zzb(String str, ServiceConnection serviceConnection, String str2) {
        zzb(new zza(str), serviceConnection, str2);
    }
}
