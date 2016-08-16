package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import loquendo.tts.engine.TTSConst;

public abstract class zzj<T extends IInterface> implements com.google.android.gms.common.api.Api.zzb, com.google.android.gms.common.internal.zzk.zza {
    public static final String[] zzadF;
    private final Context mContext;
    final Handler mHandler;
    private final Account zzOY;
    private final Looper zzYV;
    private final zzf zzZH;
    private final GoogleApiAvailability zzZi;
    private final Set<Scope> zzZp;
    private int zzadA;
    private final ConnectionCallbacks zzadB;
    private final OnConnectionFailedListener zzadC;
    private final int zzadD;
    protected AtomicInteger zzadE;
    private final zzl zzadu;
    private zzs zzadv;
    private com.google.android.gms.common.api.GoogleApiClient.zza zzadw;
    private T zzadx;
    private final ArrayList<zzc<?>> zzady;
    private com.google.android.gms.common.internal.zzj$com.google.android.gms.common.internal.zzj.zze zzadz;
    private final Object zzpc;

    protected abstract class zzc<TListener> {
        private TListener mListener;
        final /* synthetic */ zzj zzadH;
        private boolean zzadI;

        public zzc(zzj com_google_android_gms_common_internal_zzj, TListener tListener) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
            this.mListener = tListener;
            this.zzadI = false;
        }

        public void unregister() {
            zzoG();
            synchronized (this.zzadH.zzady) {
                this.zzadH.zzady.remove(this);
            }
        }

        protected abstract void zzoE();

        public void zzoF() {
            synchronized (this) {
                Object obj = this.mListener;
                if (this.zzadI) {
                    Log.w("GmsClient", "Callback proxy " + this + " being reused. This is not safe.");
                }
            }
            if (obj != null) {
                try {
                    zzs(obj);
                } catch (RuntimeException e) {
                    zzoE();
                    throw e;
                }
            }
            zzoE();
            synchronized (this) {
                this.zzadI = true;
            }
            unregister();
        }

        public void zzoG() {
            synchronized (this) {
                this.mListener = null;
            }
        }

        protected abstract void zzs(TListener tListener);
    }

    private abstract class zza extends zzc<Boolean> {
        public final int statusCode;
        public final Bundle zzadG;
        final /* synthetic */ zzj zzadH;

        protected zza(zzj com_google_android_gms_common_internal_zzj, int i, Bundle bundle) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
            super(com_google_android_gms_common_internal_zzj, Boolean.valueOf(true));
            this.statusCode = i;
            this.zzadG = bundle;
        }

        protected void zzc(Boolean bool) {
            PendingIntent pendingIntent = null;
            if (bool == null) {
                this.zzadH.zzb(1, null);
                return;
            }
            switch (this.statusCode) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    if (!zzoD()) {
                        this.zzadH.zzb(1, null);
                        zzi(new ConnectionResult(8, null));
                    }
                case TTSConst.TTSEVT_RESUME /*10*/:
                    this.zzadH.zzb(1, null);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                default:
                    this.zzadH.zzb(1, null);
                    if (this.zzadG != null) {
                        pendingIntent = (PendingIntent) this.zzadG.getParcelable("pendingIntent");
                    }
                    zzi(new ConnectionResult(this.statusCode, pendingIntent));
            }
        }

        protected abstract void zzi(ConnectionResult connectionResult);

        protected abstract boolean zzoD();

        protected void zzoE() {
        }

        protected /* synthetic */ void zzs(Object obj) {
            zzc((Boolean) obj);
        }
    }

    final class zzb extends Handler {
        final /* synthetic */ zzj zzadH;

        public zzb(zzj com_google_android_gms_common_internal_zzj, Looper looper) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
            super(looper);
        }

        private void zza(Message message) {
            zzc com_google_android_gms_common_internal_zzj_zzc = (zzc) message.obj;
            com_google_android_gms_common_internal_zzj_zzc.zzoE();
            com_google_android_gms_common_internal_zzj_zzc.unregister();
        }

        private boolean zzb(Message message) {
            return message.what == 2 || message.what == 1 || message.what == 5 || message.what == 6;
        }

        public void handleMessage(Message msg) {
            if (this.zzadH.zzadE.get() != msg.arg1) {
                if (zzb(msg)) {
                    zza(msg);
                }
            } else if ((msg.what == 1 || msg.what == 5 || msg.what == 6) && !this.zzadH.isConnecting()) {
                zza(msg);
            } else if (msg.what == 3) {
                ConnectionResult connectionResult = new ConnectionResult(msg.arg2, null);
                this.zzadH.zzadw.zza(connectionResult);
                this.zzadH.onConnectionFailed(connectionResult);
            } else if (msg.what == 4) {
                this.zzadH.zzb(4, null);
                if (this.zzadH.zzadB != null) {
                    this.zzadH.zzadB.onConnectionSuspended(msg.arg2);
                }
                this.zzadH.onConnectionSuspended(msg.arg2);
                this.zzadH.zza(4, 1, null);
            } else if (msg.what == 2 && !this.zzadH.isConnected()) {
                zza(msg);
            } else if (zzb(msg)) {
                ((zzc) msg.obj).zzoF();
            } else {
                Log.wtf("GmsClient", "Don't know how to handle this message.");
            }
        }
    }

    public static final class zzd extends com.google.android.gms.common.internal.zzr.zza {
        private zzj zzadJ;
        private final int zzadK;

        public zzd(zzj com_google_android_gms_common_internal_zzj, int i) {
            this.zzadJ = com_google_android_gms_common_internal_zzj;
            this.zzadK = i;
        }

        private void zzoH() {
            this.zzadJ = null;
        }

        public void zza(int i, IBinder iBinder, Bundle bundle) {
            zzx.zzb(this.zzadJ, (Object) "onPostInitComplete can be called only once per call to getRemoteService");
            this.zzadJ.zza(i, iBinder, bundle, this.zzadK);
            zzoH();
        }

        public void zzb(int i, Bundle bundle) {
            zzx.zzb(this.zzadJ, (Object) "onAccountValidationComplete can be called only once per call to validateAccount");
            this.zzadJ.zza(i, bundle, this.zzadK);
            zzoH();
        }
    }

    public final class zze implements ServiceConnection {
        final /* synthetic */ zzj zzadH;
        private final int zzadK;

        public zze(zzj com_google_android_gms_common_internal_zzj, int i) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
            this.zzadK = i;
        }

        public void onServiceConnected(ComponentName component, IBinder binder) {
            zzx.zzb((Object) binder, (Object) "Expecting a valid IBinder");
            this.zzadH.zzadv = com.google.android.gms.common.internal.zzs.zza.zzaK(binder);
            this.zzadH.zzbA(this.zzadK);
        }

        public void onServiceDisconnected(ComponentName component) {
            this.zzadH.mHandler.sendMessage(this.zzadH.mHandler.obtainMessage(4, this.zzadK, 1));
        }
    }

    protected class zzf implements com.google.android.gms.common.api.GoogleApiClient.zza {
        final /* synthetic */ zzj zzadH;

        public zzf(zzj com_google_android_gms_common_internal_zzj) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
        }

        public void zza(ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                this.zzadH.zza(null, this.zzadH.zzZp);
            } else if (this.zzadH.zzadC != null) {
                this.zzadH.zzadC.onConnectionFailed(connectionResult);
            }
        }

        public void zzb(ConnectionResult connectionResult) {
            throw new IllegalStateException("Legacy GmsClient received onReportAccountValidation callback.");
        }
    }

    protected final class zzg extends com.google.android.gms.common.internal.zzj$com.google.android.gms.common.internal.zzj.zza {
        final /* synthetic */ zzj zzadH;
        public final IBinder zzadL;

        public zzg(zzj com_google_android_gms_common_internal_zzj, int i, IBinder iBinder, Bundle bundle) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
            super(com_google_android_gms_common_internal_zzj, i, bundle);
            this.zzadL = iBinder;
        }

        protected void zzi(ConnectionResult connectionResult) {
            if (this.zzadH.zzadC != null) {
                this.zzadH.zzadC.onConnectionFailed(connectionResult);
            }
            this.zzadH.onConnectionFailed(connectionResult);
        }

        protected boolean zzoD() {
            try {
                String interfaceDescriptor = this.zzadL.getInterfaceDescriptor();
                if (this.zzadH.zzfB().equals(interfaceDescriptor)) {
                    IInterface zzV = this.zzadH.zzV(this.zzadL);
                    if (zzV == null || !this.zzadH.zza(2, 3, zzV)) {
                        return false;
                    }
                    Bundle zzmw = this.zzadH.zzmw();
                    if (this.zzadH.zzadB != null) {
                        this.zzadH.zzadB.onConnected(zzmw);
                    }
                    return true;
                }
                Log.e("GmsClient", "service descriptor mismatch: " + this.zzadH.zzfB() + " vs. " + interfaceDescriptor);
                return false;
            } catch (RemoteException e) {
                Log.w("GmsClient", "service probably died");
                return false;
            }
        }
    }

    protected final class zzh extends com.google.android.gms.common.internal.zzj$com.google.android.gms.common.internal.zzj.zza {
        final /* synthetic */ zzj zzadH;

        public zzh(zzj com_google_android_gms_common_internal_zzj) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
            super(com_google_android_gms_common_internal_zzj, 0, null);
        }

        protected void zzi(ConnectionResult connectionResult) {
            this.zzadH.zzadw.zza(connectionResult);
            this.zzadH.onConnectionFailed(connectionResult);
        }

        protected boolean zzoD() {
            this.zzadH.zzadw.zza(ConnectionResult.zzYi);
            return true;
        }
    }

    protected final class zzi extends com.google.android.gms.common.internal.zzj$com.google.android.gms.common.internal.zzj.zza {
        final /* synthetic */ zzj zzadH;

        public zzi(zzj com_google_android_gms_common_internal_zzj, int i, Bundle bundle) {
            this.zzadH = com_google_android_gms_common_internal_zzj;
            super(com_google_android_gms_common_internal_zzj, i, bundle);
        }

        protected void zzi(ConnectionResult connectionResult) {
            this.zzadH.zzadw.zzb(connectionResult);
            this.zzadH.onConnectionFailed(connectionResult);
        }

        protected boolean zzoD() {
            this.zzadH.zzadw.zzb(ConnectionResult.zzYi);
            return true;
        }
    }

    static {
        zzadF = new String[]{"service_esmobile", "service_googleme"};
    }

    protected zzj(Context context, Looper looper, int i, zzf com_google_android_gms_common_internal_zzf, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzl.zzak(context), GoogleApiAvailability.getInstance(), i, com_google_android_gms_common_internal_zzf, (ConnectionCallbacks) zzx.zzv(connectionCallbacks), (OnConnectionFailedListener) zzx.zzv(onConnectionFailedListener));
    }

    protected zzj(Context context, Looper looper, zzl com_google_android_gms_common_internal_zzl, GoogleApiAvailability googleApiAvailability, int i, zzf com_google_android_gms_common_internal_zzf, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this.zzpc = new Object();
        this.zzady = new ArrayList();
        this.zzadA = 1;
        this.zzadE = new AtomicInteger(0);
        this.mContext = (Context) zzx.zzb((Object) context, (Object) "Context must not be null");
        this.zzYV = (Looper) zzx.zzb((Object) looper, (Object) "Looper must not be null");
        this.zzadu = (zzl) zzx.zzb((Object) com_google_android_gms_common_internal_zzl, (Object) "Supervisor must not be null");
        this.zzZi = (GoogleApiAvailability) zzx.zzb((Object) googleApiAvailability, (Object) "API availability must not be null");
        this.mHandler = new zzb(this, looper);
        this.zzadD = i;
        this.zzZH = (zzf) zzx.zzv(com_google_android_gms_common_internal_zzf);
        this.zzOY = com_google_android_gms_common_internal_zzf.getAccount();
        this.zzZp = zzb(com_google_android_gms_common_internal_zzf.zzoj());
        this.zzadB = connectionCallbacks;
        this.zzadC = onConnectionFailedListener;
    }

    private boolean zza(int i, int i2, T t) {
        boolean z;
        synchronized (this.zzpc) {
            if (this.zzadA != i) {
                z = false;
            } else {
                zzb(i2, t);
                z = true;
            }
        }
        return z;
    }

    private Set<Scope> zzb(Set<Scope> set) {
        Set<Scope> zza = zza((Set) set);
        if (zza == null) {
            return zza;
        }
        for (Scope contains : zza) {
            if (!set.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        return zza;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzb(int r5, T r6) {
        /*
        r4 = this;
        r0 = 1;
        r1 = 0;
        r2 = 3;
        if (r5 != r2) goto L_0x001d;
    L_0x0005:
        r3 = r0;
    L_0x0006:
        if (r6 == 0) goto L_0x001f;
    L_0x0008:
        r2 = r0;
    L_0x0009:
        if (r3 != r2) goto L_0x0021;
    L_0x000b:
        com.google.android.gms.common.internal.zzx.zzZ(r0);
        r1 = r4.zzpc;
        monitor-enter(r1);
        r4.zzadA = r5;	 Catch:{ all -> 0x0027 }
        r4.zzadx = r6;	 Catch:{ all -> 0x0027 }
        r4.zzc(r5, r6);	 Catch:{ all -> 0x0027 }
        switch(r5) {
            case 1: goto L_0x002e;
            case 2: goto L_0x0023;
            case 3: goto L_0x002a;
            default: goto L_0x001b;
        };	 Catch:{ all -> 0x0027 }
    L_0x001b:
        monitor-exit(r1);	 Catch:{ all -> 0x0027 }
        return;
    L_0x001d:
        r3 = r1;
        goto L_0x0006;
    L_0x001f:
        r2 = r1;
        goto L_0x0009;
    L_0x0021:
        r0 = r1;
        goto L_0x000b;
    L_0x0023:
        r4.zzov();	 Catch:{ all -> 0x0027 }
        goto L_0x001b;
    L_0x0027:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0027 }
        throw r0;
    L_0x002a:
        r4.zzou();	 Catch:{ all -> 0x0027 }
        goto L_0x001b;
    L_0x002e:
        r4.zzow();	 Catch:{ all -> 0x0027 }
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzj.zzb(int, android.os.IInterface):void");
    }

    private void zzov() {
        if (this.zzadz != null) {
            Log.e("GmsClient", "Calling connect() while still connected, missing disconnect() for " + zzfA());
            this.zzadu.zzb(zzfA(), this.zzadz, zzot());
            this.zzadE.incrementAndGet();
        }
        this.zzadz = new zze(this, this.zzadE.get());
        if (!this.zzadu.zza(zzfA(), this.zzadz, zzot())) {
            Log.e("GmsClient", "unable to connect to service: " + zzfA());
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzadE.get(), 9));
        }
    }

    private void zzow() {
        if (this.zzadz != null) {
            this.zzadu.zzb(zzfA(), this.zzadz, zzot());
            this.zzadz = null;
        }
    }

    public void disconnect() {
        this.zzadE.incrementAndGet();
        synchronized (this.zzady) {
            int size = this.zzady.size();
            for (int i = 0; i < size; i++) {
                ((zzc) this.zzady.get(i)).zzoG();
            }
            this.zzady.clear();
        }
        zzb(1, null);
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        synchronized (this.zzpc) {
            int i = this.zzadA;
            IInterface iInterface = this.zzadx;
        }
        writer.append(prefix).append("mConnectState=");
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
                writer.print("DISCONNECTED");
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                writer.print("CONNECTING");
                break;
            case TTSConst.TTSUNICODE /*3*/:
                writer.print("CONNECTED");
                break;
            case TTSConst.TTSXML /*4*/:
                writer.print("DISCONNECTING");
                break;
            default:
                writer.print("UNKNOWN");
                break;
        }
        writer.append(" mService=");
        if (iInterface == null) {
            writer.println("null");
        } else {
            writer.append(zzfB()).append("@").println(Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzadA == 3;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzadA == 2;
        }
        return z;
    }

    protected void onConnectionFailed(ConnectionResult result) {
    }

    protected void onConnectionSuspended(int cause) {
    }

    protected abstract T zzV(IBinder iBinder);

    protected Set<Scope> zza(Set<Scope> set) {
        return set;
    }

    protected void zza(int i, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, i2, -1, new zzi(this, i, bundle)));
    }

    protected void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, -1, new zzg(this, i, iBinder, bundle)));
    }

    public void zza(com.google.android.gms.common.api.GoogleApiClient.zza com_google_android_gms_common_api_GoogleApiClient_zza) {
        this.zzadw = (com.google.android.gms.common.api.GoogleApiClient.zza) zzx.zzb((Object) com_google_android_gms_common_api_GoogleApiClient_zza, (Object) "Connection progress callbacks cannot be null.");
        zzb(2, null);
    }

    public void zza(zzp com_google_android_gms_common_internal_zzp) {
        try {
            this.zzadv.zza(new zzd(this, this.zzadE.get()), new ValidateAccountRequest(com_google_android_gms_common_internal_zzp, (Scope[]) this.zzZp.toArray(new Scope[this.zzZp.size()]), this.mContext.getPackageName(), zzoB()));
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "service died");
            zzbz(1);
        } catch (Throwable e2) {
            Log.w("GmsClient", "Remote exception occurred", e2);
        }
    }

    public void zza(zzp com_google_android_gms_common_internal_zzp, Set<Scope> set) {
        try {
            GetServiceRequest zzg = new GetServiceRequest(this.zzadD).zzck(this.mContext.getPackageName()).zzg(zzli());
            if (set != null) {
                zzg.zzd(set);
            }
            if (zzlm()) {
                zzg.zzb(zzog()).zzc(com_google_android_gms_common_internal_zzp);
            } else if (zzoC()) {
                zzg.zzb(this.zzOY);
            }
            this.zzadv.zza(new zzd(this, this.zzadE.get()), zzg);
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "service died");
            zzbz(1);
        } catch (Throwable e2) {
            Log.w("GmsClient", "Remote exception occurred", e2);
        }
    }

    protected void zzbA(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, i, -1, new zzh(this)));
    }

    public void zzbz(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, this.zzadE.get(), i));
    }

    protected void zzc(int i, T t) {
    }

    protected abstract String zzfA();

    protected abstract String zzfB();

    protected Bundle zzli() {
        return new Bundle();
    }

    public boolean zzlm() {
        return false;
    }

    public Bundle zzmw() {
        return null;
    }

    public final T zzoA() throws DeadObjectException {
        T t;
        synchronized (this.zzpc) {
            if (this.zzadA == 4) {
                throw new DeadObjectException();
            }
            zzoz();
            zzx.zza(this.zzadx != null, "Client is connected but service is null");
            t = this.zzadx;
        }
        return t;
    }

    protected Bundle zzoB() {
        return null;
    }

    public boolean zzoC() {
        return false;
    }

    public final Account zzog() {
        return this.zzOY != null ? this.zzOY : new Account("<<default account>>", "com.google");
    }

    protected final String zzot() {
        return this.zzZH.zzom();
    }

    protected void zzou() {
    }

    public void zzox() {
        int isGooglePlayServicesAvailable = this.zzZi.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            zzb(1, null);
            this.zzadw = new zzf(this);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzadE.get(), isGooglePlayServicesAvailable));
            return;
        }
        zza(new zzf(this));
    }

    protected final void zzoz() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }
}
