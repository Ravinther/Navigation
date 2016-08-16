package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzhj.zza;

@zzgk
public class zzgc extends zzgb {
    private Object zzCK;
    private PopupWindow zzCL;
    private boolean zzCM;

    zzgc(Context context, zza com_google_android_gms_internal_zzhj_zza, zzip com_google_android_gms_internal_zzip, zzga.zza com_google_android_gms_internal_zzga_zza) {
        super(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzga_zza);
        this.zzCK = new Object();
        this.zzCM = false;
    }

    private void zzfq() {
        synchronized (this.zzCK) {
            this.zzCM = true;
            if ((this.mContext instanceof Activity) && ((Activity) this.mContext).isDestroyed()) {
                this.zzCL = null;
            }
            if (this.zzCL != null) {
                if (this.zzCL.isShowing()) {
                    this.zzCL.dismiss();
                }
                this.zzCL = null;
            }
        }
    }

    public void onStop() {
        zzfq();
        super.onStop();
    }

    protected void zzfp() {
        Window window = this.mContext instanceof Activity ? ((Activity) this.mContext).getWindow() : null;
        if (window != null && window.getDecorView() != null && !((Activity) this.mContext).isDestroyed()) {
            View frameLayout = new FrameLayout(this.mContext);
            frameLayout.setLayoutParams(new LayoutParams(-1, -1));
            frameLayout.addView(this.zzoL.getWebView(), -1, -1);
            synchronized (this.zzCK) {
                if (this.zzCM) {
                    return;
                }
                this.zzCL = new PopupWindow(frameLayout, 1, 1, false);
                this.zzCL.setOutsideTouchable(true);
                this.zzCL.setClippingEnabled(false);
                zzb.zzaC("Displaying the 1x1 popup off the screen.");
                try {
                    this.zzCL.showAtLocation(window.getDecorView(), 0, -1, -1);
                } catch (Exception e) {
                    this.zzCL = null;
                }
            }
        }
    }

    protected void zzi(zzhj com_google_android_gms_internal_zzhj) {
        zzfq();
        super.zzi(com_google_android_gms_internal_zzhj);
    }
}
