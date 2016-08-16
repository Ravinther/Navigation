package com.mirrorlink.android.commonapi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public interface IContextManager extends IInterface {

    public static abstract class Stub extends Binder implements IContextManager {

        private static class Proxy implements IContextManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void setFramebufferContextInformation(List<Bundle> content, boolean handleBlocking) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IContextManager");
                    _data.writeTypedList(content);
                    if (!handleBlocking) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAudioContextInformation(boolean audioContent, int[] audioCategories, boolean handleBlocking) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IContextManager");
                    _data.writeInt(audioContent ? 1 : 0);
                    _data.writeIntArray(audioCategories);
                    if (!handleBlocking) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(2, _data, _reply, 0);
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
                    _data.writeInterfaceToken("com.mirrorlink.android.commonapi.IContextManager");
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static IContextManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.mirrorlink.android.commonapi.IContextManager");
            if (iin == null || !(iin instanceof IContextManager)) {
                return new Proxy(obj);
            }
            return (IContextManager) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    boolean _arg1;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IContextManager");
                    List<Bundle> _arg0 = data.createTypedArrayList(Bundle.CREATOR);
                    if (data.readInt() != 0) {
                        _arg1 = true;
                    } else {
                        _arg1 = false;
                    }
                    setFramebufferContextInformation(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    boolean _arg02;
                    boolean _arg2;
                    data.enforceInterface("com.mirrorlink.android.commonapi.IContextManager");
                    if (data.readInt() != 0) {
                        _arg02 = true;
                    } else {
                        _arg02 = false;
                    }
                    int[] _arg12 = data.createIntArray();
                    if (data.readInt() != 0) {
                        _arg2 = true;
                    } else {
                        _arg2 = false;
                    }
                    setAudioContextInformation(_arg02, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.mirrorlink.android.commonapi.IContextManager");
                    unregister();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.mirrorlink.android.commonapi.IContextManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void setAudioContextInformation(boolean z, int[] iArr, boolean z2) throws RemoteException;

    void setFramebufferContextInformation(List<Bundle> list, boolean z) throws RemoteException;

    void unregister() throws RemoteException;
}
