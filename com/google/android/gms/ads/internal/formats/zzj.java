package com.google.android.gms.ads.internal.formats;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzcl.zza;
import com.google.android.gms.internal.zzhu;
import com.google.android.gms.internal.zzil;
import com.google.android.gms.internal.zzip;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class zzj extends zza implements OnClickListener, OnTouchListener, OnGlobalLayoutListener, OnScrollChangedListener {
    private final FrameLayout zznY;
    private final Object zzpc;
    private zzh zzvT;
    private final FrameLayout zzwq;
    private final Map<String, WeakReference<View>> zzwr;
    private zzb zzws;
    boolean zzwt;

    /* renamed from: com.google.android.gms.ads.internal.formats.zzj.1 */
    class C05771 implements Runnable {
        final /* synthetic */ zzh zzwu;
        final /* synthetic */ zzj zzwv;

        C05771(zzj com_google_android_gms_ads_internal_formats_zzj, zzh com_google_android_gms_ads_internal_formats_zzh) {
            this.zzwv = com_google_android_gms_ads_internal_formats_zzj;
            this.zzwu = com_google_android_gms_ads_internal_formats_zzh;
        }

        public void run() {
            zzip zzdy = this.zzwu.zzdy();
            if (zzdy != null) {
                this.zzwv.zznY.addView(zzdy.getWebView());
            }
        }
    }

    public zzj(FrameLayout frameLayout, FrameLayout frameLayout2) {
        this.zzpc = new Object();
        this.zzwr = new HashMap();
        this.zzwt = false;
        this.zzwq = frameLayout;
        this.zznY = frameLayout2;
        zzil.zza(this.zzwq, (OnGlobalLayoutListener) this);
        zzil.zza(this.zzwq, (OnScrollChangedListener) this);
        this.zzwq.setOnTouchListener(this);
    }

    int getMeasuredHeight() {
        return this.zzwq.getMeasuredHeight();
    }

    int getMeasuredWidth() {
        return this.zzwq.getMeasuredWidth();
    }

    public void onClick(View view) {
        synchronized (this.zzpc) {
            if (this.zzvT == null) {
                return;
            }
            if (this.zzws == null || !this.zzws.zzdq().equals(view)) {
                this.zzvT.zzb(view, this.zzwr);
            } else {
                this.zzvT.performClick("1007");
            }
        }
    }

    public void onGlobalLayout() {
        synchronized (this.zzpc) {
            if (this.zzwt) {
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                if (!(measuredWidth == 0 || measuredHeight == 0)) {
                    this.zznY.setLayoutParams(new LayoutParams(measuredWidth, measuredHeight));
                    this.zzwt = false;
                }
            }
            if (this.zzvT != null) {
                this.zzvT.zzi(this.zzwq);
            }
        }
    }

    public void onScrollChanged() {
        synchronized (this.zzpc) {
            if (this.zzvT != null) {
                this.zzvT.zzi(this.zzwq);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this.zzpc) {
            if (this.zzvT == null) {
            } else {
                Point zzc = zzc(motionEvent);
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                obtain.setLocation((float) zzc.x, (float) zzc.y);
                this.zzvT.zzb(obtain);
                obtain.recycle();
            }
        }
        return false;
    }

    public zzd zzU(String str) {
        zzd zzx;
        synchronized (this.zzpc) {
            Object obj;
            WeakReference weakReference = (WeakReference) this.zzwr.get(str);
            if (weakReference == null) {
                obj = null;
            } else {
                View view = (View) weakReference.get();
            }
            zzx = zze.zzx(obj);
        }
        return zzx;
    }

    public void zza(String str, zzd com_google_android_gms_dynamic_zzd) {
        View view = (View) zze.zzp(com_google_android_gms_dynamic_zzd);
        synchronized (this.zzpc) {
            if (view == null) {
                this.zzwr.remove(str);
            } else {
                this.zzwr.put(str, new WeakReference(view));
                view.setOnTouchListener(this);
                view.setOnClickListener(this);
            }
        }
    }

    public void zzb(zzd com_google_android_gms_dynamic_zzd) {
        synchronized (this.zzpc) {
            this.zzwt = true;
            zzh com_google_android_gms_ads_internal_formats_zzh = (zzh) zze.zzp(com_google_android_gms_dynamic_zzd);
            if ((this.zzvT instanceof zzg) && ((zzg) this.zzvT).zzdx()) {
                ((zzg) this.zzvT).zzb(com_google_android_gms_ads_internal_formats_zzh);
            } else {
                this.zzvT = com_google_android_gms_ads_internal_formats_zzh;
                if (this.zzvT instanceof zzg) {
                    ((zzg) this.zzvT).zzb(null);
                }
            }
            this.zznY.removeAllViews();
            this.zzws = zzf(com_google_android_gms_ads_internal_formats_zzh);
            if (this.zzws != null) {
                this.zznY.addView(this.zzws);
            }
            zzhu.zzHK.post(new C05771(this, com_google_android_gms_ads_internal_formats_zzh));
            com_google_android_gms_ads_internal_formats_zzh.zzh(this.zzwq);
        }
    }

    Point zzc(MotionEvent motionEvent) {
        int[] iArr = new int[2];
        this.zzwq.getLocationOnScreen(iArr);
        return new Point((int) (motionEvent.getRawX() - ((float) iArr[0])), (int) (motionEvent.getRawY() - ((float) iArr[1])));
    }

    zzb zzf(zzh com_google_android_gms_ads_internal_formats_zzh) {
        return com_google_android_gms_ads_internal_formats_zzh.zza(this);
    }
}
