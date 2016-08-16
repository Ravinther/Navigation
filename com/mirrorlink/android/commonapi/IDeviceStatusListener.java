package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IDeviceStatusListener extends IInterface {

    public static abstract class Stub extends Binder implements IDeviceStatusListener {

        private static class Proxy implements IDeviceStatusListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onDriveModeChange(boolean driveMode) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusListener");
                    if (!driveMode) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onNightModeChanged(boolean nightMode) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusListener");
                    if (!nightMode) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onMicrophoneStatusChanged(boolean micInput) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusListener");
                    if (!micInput) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.mirrorlink.android.commonapi.IDeviceStatusListener");
        }

        public static IDeviceStatusListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IDeviceStatusListener");
            if (iin == null || !(iin instanceof IDeviceStatusListener)) {
                return new Proxy(obj);
            }
            return (IDeviceStatusListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg0 = false;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusListener");
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    onDriveModeChange(_arg0);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusListener");
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    onNightModeChanged(_arg0);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusListener");
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    onMicrophoneStatusChanged(_arg0);
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IDeviceStatusListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onDriveModeChange(boolean z) throws RemoteException;

    void onMicrophoneStatusChanged(boolean z) throws RemoteException;

    void onNightModeChanged(boolean z) throws RemoteException;
}
