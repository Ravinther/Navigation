package com.sygic.aura.travelbook;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import com.sygic.aura.resources.Typefaces;
import com.sygic.aura.travelbook.data.TravelbookGraphData;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem.ETravelLogDataType;
import com.sygic.aura.views.font_specials.STextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import loquendo.tts.engine.TTSConst;

public class Graph extends View {
    private final Paint mBorderHorizontalPaint;
    private final Paint mBorderVerticalPaint;
    private TravelbookGraphData mCurrentItem;
    private float mDownX;
    private boolean mDragging;
    private final Paint mGraphDashedLinePaint;
    private final Paint mGraphFillPaint;
    private final Paint mGraphLinePaint;
    private TravelbookGraphData[] mItems;
    private String mNoGraphDataText;
    private int mOffsetX;
    private int mOffsetY;
    private ArrayList<PaintedPath> mPaths;
    private ETravelLogDataType mType;
    private float mValueIntersectionCircleRadius;
    private final RectF mValueRect;
    private final Paint mValueRectPaint;
    private float mValueRoundRectRadius;
    private String mValueText;
    private float mValueTextPadding;
    private final Paint mValueTextPaint;
    private STextView mValueTextView;

    private class PaintedPath extends Path {
        private final Paint mPaint;

        public PaintedPath(Paint paint) {
            this.mPaint = paint;
        }

        public Paint getPaint() {
            return this.mPaint;
        }
    }

    public Graph(Context context) {
        super(context);
        this.mGraphFillPaint = new Paint();
        this.mGraphLinePaint = new Paint();
        this.mGraphDashedLinePaint = new Paint();
        this.mBorderHorizontalPaint = new Paint();
        this.mBorderVerticalPaint = new Paint();
        this.mValueRectPaint = new Paint();
        this.mValueTextPaint = new Paint();
        this.mValueRect = new RectF();
        init();
    }

    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mGraphFillPaint = new Paint();
        this.mGraphLinePaint = new Paint();
        this.mGraphDashedLinePaint = new Paint();
        this.mBorderHorizontalPaint = new Paint();
        this.mBorderVerticalPaint = new Paint();
        this.mValueRectPaint = new Paint();
        this.mValueTextPaint = new Paint();
        this.mValueRect = new RectF();
        init();
    }

    public Graph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mGraphFillPaint = new Paint();
        this.mGraphLinePaint = new Paint();
        this.mGraphDashedLinePaint = new Paint();
        this.mBorderHorizontalPaint = new Paint();
        this.mBorderVerticalPaint = new Paint();
        this.mValueRectPaint = new Paint();
        this.mValueTextPaint = new Paint();
        this.mValueRect = new RectF();
        init();
    }

    private void init() {
        Resources res = getResources();
        this.mGraphFillPaint.setColor(res.getColor(2131558779));
        this.mGraphFillPaint.setAntiAlias(true);
        this.mGraphFillPaint.setStyle(Style.FILL);
        this.mGraphLinePaint.setColor(res.getColor(2131558780));
        this.mGraphLinePaint.setAntiAlias(true);
        this.mGraphLinePaint.setStrokeWidth(res.getDimension(2131231066));
        this.mGraphLinePaint.setStyle(Style.STROKE);
        this.mGraphLinePaint.setStrokeJoin(Join.ROUND);
        this.mGraphDashedLinePaint.setColor(res.getColor(2131558778));
        this.mGraphDashedLinePaint.setAntiAlias(true);
        this.mGraphDashedLinePaint.setStrokeWidth(res.getDimension(2131231066));
        this.mGraphDashedLinePaint.setStyle(Style.STROKE);
        this.mGraphDashedLinePaint.setStrokeJoin(Join.ROUND);
        this.mGraphDashedLinePaint.setPathEffect(new DashPathEffect(new float[]{8.0f, 8.0f}, 0.0f));
        this.mBorderHorizontalPaint.setColor(res.getColor(2131558776));
        this.mBorderHorizontalPaint.setAntiAlias(true);
        this.mBorderHorizontalPaint.setStrokeWidth(res.getDimension(2131231061));
        this.mBorderHorizontalPaint.setStyle(Style.STROKE);
        this.mBorderVerticalPaint.setColor(res.getColor(2131558777));
        this.mBorderVerticalPaint.setAntiAlias(true);
        this.mBorderVerticalPaint.setStrokeWidth(res.getDimension(2131231062));
        this.mBorderVerticalPaint.setStyle(Style.STROKE);
        this.mValueRectPaint.setColor(res.getColor(2131558781));
        this.mValueRectPaint.setAntiAlias(true);
        this.mValueRectPaint.setStrokeWidth(res.getDimension(2131231065));
        this.mValueRectPaint.setStyle(Style.FILL);
        this.mValueTextPaint.setColor(res.getColor(2131558782));
        this.mValueTextPaint.setAntiAlias(true);
        this.mValueTextPaint.setTypeface(Typefaces.getFont(res, 2131166099));
        this.mValueTextPaint.setTextSize(res.getDimension(2131231069));
        this.mValueIntersectionCircleRadius = res.getDimension(2131231064);
        this.mValueRoundRectRadius = res.getDimension(2131231067);
        this.mValueTextPadding = res.getDimension(2131231068);
        this.mValueRect.top = 0.0f;
        this.mValueRect.bottom = this.mValueTextPaint.getTextSize() + (this.mValueTextPadding * 2.0f);
        this.mOffsetX = (int) TypedValue.applyDimension(1, 16.0f, res.getDisplayMetrics());
        this.mOffsetY = (int) ((this.mValueTextPaint.getTextSize() + (this.mValueTextPadding * 2.0f)) + TypedValue.applyDimension(1, 4.0f, res.getDisplayMetrics()));
    }

    public void load(int logIndex, ETravelLogDataType type) {
        setItems(TravelBookManager.getAllGraphData(logIndex, type, getWindow()), type);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mItems == null || this.mItems.length == 0) {
            return false;
        }
        switch (event.getAction()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mDownX = event.getX();
                break;
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSUNICODE /*3*/:
                getParent().requestDisallowInterceptTouchEvent(false);
                this.mDragging = false;
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                float x = event.getX();
                float deltaX = Math.abs(x - this.mDownX);
                if (!this.mDragging && deltaX > 50.0f) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    this.mDragging = true;
                }
                if (this.mDragging) {
                    TravelbookGraphData closestPoint = findClosesItem((int) x);
                    if (!this.mCurrentItem.equals(closestPoint)) {
                        updateValue(closestPoint);
                        this.mCurrentItem = closestPoint;
                        break;
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    private TravelbookGraphData findClosesItem(int x) {
        TravelbookGraphData closestItem = this.mItems[0];
        for (int i = 1; i < this.mItems.length; i++) {
            if (Math.abs(this.mItems[i].getNormalizedPoint().x - x) < Math.abs(closestItem.getNormalizedPoint().x - x)) {
                closestItem = this.mItems[i];
            }
        }
        return closestItem;
    }

    private void updateValue(TravelbookGraphData point) {
        updateValueTexts(point);
        updateValueRect(point);
    }

    private void updateValueTexts(TravelbookGraphData point) {
        this.mValueText = TravelBookManager.nativeFormatValue(this.mType, point.getPoint().y);
        if (this.mValueTextView != null) {
            this.mValueTextView.setCoreText(TravelBookManager.nativeFormatValueX(this.mType, point.getPoint().x));
        }
    }

    private void updateValueRect(TravelbookGraphData point) {
        float paddedWidth = ((float) ((int) this.mValueTextPaint.measureText(this.mValueText))) + (this.mValueTextPadding * 2.0f);
        this.mValueRect.left = clamp(((float) point.getNormalizedPoint().x) - (paddedWidth / 2.0f), 0.0f, ((float) getMeasuredWidth()) - paddedWidth);
        this.mValueRect.right = clamp(((float) point.getNormalizedPoint().x) + (paddedWidth / 2.0f), paddedWidth, (float) getMeasuredWidth());
    }

    private int[] getWindow() {
        return new int[]{this.mOffsetX, this.mOffsetY + ((int) (this.mGraphLinePaint.getStrokeWidth() / 2.0f)), getMeasuredWidth() - (this.mOffsetX * 2), getMeasuredHeight() - this.mOffsetY};
    }

    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    public void setItems(TravelbookGraphData[] items, ETravelLogDataType type) {
        if (items != null && items.length != 0) {
            this.mPaths = new ArrayList();
            PaintedPath backgroundPath = new PaintedPath(this.mGraphFillPaint);
            PaintedPath linePath = new PaintedPath(this.mGraphLinePaint);
            int length = items.length;
            for (int i = 0; i < length; i++) {
                TravelbookGraphData item = items[i];
                if (i == 0) {
                    backgroundPath.moveTo((float) item.getNormalizedPoint().x, (float) item.getNormalizedPoint().y);
                    linePath.moveTo((float) item.getNormalizedPoint().x, (float) item.getNormalizedPoint().y);
                } else {
                    backgroundPath.lineTo((float) item.getNormalizedPoint().x, (float) item.getNormalizedPoint().y);
                    TravelbookGraphData previousItem = items[i - 1];
                    if (previousItem.hasNoSignal()) {
                        this.mPaths.add(linePath);
                        linePath = new PaintedPath(this.mGraphDashedLinePaint);
                        linePath.moveTo((float) previousItem.getNormalizedPoint().x, (float) previousItem.getNormalizedPoint().y);
                        linePath.lineTo((float) item.getNormalizedPoint().x, (float) item.getNormalizedPoint().y);
                        this.mPaths.add(linePath);
                        linePath = new PaintedPath(this.mGraphLinePaint);
                        linePath.moveTo((float) item.getNormalizedPoint().x, (float) item.getNormalizedPoint().y);
                    } else {
                        linePath.lineTo((float) item.getNormalizedPoint().x, (float) item.getNormalizedPoint().y);
                    }
                }
                if (i == length - 1) {
                    backgroundPath.lineTo((float) item.getNormalizedPoint().x, (float) getMeasuredHeight());
                    backgroundPath.lineTo((float) items[0].getNormalizedPoint().x, (float) getMeasuredHeight());
                    backgroundPath.close();
                }
            }
            this.mPaths.add(linePath);
            this.mPaths.add(backgroundPath);
            Collections.reverse(this.mPaths);
            this.mCurrentItem = items[items.length / 10];
            this.mItems = items;
            this.mType = type;
            updateValue(this.mCurrentItem);
            invalidate();
        }
    }

    public ArrayList<PaintedPath> getPaths() {
        return this.mPaths;
    }

    public void setXValueTextView(STextView textView) {
        this.mValueTextView = textView;
    }

    public void setNoDataMessage(String noDataMessage) {
        this.mNoGraphDataText = noDataMessage;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPaths != null && !this.mPaths.isEmpty()) {
            Iterator it = this.mPaths.iterator();
            while (it.hasNext()) {
                PaintedPath path = (PaintedPath) it.next();
                canvas.drawPath(path, path.getPaint());
            }
        } else if (this.mNoGraphDataText != null) {
            canvas.drawText(this.mNoGraphDataText, ((float) (getMeasuredWidth() / 2)) - (this.mValueTextPaint.measureText(this.mNoGraphDataText) / 2.0f), ((float) (getMeasuredHeight() / 2)) - (this.mValueRectPaint.getTextSize() / 2.0f), this.mValueTextPaint);
        }
        if (this.mCurrentItem != null) {
            canvas.drawCircle((float) this.mCurrentItem.getNormalizedPoint().x, (float) this.mCurrentItem.getNormalizedPoint().y, this.mValueIntersectionCircleRadius, this.mValueRectPaint);
            canvas.drawLine((float) this.mCurrentItem.getNormalizedPoint().x, 0.0f, (float) this.mCurrentItem.getNormalizedPoint().x, (float) getMeasuredHeight(), this.mValueRectPaint);
            canvas.drawRoundRect(this.mValueRect, this.mValueRoundRectRadius, this.mValueRoundRectRadius, this.mValueRectPaint);
            canvas.drawText(this.mValueText, this.mValueRect.left + this.mValueTextPadding, this.mValueRect.bottom - this.mValueTextPadding, this.mValueTextPaint);
        }
        canvas.drawLine(0.0f, (float) (this.mOffsetY - 1), (float) getMeasuredWidth(), (float) (this.mOffsetY - 1), this.mBorderHorizontalPaint);
        canvas.drawLine(0.0f, (float) (getMeasuredHeight() - 1), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - 1), this.mBorderHorizontalPaint);
        canvas.drawLine((float) this.mOffsetX, (float) this.mOffsetY, (float) this.mOffsetX, (float) getMeasuredHeight(), this.mBorderVerticalPaint);
        canvas.drawLine((float) (getMeasuredWidth() - this.mOffsetX), (float) this.mOffsetY, (float) (getMeasuredWidth() - this.mOffsetX), (float) getMeasuredHeight(), this.mBorderVerticalPaint);
    }
}
