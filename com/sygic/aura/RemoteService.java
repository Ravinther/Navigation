package com.sygic.aura;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.sygic.aura.IRemoteService.Stub;

public class RemoteService extends Service {
    private static IRemoteServiceCallback mCallback;

    /* renamed from: com.sygic.aura.RemoteService.1 */
    class C10911 extends Stub {
        C10911() {
        }

        public void registerCallback(IRemoteServiceCallback callback) throws RemoteException {
            RemoteService.mCallback = callback;
        }

        public boolean soundMutex(boolean bMutex) throws RemoteException {
            if (RemoteService.mCallback != null) {
                return RemoteService.mCallback.soundMutex(bMutex);
            }
            return false;
        }
    }

    public IBinder onBind(Intent intent) {
        return new C10911();
    }
}
