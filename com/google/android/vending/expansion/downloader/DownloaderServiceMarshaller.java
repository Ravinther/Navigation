package com.google.android.vending.expansion.downloader;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public class DownloaderServiceMarshaller {

    private static class Proxy implements IDownloaderService {
        private Messenger mMsg;

        private void send(int method, Bundle params) {
            Message m = Message.obtain(null, method);
            m.setData(params);
            try {
                this.mMsg.send(m);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public Proxy(Messenger msg) {
            this.mMsg = msg;
        }

        public void requestAbortDownload() {
            send(1, new Bundle());
        }

        public void requestPauseDownload() {
            send(2, new Bundle());
        }

        public void setDownloadFlags(int flags) {
            Bundle params = new Bundle();
            params.putInt("flags", flags);
            send(3, params);
        }

        public void requestContinueDownload() {
            send(4, new Bundle());
        }

        public void requestDownloadStatus() {
            send(5, new Bundle());
        }

        public void onClientUpdated(Messenger clientMessenger) {
            Bundle bundle = new Bundle(1);
            bundle.putParcelable("EMH", clientMessenger);
            send(6, bundle);
        }
    }

    private static class Stub implements IStub {
        private IDownloaderService mItf;
        final Messenger mMessenger;

        /* renamed from: com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller.Stub.1 */
        class C10331 extends Handler {
            C10331() {
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        Stub.this.mItf.requestAbortDownload();
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        Stub.this.mItf.requestPauseDownload();
                    case TTSConst.TTSUNICODE /*3*/:
                        Stub.this.mItf.setDownloadFlags(msg.getData().getInt("flags"));
                    case TTSConst.TTSXML /*4*/:
                        Stub.this.mItf.requestContinueDownload();
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        Stub.this.mItf.requestDownloadStatus();
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                        Stub.this.mItf.onClientUpdated((Messenger) msg.getData().getParcelable("EMH"));
                    default:
                }
            }
        }

        public Stub(IDownloaderService itf) {
            this.mItf = null;
            this.mMessenger = new Messenger(new C10331());
            this.mItf = itf;
        }

        public Messenger getMessenger() {
            return this.mMessenger;
        }

        public void connect(Context c) {
        }

        public void disconnect(Context c) {
        }
    }

    public static IDownloaderService CreateProxy(Messenger msg) {
        return new Proxy(msg);
    }

    public static IStub CreateStub(IDownloaderService itf) {
        return new Stub(itf);
    }
}
