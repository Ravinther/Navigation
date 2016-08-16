package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public interface IDataServicesManager extends IInterface {

    public static abstract class Stub extends Binder implements IDataServicesManager {

        private static class Proxy implements IDataServicesManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public List<Bundle> getAvailableServices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<Bundle> _result = _reply.createTypedArrayList(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerToService(int serviceId, int versionMajor, int versionMinor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    _data.writeInt(serviceId);
                    _data.writeInt(versionMajor);
                    _data.writeInt(versionMinor);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterFromService(int serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    _data.writeInt(serviceId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void subscribeObject(int serviceId, int objectId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    _data.writeInt(serviceId);
                    _data.writeInt(objectId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unsubscribeObject(int serviceId, int objectId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    _data.writeInt(serviceId);
                    _data.writeInt(objectId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setObject(int serviceId, int objectId, Bundle object) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    _data.writeInt(serviceId);
                    _data.writeInt(objectId);
                    if (object != null) {
                        _data.writeInt(1);
                        object.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getObject(int serviceId, int objectId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    _data.writeInt(serviceId);
                    _data.writeInt(objectId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregister() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesManager");
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static IDataServicesManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
            if (iin == null || !(iin instanceof IDataServicesManager)) {
                return new Proxy(obj);
            }
            return (IDataServicesManager) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    List<Bundle> _result = getAvailableServices();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    registerToService(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    unregisterFromService(data.readInt());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    subscribeObject(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    unsubscribeObject(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    Bundle _arg2;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    setObject(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    getObject(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_TAG /*8*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesManager");
                    unregister();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IDataServicesManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    List<Bundle> getAvailableServices() throws RemoteException;

    void getObject(int i, int i2) throws RemoteException;

    void registerToService(int i, int i2, int i3) throws RemoteException;

    void setObject(int i, int i2, Bundle bundle) throws RemoteException;

    void subscribeObject(int i, int i2) throws RemoteException;

    void unregister() throws RemoteException;

    void unregisterFromService(int i) throws RemoteException;

    void unsubscribeObject(int i, int i2) throws RemoteException;
}
