package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public interface IDataServicesListener extends IInterface {

    public static abstract class Stub extends Binder implements IDataServicesListener {

        private static class Proxy implements IDataServicesListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onAvailableServicesChanged(List<Bundle> services) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _data.writeTypedList(services);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onRegisterForService(int serviceId, boolean success) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _data.writeInt(serviceId);
                    if (!success) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onSubscribeResponse(int serviceId, int objectId, boolean success, int subscriptionType, int interval) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _data.writeInt(serviceId);
                    _data.writeInt(objectId);
                    if (!success) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    _data.writeInt(subscriptionType);
                    _data.writeInt(interval);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onSetDataObjectResponse(int serviceId, int objectId, boolean success) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _data.writeInt(serviceId);
                    _data.writeInt(objectId);
                    if (!success) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onGetDataObjectResponse(int serviceId, int objectId, boolean success, Bundle object) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _data.writeInt(serviceId);
                    _data.writeInt(objectId);
                    if (!success) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    if (object != null) {
                        _data.writeInt(1);
                        object.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public static IDataServicesListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IDataServicesListener");
            if (iin == null || !(iin instanceof IDataServicesListener)) {
                return new Proxy(obj);
            }
            return (IDataServicesListener) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int _arg0;
            int _arg1;
            boolean _arg2;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesListener");
                    onAvailableServicesChanged(data.createTypedArrayList(Bundle.CREATOR));
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    boolean _arg12;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg12 = true;
                    } else {
                        _arg12 = false;
                    }
                    onRegisterForService(_arg0, _arg12);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _arg0 = data.readInt();
                    _arg1 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg2 = true;
                    } else {
                        _arg2 = false;
                    }
                    onSubscribeResponse(_arg0, _arg1, _arg2, data.readInt(), data.readInt());
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _arg0 = data.readInt();
                    _arg1 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg2 = true;
                    } else {
                        _arg2 = false;
                    }
                    onSetDataObjectResponse(_arg0, _arg1, _arg2);
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    Bundle _arg3;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDataServicesListener");
                    _arg0 = data.readInt();
                    _arg1 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg2 = true;
                    } else {
                        _arg2 = false;
                    }
                    if (data.readInt() != 0) {
                        _arg3 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    onGetDataObjectResponse(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IDataServicesListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onAvailableServicesChanged(List<Bundle> list) throws RemoteException;

    void onGetDataObjectResponse(int i, int i2, boolean z, Bundle bundle) throws RemoteException;

    void onRegisterForService(int i, boolean z) throws RemoteException;

    void onSetDataObjectResponse(int i, int i2, boolean z) throws RemoteException;

    void onSubscribeResponse(int i, int i2, boolean z, int i3, int i4) throws RemoteException;
}
