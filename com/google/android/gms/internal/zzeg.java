package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.mediation.customevent.CustomEvent;
import com.google.android.gms.internal.zzeh.zza;
import java.util.Map;

@zzgk
public final class zzeg extends zza {
    private Map<Class<? extends Object>, Object> zzyU;

    private <NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> zzei zzad(String str) throws RemoteException {
        try {
            Class cls = Class.forName(str, false, zzeg.class.getClassLoader());
            if (MediationAdapter.class.isAssignableFrom(cls)) {
                MediationAdapter mediationAdapter = (MediationAdapter) cls.newInstance();
                return new zzes(mediationAdapter, (NetworkExtras) this.zzyU.get(mediationAdapter.getAdditionalParametersType()));
            } else if (com.google.android.gms.ads.mediation.MediationAdapter.class.isAssignableFrom(cls)) {
                return new zzen((com.google.android.gms.ads.mediation.MediationAdapter) cls.newInstance());
            } else {
                zzb.zzaE("Could not instantiate mediation adapter: " + str + " (not a valid adapter).");
                throw new RemoteException();
            }
        } catch (Throwable th) {
            zzb.zzaE("Could not instantiate mediation adapter: " + str + ". " + th.getMessage());
            RemoteException remoteException = new RemoteException();
        }
    }

    public zzei zzab(String str) throws RemoteException {
        return zzad(str);
    }

    public boolean zzac(String str) throws RemoteException {
        boolean z = false;
        try {
            z = CustomEvent.class.isAssignableFrom(Class.forName(str, false, zzeg.class.getClassLoader()));
        } catch (Throwable th) {
            zzb.zzaE("Could not load custom event implementation class: " + str + ", assuming old implementation.");
        }
        return z;
    }

    public void zze(Map<Class<? extends Object>, Object> map) {
        this.zzyU = map;
    }
}
