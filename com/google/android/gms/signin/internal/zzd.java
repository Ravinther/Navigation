package com.google.android.gms.signin.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Scope;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public interface zzd extends IInterface {

    public static abstract class zza extends Binder implements zzd {
        public zza() {
            attachInterface(this, "com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
                    zza(data.readString(), data.createTypedArrayList(Scope.CREATOR), com.google.android.gms.signin.internal.zzf.zza.zzdH(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
                    zza(data.readString(), data.readString(), com.google.android.gms.signin.internal.zzf.zza.zzdH(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zza(String str, String str2, zzf com_google_android_gms_signin_internal_zzf) throws RemoteException;

    void zza(String str, List<Scope> list, zzf com_google_android_gms_signin_internal_zzf) throws RemoteException;
}
