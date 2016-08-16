package com.sygic.sdk.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IRemoteServiceCallback extends IInterface {

    public static abstract class Stub extends Binder implements IRemoteServiceCallback {

        private static class Proxy implements IRemoteServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void sendEvent(int id, String desc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.sygic.sdk.aidl.IRemoteServiceCallback");
                    _data.writeInt(id);
                    _data.writeString(desc);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public static IRemoteServiceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.sygic.sdk.aidl.IRemoteServiceCallback");
            if (iin == null || !(iin instanceof IRemoteServiceCallback)) {
                return new Proxy(obj);
            }
            return (IRemoteServiceCallback) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.sygic.sdk.aidl.IRemoteServiceCallback");
                    sendEvent(data.readInt(), data.readString());
                    return true;
                case 1598968902:
                    reply.writeString("com.sygic.sdk.aidl.IRemoteServiceCallback");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void sendEvent(int i, String str) throws RemoteException;
}
