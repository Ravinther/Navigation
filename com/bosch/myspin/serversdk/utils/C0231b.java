package com.bosch.myspin.serversdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bosch.myspin.serversdk.resource.bitmaps.C0177a;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.bosch.myspin.serversdk.utils.b */
public class C0231b {
    private static final LogComponent f417a;
    private static Map<Context, C0231b> f418b;
    private Context f419c;
    private RelativeLayout f420d;
    private TextView f421e;
    private Drawable f422f;
    private boolean f423g;
    private LayoutParams f424h;
    private WindowManager f425i;
    private Handler f426j;
    private Runnable f427k;

    static {
        f417a = LogComponent.UI;
        f418b = new HashMap();
    }

    public static C0231b m359a(Context context) {
        if (context == null) {
            Logger.logWarning(f417a, "ConnectedBaseWindow/getWindowForContext [Given context is null!]");
            return null;
        }
        if (!f418b.containsKey(context)) {
            Logger.logDebug(f417a, "ConnectedBaseWindow/create new ConnectedBaseWindow");
            f418b.put(context, new C0231b(context));
        }
        return (C0231b) f418b.get(context);
    }

    public static void m360b(Context context) {
        if (f418b.containsKey(context)) {
            Logger.logDebug(f417a, "ConnectedBaseWindow/deleteWindowForContext");
            f418b.remove(context);
        }
    }

    @SuppressLint({"NewApi"})
    private C0231b(Context context) {
        this.f427k = new C0232c(this);
        this.f419c = context;
        this.f426j = new Handler();
        this.f420d = new RelativeLayout(context);
        this.f420d.setBackgroundColor(-16777216);
        this.f420d.setGravity(17);
        this.f420d.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.f421e = new TextView(this.f419c);
        this.f421e.setBackgroundColor(-16777216);
        this.f421e.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.f421e.setTextColor(this.f419c.getResources().getColor(17170443));
        this.f421e.setGravity(17);
        this.f422f = new BitmapDrawable(this.f419c.getResources(), C0177a.m140a(this.f419c.getResources(), 0));
        m363g();
        this.f424h = new LayoutParams(99);
        this.f424h.width = -1;
        this.f424h.height = -1;
        this.f424h.flags = 1160;
        this.f424h.screenOrientation = 12;
        this.f424h.screenBrightness = 0.1f;
        this.f424h.buttonBrightness = 0.1f;
        if (VERSION.SDK_INT >= 18) {
            this.f424h.rotationAnimation = 2;
        }
        this.f425i = (WindowManager) this.f419c.getSystemService("window");
    }

    public void m364a() {
        this.f420d.removeAllViews();
    }

    public void m365b() {
        int applyDimension = (int) TypedValue.applyDimension(1, 30.0f, this.f419c.getResources().getDisplayMetrics());
        this.f421e.setTranslationX((float) ((int) (((Math.random() * ((double) ((this.f420d.getWidth() - this.f421e.getWidth()) - (applyDimension * 2)))) - ((double) this.f421e.getLeft())) + ((double) applyDimension))));
        int height = (this.f420d.getHeight() - this.f421e.getHeight()) - (applyDimension * 2);
        this.f421e.setTranslationY((float) ((int) (((double) applyDimension) + ((Math.random() * ((double) height)) - ((double) this.f421e.getTop())))));
    }

    public void m366c() {
        Logger.logDebug(f417a, "ConnectedBaseWindow/show [isShowing=" + this.f423g + "]");
        if (!this.f423g) {
            m363g();
            this.f425i.addView(this.f420d, this.f424h);
            this.f423g = true;
        }
        m361e();
    }

    private void m361e() {
        m362f();
        this.f426j.postDelayed(this.f427k, 10000);
    }

    private void m362f() {
        this.f426j.removeCallbacks(this.f427k);
    }

    private void m363g() {
        double d;
        double d2;
        int i;
        int i2;
        int i3;
        this.f420d.removeAllViews();
        DisplayMetrics displayMetrics = this.f419c.getResources().getDisplayMetrics();
        if (this.f419c.getResources().getConfiguration().orientation == 2) {
            this.f421e.setRotation(-90.0f);
            d = ((double) displayMetrics.widthPixels) * 0.7d;
            d2 = ((double) displayMetrics.heightPixels) * 0.7d;
        } else {
            this.f421e.setRotation(0.0f);
            d = ((double) displayMetrics.heightPixels) * 0.7d;
            d2 = ((double) displayMetrics.widthPixels) * 0.7d;
        }
        int applyDimension = (int) TypedValue.applyDimension(1, 105.0f, displayMetrics);
        int applyDimension2 = (int) TypedValue.applyDimension(1, 228.0f, displayMetrics);
        int applyDimension3 = (int) TypedValue.applyDimension(1, 35.0f, displayMetrics);
        double d3 = d / ((double) applyDimension2);
        if (d3 >= 1.0d) {
            d3 = 1.0d;
        }
        d = d2 / ((double) applyDimension);
        if (d < d3) {
            d3 = d;
        }
        if (d3 < 1.0d) {
            i = (int) (((double) applyDimension2) * d3);
            i2 = (int) (((double) applyDimension) * d3);
            i3 = (int) (((double) applyDimension3) * d3);
        } else {
            i3 = applyDimension3;
            i = applyDimension2;
            i2 = applyDimension;
        }
        this.f421e.setCompoundDrawablePadding(i3);
        this.f422f.setBounds(0, 0, i2, i);
        this.f421e.setCompoundDrawables(null, this.f422f, null, null);
        this.f420d.addView(this.f421e);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m367d() {
        /*
        r5 = this;
        r4 = 0;
        r0 = f417a;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "ConnectedBaseWindow/dismiss [isShowing=";
        r1 = r1.append(r2);
        r2 = r5.f423g;
        r1 = r1.append(r2);
        r2 = "]";
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.bosch.myspin.serversdk.utils.Logger.logDebug(r0, r1);
        r0 = r5.f423g;	 Catch:{ Exception -> 0x0046 }
        if (r0 == 0) goto L_0x0036;
    L_0x0027:
        r0 = r5.f420d;	 Catch:{ Exception -> 0x0046 }
        r0 = r0.isShown();	 Catch:{ Exception -> 0x0046 }
        if (r0 == 0) goto L_0x0036;
    L_0x002f:
        r0 = r5.f425i;	 Catch:{ IllegalArgumentException -> 0x003c }
        r1 = r5.f420d;	 Catch:{ IllegalArgumentException -> 0x003c }
        r0.removeViewImmediate(r1);	 Catch:{ IllegalArgumentException -> 0x003c }
    L_0x0036:
        r5.f423g = r4;
    L_0x0038:
        r5.m362f();
        return;
    L_0x003c:
        r0 = move-exception;
        r1 = f417a;	 Catch:{ Exception -> 0x0046 }
        r2 = "ConnectedBaseWindow/Connected Windows is not showing, can't dismiss it.";
        com.bosch.myspin.serversdk.utils.Logger.logError(r1, r2, r0);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0036;
    L_0x0046:
        r0 = move-exception;
        r1 = f417a;	 Catch:{ all -> 0x006c }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006c }
        r2.<init>();	 Catch:{ all -> 0x006c }
        r3 = "ConnectedBaseWindow/Tried to remove window: ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006c }
        r3 = r5.f420d;	 Catch:{ all -> 0x006c }
        r2 = r2.append(r3);	 Catch:{ all -> 0x006c }
        r3 = " but is not attached!";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006c }
        r2 = r2.toString();	 Catch:{ all -> 0x006c }
        com.bosch.myspin.serversdk.utils.Logger.logError(r1, r2, r0);	 Catch:{ all -> 0x006c }
        r5.f423g = r4;
        goto L_0x0038;
    L_0x006c:
        r0 = move-exception;
        r5.f423g = r4;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bosch.myspin.serversdk.utils.b.d():void");
    }
}
