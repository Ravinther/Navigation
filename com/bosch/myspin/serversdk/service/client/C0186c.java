package com.bosch.myspin.serversdk.service.client;

import com.bosch.myspin.serversdk.MySpinServerSDK.ConnectionStateListener;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.bosch.myspin.serversdk.service.client.c */
public class C0186c {
    private C0185b f152a;
    private Set<ConnectionStateListener> f153b;

    C0186c() {
        this.f152a = C0185b.UNDEFINED;
        this.f153b = new HashSet();
    }

    public synchronized void m173a(ConnectionStateListener connectionStateListener) {
        if (connectionStateListener != null) {
            this.f153b.add(connectionStateListener);
            if (this.f152a != C0185b.UNDEFINED) {
                connectionStateListener.onConnectionStateChanged(C0186c.m172b(this.f152a));
            }
        }
    }

    synchronized void m174a(C0185b c0185b) {
        if (!(c0185b == C0185b.UNDEFINED || this.f152a == c0185b)) {
            for (ConnectionStateListener onConnectionStateChanged : this.f153b) {
                onConnectionStateChanged.onConnectionStateChanged(C0186c.m172b(c0185b));
            }
        }
        this.f152a = c0185b;
    }

    private static boolean m172b(C0185b c0185b) {
        if (C0185b.CONNECTED == c0185b) {
            return true;
        }
        if (C0185b.DISCONNECTED == c0185b) {
            return false;
        }
        throw new IllegalArgumentException("toBoolean(UNDEFINED) is not supported!");
    }
}
