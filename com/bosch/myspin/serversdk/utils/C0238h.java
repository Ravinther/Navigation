package com.bosch.myspin.serversdk.utils;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;

/* renamed from: com.bosch.myspin.serversdk.utils.h */
public class C0238h extends HandlerThread {
    private Handler f433a;
    private Callback f434b;

    public C0238h(String str, Callback callback) {
        super(str);
        this.f434b = null;
        this.f434b = callback;
    }

    protected synchronized void onLooperPrepared() {
        if (this.f434b != null) {
            this.f433a = new Handler(this.f434b);
        } else {
            this.f433a = new Handler();
        }
        notifyAll();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized android.os.Handler m373a() {
        /*
        r1 = this;
        monitor-enter(r1);
        r1.getLooper();	 Catch:{ all -> 0x0011 }
    L_0x0004:
        r0 = r1.f433a;	 Catch:{ all -> 0x0011 }
        if (r0 != 0) goto L_0x0014;
    L_0x0008:
        r1.wait();	 Catch:{ InterruptedException -> 0x000c }
        goto L_0x0004;
    L_0x000c:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0011 }
        goto L_0x0004;
    L_0x0011:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
    L_0x0014:
        r0 = r1.f433a;	 Catch:{ all -> 0x0011 }
        monitor-exit(r1);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bosch.myspin.serversdk.utils.h.a():android.os.Handler");
    }
}
