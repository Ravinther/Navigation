package com.android.vending.billing;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

public interface IInAppBillingService extends IInterface {

    public static abstract class Stub extends Binder implements IInAppBillingService {

        private static class Proxy implements IInAppBillingService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public int isBillingSupported(int apiVersion, String packageName, String type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getSkuDetails(int apiVersion, String packageName, String type, Bundle skusBundle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    Bundle _result;
                    _data.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    if (skusBundle != null) {
                        _data.writeInt(1);
                        skusBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
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

            public Bundle getBuyIntent(int apiVersion, String packageName, String sku, String type, String developerPayload) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    Bundle _result;
                    _data.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(sku);
                    _data.writeString(type);
                    _data.writeString(developerPayload);
                    this.mRemote.transact(3, _data, _reply, 0);
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

            public Bundle getPurchases(int apiVersion, String packageName, String type, String continuationToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    Bundle _result;
                    _data.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    _data.writeString(continuationToken);
                    this.mRemote.transact(4, _data, _reply, 0);
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

            public int consumePurchase(int apiVersion, String packageName, String purchaseToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(purchaseToken);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.vending.billing.IInAppBillingService");
        }

        public static IInAppBillingService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.android.vending.billing.IInAppBillingService");
            if (iin == null || !(iin instanceof IInAppBillingService)) {
                return new Proxy(obj);
            }
            return (IInAppBillingService) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int _result;
            Bundle _result2;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    _result = isBillingSupported(data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    Bundle _arg3;
                    data.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    if (data.readInt() != 0) {
                        _arg3 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    _result2 = getSkuDetails(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    _result2 = getBuyIntent(data.readInt(), data.readString(), data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    _result2 = getPurchases(data.readInt(), data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    data.enforceInterface("com.android.vending.billing.IInAppBillingService");
                    _result = consumePurchase(data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 1598968902:
                    reply.writeString("com.android.vending.billing.IInAppBillingService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    int consumePurchase(int i, String str, String str2) throws RemoteException;

    Bundle getBuyIntent(int i, String str, String str2, String str3, String str4) throws RemoteException;

    Bundle getPurchases(int i, String str, String str2, String str3) throws RemoteException;

    Bundle getSkuDetails(int i, String str, String str2, Bundle bundle) throws RemoteException;

    int isBillingSupported(int i, String str, String str2) throws RemoteException;
}
