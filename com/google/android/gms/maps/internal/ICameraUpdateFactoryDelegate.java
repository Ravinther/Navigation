package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import loquendo.tts.engine.TTSConst;

public interface ICameraUpdateFactoryDelegate extends IInterface {

    public static abstract class zza extends Binder implements ICameraUpdateFactoryDelegate {

        private static class zza implements ICameraUpdateFactoryDelegate {
            private IBinder zznI;

            zza(IBinder iBinder) {
                this.zznI = iBinder;
            }

            public IBinder asBinder() {
                return this.zznI;
            }

            public zzd newCameraPosition(CameraPosition cameraPosition) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (cameraPosition != null) {
                        obtain.writeInt(1);
                        cameraPosition.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zznI.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd newLatLng(LatLng latLng) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zznI.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd newLatLngBounds(LatLngBounds bounds, int padding) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (bounds != null) {
                        obtain.writeInt(1);
                        bounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(padding);
                    this.zznI.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd newLatLngBoundsWithSize(LatLngBounds bounds, int width, int height, int padding) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (bounds != null) {
                        obtain.writeInt(1);
                        bounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(width);
                    obtain.writeInt(height);
                    obtain.writeInt(padding);
                    this.zznI.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd newLatLngZoom(LatLng latLng, float zoom) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeFloat(zoom);
                    this.zznI.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd scrollBy(float xPixel, float yPixel) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(xPixel);
                    obtain.writeFloat(yPixel);
                    this.zznI.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zoomBy(float amount) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(amount);
                    this.zznI.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zoomByWithFocus(float amount, int screenFocusX, int screenFocusY) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(amount);
                    obtain.writeInt(screenFocusX);
                    obtain.writeInt(screenFocusY);
                    this.zznI.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zoomIn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    this.zznI.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zoomOut() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    this.zznI.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zoomTo(float zoom) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(zoom);
                    this.zznI.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static ICameraUpdateFactoryDelegate zzci(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICameraUpdateFactoryDelegate)) ? new zza(iBinder) : (ICameraUpdateFactoryDelegate) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            zzd zoomIn;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomIn();
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomOut();
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = scrollBy(data.readFloat(), data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomTo(data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomBy(data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomByWithFocus(data.readFloat(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newCameraPosition(data.readInt() != 0 ? CameraPosition.CREATOR.zzeW(data) : null);
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_TAG /*8*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLng(data.readInt() != 0 ? LatLng.CREATOR.zzfa(data) : null);
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLngZoom(data.readInt() != 0 ? LatLng.CREATOR.zzfa(data) : null, data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLngBounds(data.readInt() != 0 ? LatLngBounds.CREATOR.zzeZ(data) : null, data.readInt());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLngBoundsWithSize(data.readInt() != 0 ? LatLngBounds.CREATOR.zzeZ(data) : null, data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    zzd newCameraPosition(CameraPosition cameraPosition) throws RemoteException;

    zzd newLatLng(LatLng latLng) throws RemoteException;

    zzd newLatLngBounds(LatLngBounds latLngBounds, int i) throws RemoteException;

    zzd newLatLngBoundsWithSize(LatLngBounds latLngBounds, int i, int i2, int i3) throws RemoteException;

    zzd newLatLngZoom(LatLng latLng, float f) throws RemoteException;

    zzd scrollBy(float f, float f2) throws RemoteException;

    zzd zoomBy(float f) throws RemoteException;

    zzd zoomByWithFocus(float f, int i, int i2) throws RemoteException;

    zzd zoomIn() throws RemoteException;

    zzd zoomOut() throws RemoteException;

    zzd zoomTo(float f) throws RemoteException;
}
