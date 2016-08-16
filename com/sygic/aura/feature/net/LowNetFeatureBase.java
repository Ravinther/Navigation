package com.sygic.aura.feature.net;

import android.content.Context;
import android.net.NetworkInfo;

/* compiled from: LowNetFeature */
class LowNetFeatureBase extends LowNetFeature {
    protected LowNetFeatureBase() {
    }

    protected LowNetFeatureBase(Context context) {
        super(context);
    }

    public boolean isConnected() {
        if (this.mConnManager != null) {
            NetworkInfo ni = this.mConnManager.getActiveNetworkInfo();
            if (ni != null) {
                return ni.isConnected();
            }
        }
        return false;
    }

    public int getType() {
        if (this.mConnManager != null) {
            NetworkInfo ni = this.mConnManager.getActiveNetworkInfo();
            if (ni != null) {
                return ni.getType();
            }
        }
        return -1;
    }

    public int secureConnect(String strHost) {
        if (this.mNet == null) {
            this.mNet = new NetworkConnections();
        }
        if (this.mNet != null) {
            return this.mNet.secureConnect(strHost);
        }
        return -1;
    }

    public boolean secureDisconnect(int iHandle) {
        if (this.mNet != null) {
            return this.mNet.secureDisconnect(iHandle);
        }
        return false;
    }

    public int secureSend(int iHandle, byte[] data, int nLen) {
        if (this.mNet != null) {
            return this.mNet.secureSend(iHandle, data, nLen);
        }
        return -1;
    }

    public byte[] secureReceive(int iHandle, int nLen) {
        if (this.mNet != null) {
            return this.mNet.secureReceive(iHandle, nLen);
        }
        return null;
    }
}
