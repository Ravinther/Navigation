package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IDisplayListener extends IInterface {

    public static abstract class Stub extends Binder implements IDisplayListener {

        private static class Proxy implements IDisplayListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onDisplayConfigurationChanged(Bundle displayConfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDisplayListener");
                    if (displayConfiguration != null) {
                        _data.writeInt(1);
                        displayConfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onPixelFormatChanged(Bundle pixelFormat) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDisplayListener");
                    if (pixelFormat != null) {
                        _data.writeInt(1);
                        pixelFormat.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.mirrorlink.android.commonapi.IDisplayListener");
        }

        public static IDisplayListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IDisplayListener");
            if (iin == null || !(iin instanceof IDisplayListener)) {
                return new Proxy(obj);
            }
            return (IDisplayListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle _arg0;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDisplayListener");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onDisplayConfigurationChanged(_arg0);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDisplayListener");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onPixelFormatChanged(_arg0);
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IDisplayListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onDisplayConfigurationChanged(Bundle bundle) throws RemoteException;

    void onPixelFormatChanged(Bundle bundle) throws RemoteException;
}
