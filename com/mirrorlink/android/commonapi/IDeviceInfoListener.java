package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IDeviceInfoListener extends IInterface {

    public static abstract class Stub extends Binder implements IDeviceInfoListener {

        private static class Proxy implements IDeviceInfoListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onDeviceInfoChanged(Bundle clientInformation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceInfoListener");
                    if (clientInformation != null) {
                        _data.writeInt(1);
                        clientInformation.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.mirrorlink.android.commonapi.IDeviceInfoListener");
        }

        public static IDeviceInfoListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IDeviceInfoListener");
            if (iin == null || !(iin instanceof IDeviceInfoListener)) {
                return new Proxy(obj);
            }
            return (IDeviceInfoListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    Bundle _arg0;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceInfoListener");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onDeviceInfoChanged(_arg0);
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IDeviceInfoListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onDeviceInfoChanged(Bundle bundle) throws RemoteException;
}
