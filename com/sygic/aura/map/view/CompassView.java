package com.sygic.aura.map.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.actionbar.DrawerIF;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.MapControlsManager;

public class CompassView extends View {
    private final Handler customHandler;
    private int mColorBg;
    private int mColorNorth;
    private int mColorSouth;
    private Paint mPaint;
    private int mVisibility;
    private final CompassThread updateThread;

    /* renamed from: com.sygic.aura.map.view.CompassView.1 */
    class C13621 implements OnClickListener {
        C13621() {
        }

        public void onClick(View v) {
            MapControlsManager.nativeSetWantedRotation(0.0f);
        }
    }

    /* renamed from: com.sygic.aura.map.view.CompassView.2 */
    class C13632 implements Runnable {
        C13632() {
        }

        public void run() {
            while (!CompassView.this.updateThread.mFinished) {
                try {
                    if (CompassView.this.mVisibility == 0) {
                        DrawerIF drawerIF = (DrawerIF) SygicHelper.getFragmentActivityWrapper();
                        if (!(drawerIF == null || drawerIF.isDrawerVisible())) {
                            CompassView.this.postInvalidate();
                        }
                        Thread.sleep(100);
                    } else {
                        synchronized (CompassView.this) {
                            CompassView.this.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private class CompassThread extends Thread {
        private boolean mFinished;

        private CompassThread(Runnable runnable) {
            super(runnable);
            this.mFinished = false;
        }

        public void setFinished(boolean finished) {
            this.mFinished = finished;
        }
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mVisibility = 0;
        this.customHandler = new Handler();
        this.updateThread = new CompassThread(new C13632(), null);
        loadColors();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mVisibility = 0;
        this.customHandler = new Handler();
        this.updateThread = new CompassThread(new C13632(), null);
        loadColors();
    }

    public CompassView(Context context) {
        super(context);
        this.mVisibility = 0;
        this.customHandler = new Handler();
        this.updateThread = new CompassThread(new C13632(), null);
        loadColors();
    }

    private void loadColors() {
        this.mColorSouth = getResources().getColor(2131558560);
        this.mColorNorth = getResources().getColor(2131558559);
        this.mColorBg = getResources().getColor(2131558558);
    }

    @SuppressLint({"NewApi"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (!isInEditMode()) {
            canvas.rotate(MapControlsManager.nativeGetViewRotation(), ((float) getWidth()) * 0.5f, ((float) getHeight()) * 0.5f);
            drawCompass(canvas);
        }
        super.onDraw(canvas);
        canvas.restore();
    }

    private Path drawTriangle(Point a, Point b, Point c) {
        Path path = new Path();
        path.setFillType(FillType.EVEN_ODD);
        path.moveTo((float) a.x, (float) a.y);
        path.lineTo((float) b.x, (float) b.y);
        path.lineTo((float) c.x, (float) c.y);
        path.lineTo((float) a.x, (float) a.y);
        path.close();
        return path;
    }

    private void drawCompass(Canvas canvas) {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
            this.mPaint.setStyle(Style.FILL_AND_STROKE);
            this.mPaint.setAntiAlias(true);
        }
        this.mPaint.setStrokeWidth(4.0f);
        this.mPaint.setColor(this.mColorSouth);
        int height = getHeight() - 8;
        int width = getWidth() - 8;
        int centerDistance = ((int) getResources().getDimension(2131230853)) / 4;
        int centerWidth = (width / 2) + 4;
        int centerHeight = (height / 2) + 4;
        int topHeight = height / 10;
        canvas.drawPath(drawTriangle(new Point(centerWidth - (width / 12), centerHeight + topHeight), new Point((width / 12) + centerWidth, centerHeight + topHeight), new Point(centerWidth, height - topHeight)), this.mPaint);
        Point northA = new Point(centerWidth - (width / 12), centerHeight - topHeight);
        Point northB = new Point((width / 12) + centerWidth, centerHeight - topHeight);
        Point northC = new Point(centerWidth, topHeight + 8);
        this.mPaint.setColor(this.mColorNorth);
        canvas.drawPath(drawTriangle(northA, northB, northC), this.mPaint);
        this.mPaint.setColor(this.mColorBg);
        RectF oval = new RectF();
        oval.left = ((float) centerWidth) - (((float) centerDistance) * 1.5f);
        oval.right = ((float) centerWidth) + (((float) centerDistance) * 1.5f);
        oval.bottom = ((float) centerHeight) - (((float) centerDistance) * 1.5f);
        oval.top = ((float) centerHeight) + (((float) centerDistance) * 1.5f);
        this.mPaint.setStrokeWidth(0.0f);
        canvas.drawOval(oval, this.mPaint);
        this.mPaint.setColor(this.mColorSouth);
        oval = new RectF();
        oval.left = ((float) centerWidth) - (((float) centerDistance) * 0.8f);
        oval.right = ((float) centerWidth) + (((float) centerDistance) * 0.8f);
        oval.bottom = ((float) centerHeight) - (((float) centerDistance) * 0.8f);
        oval.top = ((float) centerHeight) + (((float) centerDistance) * 0.8f);
        canvas.drawOval(oval, this.mPaint);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(new C13621());
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.updateThread.start();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.updateThread.setFinished(true);
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.mVisibility = visibility;
        synchronized (this) {
            notify();
        }
    }
}
