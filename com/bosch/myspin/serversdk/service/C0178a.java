package com.bosch.myspin.serversdk.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.bosch.myspin.serversdk.service.C0181b.C0183a;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.service.a */
public interface C0178a extends IInterface {

    /* renamed from: com.bosch.myspin.serversdk.service.a.a */
    public static abstract class C0180a extends Binder implements C0178a {

        /* renamed from: com.bosch.myspin.serversdk.service.a.a.a */
        static class C0179a implements C0178a {
            private IBinder f143a;

            C0179a(IBinder iBinder) {
                this.f143a = iBinder;
            }

            public IBinder asBinder() {
                return this.f143a;
            }

            public void m147a(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinService");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f143a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m148a(C0181b c0181b, int i, String str, String str2) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinService");
                    if (c0181b != null) {
                        iBinder = c0181b.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.f143a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public Bundle m146a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    Bundle bundle;
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinService");
                    this.f143a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(obtain2);
                    } else {
                        bundle = null;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return bundle;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean m149b() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinService");
                    this.f143a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle m150c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    Bundle bundle;
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.service.IMySpinService");
                    this.f143a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(obtain2);
                    } else {
                        bundle = null;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return bundle;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0178a m151a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.bosch.myspin.serversdk.service.IMySpinService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0178a)) {
                return new C0179a(iBinder);
            }
            return (C0178a) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int i3 = 0;
            Bundle a;
            switch (i) {
                case TTSConst.TTSMULTILINE /*1*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinService");
                    m142a(parcel.readInt(), parcel.readInt());
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinService");
                    m143a(C0183a.m168a(parcel.readStrongBinder()), parcel.readInt(), parcel.readString(), parcel.readString());
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinService");
                    a = m141a();
                    parcel2.writeNoException();
                    if (a != null) {
                        parcel2.writeInt(1);
                        a.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case TTSConst.TTSXML /*4*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinService");
                    boolean b = m144b();
                    parcel2.writeNoException();
                    if (b) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.service.IMySpinService");
                    a = m145c();
                    parcel2.writeNoException();
                    if (a != null) {
                        parcel2.writeInt(1);
                        a.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.bosch.myspin.serversdk.service.IMySpinService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    Bundle m141a() throws RemoteException;

    void m142a(int i, int i2) throws RemoteException;

    void m143a(C0181b c0181b, int i, String str, String str2) throws RemoteException;

    boolean m144b() throws RemoteException;

    Bundle m145c() throws RemoteException;
}
