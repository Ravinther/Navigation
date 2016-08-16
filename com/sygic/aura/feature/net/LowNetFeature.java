package com.sygic.aura.feature.net;

import android.content.Context;
import android.net.ConnectivityManager;

public abstract class LowNetFeature {
    protected ConnectivityManager mConnManager;
    protected NetworkConnections mNet;

    public abstract int getType();

    public abstract boolean isConnected();

    public abstract int secureConnect(String str);

    public abstract boolean secureDisconnect(int i);

    public abstract byte[] secureReceive(int i, int i2);

    public abstract int secureSend(int i, byte[] bArr, int i2);

    protected LowNetFeature() {
    }

    public LowNetFeature(Context context) {
        this.mConnManager = (ConnectivityManager) context.getSystemService("connectivity");
    }

    public static LowNetFeature createInstance(Context context) {
        return new LowNetFeatureBase(context);
    }
}
