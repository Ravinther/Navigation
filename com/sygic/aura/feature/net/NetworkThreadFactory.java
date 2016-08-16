package com.sygic.aura.feature.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class NetworkThreadFactory {
    private final Map<Integer, Socket> mSocketMap;

    private class ConnectThread extends NetworkThread<String, Integer> {
        private ConnectThread() {
        }

        public void run() {
            EasySSLSocketFactory socketFactory = new EasySSLSocketFactory();
            this.mRetValue = Integer.valueOf(-1);
            String strHost = this.mParam;
            try {
                Socket socket = socketFactory.createSocket(new Socket(strHost, 443), strHost, 443, false);
                int iHash = socket.hashCode();
                NetworkThreadFactory.this.mSocketMap.put(Integer.valueOf(iHash), socket);
                this.mRetValue = Integer.valueOf(iHash);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private class DisconnectThread extends NetworkThread<Integer, Boolean> {
        private DisconnectThread() {
        }

        public void run() {
            this.mRetValue = Boolean.valueOf(false);
            if (NetworkThreadFactory.this.mSocketMap != null) {
                int iHandle = ((Integer) this.mParam).intValue();
                Socket socket = (Socket) NetworkThreadFactory.this.mSocketMap.get(Integer.valueOf(iHandle));
                if (socket != null) {
                    try {
                        socket.close();
                        NetworkThreadFactory.this.mSocketMap.remove(Integer.valueOf(iHandle));
                        this.mRetValue = Boolean.valueOf(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class ReceivingThread extends NetworkThread<Integer, byte[]> {
        private int mHandle;

        public ReceivingThread(int iHandle) {
            this.mHandle = -1;
            this.mHandle = iHandle;
        }

        public void run() {
            this.mRetValue = null;
            if (NetworkThreadFactory.this.mSocketMap != null) {
                Socket socket = (Socket) NetworkThreadFactory.this.mSocketMap.get(Integer.valueOf(this.mHandle));
                if (socket != null) {
                    int nLen = ((Integer) this.mParam).intValue();
                    byte[] cbData = new byte[nLen];
                    byte[] cbRetData = new byte[(nLen + 8)];
                    try {
                        int nRead = socket.getInputStream().read(cbData, 0, nLen);
                        if (nRead == -1) {
                            nRead = 0;
                        }
                        int nDen = 10000000;
                        int nNum = nRead;
                        for (int i = 0; i < 8; i++) {
                            cbRetData[i] = (byte) (nNum / nDen);
                            nNum -= (nNum / nDen) * nDen;
                            nDen /= 10;
                        }
                        System.arraycopy(cbData, 0, cbRetData, 8, nRead);
                        this.mRetValue = cbRetData;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class SendingThread extends NetworkThread<byte[], Boolean> {
        private int mHandle;

        public SendingThread(int iHandle) {
            this.mHandle = -1;
            this.mHandle = iHandle;
        }

        public void run() {
            this.mRetValue = Boolean.valueOf(false);
            if (NetworkThreadFactory.this.mSocketMap != null) {
                Socket socket = (Socket) NetworkThreadFactory.this.mSocketMap.get(Integer.valueOf(this.mHandle));
                if (socket != null) {
                    try {
                        socket.getOutputStream().write(this.mParam);
                        this.mRetValue = Boolean.valueOf(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    NetworkThreadFactory() {
        this.mSocketMap = Collections.synchronizedMap(new HashMap());
    }

    public NetworkThread<String, Integer> getConnectThread() {
        return new ConnectThread();
    }

    public NetworkThread<Integer, Boolean> getDisconnectThread() {
        return new DisconnectThread();
    }

    public NetworkThread<byte[], Boolean> getSendingThread(int iHandle) {
        return new SendingThread(iHandle);
    }

    public NetworkThread<Integer, byte[]> getReceivingThread(int iHandle) {
        return new ReceivingThread(iHandle);
    }
}
