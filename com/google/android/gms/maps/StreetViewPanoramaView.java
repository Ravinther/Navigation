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
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class StreetViewPanoramaView extends FrameLayout {
    private final zzb zzaGC;
    private StreetViewPanorama zzaGq;

    static class zza implements StreetViewLifecycleDelegate {
        private final IStreetViewPanoramaViewDelegate zzaGD;
        private final ViewGroup zzaGc;

        /* renamed from: com.google.android.gms.maps.StreetViewPanoramaView.zza.1 */
        class C09951 extends com.google.android.gms.maps.internal.zzv.zza {
            final /* synthetic */ zza zzaGF;
            final /* synthetic */ OnStreetViewPanoramaReadyCallback zzaGs;

            C09951(zza com_google_android_gms_maps_StreetViewPanoramaView_zza, OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
                this.zzaGF = com_google_android_gms_maps_StreetViewPanoramaView_zza;
                this.zzaGs = onStreetViewPanoramaReadyCallback;
            }

            public void zza(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) throws RemoteException {
                this.zzaGs.onStreetViewPanoramaReady(new StreetViewPanorama(iStreetViewPanoramaDelegate));
            }
        }

        public zza(ViewGroup viewGroup, IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate) {
            this.zzaGD = (IStreetViewPanoramaViewDelegate) zzx.zzv(iStreetViewPanoramaViewDelegate);
            this.zzaGc = (ViewGroup) zzx.zzv(viewGroup);
        }

        public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback callback) {
            try {
                this.zzaGD.getStreetViewPanoramaAsync(new C09951(this, callback));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public IStreetViewPanoramaViewDelegate zzwW() {
            return this.zzaGD;
        }
    }

    static class zzb extends com.google.android.gms.dynamic.zza<zza> {
        private final Context mContext;
        protected zzf<zza> zzaFZ;
        private final StreetViewPanoramaOptions zzaGG;
        private final ViewGroup zzaGg;
        private final List<OnStreetViewPanoramaReadyCallback> zzaGu;

        zzb(ViewGroup viewGroup, Context context, StreetViewPanoramaOptions streetViewPanoramaOptions) {
            this.zzaGu = new ArrayList();
            this.zzaGg = viewGroup;
            this.mContext = context;
            this.zzaGG = streetViewPanoramaOptions;
        }

        public void zzwP() {
            if (this.zzaFZ != null && zzrn() == null) {
                try {
                    this.zzaFZ.zza(new zza(this.zzaGg, zzy.zzaF(this.mContext).zza(zze.zzx(this.mContext), this.zzaGG)));
                    for (OnStreetViewPanoramaReadyCallback streetViewPanoramaAsync : this.zzaGu) {
                        ((zza) zzrn()).getStreetViewPanoramaAsync(streetViewPanoramaAsync);
                    }
                    this.zzaGu.clear();
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public StreetViewPanoramaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.zzaGC = new zzb(this, context, null);
    }

    @Deprecated
    public final StreetViewPanorama getStreetViewPanorama() {
        if (this.zzaGq != null) {
            return this.zzaGq;
        }
        this.zzaGC.zzwP();
        if (this.zzaGC.zzrn() == null) {
            return null;
        }
        try {
            this.zzaGq = new StreetViewPanorama(((zza) this.zzaGC.zzrn()).zzwW().getStreetViewPanorama());
            return this.zzaGq;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
