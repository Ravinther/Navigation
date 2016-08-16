package com.google.ads.conversiontracking;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.google.ads.conversiontracking.n */
public class C0561n implements ServiceConnection {
    boolean f1230a;
    private final BlockingQueue<IBinder> f1231b;

    public C0561n() {
        this.f1230a = false;
        this.f1231b = new LinkedBlockingQueue();
    }

    public IBinder m1424a() throws InterruptedException {
        if (this.f1230a) {
            throw new IllegalStateException();
        }
        this.f1230a = true;
        return (IBinder) this.f1231b.take();
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        try {
            this.f1231b.put(service);
        } catch (InterruptedException e) {
        }
    }

    public void onServiceDisconnected(ComponentName name) {
    }
}
