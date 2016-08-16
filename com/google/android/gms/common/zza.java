package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class zza implements ServiceConnection {
    boolean zzYg;
    private final BlockingQueue<IBinder> zzYh;

    public zza() {
        this.zzYg = false;
        this.zzYh = new LinkedBlockingQueue();
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        this.zzYh.add(service);
    }

    public void onServiceDisconnected(ComponentName name) {
    }

    public IBinder zzmS() throws InterruptedException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("BlockingServiceConnection.getService() called on main thread");
        } else if (this.zzYg) {
            throw new IllegalStateException();
        } else {
            this.zzYg = true;
            return (IBinder) this.zzYh.take();
        }
    }
}
