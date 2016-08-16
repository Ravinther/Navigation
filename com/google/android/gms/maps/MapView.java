package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class MapView extends FrameLayout {
    private GoogleMap zzaFV;
    private final zzb zzaGb;

    static class zza implements MapLifecycleDelegate {
        private final ViewGroup zzaGc;
        private final IMapViewDelegate zzaGd;

        /* renamed from: com.google.android.gms.maps.MapView.zza.1 */
        class C09941 extends com.google.android.gms.maps.internal.zzl.zza {
            final /* synthetic */ OnMapReadyCallback zzaFX;
            final /* synthetic */ zza zzaGf;

            C09941(zza com_google_android_gms_maps_MapView_zza, OnMapReadyCallback onMapReadyCallback) {
                this.zzaGf = com_google_android_gms_maps_MapView_zza;
                this.zzaFX = onMapReadyCallback;
            }

            public void zza(IGoogleMapDelegate iGoogleMapDelegate) throws RemoteException {
                this.zzaFX.onMapReady(new GoogleMap(iGoogleMapDelegate));
            }
        }

        public zza(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.zzaGd = (IMapViewDelegate) zzx.zzv(iMapViewDelegate);
            this.zzaGc = (ViewGroup) zzx.zzv(viewGroup);
        }

        public void getMapAsync(OnMapReadyCallback callback) {
            try {
                this.zzaGd.getMapAsync(new C09941(this, callback));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public IMapViewDelegate zzwQ() {
            return this.zzaGd;
        }
    }

    static class zzb extends com.google.android.gms.dynamic.zza<zza> {
        private final Context mContext;
        protected zzf<zza> zzaFZ;
        private final List<OnMapReadyCallback> zzaGa;
        private final ViewGroup zzaGg;
        private final GoogleMapOptions zzaGh;

        zzb(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.zzaGa = new ArrayList();
            this.zzaGg = viewGroup;
            this.mContext = context;
            this.zzaGh = googleMapOptions;
        }

        public void zzwP() {
            if (this.zzaFZ != null && zzrn() == null) {
                try {
                    MapsInitializer.initialize(this.mContext);
                    IMapViewDelegate zza = zzy.zzaF(this.mContext).zza(zze.zzx(this.mContext), this.zzaGh);
                    if (zza != null) {
                        this.zzaFZ.zza(new zza(this.zzaGg, zza));
                        for (OnMapReadyCallback mapAsync : this.zzaGa) {
                            ((zza) zzrn()).getMapAsync(mapAsync);
                        }
                        this.zzaGa.clear();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.zzaGb = new zzb(this, context, GoogleMapOptions.createFromAttributes(context, attrs));
        init();
    }

    private void init() {
        setClickable(true);
    }

    @Deprecated
    public final GoogleMap getMap() {
        if (this.zzaFV != null) {
            return this.zzaFV;
        }
        this.zzaGb.zzwP();
        if (this.zzaGb.zzrn() == null) {
            return null;
        }
        try {
            this.zzaFV = new GoogleMap(((zza) this.zzaGb.zzrn()).zzwQ().getMap());
            return this.zzaFV;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
