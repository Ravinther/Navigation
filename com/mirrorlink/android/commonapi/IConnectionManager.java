package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IConnectionManager extends IInterface {

    public static abstract class Stub extends Binder implements IConnectionManager {

        private static class Proxy implements IConnectionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public boolean isMirrorLinkSessionEstablished() throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IConnectionManager");
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getAudioConnections() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    Bundle _result;
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IConnectionManager");
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (Bundle) Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRemoteDisplayConnections() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IConnectionManager");
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregister() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IConnectionManager");
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static IConnectionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IConnectionManager");
            if (iin == null || !(iin instanceof IConnectionManager)) {
                return new Proxy(obj);
            }
            return (IConnectionManager) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IConnectionManager");
                    boolean _result = isMirrorLinkSessionEstablished();
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IConnectionManager");
                    Bundle _result2 = getAudioConnections();
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IConnectionManager");
                    int _result3 = getRemoteDisplayConnections();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IConnectionManager");
                    unregister();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IConnectionManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    Bundle getAudioConnections() throws RemoteException;

    int getRemoteDisplayConnections() throws RemoteException;

    boolean isMirrorLinkSessionEstablished() throws RemoteException;

    void unregister() throws RemoteException;
}
