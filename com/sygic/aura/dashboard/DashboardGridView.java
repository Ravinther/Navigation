package com.sygic.aura.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import com.sygic.aura.dashboard.interfaces.ScrollingStrategyInterface;

public class DashboardGridView extends SpanVariableGridView implements OnTouchListener {
    private int mCurrentPosition;
    private Paint mDividerPaint;
    protected ScrollingStrategyInterface mScrollingStrategy;

    public DashboardGridView(Context context) {
        super(context);
        this.mCurrentPosition = -1;
        this.mScrollingStrategy = null;
        initialize(context);
    }

    public DashboardGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCurrentPosition = -1;
        this.mScrollingStrategy = null;
        initialize(context);
    }

    public DashboardGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCurrentPosition = -1;
        this.mScrollingStrategy = null;
        initialize(context);
    }

    protected void initialize(Context context) {
        this.mDividerPaint = new Paint();
        this.mDividerPaint.setColor(context.getResources().getColor(2131558400));
        setOnTouchListener(this);
        setChildrenDrawingOrderEnabled(true);
    }

    public void setEnabled(boolean enabled) {
        if (isEnabled() != enabled) {
            super.setEnabled(enabled);
            BaseAdapter adapter = getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        if (this.mCurrentPosition == -1) {
            return i;
        }
        if (i == childCount - 1) {
            return this.mCurrentPosition;
        }
        if (i >= this.mCurrentPosition) {
            return i + 1;
        }
        return i;
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
        int right = child.getRight();
        int bottom = child.getBottom();
        canvas.drawLine((float) right, (float) child.getTop(), (float) right, (float) bottom, this.mDividerPaint);
        canvas.drawLine((float) child.getLeft(), (float) (bottom - 1), (float) right, (float) (bottom - 1), this.mDividerPaint);
        return ret;
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void setScrollingStrategy(ScrollingStrategyInterface scrollingStrategy) {
        this.mScrollingStrategy = scrollingStrategy;
    }

    public void setScrollButtons(View prev, View next) {
    }

    public void fullScroll(View container) {
    }
}
