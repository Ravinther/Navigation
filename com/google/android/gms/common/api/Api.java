package com.google.android.gms.common.api;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final String mName;
    private final zzc<?> zzXW;
    private final zza<?, O> zzYL;
    private final zze<?, O> zzYM;
    private final zzf<?> zzYN;

    public interface zzb {
        void disconnect();

        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

        boolean isConnected();

        void zza(com.google.android.gms.common.api.GoogleApiClient.zza com_google_android_gms_common_api_GoogleApiClient_zza);

        void zza(zzp com_google_android_gms_common_internal_zzp);

        void zza(zzp com_google_android_gms_common_internal_zzp, Set<Scope> set);

        boolean zzlm();
    }

    public static abstract class zza<T extends zzb, O> {
        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public abstract T zza(Context context, Looper looper, com.google.android.gms.common.internal.zzf com_google_android_gms_common_internal_zzf, O o, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener);

        public List<Scope> zzl(O o) {
            return Collections.emptyList();
        }
    }

    public interface ApiOptions {

        public interface HasOptions extends ApiOptions {
        }

        public interface NotRequiredOptions extends ApiOptions {
        }

        public static final class NoOptions implements NotRequiredOptions {
            private NoOptions() {
            }
        }

        public interface Optional extends HasOptions, NotRequiredOptions {
        }
    }

    public static final class zzc<C extends zzb> {
    }

    public interface zzd<T extends IInterface> {
        T zzV(IBinder iBinder);

        void zza(int i, T t);

        String zzfA();

        String zzfB();
    }

    public interface zze<T extends zzd, O> {
        T zzm(O o);

        int zznf();
    }

    public static final class zzf<C extends zzd> {
    }

    public <C extends zzb> Api(String name, zza<C, O> clientBuilder, zzc<C> clientKey) {
        zzx.zzb((Object) clientBuilder, (Object) "Cannot construct an Api with a null ClientBuilder");
        zzx.zzb((Object) clientKey, (Object) "Cannot construct an Api with a null ClientKey");
        this.mName = name;
        this.zzYL = clientBuilder;
        this.zzYM = null;
        this.zzXW = clientKey;
        this.zzYN = null;
    }

    public String getName() {
        return this.mName;
    }

    public zza<?, O> zznb() {
        zzx.zza(this.zzYL != null, "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.zzYL;
    }

    public zze<?, O> zznc() {
        zzx.zza(this.zzYM != null, "This API was constructed with a ClientBuilder. Use getClientBuilder");
        return this.zzYM;
    }

    public zzc<?> zznd() {
        zzx.zza(this.zzXW != null, "This API was constructed with a SimpleClientKey. Use getSimpleClientKey");
        return this.zzXW;
    }

    public boolean zzne() {
        return this.zzYN != null;
    }
}
