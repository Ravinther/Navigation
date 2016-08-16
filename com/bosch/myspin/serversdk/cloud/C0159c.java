package com.bosch.myspin.serversdk.cloud;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.cloud.c */
public interface C0159c extends IInterface {

    /* renamed from: com.bosch.myspin.serversdk.cloud.c.a */
    public static abstract class C0161a extends Binder implements C0159c {

        /* renamed from: com.bosch.myspin.serversdk.cloud.c.a.a */
        static class C0160a implements C0159c {
            private IBinder f72a;

            C0160a(IBinder iBinder) {
                this.f72a = iBinder;
            }

            public IBinder asBinder() {
                return this.f72a;
            }

            public void m85a(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.cloud.ICloudService");
                    obtain.writeMap(map);
                    this.f72a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0159c m86a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.bosch.myspin.serversdk.cloud.ICloudService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0159c)) {
                return new C0160a(iBinder);
            }
            return (C0159c) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case TTSConst.TTSMULTILINE /*1*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.cloud.ICloudService");
                    m84a(parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.bosch.myspin.serversdk.cloud.ICloudService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void m84a(Map map) throws RemoteException;
}
