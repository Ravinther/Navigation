package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface ICertificationListener extends IInterface {

    public static abstract class Stub extends Binder implements ICertificationListener {

        private static class Proxy implements ICertificationListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onCertificationStatusChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICertificationListener");
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public static ICertificationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.ICertificationListener");
            if (iin == null || !(iin instanceof ICertificationListener)) {
                return new Proxy(obj);
            }
            return (ICertificationListener) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICertificationListener");
                    onCertificationStatusChanged();
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.ICertificationListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onCertificationStatusChanged() throws RemoteException;
}
