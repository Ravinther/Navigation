package com.bosch.myspin.serversdk.service.client.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.RelativeLayout.LayoutParams;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

public class GlImageView extends View implements OnLayoutChangeListener {
    private static final LogComponent f244a;
    private Bitmap f245b;
    private Paint f246c;
    private SurfaceView f247d;
    private Matrix f248e;
    private C0200a f249f;
    private int f250g;
    private int f251h;

    /* renamed from: com.bosch.myspin.serversdk.service.client.opengl.GlImageView.a */
    public interface C0200a {
        void m275a();
    }

    static {
        f244a = LogComponent.UI;
    }

    protected GlImageView(Context context, SurfaceView surfaceView) {
        super(context);
        this.f246c = new Paint();
        this.f248e = new Matrix();
        if (context == null || surfaceView == null) {
            throw new IllegalArgumentException("Parameter is null Context: " + context + " SurfaceView: " + surfaceView);
        }
        this.f247d = surfaceView;
        if (surfaceView.getLayoutParams() == null) {
            setLayoutParams(new LayoutParams(0, 0));
        } else {
            setLayoutParams(surfaceView.getLayoutParams());
        }
        this.f247d.addOnLayoutChangeListener(this);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f245b == null || canvas == null) {
            Logger.logWarning(f244a, "GlImageView/Parameter is null mContentBitmap: " + this.f245b + " Canvas: " + canvas);
        } else if (this.f245b.getHeight() != 0 || this.f245b.getWidth() != 0) {
            System.currentTimeMillis();
            canvas.drawBitmap(this.f245b, this.f248e, this.f246c);
            if (!(this.f249f == null || (this.f250g == getWidth() && this.f251h == getHeight()))) {
                this.f249f.m275a();
            }
            this.f250g = getWidth();
            this.f251h = getHeight();
        }
    }

    public void m276a(Bitmap bitmap) {
        this.f245b = bitmap;
        invalidate();
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        setLeft(i);
        setRight(i3);
        setBottom(i4);
        setTop(i2);
        this.f248e.setScale(1.0f, -1.0f);
        this.f248e.postTranslate(0.0f, (float) i4);
    }

    public void setOnViewSizeChangedListener(C0200a c0200a) {
        this.f249f = c0200a;
    }
}
