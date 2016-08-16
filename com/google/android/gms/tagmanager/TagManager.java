package com.google.android.gms.tagmanager;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager zzaSv;
    private final Context mContext;
    private final DataLayer zzaOT;
    private final zzs zzaRp;
    private final zza zzaSs;
    private final zzct zzaSt;
    private final ConcurrentMap<zzo, Boolean> zzaSu;

    /* renamed from: com.google.android.gms.tagmanager.TagManager.1 */
    class C10051 implements zzb {
        final /* synthetic */ TagManager zzaSw;

        C10051(TagManager tagManager) {
            this.zzaSw = tagManager;
        }

        public void zzH(Map<String, Object> map) {
            Object obj = map.get("event");
            if (obj != null) {
                this.zzaSw.zzeU(obj.toString());
            }
        }
    }

    public interface zza {
        zzp zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzs com_google_android_gms_tagmanager_zzs);
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.2 */
    static class C10062 implements zza {
        C10062() {
        }

        public zzp zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzs com_google_android_gms_tagmanager_zzs) {
            return new zzp(context, tagManager, looper, str, i, com_google_android_gms_tagmanager_zzs);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.3 */
    class C10073 implements ComponentCallbacks2 {
        final /* synthetic */ TagManager zzaSw;

        C10073(TagManager tagManager) {
            this.zzaSw = tagManager;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
        }

        public void onTrimMemory(int i) {
            if (i == 20) {
                this.zzaSw.dispatch();
            }
        }
    }

    TagManager(Context context, zza containerHolderLoaderProvider, DataLayer dataLayer, zzct serviceManager) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.zzaSt = serviceManager;
        this.zzaSs = containerHolderLoaderProvider;
        this.zzaSu = new ConcurrentHashMap();
        this.zzaOT = dataLayer;
        this.zzaOT.zza(new C10051(this));
        this.zzaOT.zza(new zzd(this.mContext));
        this.zzaRp = new zzs();
        zzAU();
    }

    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (zzaSv == null) {
                if (context == null) {
                    zzbg.m1447e("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                zzaSv = new TagManager(context, new C10062(), new DataLayer(new zzw(context)), zzcu.zzAP());
            }
            tagManager = zzaSv;
        }
        return tagManager;
    }

    private void zzAU() {
        if (VERSION.SDK_INT >= 14) {
            this.mContext.registerComponentCallbacks(new C10073(this));
        }
    }

    private void zzeU(String str) {
        for (zzo zzew : this.zzaSu.keySet()) {
            zzew.zzew(str);
        }
    }

    public void dispatch() {
        this.zzaSt.dispatch();
    }

    public DataLayer getDataLayer() {
        return this.zzaOT;
    }

    void zza(zzo com_google_android_gms_tagmanager_zzo) {
        this.zzaSu.put(com_google_android_gms_tagmanager_zzo, Boolean.valueOf(true));
    }

    boolean zzb(zzo com_google_android_gms_tagmanager_zzo) {
        return this.zzaSu.remove(com_google_android_gms_tagmanager_zzo) != null;
    }

    public PendingResult<ContainerHolder> zzc(String str, int i, String str2) {
        PendingResult zza = this.zzaSs.zza(this.mContext, this, null, str, i, this.zzaRp);
        zza.load(str2);
        return zza;
    }
}
