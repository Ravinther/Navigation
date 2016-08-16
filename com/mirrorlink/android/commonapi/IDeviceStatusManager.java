package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IDeviceStatusManager extends IInterface {

    public static abstract class Stub extends Binder implements IDeviceStatusManager {

        private static class Proxy implements IDeviceStatusManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public boolean isInDriveMode() throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusManager");
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

            public boolean isInNightMode() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isMicrophoneOn() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setMicrophoneOpen(boolean micInput, boolean voiceInput) throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    int i;
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    if (micInput) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    _data.writeInt(i);
                    if (voiceInput) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(4, _data, _reply, 0);
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

            public void unregister() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static IDeviceStatusManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IDeviceStatusManager");
            if (iin == null || !(iin instanceof IDeviceStatusManager)) {
                return new Proxy(obj);
            }
            return (IDeviceStatusManager) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            boolean _result;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    _result = isInDriveMode();
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    _result = isInNightMode();
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    _result = isMicrophoneOn();
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSXML /*4*/:
                    boolean _arg0;
                    boolean _arg1;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    } else {
                        _arg0 = false;
                    }
                    if (data.readInt() != 0) {
                        _arg1 = true;
                    } else {
                        _arg1 = false;
                    }
                    _result = setMicrophoneOpen(_arg0, _arg1);
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    unregister();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IDeviceStatusManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    boolean isInDriveMode() throws RemoteException;

    boolean isInNightMode() throws RemoteException;

    boolean isMicrophoneOn() throws RemoteException;

    boolean setMicrophoneOpen(boolean z, boolean z2) throws RemoteException;

    void unregister() throws RemoteException;
}
