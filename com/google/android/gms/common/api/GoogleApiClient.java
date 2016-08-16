package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.signin.zzb;
import com.google.android.gms.signin.zzd;
import com.google.android.gms.signin.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface GoogleApiClient {

    public interface ConnectionCallbacks {
        void onConnected(Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener {
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    public static final class Builder {
        private final Context mContext;
        private Account zzOY;
        private String zzQl;
        private Looper zzYV;
        private final Set<Scope> zzYY;
        private int zzYZ;
        private View zzZa;
        private String zzZb;
        private final Map<Api<?>, com.google.android.gms.common.internal.zzf.zza> zzZc;
        private final Map<Api<?>, ApiOptions> zzZd;
        private FragmentActivity zzZe;
        private int zzZf;
        private int zzZg;
        private OnConnectionFailedListener zzZh;
        private GoogleApiAvailability zzZi;
        private com.google.android.gms.common.api.Api.zza<? extends zzd, zze> zzZj;
        private final ArrayList<ConnectionCallbacks> zzZk;
        private final ArrayList<OnConnectionFailedListener> zzZl;
        private com.google.android.gms.signin.zze.zza zzZm;

        /* renamed from: com.google.android.gms.common.api.GoogleApiClient.Builder.1 */
        class C06361 implements Runnable {
            final /* synthetic */ GoogleApiClient zzVc;
            final /* synthetic */ Builder zzZn;

            C06361(Builder builder, GoogleApiClient googleApiClient) {
                this.zzZn = builder;
                this.zzVc = googleApiClient;
            }

            public void run() {
                if (!this.zzZn.zzZe.isFinishing() && !this.zzZn.zzZe.getSupportFragmentManager().isDestroyed()) {
                    this.zzZn.zza(zzp.zzb(this.zzZn.zzZe), this.zzVc);
                }
            }
        }

        public Builder(Context context) {
            this.zzYY = new HashSet();
            this.zzZc = new zzld();
            this.zzZd = new zzld();
            this.zzZf = -1;
            this.zzZg = -1;
            this.zzZi = GoogleApiAvailability.getInstance();
            this.zzZj = zzb.zzQg;
            this.zzZk = new ArrayList();
            this.zzZl = new ArrayList();
            this.zzZm = new com.google.android.gms.signin.zze.zza();
            this.mContext = context;
            this.zzYV = context.getMainLooper();
            this.zzQl = context.getPackageName();
            this.zzZb = context.getClass().getName();
        }

        private void zza(zzp com_google_android_gms_common_api_zzp, GoogleApiClient googleApiClient) {
            com_google_android_gms_common_api_zzp.zza(this.zzZf, googleApiClient, this.zzZh);
        }

        private GoogleApiClient zznj() {
            GoogleApiClient com_google_android_gms_common_api_zzi = new zzi(this.mContext.getApplicationContext(), this.zzYV, zzni(), this.zzZi, this.zzZj, this.zzZd, this.zzZk, this.zzZl, this.zzZf, -1);
            zzp zza = zzp.zza(this.zzZe);
            if (zza == null) {
                new Handler(this.mContext.getMainLooper()).post(new C06361(this, com_google_android_gms_common_api_zzi));
            } else {
                zza(zza, com_google_android_gms_common_api_zzi);
            }
            return com_google_android_gms_common_api_zzi;
        }

        private GoogleApiClient zznk() {
            zzq zzc = zzq.zzc(this.zzZe);
            GoogleApiClient zzbj = zzc.zzbj(this.zzZg);
            if (zzbj == null) {
                zzbj = new zzi(this.mContext.getApplicationContext(), this.zzYV, zzni(), this.zzZi, this.zzZj, this.zzZd, this.zzZk, this.zzZl, -1, this.zzZg);
            }
            zzc.zza(this.zzZg, zzbj, this.zzZh);
            return zzbj;
        }

        public Builder addApi(Api<? extends NotRequiredOptions> api) {
            this.zzZd.put(api, null);
            this.zzYY.addAll(api.zznb().zzl(null));
            return this;
        }

        public Builder addConnectionCallbacks(ConnectionCallbacks listener) {
            this.zzZk.add(listener);
            return this;
        }

        public Builder addOnConnectionFailedListener(OnConnectionFailedListener listener) {
            this.zzZl.add(listener);
            return this;
        }

        public GoogleApiClient build() {
            zzx.zzb(!this.zzZd.isEmpty(), (Object) "must call addApi() to add at least one API");
            return this.zzZf >= 0 ? zznj() : this.zzZg >= 0 ? zznk() : new zzi(this.mContext, this.zzYV, zzni(), this.zzZi, this.zzZj, this.zzZd, this.zzZk, this.zzZl, -1, -1);
        }

        public zzf zzni() {
            return new zzf(this.zzOY, this.zzYY, this.zzZc, this.zzYZ, this.zzZa, this.zzQl, this.zzZb, this.zzZm.zzzr());
        }
    }

    public interface ServerAuthCodeCallbacks {

        public static class CheckResult {
            private boolean zzZo;
            private Set<Scope> zzZp;

            public boolean zznl() {
                return this.zzZo;
            }

            public Set<Scope> zznm() {
                return this.zzZp;
            }
        }

        CheckResult onCheckServerAuthorization(String str, Set<Scope> set);

        boolean onUploadServerAuthCode(String str, String str2);
    }

    public interface zza {
        void zza(ConnectionResult connectionResult);

        void zzb(ConnectionResult connectionResult);
    }

    void connect();

    void disconnect();

    void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    Looper getLooper();

    boolean isConnected();

    boolean isConnecting();

    void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

    void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);

    void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

    void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);

    <C extends Api.zzb> C zza(zzc<C> com_google_android_gms_common_api_Api_zzc_C);

    <A extends Api.zzb, R extends Result, T extends com.google.android.gms.common.api.zzc.zza<R, A>> T zza(T t);

    <A extends Api.zzb, T extends com.google.android.gms.common.api.zzc.zza<? extends Result, A>> T zzb(T t);
}
