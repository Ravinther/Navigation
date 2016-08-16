package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzk implements Callback {
    private final Handler mHandler;
    private final zza zzadM;
    private final ArrayList<ConnectionCallbacks> zzadN;
    final ArrayList<ConnectionCallbacks> zzadO;
    private final ArrayList<OnConnectionFailedListener> zzadP;
    private volatile boolean zzadQ;
    private final AtomicInteger zzadR;
    private boolean zzadS;
    private final Object zzpc;

    public interface zza {
        boolean isConnected();

        Bundle zzmw();
    }

    public zzk(Looper looper, zza com_google_android_gms_common_internal_zzk_zza) {
        this.zzadN = new ArrayList();
        this.zzadO = new ArrayList();
        this.zzadP = new ArrayList();
        this.zzadQ = false;
        this.zzadR = new AtomicInteger(0);
        this.zzadS = false;
        this.zzpc = new Object();
        this.zzadM = com_google_android_gms_common_internal_zzk_zza;
        this.mHandler = new Handler(looper, this);
    }

    public boolean handleMessage(Message msg) {
        if (msg.what == 1) {
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) msg.obj;
            synchronized (this.zzpc) {
                if (this.zzadQ && this.zzadM.isConnected() && this.zzadN.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zzadM.zzmw());
                }
            }
            return true;
        }
        Log.wtf("GmsClientEvents", "Don't know how to handle this message.");
        return false;
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        zzx.zzv(listener);
        synchronized (this.zzpc) {
            if (this.zzadN.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + listener + " is already registered");
            } else {
                this.zzadN.add(listener);
            }
        }
        if (this.zzadM.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, listener));
        }
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        zzx.zzv(listener);
        synchronized (this.zzpc) {
            if (this.zzadP.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + listener + " is already registered");
            } else {
                this.zzadP.add(listener);
            }
        }
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        zzx.zzv(listener);
        synchronized (this.zzpc) {
            if (!this.zzadN.remove(listener)) {
                Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + listener + " not found");
            } else if (this.zzadS) {
                this.zzadO.add(listener);
            }
        }
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        zzx.zzv(listener);
        synchronized (this.zzpc) {
            if (!this.zzadP.remove(listener)) {
                Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + listener + " not found");
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzbB(int r6) {
        /*
        r5 = this;
        r0 = 0;
        r1 = 1;
        r2 = android.os.Looper.myLooper();
        r3 = r5.mHandler;
        r3 = r3.getLooper();
        if (r2 != r3) goto L_0x000f;
    L_0x000e:
        r0 = r1;
    L_0x000f:
        r2 = "onUnintentionalDisconnection must only be called on the Handler thread";
        com.google.android.gms.common.internal.zzx.zza(r0, r2);
        r0 = r5.mHandler;
        r0.removeMessages(r1);
        r1 = r5.zzpc;
        monitor-enter(r1);
        r0 = 1;
        r5.zzadS = r0;	 Catch:{ all -> 0x005f }
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x005f }
        r2 = r5.zzadN;	 Catch:{ all -> 0x005f }
        r0.<init>(r2);	 Catch:{ all -> 0x005f }
        r2 = r5.zzadR;	 Catch:{ all -> 0x005f }
        r2 = r2.get();	 Catch:{ all -> 0x005f }
        r3 = r0.iterator();	 Catch:{ all -> 0x005f }
    L_0x0031:
        r0 = r3.hasNext();	 Catch:{ all -> 0x005f }
        if (r0 == 0) goto L_0x0049;
    L_0x0037:
        r0 = r3.next();	 Catch:{ all -> 0x005f }
        r0 = (com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks) r0;	 Catch:{ all -> 0x005f }
        r4 = r5.zzadQ;	 Catch:{ all -> 0x005f }
        if (r4 == 0) goto L_0x0049;
    L_0x0041:
        r4 = r5.zzadR;	 Catch:{ all -> 0x005f }
        r4 = r4.get();	 Catch:{ all -> 0x005f }
        if (r4 == r2) goto L_0x0053;
    L_0x0049:
        r0 = r5.zzadO;	 Catch:{ all -> 0x005f }
        r0.clear();	 Catch:{ all -> 0x005f }
        r0 = 0;
        r5.zzadS = r0;	 Catch:{ all -> 0x005f }
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
        return;
    L_0x0053:
        r4 = r5.zzadN;	 Catch:{ all -> 0x005f }
        r4 = r4.contains(r0);	 Catch:{ all -> 0x005f }
        if (r4 == 0) goto L_0x0031;
    L_0x005b:
        r0.onConnectionSuspended(r6);	 Catch:{ all -> 0x005f }
        goto L_0x0031;
    L_0x005f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzk.zzbB(int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzh(android.os.Bundle r6) {
        /*
        r5 = this;
        r2 = 0;
        r1 = 1;
        r0 = android.os.Looper.myLooper();
        r3 = r5.mHandler;
        r3 = r3.getLooper();
        if (r0 != r3) goto L_0x006f;
    L_0x000e:
        r0 = r1;
    L_0x000f:
        r3 = "onConnectionSuccess must only be called on the Handler thread";
        com.google.android.gms.common.internal.zzx.zza(r0, r3);
        r3 = r5.zzpc;
        monitor-enter(r3);
        r0 = r5.zzadS;	 Catch:{ all -> 0x0081 }
        if (r0 != 0) goto L_0x0071;
    L_0x001c:
        r0 = r1;
    L_0x001d:
        com.google.android.gms.common.internal.zzx.zzY(r0);	 Catch:{ all -> 0x0081 }
        r0 = r5.mHandler;	 Catch:{ all -> 0x0081 }
        r4 = 1;
        r0.removeMessages(r4);	 Catch:{ all -> 0x0081 }
        r0 = 1;
        r5.zzadS = r0;	 Catch:{ all -> 0x0081 }
        r0 = r5.zzadO;	 Catch:{ all -> 0x0081 }
        r0 = r0.size();	 Catch:{ all -> 0x0081 }
        if (r0 != 0) goto L_0x0073;
    L_0x0031:
        com.google.android.gms.common.internal.zzx.zzY(r1);	 Catch:{ all -> 0x0081 }
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0081 }
        r1 = r5.zzadN;	 Catch:{ all -> 0x0081 }
        r0.<init>(r1);	 Catch:{ all -> 0x0081 }
        r1 = r5.zzadR;	 Catch:{ all -> 0x0081 }
        r1 = r1.get();	 Catch:{ all -> 0x0081 }
        r2 = r0.iterator();	 Catch:{ all -> 0x0081 }
    L_0x0045:
        r0 = r2.hasNext();	 Catch:{ all -> 0x0081 }
        if (r0 == 0) goto L_0x0065;
    L_0x004b:
        r0 = r2.next();	 Catch:{ all -> 0x0081 }
        r0 = (com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks) r0;	 Catch:{ all -> 0x0081 }
        r4 = r5.zzadQ;	 Catch:{ all -> 0x0081 }
        if (r4 == 0) goto L_0x0065;
    L_0x0055:
        r4 = r5.zzadM;	 Catch:{ all -> 0x0081 }
        r4 = r4.isConnected();	 Catch:{ all -> 0x0081 }
        if (r4 == 0) goto L_0x0065;
    L_0x005d:
        r4 = r5.zzadR;	 Catch:{ all -> 0x0081 }
        r4 = r4.get();	 Catch:{ all -> 0x0081 }
        if (r4 == r1) goto L_0x0075;
    L_0x0065:
        r0 = r5.zzadO;	 Catch:{ all -> 0x0081 }
        r0.clear();	 Catch:{ all -> 0x0081 }
        r0 = 0;
        r5.zzadS = r0;	 Catch:{ all -> 0x0081 }
        monitor-exit(r3);	 Catch:{ all -> 0x0081 }
        return;
    L_0x006f:
        r0 = r2;
        goto L_0x000f;
    L_0x0071:
        r0 = r2;
        goto L_0x001d;
    L_0x0073:
        r1 = r2;
        goto L_0x0031;
    L_0x0075:
        r4 = r5.zzadO;	 Catch:{ all -> 0x0081 }
        r4 = r4.contains(r0);	 Catch:{ all -> 0x0081 }
        if (r4 != 0) goto L_0x0045;
    L_0x007d:
        r0.onConnected(r6);	 Catch:{ all -> 0x0081 }
        goto L_0x0045;
    L_0x0081:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0081 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzk.zzh(android.os.Bundle):void");
    }

    public void zzj(ConnectionResult connectionResult) {
        zzx.zza(Looper.myLooper() == this.mHandler.getLooper(), "onConnectionFailure must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.zzpc) {
            ArrayList arrayList = new ArrayList(this.zzadP);
            int i = this.zzadR.get();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                OnConnectionFailedListener onConnectionFailedListener = (OnConnectionFailedListener) it.next();
                if (!this.zzadQ || this.zzadR.get() != i) {
                    return;
                } else if (this.zzadP.contains(onConnectionFailedListener)) {
                    onConnectionFailedListener.onConnectionFailed(connectionResult);
                }
            }
        }
    }

    public void zzoI() {
        this.zzadQ = false;
        this.zzadR.incrementAndGet();
    }

    public void zzoJ() {
        this.zzadQ = true;
    }
}
