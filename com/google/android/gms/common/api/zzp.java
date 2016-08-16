package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzx;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzp extends Fragment implements OnCancelListener {
    private boolean mStarted;
    private boolean zzaaJ;
    private int zzaaK;
    private ConnectionResult zzaaL;
    private final Handler zzaaM;
    private final SparseArray<zza> zzaaN;

    private class zza implements OnConnectionFailedListener {
        public final int zzaaO;
        public final GoogleApiClient zzaaP;
        public final OnConnectionFailedListener zzaaQ;
        final /* synthetic */ zzp zzaaR;

        public zza(zzp com_google_android_gms_common_api_zzp, int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.zzaaR = com_google_android_gms_common_api_zzp;
            this.zzaaO = i;
            this.zzaaP = googleApiClient;
            this.zzaaQ = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            writer.append(prefix).append("GoogleApiClient #").print(this.zzaaO);
            writer.println(":");
            this.zzaaP.dump(prefix + "  ", fd, writer, args);
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.zzaaR.zzaaM.post(new zzb(this.zzaaR, this.zzaaO, result));
        }

        public void zznK() {
            this.zzaaP.unregisterConnectionFailedListener(this);
            this.zzaaP.disconnect();
        }
    }

    private class zzb implements Runnable {
        final /* synthetic */ zzp zzaaR;
        private final int zzaaS;
        private final ConnectionResult zzaaT;

        public zzb(zzp com_google_android_gms_common_api_zzp, int i, ConnectionResult connectionResult) {
            this.zzaaR = com_google_android_gms_common_api_zzp;
            this.zzaaS = i;
            this.zzaaT = connectionResult;
        }

        public void run() {
            if (this.zzaaR.mStarted && !this.zzaaR.zzaaJ) {
                this.zzaaR.zzaaJ = true;
                this.zzaaR.zzaaK = this.zzaaS;
                this.zzaaR.zzaaL = this.zzaaT;
                if (this.zzaaT.hasResolution()) {
                    try {
                        this.zzaaT.startResolutionForResult(this.zzaaR.getActivity(), ((this.zzaaR.getActivity().getSupportFragmentManager().getFragments().indexOf(this.zzaaR) + 1) << 16) + 1);
                    } catch (SendIntentException e) {
                        this.zzaaR.zznJ();
                    }
                } else if (GooglePlayServicesUtil.isUserRecoverableError(this.zzaaT.getErrorCode())) {
                    GooglePlayServicesUtil.showErrorDialogFragment(this.zzaaT.getErrorCode(), this.zzaaR.getActivity(), this.zzaaR, 2, this.zzaaR);
                } else {
                    this.zzaaR.zza(this.zzaaS, this.zzaaT);
                }
            }
        }
    }

    public zzp() {
        this.zzaaK = -1;
        this.zzaaM = new Handler(Looper.getMainLooper());
        this.zzaaN = new SparseArray();
    }

    public static zzp zza(FragmentActivity fragmentActivity) {
        zzx.zzch("Must be called from main thread of process");
        try {
            zzp com_google_android_gms_common_api_zzp = (zzp) fragmentActivity.getSupportFragmentManager().findFragmentByTag("GmsSupportLifecycleFragment");
            return (com_google_android_gms_common_api_zzp == null || com_google_android_gms_common_api_zzp.isRemoving()) ? null : com_google_android_gms_common_api_zzp;
        } catch (Throwable e) {
            throw new IllegalStateException("Fragment with tag GmsSupportLifecycleFragment is not a SupportLifecycleFragment", e);
        }
    }

    private void zza(int i, ConnectionResult connectionResult) {
        Log.w("GmsSupportLifecycleFragment", "Unresolved error while connecting client. Stopping auto-manage.");
        zza com_google_android_gms_common_api_zzp_zza = (zza) this.zzaaN.get(i);
        if (com_google_android_gms_common_api_zzp_zza != null) {
            zzbi(i);
            OnConnectionFailedListener onConnectionFailedListener = com_google_android_gms_common_api_zzp_zza.zzaaQ;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
        zznJ();
    }

    public static zzp zzb(FragmentActivity fragmentActivity) {
        zzp zza = zza(fragmentActivity);
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        if (zza != null) {
            return zza;
        }
        Fragment com_google_android_gms_common_api_zzp = new zzp();
        supportFragmentManager.beginTransaction().add(com_google_android_gms_common_api_zzp, "GmsSupportLifecycleFragment").commitAllowingStateLoss();
        supportFragmentManager.executePendingTransactions();
        return com_google_android_gms_common_api_zzp;
    }

    private void zznJ() {
        this.zzaaJ = false;
        this.zzaaK = -1;
        this.zzaaL = null;
        for (int i = 0; i < this.zzaaN.size(); i++) {
            ((zza) this.zzaaN.valueAt(i)).zzaaP.connect();
        }
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        for (int i = 0; i < this.zzaaN.size(); i++) {
            ((zza) this.zzaaN.valueAt(i)).dump(prefix, fd, writer, args);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r4, int r5, android.content.Intent r6) {
        /*
        r3 = this;
        r0 = 1;
        r1 = 0;
        switch(r4) {
            case 1: goto L_0x0017;
            case 2: goto L_0x000c;
            default: goto L_0x0005;
        };
    L_0x0005:
        r0 = r1;
    L_0x0006:
        if (r0 == 0) goto L_0x001b;
    L_0x0008:
        r3.zznJ();
    L_0x000b:
        return;
    L_0x000c:
        r2 = r3.getActivity();
        r2 = com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable(r2);
        if (r2 != 0) goto L_0x0005;
    L_0x0016:
        goto L_0x0006;
    L_0x0017:
        r2 = -1;
        if (r5 != r2) goto L_0x0005;
    L_0x001a:
        goto L_0x0006;
    L_0x001b:
        r0 = r3.zzaaK;
        r1 = r3.zzaaL;
        r3.zza(r0, r1);
        goto L_0x000b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.zzp.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(this.zzaaK, new ConnectionResult(13, null));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.zzaaJ = savedInstanceState.getBoolean("resolving_error", false);
            this.zzaaK = savedInstanceState.getInt("failed_client_id", -1);
            if (this.zzaaK >= 0) {
                this.zzaaL = new ConnectionResult(savedInstanceState.getInt("failed_status"), (PendingIntent) savedInstanceState.getParcelable("failed_resolution"));
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("resolving_error", this.zzaaJ);
        if (this.zzaaK >= 0) {
            outState.putInt("failed_client_id", this.zzaaK);
            outState.putInt("failed_status", this.zzaaL.getErrorCode());
            outState.putParcelable("failed_resolution", this.zzaaL.getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
        if (!this.zzaaJ) {
            for (int i = 0; i < this.zzaaN.size(); i++) {
                ((zza) this.zzaaN.valueAt(i)).zzaaP.connect();
            }
        }
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
        for (int i = 0; i < this.zzaaN.size(); i++) {
            ((zza) this.zzaaN.valueAt(i)).zzaaP.disconnect();
        }
    }

    public void zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        zzx.zzb((Object) googleApiClient, (Object) "GoogleApiClient instance cannot be null");
        zzx.zza(this.zzaaN.indexOfKey(i) < 0, "Already managing a GoogleApiClient with id " + i);
        this.zzaaN.put(i, new zza(this, i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && !this.zzaaJ) {
            googleApiClient.connect();
        }
    }

    public void zzbi(int i) {
        zza com_google_android_gms_common_api_zzp_zza = (zza) this.zzaaN.get(i);
        this.zzaaN.remove(i);
        if (com_google_android_gms_common_api_zzp_zza != null) {
            com_google_android_gms_common_api_zzp_zza.zznK();
        }
    }
}
