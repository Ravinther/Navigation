package com.bosch.myspin.serversdk.vehicledata;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.a */
public interface C0239a extends IInterface {

    /* renamed from: com.bosch.myspin.serversdk.vehicledata.a.a */
    public static abstract class C0241a extends Binder implements C0239a {

        /* renamed from: com.bosch.myspin.serversdk.vehicledata.a.a.a */
        static class C0240a implements C0239a {
            private IBinder f439a;

            C0240a(IBinder iBinder) {
                this.f439a = iBinder;
            }

            public IBinder asBinder() {
                return this.f439a;
            }

            public void m377a(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.vehicledata.IVehicleDataListenerRegistration");
                    obtain.writeStrongBinder(iBinder);
                    this.f439a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void m378b(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.vehicledata.IVehicleDataListenerRegistration");
                    obtain.writeStrongBinder(iBinder);
                    this.f439a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static C0239a m379c(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.bosch.myspin.serversdk.vehicledata.IVehicleDataListenerRegistration");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0239a)) {
                return new C0240a(iBinder);
            }
            return (C0239a) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case TTSConst.TTSMULTILINE /*1*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.vehicledata.IVehicleDataListenerRegistration");
                    m375a(parcel.readStrongBinder());
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.vehicledata.IVehicleDataListenerRegistration");
                    m376b(parcel.readStrongBinder());
                    return true;
                case 1598968902:
                    parcel2.writeString("com.bosch.myspin.serversdk.vehicledata.IVehicleDataListenerRegistration");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void m375a(IBinder iBinder) throws RemoteException;

    void m376b(IBinder iBinder) throws RemoteException;
}
