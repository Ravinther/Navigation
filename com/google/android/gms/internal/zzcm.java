package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import loquendo.tts.engine.TTSConst;

public interface zzcm extends IInterface {

    public static abstract class zza extends Binder implements zzcm {

        private static class zza implements zzcm {
            private IBinder zznI;

            zza(IBinder iBinder) {
                this.zznI = iBinder;
            }

            public IBinder asBinder() {
                return this.zznI;
            }

            public IBinder zza(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2, zzd com_google_android_gms_dynamic_zzd3, int i) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd != null ? com_google_android_gms_dynamic_zzd.asBinder() : null);
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd2 != null ? com_google_android_gms_dynamic_zzd2.asBinder() : null);
                    if (com_google_android_gms_dynamic_zzd3 != null) {
                        iBinder = com_google_android_gms_dynamic_zzd3.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.zznI.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    iBinder = obtain2.readStrongBinder();
                    return iBinder;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzcm zzv(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzcm)) ? new zza(iBinder) : (zzcm) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
                    IBinder zza = zza(com.google.android.gms.dynamic.zzd.zza.zzbk(data.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzbk(data.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzbk(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    reply.writeStrongBinder(zza);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IBinder zza(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2, zzd com_google_android_gms_dynamic_zzd3, int i) throws RemoteException;
}
