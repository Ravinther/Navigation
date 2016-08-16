package com.sygic.aura.map.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.helper.interfaces.TrafficProgressSegmentsListener;
import com.sygic.aura.resources.ResourceManager;
import java.util.ArrayList;
import java.util.List;

public class TrafficProgressBar extends ProgressBar implements RouteEventsListener, TrafficProgressSegmentsListener {
    private Rect mClipRect;
    private Paint mPaint;
    private RectF mRectF;
    private final List<Segment> mSegments;

    private class Segment {
        private final int color;
        private final float end;
        private final float start;

        private Segment(float start, float end, int color) {
            int width = TrafficProgressBar.this.mClipRect != null ? TrafficProgressBar.this.mClipRect.width() : 0;
            this.start = ((float) width) * start;
            this.end = ((float) width) * end;
            this.color = color;
        }
    }

    public TrafficProgressBar(Context context) {
        super(context);
        this.mSegments = new ArrayList();
    }

    public TrafficProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSegments = new ArrayList();
    }

    public TrafficProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSegments = new ArrayList();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            MapEventsReceiver.registerTrafficSegmentsListener(this);
            RouteEventsReceiver.registerRouteEventsListener(this);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MapEventsReceiver.unregisterTrafficSegmentsListener(this);
        RouteEventsReceiver.unregisterRouteEventsListener(this);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        if (this.mClipRect == null) {
            this.mClipRect = new Rect();
        }
        if (canvas.getClipBounds(this.mClipRect)) {
            float progressEnd = ((float) getProgress()) * ((float) (this.mClipRect.width() / getMax()));
            synchronized (this) {
                if (this.mRectF == null) {
                    this.mRectF = new RectF(this.mClipRect);
                } else {
                    this.mRectF.set(this.mClipRect);
                }
                for (Segment segment : this.mSegments) {
                    if (progressEnd < segment.end) {
                        this.mRectF.left = progressEnd > segment.start ? progressEnd : segment.start;
                        this.mRectF.right = segment.end;
                        this.mPaint.setColor(ResourceManager.getTrafficColor(getContext(), segment.color));
                        canvas.drawRoundRect(this.mRectF, 0.5f, 0.5f, this.mPaint);
                    }
                }
            }
        }
    }

    public void onTrafficSegmentsComputed(ArrayList<Float> segmentsArray, ArrayList<Integer> colorsArray) {
        Float[] segments = (Float[]) segmentsArray.toArray(new Float[segmentsArray.size()]);
        Integer[] colors = (Integer[]) colorsArray.toArray(new Integer[colorsArray.size()]);
        List<Segment> tmpList = new ArrayList(colors.length);
        for (int i = 0; i < colors.length; i++) {
            tmpList.add(new Segment(segments[i * 2].floatValue(), segments[(i * 2) + 1].floatValue(), colors[i].intValue(), null));
        }
        synchronized (this) {
            this.mSegments.clear();
            this.mSegments.addAll(tmpList);
        }
        postInvalidate();
    }

    public synchronized void onStartComputingProgress() {
        this.mSegments.clear();
    }

    public void onFinishComputingProgress() {
    }

    public void onRouteComputeFinishedAll() {
    }
}
