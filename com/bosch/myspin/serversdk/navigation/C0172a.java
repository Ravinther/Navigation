package com.bosch.myspin.serversdk.navigation;

import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.navigation.a */
public interface C0172a extends IInterface {

    /* renamed from: com.bosch.myspin.serversdk.navigation.a.a */
    public static abstract class C0174a extends Binder implements C0172a {

        /* renamed from: com.bosch.myspin.serversdk.navigation.a.a.a */
        static class C0173a implements C0172a {
            private IBinder f134a;

            C0173a(IBinder iBinder) {
                this.f134a = iBinder;
            }

            public IBinder asBinder() {
                return this.f134a;
            }

            public int m130a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.navigation.INavigation");
                    this.f134a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean m131a(Location location, String str) throws RemoteException {
                boolean z = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.navigation.INavigation");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.f134a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean m132a(Bundle bundle) throws RemoteException {
                boolean z = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.navigation.INavigation");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f134a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0172a m133a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.bosch.myspin.serversdk.navigation.INavigation");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0172a)) {
                return new C0173a(iBinder);
            }
            return (C0172a) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Bundle bundle = null;
            int i3 = 0;
            boolean a;
            switch (i) {
                case TTSConst.TTSMULTILINE /*1*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.navigation.INavigation");
                    int a2 = m127a();
                    parcel2.writeNoException();
                    parcel2.writeInt(a2);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    Location location;
                    parcel.enforceInterface("com.bosch.myspin.serversdk.navigation.INavigation");
                    if (parcel.readInt() != 0) {
                        location = (Location) Location.CREATOR.createFromParcel(parcel);
                    }
                    a = m128a(location, parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(a ? 1 : 0);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.navigation.INavigation");
                    if (parcel.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                    }
                    a = m129a(bundle);
                    parcel2.writeNoException();
                    if (a) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.bosch.myspin.serversdk.navigation.INavigation");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    int m127a() throws RemoteException;

    boolean m128a(Location location, String str) throws RemoteException;

    boolean m129a(Bundle bundle) throws RemoteException;
}
