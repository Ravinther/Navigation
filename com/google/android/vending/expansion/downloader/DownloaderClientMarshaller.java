package com.google.android.vending.expansion.downloader;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.google.android.vending.expansion.downloader.impl.DownloaderService;
import loquendo.tts.engine.TTSConst;

public class DownloaderClientMarshaller {

    private static class Proxy implements IDownloaderClient {
        private Messenger mServiceMessenger;

        public void onDownloadStateChanged(int newState) {
            Bundle params = new Bundle(1);
            params.putInt("newState", newState);
            send(10, params);
        }

        public void onDownloadProgress(DownloadProgressInfo progress) {
            Bundle params = new Bundle(1);
            params.putParcelable("progress", progress);
            send(11, params);
        }

        private void send(int method, Bundle params) {
            Message m = Message.obtain(null, method);
            m.setData(params);
            try {
                this.mServiceMessenger.send(m);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public Proxy(Messenger msg) {
            this.mServiceMessenger = msg;
        }

        public void onServiceConnected(Messenger m) {
        }
    }

    private static class Stub implements IStub {
        private boolean mBound;
        private ServiceConnection mConnection;
        private Context mContext;
        private Class<?> mDownloaderServiceClass;
        private IDownloaderClient mItf;
        final Messenger mMessenger;
        private Messenger mServiceMessenger;

        /* renamed from: com.google.android.vending.expansion.downloader.DownloaderClientMarshaller.Stub.1 */
        class C10311 extends Handler {
            C10311() {
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TTSConst.TTSEVT_RESUME /*10*/:
                        Stub.this.mItf.onDownloadStateChanged(msg.getData().getInt("newState"));
                    case TTSConst.TTSEVT_FREESPACE /*11*/:
                        Bundle bun = msg.getData();
                        if (Stub.this.mContext != null) {
                            bun.setClassLoader(Stub.this.mContext.getClassLoader());
                            Stub.this.mItf.onDownloadProgress((DownloadProgressInfo) msg.getData().getParcelable("progress"));
                        }
                    case TTSConst.TTSEVT_NOTSENT /*12*/:
                        Stub.this.mItf.onServiceConnected((Messenger) msg.getData().getParcelable("EMH"));
                    default:
                }
            }
        }

        /* renamed from: com.google.android.vending.expansion.downloader.DownloaderClientMarshaller.Stub.2 */
        class C10322 implements ServiceConnection {
            C10322() {
            }

            public void onServiceConnected(ComponentName className, IBinder service) {
                Stub.this.mServiceMessenger = new Messenger(service);
                Stub.this.mItf.onServiceConnected(Stub.this.mServiceMessenger);
            }

            public void onServiceDisconnected(ComponentName className) {
                Stub.this.mServiceMessenger = null;
            }
        }

        public Stub(IDownloaderClient itf, Class<?> downloaderService) {
            this.mItf = null;
            this.mMessenger = new Messenger(new C10311());
            this.mConnection = new C10322();
            this.mItf = itf;
            this.mDownloaderServiceClass = downloaderService;
        }

        public void connect(Context c) {
            this.mContext = c;
            Intent bindIntent = new Intent(c, this.mDownloaderServiceClass);
            bindIntent.putExtra("EMH", this.mMessenger);
            if (c.bindService(bindIntent, this.mConnection, 2)) {
                this.mBound = true;
            }
        }

        public void disconnect(Context c) {
            if (this.mBound) {
                c.unbindService(this.mConnection);
                this.mBound = false;
            }
            this.mContext = null;
        }

        public Messenger getMessenger() {
            return this.mMessenger;
        }
    }

    public static IDownloaderClient CreateProxy(Messenger msg) {
        return new Proxy(msg);
    }

    public static IStub CreateStub(IDownloaderClient itf, Class<?> downloaderService) {
        return new Stub(itf, downloaderService);
    }

    public static int startDownloadServiceIfRequired(Context context, PendingIntent notificationClient, Class<?> serviceClass) throws NameNotFoundException {
        return DownloaderService.startDownloadServiceIfRequired(context, notificationClient, (Class) serviceClass);
    }

    public static int startDownloadServiceIfRequired(Context context, Intent notificationClient, Class<?> serviceClass) throws NameNotFoundException {
        return DownloaderService.startDownloadServiceIfRequired(context, notificationClient, (Class) serviceClass);
    }
}
