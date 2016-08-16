package com.google.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface ILicensingService extends IInterface {

    public static abstract class Stub extends Binder implements ILicensingService {

        private static class Proxy implements ILicensingService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void checkLicense(long nonce, String packageName, ILicenseResultListener listener) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.vending.licensing.ILicensingService");
                    _data.writeLong(nonce);
                    _data.writeString(packageName);
                    if (listener != null) {
                        iBinder = listener.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public static ILicensingService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.android.vending.licensing.ILicensingService");
            if (iin == null || !(iin instanceof ILicensingService)) {
                return new Proxy(obj);
            }
            return (ILicensingService) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.android.vending.licensing.ILicensingService");
                    checkLicense(data.readLong(), data.readString(), com.google.android.vending.licensing.ILicenseResultListener.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 1598968902:
                    reply.writeString("com.android.vending.licensing.ILicensingService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void checkLicense(long j, String str, ILicenseResultListener iLicenseResultListener) throws RemoteException;
}
