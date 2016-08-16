package com.sygic.aura.dashboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import com.sygic.aura.C1090R;
import java.util.LinkedList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class SpanVariableGridView extends AdapterView<BaseAdapter> {
    private BaseAdapter mAdapter;
    private final List<CalculateChildrenPosition> mCalculateChildrenPositionList;
    protected int mColCount;
    private int mControlHeight;
    private boolean mIsTransitionsEnabled;
    private int mItemMargin;
    private TransitionDrawable mItemTransitionDrawable;
    private Runnable mLongPressRunnable;
    private final DataSetObserver mObserver;
    private int mOrientation;
    private boolean mPopulating;
    private final Rect mRect;
    protected int mRowCount;
    private int mTouchStartItemPosition;
    private int mTouchStartX;
    private int mTouchStartY;
    private int mTouchState;

    public interface CalculateChildrenPosition {
        void onCalculatePosition(View view, int i, int i2, int i3);
    }

    /* renamed from: com.sygic.aura.dashboard.SpanVariableGridView.1 */
    class C11551 extends DataSetObserver {
        C11551() {
        }

        public void onChanged() {
            SpanVariableGridView.this.mPopulating = false;
            SpanVariableGridView.this.removeAllViewsInLayout();
            SpanVariableGridView.this.requestLayout();
        }

        public void onInvalidated() {
        }
    }

    /* renamed from: com.sygic.aura.dashboard.SpanVariableGridView.2 */
    class C11562 implements Runnable {
        C11562() {
        }

        public void run() {
            if (SpanVariableGridView.this.mTouchState == 1) {
                int index = SpanVariableGridView.this.pointToPosition(-1, SpanVariableGridView.this.mTouchStartX, SpanVariableGridView.this.mTouchStartY);
                if (index != -1 && index == SpanVariableGridView.this.mTouchStartItemPosition) {
                    SpanVariableGridView.this.longClickChild(index);
                    SpanVariableGridView.this.mTouchState = 2;
                }
            }
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        private static final int[] LAYOUT_ATTRS;
        public int column;
        long id;
        public int position;
        public int row;
        public int span;

        static {
            LAYOUT_ATTRS = new int[]{16843085};
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams other) {
            super(other);
            this.span = 1;
            this.position = -1;
            this.row = -1;
            this.column = -1;
            this.id = -1;
            setupWidthAndHeight();
        }

        private void setupWidthAndHeight() {
            if (this.width != -1) {
                this.width = -1;
            }
            if (this.height == -1) {
                this.height = -2;
            }
        }
    }

    public SpanVariableGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mColCount = 2;
        this.mRowCount = -1;
        this.mItemMargin = 0;
        this.mOrientation = 1;
        this.mControlHeight = 0;
        this.mRect = new Rect();
        this.mPopulating = false;
        this.mTouchState = 0;
        this.mAdapter = null;
        this.mIsTransitionsEnabled = true;
        this.mItemTransitionDrawable = null;
        this.mCalculateChildrenPositionList = new LinkedList();
        this.mObserver = new C11551();
        initialize(attrs);
    }

    public SpanVariableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mColCount = 2;
        this.mRowCount = -1;
        this.mItemMargin = 0;
        this.mOrientation = 1;
        this.mControlHeight = 0;
        this.mRect = new Rect();
        this.mPopulating = false;
        this.mTouchState = 0;
        this.mAdapter = null;
        this.mIsTransitionsEnabled = true;
        this.mItemTransitionDrawable = null;
        this.mCalculateChildrenPositionList = new LinkedList();
        this.mObserver = new C11551();
        initialize(attrs);
    }

    public SpanVariableGridView(Context context) {
        super(context);
        this.mColCount = 2;
        this.mRowCount = -1;
        this.mItemMargin = 0;
        this.mOrientation = 1;
        this.mControlHeight = 0;
        this.mRect = new Rect();
        this.mPopulating = false;
        this.mTouchState = 0;
        this.mAdapter = null;
        this.mIsTransitionsEnabled = true;
        this.mItemTransitionDrawable = null;
        this.mCalculateChildrenPositionList = new LinkedList();
        this.mObserver = new C11551();
    }

    public int getColumnCount() {
        return this.mColCount;
    }

    private void initialize(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C1090R.styleable.SpanVariableGridView);
            try {
                this.mColCount = a.getInteger(0, 2);
                this.mRowCount = a.getInteger(1, -1);
                this.mItemMargin = a.getDimensionPixelSize(2, 0);
                this.mOrientation = a.getInteger(3, 1);
            } finally {
                a.recycle();
            }
        } else {
            this.mColCount = 2;
            this.mRowCount = -1;
            this.mItemMargin = 0;
            this.mOrientation = 1;
        }
        if (this.mColCount < 1) {
            throw new IllegalArgumentException("Argument numColumns is lower than 1.");
        }
    }

    private boolean isHorizontal() {
        return this.mRowCount > 0 && this.mOrientation == 0;
    }

    public boolean addCalculateChildrenPositionListener(CalculateChildrenPosition listener) {
        return this.mCalculateChildrenPositionList.add(listener);
    }

    public boolean removeCalculateChildrenPositionListener(CalculateChildrenPosition listener) {
        return this.mCalculateChildrenPositionList.remove(listener);
    }

    public BaseAdapter getAdapter() {
        return this.mAdapter;
    }

    public View getSelectedView() {
        return null;
    }

    public void setAdapter(BaseAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = adapter;
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(this.mObserver);
        }
        removeAllViewsInLayout();
        requestLayout();
    }

    public void setSelection(int position) {
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.mAdapter != null) {
            layoutChildrens(-1, false);
        }
    }

    protected void performDragAndDropSwapping(int from, int to) {
        this.mPopulating = true;
        if (from != to) {
            View removedChild = getChildAt(from);
            removedChild.clearAnimation();
            removeViewInLayout(removedChild);
            addViewInLayout(removedChild, to, removedChild.getLayoutParams());
        }
        this.mControlHeight = measureChildrens(false);
        layoutChildrens(to, true);
        this.mPopulating = false;
    }

    public void requestLayout() {
        if (!this.mPopulating) {
            super.requestLayout();
        }
    }

    protected Rect layoutChildrens(int draggedChild, boolean animate) {
        int row = 0;
        int rowHeight = 0;
        int fullHeight = this.mItemMargin;
        int width = getMeasuredWidth() - (this.mItemMargin * 2);
        int height = getMeasuredHeight() - (this.mItemMargin * 2);
        int i = this.mColCount;
        int i2 = this.mItemMargin;
        int colWidth = (width - ((r0 - 1) * r0)) / this.mColCount;
        i = this.mRowCount;
        i2 = this.mItemMargin;
        int colHeight = (height - ((r0 - 1) * r0)) / this.mRowCount;
        Rect draggedChildRect = null;
        int position = 0;
        while (true) {
            if (position >= this.mAdapter.getCount()) {
                return draggedChildRect;
            }
            int width_;
            int height_;
            int left_;
            int top_;
            int right_;
            int bottom_;
            View childView = getChildAt(position);
            Point point = new Point(childView.getLeft(), childView.getTop());
            LayoutParams lp = (LayoutParams) childView.getLayoutParams();
            int column = lp.column;
            if (row != lp.row) {
                fullHeight += this.mItemMargin + rowHeight;
                rowHeight = 0;
            }
            rowHeight = Math.max(rowHeight, childView.getMeasuredHeight());
            row = lp.row;
            if (column == -1) {
                width_ = width;
            } else {
                i = lp.span;
                i2 = this.mItemMargin;
                width_ = (r0 * (r0 + colWidth)) - this.mItemMargin;
            }
            if (row == -1) {
                height_ = height;
            } else {
                i = lp.span;
                i2 = this.mItemMargin;
                height_ = (r0 * (r0 + colHeight)) - this.mItemMargin;
            }
            if (isHorizontal()) {
                i2 = this.mItemMargin;
                if (column == -1) {
                    i = 0;
                } else {
                    i = (this.mItemMargin + colWidth) * column;
                }
                left_ = i2 + i;
                i2 = this.mItemMargin;
                if (row == -1) {
                    i = 0;
                } else {
                    i = (this.mItemMargin + colHeight) * row;
                }
                top_ = i2 + i;
                right_ = left_ + width_;
                bottom_ = top_ + height_;
                measureChildren(childView, width_, height_);
            } else {
                i2 = this.mItemMargin;
                if (column == -1) {
                    i = 0;
                } else {
                    i = (this.mItemMargin + colWidth) * column;
                }
                left_ = i2 + i;
                top_ = fullHeight;
                right_ = left_ + width_;
                bottom_ = top_ + childView.getMeasuredHeight();
                measureChildren(childView, width_, lp.height);
            }
            if (position != draggedChild) {
                Point now = new Point(left_, top_);
                childView.layout(left_, top_, right_, bottom_);
                if (animate) {
                    translateChild(childView, point, now);
                }
            } else {
                draggedChildRect = new Rect(left_, top_, right_, bottom_);
            }
            position++;
        }
    }

    protected final void translateChild(View v, Point prev, Point now) {
        TranslateAnimation translate = new TranslateAnimation(0, (float) ((-now.x) + prev.x), 0, 0.0f, 0, (float) ((-now.y) + prev.y), 0, 0.0f);
        translate.setInterpolator(new AccelerateInterpolator(4.0f));
        translate.setDuration(350);
        translate.setFillEnabled(false);
        translate.setFillAfter(false);
        v.clearAnimation();
        v.startAnimation(translate);
    }

    protected void measureChildren(View child, int width, int height) {
        int heightSpec;
        int widthSpec;
        if (height == -2) {
            heightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        } else {
            heightSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
        }
        if (width == -2) {
            widthSpec = MeasureSpec.makeMeasureSpec(0, 0);
        } else {
            widthSpec = MeasureSpec.makeMeasureSpec(width, 1073741824);
        }
        child.measure(widthSpec, heightSpec);
    }

    protected int pointToPosition(int draggedChild, int x, int y) {
        for (int index = 0; index < getChildCount(); index++) {
            if (index != draggedChild) {
                getChildAt(index).getHitRect(this.mRect);
                if (this.mRect.contains(x, y)) {
                    return index;
                }
            }
        }
        return -1;
    }

    private void clickChildAt() {
        int index = pointToPosition(-1, this.mTouchStartX, this.mTouchStartY);
        if (index != -1 && index == this.mTouchStartItemPosition) {
            int position = index;
            performItemClick(getChildAt(index), position, this.mAdapter.getItemId(position));
        }
    }

    private void longClickChild(int index) {
        View itemView = getChildAt(index);
        long id = this.mAdapter.getItemId(index);
        OnItemLongClickListener listener = getOnItemLongClickListener();
        if (listener != null) {
            listener.onItemLongClick(this, itemView, index, id);
        }
    }

    private void startLongPressCheck() {
        if (this.mLongPressRunnable == null) {
            this.mLongPressRunnable = new C11562();
        }
        postDelayed(this.mLongPressRunnable, (long) ViewConfiguration.getLongPressTimeout());
    }

    protected void startLongClickTransition(View clickedChild) {
        if (clickedChild != null && this.mItemTransitionDrawable == null && this.mIsTransitionsEnabled && (clickedChild.getBackground().getCurrent() instanceof TransitionDrawable)) {
            this.mItemTransitionDrawable = (TransitionDrawable) clickedChild.getBackground().getCurrent();
            this.mItemTransitionDrawable.startTransition(ViewConfiguration.getLongPressTimeout());
        }
    }

    protected void resetLongClickTransition() {
        if (this.mItemTransitionDrawable != null) {
            this.mItemTransitionDrawable.resetTransition();
            this.mItemTransitionDrawable = null;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode() || this.mAdapter == null) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 100);
        } else if (isHorizontal()) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
            this.mControlHeight = measureChildrens(false);
        } else {
            this.mControlHeight = measureChildrens(false);
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), this.mControlHeight);
        }
    }

    private void fireCalculateChildrenPositionEvent(View view, int position, int row, int column) {
        for (CalculateChildrenPosition listener : this.mCalculateChildrenPositionList) {
            listener.onCalculatePosition(view, position, row, column);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int measureChildrens(boolean r15) {
        /*
        r14 = this;
        r13 = -2;
        r9 = -1;
        r6 = 0;
        r1 = 0;
        r7 = 0;
        r8 = 0;
        r2 = r14.mItemMargin;
        r5 = 0;
    L_0x0009:
        r10 = r14.mAdapter;
        r10 = r10.getCount();
        if (r5 >= r10) goto L_0x00ba;
    L_0x0011:
        r0 = r14.getChildAt(r5);
        if (r0 != 0) goto L_0x0035;
    L_0x0017:
        r10 = r14.mAdapter;
        r11 = 0;
        r0 = r10.getView(r5, r11, r14);
        r4 = r0.getLayoutParams();
        r4 = (com.sygic.aura.dashboard.SpanVariableGridView.LayoutParams) r4;
        if (r4 != 0) goto L_0x0030;
    L_0x0026:
        r4 = new com.sygic.aura.dashboard.SpanVariableGridView$LayoutParams;
        r10 = new android.view.ViewGroup$LayoutParams;
        r10.<init>(r13, r13);
        r4.<init>(r10);
    L_0x0030:
        if (r15 != 0) goto L_0x0035;
    L_0x0032:
        r14.addViewInLayout(r0, r9, r4);
    L_0x0035:
        r3 = r0.getLayoutParams();
        r3 = (com.sygic.aura.dashboard.SpanVariableGridView.LayoutParams) r3;
        r10 = r3.width;
        r11 = r3.height;
        r14.measureChildren(r0, r10, r11);
        r3.position = r5;
        r10 = r3.span;
        r8 = r8 + r10;
    L_0x0047:
        r10 = r14.isHorizontal();
        if (r10 == 0) goto L_0x007e;
    L_0x004d:
        r10 = r14.mRowCount;
        if (r8 > r10) goto L_0x0068;
    L_0x0051:
        r3.column = r1;
        r10 = r3.span;
        r11 = r14.mRowCount;
        if (r10 != r11) goto L_0x005a;
    L_0x0059:
        r6 = r9;
    L_0x005a:
        r3.row = r6;
        r6 = r8;
        if (r15 == 0) goto L_0x0076;
    L_0x005f:
        r10 = r3.position;
        r11 = r3.row;
        r12 = r3.column;
        r14.fireCalculateChildrenPositionEvent(r0, r10, r11, r12);
    L_0x0068:
        r10 = r14.mRowCount;
        if (r8 < r10) goto L_0x007b;
    L_0x006c:
        r1 = r1 + 1;
        r6 = 0;
        r10 = r14.mRowCount;
        if (r8 == r10) goto L_0x007a;
    L_0x0073:
        r8 = r3.span;
        goto L_0x0047;
    L_0x0076:
        r0.setLayoutParams(r3);
        goto L_0x0068;
    L_0x007a:
        r8 = 0;
    L_0x007b:
        r5 = r5 + 1;
        goto L_0x0009;
    L_0x007e:
        r10 = r14.mColCount;
        if (r8 > r10) goto L_0x007b;
    L_0x0082:
        r3.row = r6;
        r10 = r3.span;
        r11 = r14.mColCount;
        if (r10 != r11) goto L_0x008b;
    L_0x008a:
        r1 = r9;
    L_0x008b:
        r3.column = r1;
        r1 = r8;
        if (r15 == 0) goto L_0x00b4;
    L_0x0090:
        r10 = r3.position;
        r11 = r3.row;
        r12 = r3.column;
        r14.fireCalculateChildrenPositionEvent(r0, r10, r11, r12);
    L_0x0099:
        r10 = r14.mItemMargin;
        r11 = r0.getMeasuredHeight();
        r10 = r10 + r11;
        r7 = java.lang.Math.max(r7, r10);
        r10 = r14.mColCount;
        if (r8 < r10) goto L_0x007b;
    L_0x00a8:
        r2 = r2 + r7;
        r6 = r6 + 1;
        r1 = 0;
        r7 = 0;
        r10 = r14.mColCount;
        if (r8 == r10) goto L_0x00b8;
    L_0x00b1:
        r8 = r3.span;
        goto L_0x0047;
    L_0x00b4:
        r0.setLayoutParams(r3);
        goto L_0x0099;
    L_0x00b8:
        r8 = 0;
        goto L_0x007b;
    L_0x00ba:
        r2 = r2 + r7;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.dashboard.SpanVariableGridView.measureChildrens(boolean):int");
    }

    public void requestCalculateChildrenPositions() {
        measureChildrens(true);
    }

    private void startTouch() {
        this.mTouchStartItemPosition = pointToPosition(-1, this.mTouchStartX, this.mTouchStartY);
        startLongPressCheck();
        this.mTouchState = 1;
    }

    public void childDrawableStateChanged(View child) {
        startLongClickTransition(child);
        super.childDrawableStateChanged(child);
    }

    private void endTouch() {
        resetLongClickTransition();
        removeCallbacks(this.mLongPressRunnable);
        this.mTouchState = 0;
    }

    public void setIsTransitionsEnabled(boolean enabled) {
        this.mIsTransitionsEnabled = enabled;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        boolean result = super.dispatchTouchEvent(event);
        if (isEnabled() && getChildCount() != 0) {
            switch (event.getAction()) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    this.mTouchStartX = (int) eventX;
                    this.mTouchStartY = (int) eventY;
                    startTouch();
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    if (this.mTouchState == 1) {
                        clickChildAt();
                    }
                    endTouch();
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    break;
                default:
                    endTouch();
                    break;
            }
        }
        return result;
    }
}
