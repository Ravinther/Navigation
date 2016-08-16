package com.bosch.myspin.serversdk.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.service.b */
public interface C0181b extends IInterface {

    /* renamed from: com.bosch.myspin.serversdk.service.b.a */
    public static abstract class C0183a extends Binder implements C0181b {

        /* renamed from: com.bosch.myspin.serversdk.service.b.a.a */
        static class C0182a implements C0181b {
            private IBinder f144a;

            C0182a(IBinder iBinder) {
                this.f144a = iBinder;
            }

            public IBinder asBinder() {
                return this.f144a;
            }

            public void m160a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    this.f144a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m163a(boolean z) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.f144a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m162a(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f144a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m164a(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    obtain.writeIntArray(iArr3);
                    obtain.writeIntArray(iArr4);
                    obtain.writeInt(i);
                    this.f144a.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m165b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    this.f144a.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m167c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    this.f144a.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m161a(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    obtain.writeInt(i);
                    this.f144a.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m166b(boolean z) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.f144a.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public C0183a() {
            attachInterface(this, "com.bosch.myspin.serversdk.service.IMySpinServiceClient");
        }

        public static C0181b m168a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0181b)) {
                return new C0182a(iBinder);
            }
            return (C0181b) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            switch (i) {
                case TTSConst.TTSMULTILINE /*1*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    m152a();
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    m155a(z);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    Bundle bundle;
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    if (parcel.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    } else {
                        bundle = null;
                    }
                    m154a(bundle);
                    return true;
                case TTSConst.TTSXML /*4*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    m156a(parcel.createIntArray(), parcel.createIntArray(), parcel.createIntArray(), parcel.createIntArray(), parcel.readInt());
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    m157b();
                    return true;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    m159c();
                    return true;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    m153a(parcel.readInt());
                    return true;
                case TTSConst.TTSEVT_TAG /*8*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    m158b(z);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.bosch.myspin.serversdk.service.IMySpinServiceClient");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void m152a() throws RemoteException;

    void m153a(int i) throws RemoteException;

    void m154a(Bundle bundle) throws RemoteException;

    void m155a(boolean z) throws RemoteException;

    void m156a(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i) throws RemoteException;

    void m157b() throws RemoteException;

    void m158b(boolean z) throws RemoteException;

    void m159c() throws RemoteException;
}
