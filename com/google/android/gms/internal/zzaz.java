package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzdv.zzd;
import com.google.android.gms.internal.zzij.zza;
import com.google.android.gms.internal.zzij.zzc;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzaz implements OnGlobalLayoutListener, OnScrollChangedListener {
    private boolean zzpB;
    private final Object zzpc;
    private zzib zzqE;
    private final Context zzqO;
    private final WeakReference<zzhj> zzqQ;
    private WeakReference<ViewTreeObserver> zzqR;
    private final WeakReference<View> zzqS;
    private final zzax zzqT;
    private final zzdv zzqU;
    private final zzd zzqV;
    private boolean zzqW;
    private final WindowManager zzqX;
    private final PowerManager zzqY;
    private final KeyguardManager zzqZ;
    private zzba zzra;
    private boolean zzrb;
    private boolean zzrc;
    private boolean zzrd;
    private boolean zzre;
    private BroadcastReceiver zzrf;
    private final HashSet<zzaw> zzrg;
    private final zzdg zzrh;
    private final zzdg zzri;
    private final zzdg zzrj;

    /* renamed from: com.google.android.gms.internal.zzaz.1 */
    class C08491 implements zzc<zzbe> {
        final /* synthetic */ JSONObject zzrk;
        final /* synthetic */ zzaz zzrl;

        C08491(zzaz com_google_android_gms_internal_zzaz, JSONObject jSONObject) {
            this.zzrl = com_google_android_gms_internal_zzaz;
            this.zzrk = jSONObject;
        }

        public void zzb(zzbe com_google_android_gms_internal_zzbe) {
            this.zzrl.zza(this.zzrk);
        }

        public /* synthetic */ void zzc(Object obj) {
            zzb((zzbe) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.2 */
    class C08502 implements zza {
        final /* synthetic */ zzaz zzrl;

        C08502(zzaz com_google_android_gms_internal_zzaz) {
            this.zzrl = com_google_android_gms_internal_zzaz;
        }

        public void run() {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.3 */
    class C08513 implements zzc<zzbe> {
        final /* synthetic */ zzaz zzrl;

        C08513(zzaz com_google_android_gms_internal_zzaz) {
            this.zzrl = com_google_android_gms_internal_zzaz;
        }

        public void zzb(zzbe com_google_android_gms_internal_zzbe) {
            this.zzrl.zzqW = true;
            this.zzrl.zza(com_google_android_gms_internal_zzbe);
            this.zzrl.zzbZ();
            this.zzrl.zzh(false);
        }

        public /* synthetic */ void zzc(Object obj) {
            zzb((zzbe) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.4 */
    class C08524 implements zza {
        final /* synthetic */ zzaz zzrl;

        C08524(zzaz com_google_android_gms_internal_zzaz) {
            this.zzrl = com_google_android_gms_internal_zzaz;
        }

        public void run() {
            this.zzrl.destroy();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.5 */
    class C08535 extends BroadcastReceiver {
        final /* synthetic */ zzaz zzrl;

        C08535(zzaz com_google_android_gms_internal_zzaz) {
            this.zzrl = com_google_android_gms_internal_zzaz;
        }

        public void onReceive(Context context, Intent intent) {
            this.zzrl.zzh(false);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.6 */
    class C08546 implements zzdg {
        final /* synthetic */ zzaz zzrl;

        C08546(zzaz com_google_android_gms_internal_zzaz) {
            this.zzrl = com_google_android_gms_internal_zzaz;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            if (this.zzrl.zzb((Map) map)) {
                this.zzrl.zza(com_google_android_gms_internal_zzip.getWebView(), (Map) map);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.7 */
    class C08557 implements zzdg {
        final /* synthetic */ zzaz zzrl;

        C08557(zzaz com_google_android_gms_internal_zzaz) {
            this.zzrl = com_google_android_gms_internal_zzaz;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            if (this.zzrl.zzb((Map) map)) {
                zzb.zzaC("Received request to untrack: " + this.zzrl.zzqT.zzbX());
                this.zzrl.destroy();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.8 */
    class C08568 implements zzdg {
        final /* synthetic */ zzaz zzrl;

        C08568(zzaz com_google_android_gms_internal_zzaz) {
            this.zzrl = com_google_android_gms_internal_zzaz;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            if (this.zzrl.zzb((Map) map) && map.containsKey("isVisible")) {
                boolean z = "1".equals(map.get("isVisible")) || "true".equals(map.get("isVisible"));
                this.zzrl.zzg(Boolean.valueOf(z).booleanValue());
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.9 */
    class C08579 implements zzc<zzbe> {
        final /* synthetic */ zzaz zzrl;
        final /* synthetic */ JSONObject zzrm;

        C08579(zzaz com_google_android_gms_internal_zzaz, JSONObject jSONObject) {
            this.zzrl = com_google_android_gms_internal_zzaz;
            this.zzrm = jSONObject;
        }

        public void zzb(zzbe com_google_android_gms_internal_zzbe) {
            com_google_android_gms_internal_zzbe.zza("AFMA_updateActiveView", this.zzrm);
        }

        public /* synthetic */ void zzc(Object obj) {
            zzb((zzbe) obj);
        }
    }

    public zzaz(AdSizeParcel adSizeParcel, zzhj com_google_android_gms_internal_zzhj, VersionInfoParcel versionInfoParcel, View view, zzdv com_google_android_gms_internal_zzdv) {
        this.zzpc = new Object();
        this.zzpB = false;
        this.zzrc = false;
        this.zzrg = new HashSet();
        this.zzrh = new C08546(this);
        this.zzri = new C08557(this);
        this.zzrj = new C08568(this);
        this.zzqU = com_google_android_gms_internal_zzdv;
        this.zzqQ = new WeakReference(com_google_android_gms_internal_zzhj);
        this.zzqS = new WeakReference(view);
        this.zzqR = new WeakReference(null);
        this.zzrd = true;
        this.zzqE = new zzib(200);
        this.zzqT = new zzax(UUID.randomUUID().toString(), versionInfoParcel, adSizeParcel.zzsG, com_google_android_gms_internal_zzhj.zzGF, com_google_android_gms_internal_zzhj.zzbY());
        this.zzqV = this.zzqU.zzdL();
        this.zzqX = (WindowManager) view.getContext().getSystemService("window");
        this.zzqY = (PowerManager) view.getContext().getApplicationContext().getSystemService("power");
        this.zzqZ = (KeyguardManager) view.getContext().getSystemService("keyguard");
        this.zzqO = view.getContext().getApplicationContext();
        try {
            this.zzqV.zza(new C08491(this, zzd(view)), new C08502(this));
        } catch (JSONException e) {
        } catch (Throwable e2) {
            zzb.zzb("Failure while processing active view data.", e2);
        }
        this.zzqV.zza(new C08513(this), new C08524(this));
        zzb.zzaC("Tracking ad unit: " + this.zzqT.zzbX());
    }

    protected void destroy() {
        synchronized (this.zzpc) {
            zzcf();
            zzca();
            this.zzrd = false;
            zzcc();
            this.zzqV.release();
        }
    }

    boolean isScreenOn() {
        return this.zzqY.isScreenOn();
    }

    public void onGlobalLayout() {
        zzh(false);
    }

    public void onScrollChanged() {
        zzh(true);
    }

    public void pause() {
        synchronized (this.zzpc) {
            this.zzpB = true;
            zzh(false);
        }
    }

    public void resume() {
        synchronized (this.zzpc) {
            this.zzpB = false;
            zzh(false);
        }
    }

    public void stop() {
        synchronized (this.zzpc) {
            this.zzrc = true;
            zzh(false);
        }
    }

    protected int zza(int i, DisplayMetrics displayMetrics) {
        return (int) (((float) i) / displayMetrics.density);
    }

    protected void zza(View view, Map<String, String> map) {
        zzh(false);
    }

    public void zza(zzaw com_google_android_gms_internal_zzaw) {
        this.zzrg.add(com_google_android_gms_internal_zzaw);
    }

    public void zza(zzba com_google_android_gms_internal_zzba) {
        synchronized (this.zzpc) {
            this.zzra = com_google_android_gms_internal_zzba;
        }
    }

    protected void zza(zzbe com_google_android_gms_internal_zzbe) {
        com_google_android_gms_internal_zzbe.zza("/updateActiveView", this.zzrh);
        com_google_android_gms_internal_zzbe.zza("/untrackActiveViewUnit", this.zzri);
        com_google_android_gms_internal_zzbe.zza("/visibilityChanged", this.zzrj);
    }

    protected void zza(JSONObject jSONObject) {
        try {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONArray.put(jSONObject);
            jSONObject2.put("units", jSONArray);
            this.zzqV.zza(new C08579(this, jSONObject2), new zzij.zzb());
        } catch (Throwable th) {
            zzb.zzb("Skipping active view message.", th);
        }
    }

    protected boolean zzb(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        String str = (String) map.get("hashCode");
        boolean z = !TextUtils.isEmpty(str) && str.equals(this.zzqT.zzbX());
        return z;
    }

    protected void zzbZ() {
        synchronized (this.zzpc) {
            if (this.zzrf != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.zzrf = new C08535(this);
            this.zzqO.registerReceiver(this.zzrf, intentFilter);
        }
    }

    protected void zzca() {
        synchronized (this.zzpc) {
            if (this.zzrf != null) {
                this.zzqO.unregisterReceiver(this.zzrf);
                this.zzrf = null;
            }
        }
    }

    public void zzcb() {
        synchronized (this.zzpc) {
            if (this.zzrd) {
                this.zzre = true;
                try {
                    zza(zzch());
                } catch (Throwable e) {
                    zzb.zzb("JSON failure while processing active view data.", e);
                } catch (Throwable e2) {
                    zzb.zzb("Failure while processing active view data.", e2);
                }
                zzb.zzaC("Untracking ad unit: " + this.zzqT.zzbX());
            }
        }
    }

    protected void zzcc() {
        if (this.zzra != null) {
            this.zzra.zza(this);
        }
    }

    public boolean zzcd() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzrd;
        }
        return z;
    }

    protected void zzce() {
        View view = (View) this.zzqS.get();
        if (view != null) {
            ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzqR.get();
            ViewTreeObserver viewTreeObserver2 = view.getViewTreeObserver();
            if (viewTreeObserver2 != viewTreeObserver) {
                zzcf();
                if (!this.zzrb || (viewTreeObserver != null && viewTreeObserver.isAlive())) {
                    this.zzrb = true;
                    viewTreeObserver2.addOnScrollChangedListener(this);
                    viewTreeObserver2.addOnGlobalLayoutListener(this);
                }
                this.zzqR = new WeakReference(viewTreeObserver2);
            }
        }
    }

    protected void zzcf() {
        ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzqR.get();
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnScrollChangedListener(this);
            viewTreeObserver.removeGlobalOnLayoutListener(this);
        }
    }

    protected JSONObject zzcg() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.zzqT.zzbV()).put("activeViewJSON", this.zzqT.zzbW()).put("timestamp", zzp.zzbB().elapsedRealtime()).put("adFormat", this.zzqT.zzbU()).put("hashCode", this.zzqT.zzbX()).put("isMraid", this.zzqT.zzbY());
        return jSONObject;
    }

    protected JSONObject zzch() throws JSONException {
        JSONObject zzcg = zzcg();
        zzcg.put("doneReasonCode", "u");
        return zzcg;
    }

    protected JSONObject zzd(View view) throws JSONException {
        boolean isAttachedToWindow = zzp.zzbz().isAttachedToWindow(view);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        try {
            view.getLocationOnScreen(iArr);
            view.getLocationInWindow(iArr2);
        } catch (Throwable e) {
            zzb.zzb("Failure getting view location.", e);
        }
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        rect2.right = this.zzqX.getDefaultDisplay().getWidth();
        rect2.bottom = this.zzqX.getDefaultDisplay().getHeight();
        Rect rect3 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect3, null);
        Rect rect4 = new Rect();
        boolean localVisibleRect = view.getLocalVisibleRect(rect4);
        Rect rect5 = new Rect();
        view.getHitRect(rect5);
        JSONObject zzcg = zzcg();
        zzcg.put("windowVisibility", view.getWindowVisibility()).put("isStopped", this.zzrc).put("isPaused", this.zzpB).put("isAttachedToWindow", isAttachedToWindow).put("viewBox", new JSONObject().put("top", zza(rect2.top, displayMetrics)).put("bottom", zza(rect2.bottom, displayMetrics)).put("left", zza(rect2.left, displayMetrics)).put("right", zza(rect2.right, displayMetrics))).put("adBox", new JSONObject().put("top", zza(rect.top, displayMetrics)).put("bottom", zza(rect.bottom, displayMetrics)).put("left", zza(rect.left, displayMetrics)).put("right", zza(rect.right, displayMetrics))).put("globalVisibleBox", new JSONObject().put("top", zza(rect3.top, displayMetrics)).put("bottom", zza(rect3.bottom, displayMetrics)).put("left", zza(rect3.left, displayMetrics)).put("right", zza(rect3.right, displayMetrics))).put("globalVisibleBoxVisible", globalVisibleRect).put("localVisibleBox", new JSONObject().put("top", zza(rect4.top, displayMetrics)).put("bottom", zza(rect4.bottom, displayMetrics)).put("left", zza(rect4.left, displayMetrics)).put("right", zza(rect4.right, displayMetrics))).put("localVisibleBoxVisible", localVisibleRect).put("hitBox", new JSONObject().put("top", zza(rect5.top, displayMetrics)).put("bottom", zza(rect5.bottom, displayMetrics)).put("left", zza(rect5.left, displayMetrics)).put("right", zza(rect5.right, displayMetrics))).put("screenDensity", (double) displayMetrics.density).put("isVisible", zze(view));
        return zzcg;
    }

    protected boolean zze(View view) {
        return view.getVisibility() == 0 && view.isShown() && isScreenOn() && (!this.zzqZ.inKeyguardRestrictedInputMode() || zzp.zzbx().zzgq());
    }

    protected void zzg(boolean z) {
        Iterator it = this.zzrg.iterator();
        while (it.hasNext()) {
            ((zzaw) it.next()).zza(this, z);
        }
    }

    protected void zzh(boolean z) {
        Throwable e;
        synchronized (this.zzpc) {
            if (!this.zzqW || !this.zzrd) {
            } else if (!z || this.zzqE.tryAcquire()) {
                View view = (View) this.zzqS.get();
                Object obj = (view == null || ((zzhj) this.zzqQ.get()) == null) ? 1 : null;
                if (obj != null) {
                    zzcb();
                    return;
                }
                try {
                    zza(zzd(view));
                } catch (JSONException e2) {
                    e = e2;
                    zzb.zza("Active view update failed.", e);
                    zzce();
                    zzcc();
                } catch (RuntimeException e3) {
                    e = e3;
                    zzb.zza("Active view update failed.", e);
                    zzce();
                    zzcc();
                }
                zzce();
                zzcc();
            }
        }
    }
}
