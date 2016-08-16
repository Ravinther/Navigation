package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface ICommonAPIService extends IInterface {

    public static abstract class Stub extends Binder implements ICommonAPIService {

        private static class Proxy implements ICommonAPIService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public int getCommonAPIServiceApiLevel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applicationStarted(String packageName, int commonApiLevel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeInt(commonApiLevel);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applicationStopping(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ICertificationManager getCertificationManager(String packageName, ICertificationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ICertificationManager _result = com.mirrorlink.android.commonapi.ICertificationManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IConnectionManager getConnectionManager(String packageName, IConnectionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    IConnectionManager _result = com.mirrorlink.android.commonapi.IConnectionManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IContextManager getContextManager(String packageName, IContextListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    IContextManager _result = com.mirrorlink.android.commonapi.IContextManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IDataServicesManager getDataServicesManager(String packageName, IDataServicesListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    IDataServicesManager _result = com.mirrorlink.android.commonapi.IDataServicesManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IDeviceInfoManager getDeviceInfoManager(String packageName, IDeviceInfoListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    IDeviceInfoManager _result = com.mirrorlink.android.commonapi.IDeviceInfoManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IDeviceStatusManager getDeviceStatusManager(String packageName, IDeviceStatusListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    IDeviceStatusManager _result = com.mirrorlink.android.commonapi.IDeviceStatusManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IDisplayManager getDisplayManager(String packageName, IDisplayListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    IDisplayManager _result = com.mirrorlink.android.commonapi.IDisplayManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IEventMappingManager getEventMappingManager(String packageName, IEventMappingListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    IEventMappingManager _result = com.mirrorlink.android.commonapi.IEventMappingManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public INotificationManager getNotificationManager(String packageName, INotificationListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.ICommonAPIService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    INotificationManager _result = com.mirrorlink.android.commonapi.INotificationManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static ICommonAPIService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
            if (iin == null || !(iin instanceof ICommonAPIService)) {
                return new Proxy(obj);
            }
            return (ICommonAPIService) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    int _result = getCommonAPIServiceApiLevel();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    applicationStarted(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    applicationStopping(data.readString());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    ICertificationManager _result2 = getCertificationManager(data.readString(), com.mirrorlink.android.commonapi.ICertificationListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result2 != null) {
                        iBinder = _result2.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    IConnectionManager _result3 = getConnectionManager(data.readString(), com.mirrorlink.android.commonapi.IConnectionListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result3 != null) {
                        iBinder = _result3.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    IContextManager _result4 = getContextManager(data.readString(), com.mirrorlink.android.commonapi.IContextListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result4 != null) {
                        iBinder = _result4.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    IDataServicesManager _result5 = getDataServicesManager(data.readString(), com.mirrorlink.android.commonapi.IDataServicesListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result5 != null) {
                        iBinder = _result5.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_TAG /*8*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    IDeviceInfoManager _result6 = getDeviceInfoManager(data.readString(), com.mirrorlink.android.commonapi.IDeviceInfoListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result6 != null) {
                        iBinder = _result6.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    IDeviceStatusManager _result7 = getDeviceStatusManager(data.readString(), com.mirrorlink.android.commonapi.IDeviceStatusListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result7 != null) {
                        iBinder = _result7.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    IDisplayManager _result8 = getDisplayManager(data.readString(), com.mirrorlink.android.commonapi.IDisplayListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result8 != null) {
                        iBinder = _result8.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    IEventMappingManager _result9 = getEventMappingManager(data.readString(), com.mirrorlink.android.commonapi.IEventMappingListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result9 != null) {
                        iBinder = _result9.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.ICommonAPIService");
                    INotificationManager _result10 = getNotificationManager(data.readString(), com.mirrorlink.android.commonapi.INotificationListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result10 != null) {
                        iBinder = _result10.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.ICommonAPIService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void applicationStarted(String str, int i) throws RemoteException;

    void applicationStopping(String str) throws RemoteException;

    ICertificationManager getCertificationManager(String str, ICertificationListener iCertificationListener) throws RemoteException;

    int getCommonAPIServiceApiLevel() throws RemoteException;

    IConnectionManager getConnectionManager(String str, IConnectionListener iConnectionListener) throws RemoteException;

    IContextManager getContextManager(String str, IContextListener iContextListener) throws RemoteException;

    IDataServicesManager getDataServicesManager(String str, IDataServicesListener iDataServicesListener) throws RemoteException;

    IDeviceInfoManager getDeviceInfoManager(String str, IDeviceInfoListener iDeviceInfoListener) throws RemoteException;

    IDeviceStatusManager getDeviceStatusManager(String str, IDeviceStatusListener iDeviceStatusListener) throws RemoteException;

    IDisplayManager getDisplayManager(String str, IDisplayListener iDisplayListener) throws RemoteException;

    IEventMappingManager getEventMappingManager(String str, IEventMappingListener iEventMappingListener) throws RemoteException;

    INotificationManager getNotificationManager(String str, INotificationListener iNotificationListener) throws RemoteException;
}
