package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IConnectionListener extends IInterface {

    public static abstract class Stub extends Binder implements IConnectionListener {

        private static class Proxy implements IConnectionListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onMirrorLinkSessionChanged(boolean mirrolinkSessionIsEstablished) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IConnectionListener");
                    if (!mirrolinkSessionIsEstablished) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onAudioConnectionsChanged(Bundle audioConnections) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IConnectionListener");
                    if (audioConnections != null) {
                        _data.writeInt(1);
                        audioConnections.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onRemoteDisplayConnectionChanged(int remoteDisplayConnection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IConnectionListener");
                    _data.writeInt(remoteDisplayConnection);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.mirrorlink.android.commonapi.IConnectionListener");
        }

        public static IConnectionListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IConnectionListener");
            if (iin == null || !(iin instanceof IConnectionListener)) {
                return new Proxy(obj);
            }
            return (IConnectionListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IConnectionListener");
                    onMirrorLinkSessionChanged(data.readInt() != 0);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    Bundle _arg0;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IConnectionListener");
                    if (data.readInt() != 0) {
                        _arg0 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onAudioConnectionsChanged(_arg0);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IConnectionListener");
                    onRemoteDisplayConnectionChanged(data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IConnectionListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onAudioConnectionsChanged(Bundle bundle) throws RemoteException;

    void onMirrorLinkSessionChanged(boolean z) throws RemoteException;

    void onRemoteDisplayConnectionChanged(int i) throws RemoteException;
}
