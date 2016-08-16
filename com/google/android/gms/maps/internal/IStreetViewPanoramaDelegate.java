package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import loquendo.tts.engine.TTSConst;

public interface IStreetViewPanoramaDelegate extends IInterface {

    public static abstract class zza extends Binder implements IStreetViewPanoramaDelegate {

        private static class zza implements IStreetViewPanoramaDelegate {
            private IBinder zznI;

            zza(IBinder iBinder) {
                this.zznI = iBinder;
            }

            public void animateTo(StreetViewPanoramaCamera camera, long duration) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (camera != null) {
                        obtain.writeInt(1);
                        camera.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeLong(duration);
                    this.zznI.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.zznI;
            }

            public void enablePanning(boolean enablePanning) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (enablePanning) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zznI.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void enableStreetNames(boolean enableStreetNames) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (enableStreetNames) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zznI.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void enableUserNavigation(boolean enableUserNavigation) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (enableUserNavigation) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zznI.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void enableZoom(boolean enableZoom) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (!enableZoom) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.zznI.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public StreetViewPanoramaCamera getPanoramaCamera() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.zznI.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    StreetViewPanoramaCamera zzff = obtain2.readInt() != 0 ? StreetViewPanoramaCamera.CREATOR.zzff(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return zzff;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public StreetViewPanoramaLocation getStreetViewPanoramaLocation() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.zznI.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    StreetViewPanoramaLocation zzfh = obtain2.readInt() != 0 ? StreetViewPanoramaLocation.CREATOR.zzfh(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return zzfh;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isPanningGesturesEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.zznI.transact(6, obtain, obtain2, 0);
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

            public boolean isStreetNamesEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.zznI.transact(8, obtain, obtain2, 0);
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

            public boolean isUserNavigationEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.zznI.transact(7, obtain, obtain2, 0);
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

            public boolean isZoomGesturesEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.zznI.transact(5, obtain, obtain2, 0);
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

            public zzd orientationToPoint(StreetViewPanoramaOrientation orientation) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (orientation != null) {
                        obtain.writeInt(1);
                        orientation.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zznI.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbk = com.google.android.gms.dynamic.zzd.zza.zzbk(obtain2.readStrongBinder());
                    return zzbk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public StreetViewPanoramaOrientation pointToOrientation(zzd point) throws RemoteException {
                StreetViewPanoramaOrientation streetViewPanoramaOrientation = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(point != null ? point.asBinder() : null);
                    this.zznI.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        streetViewPanoramaOrientation = StreetViewPanoramaOrientation.CREATOR.zzfi(obtain2);
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return streetViewPanoramaOrientation;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaCameraChangeListener(zzr listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznI.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaChangeListener(zzs listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznI.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaClickListener(zzt listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznI.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnStreetViewPanoramaLongClickListener(zzu listener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznI.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPosition(LatLng position) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (position != null) {
                        obtain.writeInt(1);
                        position.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zznI.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPositionWithID(String panoId) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    obtain.writeString(panoId);
                    this.zznI.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPositionWithRadius(LatLng position, int radius) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (position != null) {
                        obtain.writeInt(1);
                        position.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(radius);
                    this.zznI.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IStreetViewPanoramaDelegate zzcK(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IStreetViewPanoramaDelegate)) ? new zza(iBinder) : (IStreetViewPanoramaDelegate) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            int i = 0;
            boolean z;
            boolean isZoomGesturesEnabled;
            LatLng zzfa;
            switch (code) {
                case TTSConst.TTSMULTILINE /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    enableZoom(z);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    enablePanning(z);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSUNICODE /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    enableUserNavigation(z);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSXML /*4*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    enableStreetNames(z);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    isZoomGesturesEnabled = isZoomGesturesEnabled();
                    reply.writeNoException();
                    if (isZoomGesturesEnabled) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    isZoomGesturesEnabled = isPanningGesturesEnabled();
                    reply.writeNoException();
                    if (isZoomGesturesEnabled) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    isZoomGesturesEnabled = isUserNavigationEnabled();
                    reply.writeNoException();
                    if (isZoomGesturesEnabled) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSEVT_TAG /*8*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    isZoomGesturesEnabled = isStreetNamesEnabled();
                    reply.writeNoException();
                    if (isZoomGesturesEnabled) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    animateTo(data.readInt() != 0 ? StreetViewPanoramaCamera.CREATOR.zzff(data) : null, data.readLong());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_RESUME /*10*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    StreetViewPanoramaCamera panoramaCamera = getPanoramaCamera();
                    reply.writeNoException();
                    if (panoramaCamera != null) {
                        reply.writeInt(1);
                        panoramaCamera.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    setPositionWithID(data.readString());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (data.readInt() != 0) {
                        zzfa = LatLng.CREATOR.zzfa(data);
                    }
                    setPosition(zzfa);
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_AUDIO /*13*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (data.readInt() != 0) {
                        zzfa = LatLng.CREATOR.zzfa(data);
                    }
                    setPositionWithRadius(zzfa, data.readInt());
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    StreetViewPanoramaLocation streetViewPanoramaLocation = getStreetViewPanoramaLocation();
                    reply.writeNoException();
                    if (streetViewPanoramaLocation != null) {
                        reply.writeInt(1);
                        streetViewPanoramaLocation.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    setOnStreetViewPanoramaChangeListener(com.google.android.gms.maps.internal.zzs.zza.zzcE(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_ERROR /*16*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    setOnStreetViewPanoramaCameraChangeListener(com.google.android.gms.maps.internal.zzr.zza.zzcD(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_JUMP /*17*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    setOnStreetViewPanoramaClickListener(com.google.android.gms.maps.internal.zzt.zza.zzcF(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    StreetViewPanoramaOrientation pointToOrientation = pointToOrientation(com.google.android.gms.dynamic.zzd.zza.zzbk(data.readStrongBinder()));
                    reply.writeNoException();
                    if (pointToOrientation != null) {
                        reply.writeInt(1);
                        pointToOrientation.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    zzd orientationToPoint = orientationToPoint(data.readInt() != 0 ? StreetViewPanoramaOrientation.CREATOR.zzfi(data) : null);
                    reply.writeNoException();
                    if (orientationToPoint != null) {
                        iBinder = orientationToPoint.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case TTSConst.TTSEVT_STYLECHANGE /*20*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    setOnStreetViewPanoramaLongClickListener(com.google.android.gms.maps.internal.zzu.zza.zzcG(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long j) throws RemoteException;

    void enablePanning(boolean z) throws RemoteException;

    void enableStreetNames(boolean z) throws RemoteException;

    void enableUserNavigation(boolean z) throws RemoteException;

    void enableZoom(boolean z) throws RemoteException;

    StreetViewPanoramaCamera getPanoramaCamera() throws RemoteException;

    StreetViewPanoramaLocation getStreetViewPanoramaLocation() throws RemoteException;

    boolean isPanningGesturesEnabled() throws RemoteException;

    boolean isStreetNamesEnabled() throws RemoteException;

    boolean isUserNavigationEnabled() throws RemoteException;

    boolean isZoomGesturesEnabled() throws RemoteException;

    zzd orientationToPoint(StreetViewPanoramaOrientation streetViewPanoramaOrientation) throws RemoteException;

    StreetViewPanoramaOrientation pointToOrientation(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    void setOnStreetViewPanoramaCameraChangeListener(zzr com_google_android_gms_maps_internal_zzr) throws RemoteException;

    void setOnStreetViewPanoramaChangeListener(zzs com_google_android_gms_maps_internal_zzs) throws RemoteException;

    void setOnStreetViewPanoramaClickListener(zzt com_google_android_gms_maps_internal_zzt) throws RemoteException;

    void setOnStreetViewPanoramaLongClickListener(zzu com_google_android_gms_maps_internal_zzu) throws RemoteException;

    void setPosition(LatLng latLng) throws RemoteException;

    void setPositionWithID(String str) throws RemoteException;

    void setPositionWithRadius(LatLng latLng, int i) throws RemoteException;
}
