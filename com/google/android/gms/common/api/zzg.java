package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.signin.internal.AuthAccountResult;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import loquendo.tts.engine.TTSConst;

public class zzg implements zzj {
    private final Context mContext;
    private com.google.android.gms.signin.zzd zzZA;
    private int zzZB;
    private boolean zzZC;
    private boolean zzZD;
    private zzp zzZE;
    private boolean zzZF;
    private boolean zzZG;
    private final com.google.android.gms.common.internal.zzf zzZH;
    private final Map<Api<?>, Integer> zzZI;
    private ArrayList<Future<?>> zzZJ;
    private final GoogleApiAvailability zzZi;
    private final com.google.android.gms.common.api.Api.zza<? extends com.google.android.gms.signin.zzd, com.google.android.gms.signin.zze> zzZj;
    private final zzi zzZq;
    private final Lock zzZs;
    private ConnectionResult zzZt;
    private int zzZu;
    private int zzZv;
    private boolean zzZw;
    private int zzZx;
    private final Bundle zzZy;
    private final Set<com.google.android.gms.common.api.Api.zzc> zzZz;

    /* renamed from: com.google.android.gms.common.api.zzg.1 */
    class C06381 implements Runnable {
        final /* synthetic */ zzg zzZK;

        C06381(zzg com_google_android_gms_common_api_zzg) {
            this.zzZK = com_google_android_gms_common_api_zzg;
        }

        public void run() {
            this.zzZK.zzZi.zzac(this.zzZK.mContext);
        }
    }

    private static class zza extends com.google.android.gms.signin.internal.zzb {
        private final WeakReference<zzg> zzZL;

        /* renamed from: com.google.android.gms.common.api.zzg.zza.1 */
        class C06391 extends zzb {
            final /* synthetic */ zzg zzZM;
            final /* synthetic */ ConnectionResult zzZN;
            final /* synthetic */ zza zzZO;

            C06391(zza com_google_android_gms_common_api_zzg_zza, zzj com_google_android_gms_common_api_zzj, zzg com_google_android_gms_common_api_zzg, ConnectionResult connectionResult) {
                this.zzZO = com_google_android_gms_common_api_zzg_zza;
                this.zzZM = com_google_android_gms_common_api_zzg;
                this.zzZN = connectionResult;
                super(com_google_android_gms_common_api_zzj);
            }

            public void zznn() {
                this.zzZM.zzc(this.zzZN);
            }
        }

        zza(zzg com_google_android_gms_common_api_zzg) {
            this.zzZL = new WeakReference(com_google_android_gms_common_api_zzg);
        }

        public void zza(ConnectionResult connectionResult, AuthAccountResult authAccountResult) {
            zzg com_google_android_gms_common_api_zzg = (zzg) this.zzZL.get();
            if (com_google_android_gms_common_api_zzg != null) {
                com_google_android_gms_common_api_zzg.zzZq.zza(new C06391(this, com_google_android_gms_common_api_zzg, com_google_android_gms_common_api_zzg, connectionResult));
            }
        }
    }

    private static class zzb extends com.google.android.gms.common.internal.zzt.zza {
        private final WeakReference<zzg> zzZL;

        /* renamed from: com.google.android.gms.common.api.zzg.zzb.1 */
        class C06401 extends zzb {
            final /* synthetic */ zzg zzZM;
            final /* synthetic */ ResolveAccountResponse zzZP;
            final /* synthetic */ zzb zzZQ;

            C06401(zzb com_google_android_gms_common_api_zzg_zzb, zzj com_google_android_gms_common_api_zzj, zzg com_google_android_gms_common_api_zzg, ResolveAccountResponse resolveAccountResponse) {
                this.zzZQ = com_google_android_gms_common_api_zzg_zzb;
                this.zzZM = com_google_android_gms_common_api_zzg;
                this.zzZP = resolveAccountResponse;
                super(com_google_android_gms_common_api_zzj);
            }

            public void zznn() {
                this.zzZM.zza(this.zzZP);
            }
        }

        zzb(zzg com_google_android_gms_common_api_zzg) {
            this.zzZL = new WeakReference(com_google_android_gms_common_api_zzg);
        }

        public void zzb(ResolveAccountResponse resolveAccountResponse) {
            zzg com_google_android_gms_common_api_zzg = (zzg) this.zzZL.get();
            if (com_google_android_gms_common_api_zzg != null) {
                com_google_android_gms_common_api_zzg.zzZq.zza(new C06401(this, com_google_android_gms_common_api_zzg, com_google_android_gms_common_api_zzg, resolveAccountResponse));
            }
        }
    }

    private abstract class zzi implements Runnable {
        final /* synthetic */ zzg zzZK;

        private zzi(zzg com_google_android_gms_common_api_zzg) {
            this.zzZK = com_google_android_gms_common_api_zzg;
        }

        public void run() {
            this.zzZK.zzZs.lock();
            try {
                if (!Thread.interrupted()) {
                    zznn();
                    this.zzZK.zzZs.unlock();
                }
            } catch (RuntimeException e) {
                this.zzZK.zzZq.zza(e);
            } finally {
                this.zzZK.zzZs.unlock();
            }
        }

        protected abstract void zznn();
    }

    private class zzc extends zzi {
        final /* synthetic */ zzg zzZK;

        private zzc(zzg com_google_android_gms_common_api_zzg) {
            this.zzZK = com_google_android_gms_common_api_zzg;
            super(null);
        }

        public void zznn() {
            this.zzZK.zzZA.zza(this.zzZK.zzZE, this.zzZK.zzZq.zzaah, new zza(this.zzZK));
        }
    }

    private static class zzd implements com.google.android.gms.common.api.GoogleApiClient.zza {
        private final WeakReference<zzg> zzZL;
        private final Api<?> zzZR;
        private final int zzZS;

        public zzd(zzg com_google_android_gms_common_api_zzg, Api<?> api, int i) {
            this.zzZL = new WeakReference(com_google_android_gms_common_api_zzg);
            this.zzZR = api;
            this.zzZS = i;
        }

        public void zza(ConnectionResult connectionResult) {
            boolean z = false;
            zzg com_google_android_gms_common_api_zzg = (zzg) this.zzZL.get();
            if (com_google_android_gms_common_api_zzg != null) {
                if (Looper.myLooper() == com_google_android_gms_common_api_zzg.zzZq.getLooper()) {
                    z = true;
                }
                zzx.zza(z, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
                com_google_android_gms_common_api_zzg.zzZs.lock();
                try {
                    if (com_google_android_gms_common_api_zzg.zzbe(0)) {
                        if (!connectionResult.isSuccess()) {
                            com_google_android_gms_common_api_zzg.zzb(connectionResult, this.zzZR, this.zzZS);
                        }
                        if (com_google_android_gms_common_api_zzg.zzno()) {
                            com_google_android_gms_common_api_zzg.zznp();
                        }
                        com_google_android_gms_common_api_zzg.zzZs.unlock();
                    }
                } finally {
                    com_google_android_gms_common_api_zzg.zzZs.unlock();
                }
            }
        }

        public void zzb(ConnectionResult connectionResult) {
            boolean z = true;
            zzg com_google_android_gms_common_api_zzg = (zzg) this.zzZL.get();
            if (com_google_android_gms_common_api_zzg != null) {
                if (Looper.myLooper() != com_google_android_gms_common_api_zzg.zzZq.getLooper()) {
                    z = false;
                }
                zzx.zza(z, "onReportAccountValidation must be called on the GoogleApiClient handler thread");
                com_google_android_gms_common_api_zzg.zzZs.lock();
                try {
                    if (com_google_android_gms_common_api_zzg.zzbe(1)) {
                        if (!connectionResult.isSuccess()) {
                            com_google_android_gms_common_api_zzg.zzb(connectionResult, this.zzZR, this.zzZS);
                        }
                        if (com_google_android_gms_common_api_zzg.zzno()) {
                            com_google_android_gms_common_api_zzg.zznr();
                        }
                        com_google_android_gms_common_api_zzg.zzZs.unlock();
                    }
                } finally {
                    com_google_android_gms_common_api_zzg.zzZs.unlock();
                }
            }
        }
    }

    private class zze extends zzi {
        final /* synthetic */ zzg zzZK;
        private final Map<com.google.android.gms.common.api.Api.zzb, com.google.android.gms.common.api.GoogleApiClient.zza> zzZT;

        /* renamed from: com.google.android.gms.common.api.zzg.zze.1 */
        class C06411 extends zzb {
            final /* synthetic */ ConnectionResult zzZU;
            final /* synthetic */ zze zzZV;

            C06411(zze com_google_android_gms_common_api_zzg_zze, zzj com_google_android_gms_common_api_zzj, ConnectionResult connectionResult) {
                this.zzZV = com_google_android_gms_common_api_zzg_zze;
                this.zzZU = connectionResult;
                super(com_google_android_gms_common_api_zzj);
            }

            public void zznn() {
                this.zzZV.zzZK.zzf(this.zzZU);
            }
        }

        public zze(zzg com_google_android_gms_common_api_zzg, Map<com.google.android.gms.common.api.Api.zzb, com.google.android.gms.common.api.GoogleApiClient.zza> map) {
            this.zzZK = com_google_android_gms_common_api_zzg;
            super(null);
            this.zzZT = map;
        }

        public void zznn() {
            int isGooglePlayServicesAvailable = this.zzZK.zzZi.isGooglePlayServicesAvailable(this.zzZK.mContext);
            if (isGooglePlayServicesAvailable != 0) {
                this.zzZK.zzZq.zza(new C06411(this, this.zzZK, new ConnectionResult(isGooglePlayServicesAvailable, null)));
                return;
            }
            if (this.zzZK.zzZC) {
                this.zzZK.zzZA.connect();
            }
            for (com.google.android.gms.common.api.Api.zzb com_google_android_gms_common_api_Api_zzb : this.zzZT.keySet()) {
                com_google_android_gms_common_api_Api_zzb.zza((com.google.android.gms.common.api.GoogleApiClient.zza) this.zzZT.get(com_google_android_gms_common_api_Api_zzb));
            }
        }
    }

    private class zzf extends zzi {
        final /* synthetic */ zzg zzZK;
        private final ArrayList<com.google.android.gms.common.api.Api.zzb> zzZW;

        public zzf(zzg com_google_android_gms_common_api_zzg, ArrayList<com.google.android.gms.common.api.Api.zzb> arrayList) {
            this.zzZK = com_google_android_gms_common_api_zzg;
            super(null);
            this.zzZW = arrayList;
        }

        public void zznn() {
            Set set = this.zzZK.zzZq.zzaah;
            Set zzh = set.isEmpty() ? this.zzZK.zznw() : set;
            Iterator it = this.zzZW.iterator();
            while (it.hasNext()) {
                ((com.google.android.gms.common.api.Api.zzb) it.next()).zza(this.zzZK.zzZE, zzh);
            }
        }
    }

    private class zzg implements ConnectionCallbacks, OnConnectionFailedListener {
        final /* synthetic */ zzg zzZK;

        private zzg(zzg com_google_android_gms_common_api_zzg) {
            this.zzZK = com_google_android_gms_common_api_zzg;
        }

        public void onConnected(Bundle connectionHint) {
            this.zzZK.zzZA.zza(new zzb(this.zzZK));
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.zzZK.zzZs.lock();
            try {
                if (this.zzZK.zze(result)) {
                    this.zzZK.zznu();
                    this.zzZK.zzns();
                } else {
                    this.zzZK.zzf(result);
                }
                this.zzZK.zzZs.unlock();
            } catch (Throwable th) {
                this.zzZK.zzZs.unlock();
            }
        }

        public void onConnectionSuspended(int cause) {
        }
    }

    private class zzh extends zzi {
        final /* synthetic */ zzg zzZK;
        private final ArrayList<com.google.android.gms.common.api.Api.zzb> zzZW;

        public zzh(zzg com_google_android_gms_common_api_zzg, ArrayList<com.google.android.gms.common.api.Api.zzb> arrayList) {
            this.zzZK = com_google_android_gms_common_api_zzg;
            super(null);
            this.zzZW = arrayList;
        }

        public void zznn() {
            Iterator it = this.zzZW.iterator();
            while (it.hasNext()) {
                ((com.google.android.gms.common.api.Api.zzb) it.next()).zza(this.zzZK.zzZE);
            }
        }
    }

    public zzg(zzi com_google_android_gms_common_api_zzi, com.google.android.gms.common.internal.zzf com_google_android_gms_common_internal_zzf, Map<Api<?>, Integer> map, GoogleApiAvailability googleApiAvailability, com.google.android.gms.common.api.Api.zza<? extends com.google.android.gms.signin.zzd, com.google.android.gms.signin.zze> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_signin_zzd__com_google_android_gms_signin_zze, Lock lock, Context context) {
        this.zzZv = 0;
        this.zzZw = false;
        this.zzZy = new Bundle();
        this.zzZz = new HashSet();
        this.zzZJ = new ArrayList();
        this.zzZq = com_google_android_gms_common_api_zzi;
        this.zzZH = com_google_android_gms_common_internal_zzf;
        this.zzZI = map;
        this.zzZi = googleApiAvailability;
        this.zzZj = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_signin_zzd__com_google_android_gms_signin_zze;
        this.zzZs = lock;
        this.mContext = context;
    }

    private void zzX(boolean z) {
        if (this.zzZA != null) {
            if (this.zzZA.isConnected() && z) {
                this.zzZA.zzzn();
            }
            this.zzZA.disconnect();
            this.zzZE = null;
        }
    }

    private void zza(ResolveAccountResponse resolveAccountResponse) {
        if (zzbe(0)) {
            ConnectionResult zzoP = resolveAccountResponse.zzoP();
            if (zzoP.isSuccess()) {
                this.zzZE = resolveAccountResponse.zzoO();
                this.zzZD = true;
                this.zzZF = resolveAccountResponse.zzoQ();
                this.zzZG = resolveAccountResponse.zzoR();
                zznp();
            } else if (zze(zzoP)) {
                zznu();
                zznp();
            } else {
                zzf(zzoP);
            }
        }
    }

    private boolean zza(int i, int i2, ConnectionResult connectionResult) {
        return (i2 != 1 || zzd(connectionResult)) ? this.zzZt == null || i < this.zzZu : false;
    }

    private void zzb(ConnectionResult connectionResult, Api<?> api, int i) {
        if (i != 2) {
            int priority = api.zznb().getPriority();
            if (zza(priority, i, connectionResult)) {
                this.zzZt = connectionResult;
                this.zzZu = priority;
            }
        }
        this.zzZq.zzaag.put(api.zznd(), connectionResult);
    }

    private boolean zzbe(int i) {
        if (this.zzZv == i) {
            return true;
        }
        Log.wtf("GoogleApiClientConnecting", "GoogleApiClient connecting is in step " + zzbf(this.zzZv) + " but received callback for step " + zzbf(i));
        zzf(new ConnectionResult(8, null));
        return false;
    }

    private String zzbf(int i) {
        switch (i) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "STEP_GETTING_SERVICE_BINDINGS";
            case TTSConst.TTSMULTILINE /*1*/:
                return "STEP_VALIDATING_ACCOUNT";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "STEP_AUTHENTICATING";
            case TTSConst.TTSUNICODE /*3*/:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    private void zzc(ConnectionResult connectionResult) {
        if (!zzbe(2)) {
            return;
        }
        if (connectionResult.isSuccess()) {
            zzns();
        } else if (zze(connectionResult)) {
            zznu();
            zzns();
        } else {
            zzf(connectionResult);
        }
    }

    private boolean zzd(ConnectionResult connectionResult) {
        return connectionResult.hasResolution() || this.zzZi.zzbb(connectionResult.getErrorCode()) != null;
    }

    private boolean zze(ConnectionResult connectionResult) {
        return this.zzZB != 2 ? this.zzZB == 1 && !connectionResult.hasResolution() : true;
    }

    private void zzf(ConnectionResult connectionResult) {
        boolean z = false;
        this.zzZw = false;
        zznv();
        if (!connectionResult.hasResolution()) {
            z = true;
        }
        zzX(z);
        this.zzZq.zzaag.clear();
        this.zzZq.zzg(connectionResult);
        if (!(this.zzZq.zznB() && this.zzZi.zzd(this.mContext, connectionResult.getErrorCode()))) {
            this.zzZq.zznE();
            this.zzZq.zzZY.zzj(connectionResult);
        }
        this.zzZq.zzZY.zzoI();
    }

    private boolean zzno() {
        this.zzZx--;
        if (this.zzZx > 0) {
            return false;
        }
        if (this.zzZx < 0) {
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.");
            zzf(new ConnectionResult(8, null));
            return false;
        } else if (this.zzZt == null) {
            return true;
        } else {
            zzf(this.zzZt);
            return false;
        }
    }

    private void zznp() {
        if (this.zzZx == 0) {
            if (!this.zzZC) {
                zzns();
            } else if (this.zzZD) {
                zznq();
            }
        }
    }

    private void zznq() {
        ArrayList arrayList = new ArrayList();
        this.zzZv = 1;
        this.zzZx = this.zzZq.zzaaf.size();
        for (com.google.android.gms.common.api.Api.zzc com_google_android_gms_common_api_Api_zzc : this.zzZq.zzaaf.keySet()) {
            if (!this.zzZq.zzaag.containsKey(com_google_android_gms_common_api_Api_zzc)) {
                arrayList.add(this.zzZq.zzaaf.get(com_google_android_gms_common_api_Api_zzc));
            } else if (zzno()) {
                zznr();
            }
        }
        if (!arrayList.isEmpty()) {
            this.zzZJ.add(zzk.zznF().submit(new zzh(this, arrayList)));
        }
    }

    private void zznr() {
        this.zzZv = 2;
        this.zzZq.zzaah = zznw();
        this.zzZJ.add(zzk.zznF().submit(new zzc()));
    }

    private void zzns() {
        ArrayList arrayList = new ArrayList();
        this.zzZv = 3;
        this.zzZx = this.zzZq.zzaaf.size();
        for (com.google.android.gms.common.api.Api.zzc com_google_android_gms_common_api_Api_zzc : this.zzZq.zzaaf.keySet()) {
            if (!this.zzZq.zzaag.containsKey(com_google_android_gms_common_api_Api_zzc)) {
                arrayList.add(this.zzZq.zzaaf.get(com_google_android_gms_common_api_Api_zzc));
            } else if (zzno()) {
                zznt();
            }
        }
        if (!arrayList.isEmpty()) {
            this.zzZJ.add(zzk.zznF().submit(new zzf(this, arrayList)));
        }
    }

    private void zznt() {
        this.zzZq.zznA();
        zzk.zznF().execute(new C06381(this));
        if (this.zzZA != null) {
            if (this.zzZF) {
                this.zzZA.zza(this.zzZE, this.zzZG);
            }
            zzX(false);
        }
        for (com.google.android.gms.common.api.Api.zzc com_google_android_gms_common_api_Api_zzc : this.zzZq.zzaag.keySet()) {
            ((com.google.android.gms.common.api.Api.zzb) this.zzZq.zzaaf.get(com_google_android_gms_common_api_Api_zzc)).disconnect();
        }
        if (this.zzZw) {
            this.zzZw = false;
            disconnect();
            return;
        }
        this.zzZq.zzZY.zzh(this.zzZy.isEmpty() ? null : this.zzZy);
    }

    private void zznu() {
        this.zzZC = false;
        this.zzZq.zzaah = Collections.emptySet();
        for (com.google.android.gms.common.api.Api.zzc com_google_android_gms_common_api_Api_zzc : this.zzZz) {
            if (!this.zzZq.zzaag.containsKey(com_google_android_gms_common_api_Api_zzc)) {
                this.zzZq.zzaag.put(com_google_android_gms_common_api_Api_zzc, new ConnectionResult(17, null));
            }
        }
    }

    private void zznv() {
        Iterator it = this.zzZJ.iterator();
        while (it.hasNext()) {
            ((Future) it.next()).cancel(true);
        }
        this.zzZJ.clear();
    }

    private Set<Scope> zznw() {
        Set<Scope> hashSet = new HashSet(this.zzZH.zzoi());
        Map zzok = this.zzZH.zzok();
        for (Api api : zzok.keySet()) {
            if (!this.zzZq.zzaag.containsKey(api.zznd())) {
                hashSet.addAll(((com.google.android.gms.common.internal.zzf.zza) zzok.get(api)).zzZp);
            }
        }
        return hashSet;
    }

    public void begin() {
        this.zzZq.zzZY.zzoJ();
        this.zzZq.zzaag.clear();
        this.zzZw = false;
        this.zzZC = false;
        this.zzZt = null;
        this.zzZv = 0;
        this.zzZB = 2;
        this.zzZD = false;
        this.zzZF = false;
        Map hashMap = new HashMap();
        int i = 0;
        for (Api api : this.zzZI.keySet()) {
            com.google.android.gms.common.api.Api.zzb com_google_android_gms_common_api_Api_zzb = (com.google.android.gms.common.api.Api.zzb) this.zzZq.zzaaf.get(api.zznd());
            int intValue = ((Integer) this.zzZI.get(api)).intValue();
            int i2 = (api.zznb().getPriority() == 1 ? 1 : 0) | i;
            if (com_google_android_gms_common_api_Api_zzb.zzlm()) {
                this.zzZC = true;
                if (intValue < this.zzZB) {
                    this.zzZB = intValue;
                }
                if (intValue != 0) {
                    this.zzZz.add(api.zznd());
                }
            }
            hashMap.put(com_google_android_gms_common_api_Api_zzb, new zzd(this, api, intValue));
            i = i2;
        }
        if (i != 0) {
            this.zzZC = false;
        }
        if (this.zzZC) {
            this.zzZH.zza(Integer.valueOf(this.zzZq.getSessionId()));
            ConnectionCallbacks com_google_android_gms_common_api_zzg_zzg = new zzg();
            this.zzZA = (com.google.android.gms.signin.zzd) this.zzZj.zza(this.mContext, this.zzZq.getLooper(), this.zzZH, this.zzZH.zzoo(), com_google_android_gms_common_api_zzg_zzg, com_google_android_gms_common_api_zzg_zzg);
        }
        this.zzZx = this.zzZq.zzaaf.size();
        this.zzZJ.add(zzk.zznF().submit(new zze(this, hashMap)));
    }

    public void connect() {
        this.zzZw = false;
    }

    public void disconnect() {
        Iterator it = this.zzZq.zzZZ.iterator();
        while (it.hasNext()) {
            zze com_google_android_gms_common_api_zzi_zze = (zze) it.next();
            if (com_google_android_gms_common_api_zzi_zze.zzng() != 1) {
                com_google_android_gms_common_api_zzi_zze.cancel();
                it.remove();
            }
        }
        this.zzZq.zznx();
        if (this.zzZt != null || this.zzZq.zzZZ.isEmpty()) {
            zznv();
            zzX(true);
            this.zzZq.zzaag.clear();
            this.zzZq.zzg(null);
            this.zzZq.zzZY.zzoI();
            return;
        }
        this.zzZw = true;
    }

    public String getName() {
        return "CONNECTING";
    }

    public void onConnected(Bundle connectionHint) {
        if (zzbe(3)) {
            if (connectionHint != null) {
                this.zzZy.putAll(connectionHint);
            }
            if (zzno()) {
                zznt();
            }
        }
    }

    public void onConnectionSuspended(int cause) {
        zzf(new ConnectionResult(8, null));
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.common.api.zzc.zza<R, A>> T zza(T t) {
        this.zzZq.zzZZ.add(t);
        return t;
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
        if (zzbe(3)) {
            zzb(connectionResult, api, i);
            if (zzno()) {
                zznt();
            }
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.common.api.zzc.zza<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
