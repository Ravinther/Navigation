package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface INotificationListener extends IInterface {

    public static abstract class Stub extends Binder implements INotificationListener {

        private static class Proxy implements INotificationListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onNotificationEnabledChanged(boolean notiEnabled) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.INotificationListener");
                    if (!notiEnabled) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationConfigurationChanged(Bundle notificationConfiguration) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.INotificationListener");
                    if (notificationConfiguration != null) {
                        _data.writeInt(1);
                        notificationConfiguration.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onNotificationActionReceived(int notificationId, int actionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.INotificationListener");
                    _data.writeInt(notificationId);
                    _data.writeInt(actionId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public static INotificationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.INotificationListener");
            if (iin == null || !(iin instanceof INotificationListener)) {
                return new Proxy(obj);
            }
            return (INotificationListener) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.INotificationListener");
                    onNotificationEnabledChanged(data.readInt() != 0);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    Bundle _arg0;
                    data.enforceInterface("com.mirrorlink.android.commonapi.INotificationListener");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onNotificationConfigurationChanged(_arg0);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.INotificationListener");
                    onNotificationActionReceived(data.readInt(), data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.INotificationListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onNotificationActionReceived(int i, int i2) throws RemoteException;

    void onNotificationConfigurationChanged(Bundle bundle) throws RemoteException;

    void onNotificationEnabledChanged(boolean z) throws RemoteException;
}
