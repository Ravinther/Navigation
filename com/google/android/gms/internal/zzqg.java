package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import java.util.HashSet;
import java.util.Set;

public class zzqg {
    private static zzqg zzaTp;
    private Context mContext;
    private boolean mStarted;
    private final Set<zza> zzaTq;
    private TagManager zzaTr;
    private zzqf zzpo;

    public interface zza {
        void zzbo();
    }

    /* renamed from: com.google.android.gms.internal.zzqg.1 */
    class C09791 implements ResultCallback<ContainerHolder> {
        final /* synthetic */ zzqg zzaTs;

        C09791(zzqg com_google_android_gms_internal_zzqg) {
            this.zzaTs = com_google_android_gms_internal_zzqg;
        }

        public /* synthetic */ void onResult(Result x0) {
            zza((ContainerHolder) x0);
        }

        public void zza(ContainerHolder containerHolder) {
            this.zzaTs.zzpo = new zzqe(this.zzaTs.mContext, containerHolder.getStatus().isSuccess() ? containerHolder.getContainer() : null, this.zzaTs.zzBn()).zzBh();
            this.zzaTs.zzgK();
        }
    }

    zzqg(Context context, TagManager tagManager) {
        this.zzaTq = new HashSet();
        this.zzaTr = null;
        this.mContext = context;
        this.zzaTr = tagManager;
    }

    public static zzqg zzaR(Context context) {
        zzx.zzv(context);
        if (zzaTp == null) {
            synchronized (zzqg.class) {
                if (zzaTp == null) {
                    zzaTp = new zzqg(context, TagManager.getInstance(context.getApplicationContext()));
                }
            }
        }
        return zzaTp;
    }

    private void zzgK() {
        synchronized (this) {
            for (zza zzbo : this.zzaTq) {
                zzbo.zzbo();
            }
        }
    }

    public void start() throws IllegalStateException {
        synchronized (this) {
            if (this.mStarted) {
                throw new IllegalStateException("Method start() has already been called");
            } else if (this.zzpo == null) {
                throw new IllegalStateException("No settings configured");
            } else {
                this.mStarted = true;
                this.zzaTr.zzc(this.zzpo.zzBj(), -1, "admob").setResultCallback(new C09791(this));
            }
        }
    }

    public zzqf zzBn() {
        zzqf com_google_android_gms_internal_zzqf;
        synchronized (this) {
            com_google_android_gms_internal_zzqf = this.zzpo;
        }
        return com_google_android_gms_internal_zzqf;
    }

    public void zza(zzqf com_google_android_gms_internal_zzqf) {
        synchronized (this) {
            if (this.mStarted) {
                throw new IllegalStateException("Settings can't be changed after TagManager has been started");
            }
            this.zzpo = com_google_android_gms_internal_zzqf;
        }
    }

    public void zza(zza com_google_android_gms_internal_zzqg_zza) {
        synchronized (this) {
            this.zzaTq.add(com_google_android_gms_internal_zzqg_zza);
        }
    }
}
