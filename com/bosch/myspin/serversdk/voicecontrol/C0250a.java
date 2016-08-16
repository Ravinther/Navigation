package com.bosch.myspin.serversdk.voicecontrol;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.voicecontrol.a */
public interface C0250a extends IInterface {

    /* renamed from: com.bosch.myspin.serversdk.voicecontrol.a.a */
    public static abstract class C0252a extends Binder implements C0250a {

        /* renamed from: com.bosch.myspin.serversdk.voicecontrol.a.a.a */
        static class C0251a implements C0250a {
            private IBinder f483a;

            C0251a(IBinder iBinder) {
                this.f483a = iBinder;
            }

            public IBinder asBinder() {
                return this.f483a;
            }

            public void m444a(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    obtain.writeInt(i);
                    this.f483a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean m446a() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    this.f483a.transact(2, obtain, obtain2, 0);
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

            public void m447b(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    obtain.writeInt(i);
                    this.f483a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m445a(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    obtain.writeStrongBinder(iBinder);
                    this.f483a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0250a m448b(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof C0250a)) {
                return new C0251a(iBinder);
            }
            return (C0250a) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case TTSConst.TTSMULTILINE /*1*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    m440a(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    boolean a = m442a();
                    parcel2.writeNoException();
                    parcel2.writeInt(a ? 1 : 0);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    m443b(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case TTSConst.TTSXML /*4*/:
                    parcel.enforceInterface("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    m441a(parcel.readStrongBinder());
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.bosch.myspin.serversdk.voicecontrol.IVoiceControl");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void m440a(int i) throws RemoteException;

    void m441a(IBinder iBinder) throws RemoteException;

    boolean m442a() throws RemoteException;

    void m443b(int i) throws RemoteException;
}
