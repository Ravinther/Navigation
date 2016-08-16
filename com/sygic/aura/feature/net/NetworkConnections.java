package com.sygic.aura.feature.net;

public class NetworkConnections {
    private final NetworkThreadFactory mThreadFactory;

    public NetworkConnections() {
        this.mThreadFactory = new NetworkThreadFactory();
    }

    public int secureConnect(String strHost) {
        try {
            return ((Integer) this.mThreadFactory.getConnectThread().execute(strHost).get()).intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean secureDisconnect(int iHandle) {
        try {
            return ((Boolean) this.mThreadFactory.getDisconnectThread().execute(Integer.valueOf(iHandle)).get()).booleanValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int secureSend(int iHandle, byte[] data, int nLen) {
        try {
            return ((Boolean) this.mThreadFactory.getSendingThread(iHandle).execute(data).get()).booleanValue() ? nLen : -1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public byte[] secureReceive(int iHandle, int nLen) {
        try {
            return (byte[]) this.mThreadFactory.getReceivingThread(iHandle).execute(Integer.valueOf(nLen)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
