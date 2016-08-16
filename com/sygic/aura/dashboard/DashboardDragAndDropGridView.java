package com.sygic.aura.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import loquendo.tts.engine.TTSConst;

public class DashboardDragAndDropGridView extends DashboardGridView implements OnTouchListener {
    private int mCurrentPosition;
    private Runnable mDelayedOnDragRunnable;
    private DragAndDropListener mDragAndDropListener;
    private boolean mDragAndDropStarted;
    private ImageView mDragImageView;
    private int mDragOffsetX;
    private int mDragOffsetY;
    private int mDragPointX;
    private int mDragPointY;
    private int mDragPosition;
    private int mDropPosition;
    private MotionEvent mStartEvent;
    WindowManager mWindowManager;
    LayoutParams mWindowParams;

    /* renamed from: com.sygic.aura.dashboard.DashboardDragAndDropGridView.1 */
    class C11501 implements Runnable {
        final /* synthetic */ int val$tempDropPosition;
        final /* synthetic */ int val$x;
        final /* synthetic */ int val$y;

        C11501(int i, int i2, int i3) {
            this.val$tempDropPosition = i;
            this.val$x = i2;
            this.val$y = i3;
        }

        public void run() {
            DashboardDragAndDropGridView.this.mDragAndDropListener.onDraggingItem(DashboardDragAndDropGridView.this.mCurrentPosition, this.val$tempDropPosition);
            DashboardDragAndDropGridView.this.performDragAndDropSwapping(DashboardDragAndDropGridView.this.mCurrentPosition, this.val$tempDropPosition);
            if (DashboardDragAndDropGridView.this.pointToPosition(this.val$tempDropPosition, this.val$x, this.val$y) == -1) {
                DashboardDragAndDropGridView.this.mCurrentPosition = DashboardDragAndDropGridView.this.mDropPosition = this.val$tempDropPosition;
            }
        }
    }

    /* renamed from: com.sygic.aura.dashboard.DashboardDragAndDropGridView.2 */
    class C11512 implements Runnable {
        final /* synthetic */ View val$container;

        C11512(View view) {
            this.val$container = view;
        }

        public void run() {
            this.val$container.fullScroll(130);
        }
    }

    public interface DragAndDropListener {
        boolean isDragAndDropEnabled(int i);

        void onDragItem(int i);

        void onDraggingItem(int i, int i2);

        void onDropItem(int i, int i2);
    }

    public DashboardDragAndDropGridView(Context context) {
        super(context);
        this.mDragPosition = -1;
        this.mDropPosition = -1;
        this.mCurrentPosition = -1;
        this.mDelayedOnDragRunnable = null;
        this.mWindowManager = null;
        this.mWindowParams = null;
        this.mDragImageView = null;
        this.mDragAndDropStarted = false;
        this.mDragAndDropListener = null;
    }

    public DashboardDragAndDropGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDragPosition = -1;
        this.mDropPosition = -1;
        this.mCurrentPosition = -1;
        this.mDelayedOnDragRunnable = null;
        this.mWindowManager = null;
        this.mWindowParams = null;
        this.mDragImageView = null;
        this.mDragAndDropStarted = false;
        this.mDragAndDropListener = null;
    }

    public DashboardDragAndDropGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDragPosition = -1;
        this.mDropPosition = -1;
        this.mCurrentPosition = -1;
        this.mDelayedOnDragRunnable = null;
        this.mWindowManager = null;
        this.mWindowParams = null;
        this.mDragImageView = null;
        this.mDragAndDropStarted = false;
        this.mDragAndDropListener = null;
    }

    public void startDragAndDrop() {
        this.mDragAndDropStarted = true;
        dispatchTouchEvent(this.mStartEvent);
    }

    public void setDragAndDropListener(DragAndDropListener dragAndDropListener) {
        this.mDragAndDropListener = dragAndDropListener;
    }

    private void destroyDragImageView() {
        if (this.mDragImageView != null) {
            this.mWindowManager.removeView(this.mDragImageView);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) this.mDragImageView.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (!(bitmap == null || bitmap.isRecycled())) {
                    bitmap.recycle();
                }
            }
            this.mDragImageView.setImageDrawable(null);
            this.mDragImageView = null;
        }
    }

    private ImageView createDragImageView(View v, int x, int y) {
        v.destroyDrawingCache();
        v.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(v.getDrawingCache());
        this.mDragPointX = x - v.getLeft();
        this.mDragPointY = y - v.getTop();
        this.mWindowParams = new LayoutParams();
        this.mWindowParams.gravity = 51;
        this.mWindowParams.x = (x - this.mDragPointX) + this.mDragOffsetX;
        this.mWindowParams.y = (y - this.mDragPointY) + this.mDragOffsetY;
        this.mWindowParams.height = -2;
        this.mWindowParams.width = -2;
        this.mWindowParams.flags = 408;
        this.mWindowParams.format = -3;
        this.mWindowParams.windowAnimations = 0;
        ImageView iv = new ImageView(getContext());
        iv.setBackgroundResource(2130837678);
        iv.setImageBitmap(bm);
        this.mWindowManager = (WindowManager) getContext().getSystemService("window");
        this.mWindowManager.addView(iv, this.mWindowParams);
        return iv;
    }

    private void startDrag(int x, int y) {
        View v = getChildAt(this.mDragPosition);
        destroyDragImageView();
        this.mDragImageView = createDragImageView(v, x, y);
        v.setVisibility(4);
        if (this.mDragAndDropListener != null) {
            this.mDragAndDropListener.onDragItem(this.mDragPosition);
        }
    }

    private void onDrop() {
        destroyDragImageView();
        removeCallbacks(this.mDelayedOnDragRunnable);
        View v = getChildAt(this.mCurrentPosition);
        v.setVisibility(0);
        v.clearAnimation();
        if (!(this.mDragAndDropListener == null || this.mDropPosition == -1)) {
            this.mDropPosition = this.mCurrentPosition;
            this.mDragAndDropListener.onDropItem(this.mDragPosition, this.mDropPosition);
        }
        this.mCurrentPosition = -1;
        this.mDropPosition = -1;
        this.mDragPosition = -1;
        this.mDragAndDropStarted = false;
    }

    private void onDrag(int x, int y) {
        if (this.mScrollingStrategy == null || !this.mScrollingStrategy.performScrolling(x, y, this)) {
            int tempDropPosition = pointToPosition(this.mCurrentPosition, x, y);
            if (!(this.mDragAndDropListener == null || this.mDropPosition == tempDropPosition || tempDropPosition == -1)) {
                removeCallbacks(this.mDelayedOnDragRunnable);
                if (this.mDragAndDropListener.isDragAndDropEnabled(tempDropPosition)) {
                    this.mDropPosition = tempDropPosition;
                    this.mDelayedOnDragRunnable = new C11501(tempDropPosition, x, y);
                    postDelayed(this.mDelayedOnDragRunnable, 100);
                } else {
                    this.mDropPosition = this.mDragPosition;
                }
            }
            if (this.mDragImageView != null) {
                this.mWindowParams.x = (x - this.mDragPointX) + this.mDragOffsetX;
                this.mWindowParams.y = (y - this.mDragPointY) + this.mDragOffsetY;
                this.mWindowManager.updateViewLayout(this.mDragImageView, this.mWindowParams);
                return;
            }
            return;
        }
        removeCallbacks(this.mDelayedOnDragRunnable);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mStartEvent = MotionEvent.obtain(event);
                if (this.mDragAndDropListener != null && this.mDragAndDropStarted) {
                    this.mDragAndDropStarted = false;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return launchDragAndDrop(event);
                }
            default:
                this.mDragAndDropStarted = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    private boolean launchDragAndDrop(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int pointToPosition = pointToPosition(this.mDragPosition, x, y);
        this.mDropPosition = pointToPosition;
        this.mDragPosition = pointToPosition;
        this.mCurrentPosition = pointToPosition;
        if (this.mDragPosition == -1 || !this.mDragAndDropListener.isDragAndDropEnabled(this.mDragPosition)) {
            return false;
        }
        this.mDragOffsetX = (int) (event.getRawX() - ((float) x));
        this.mDragOffsetY = (int) (event.getRawY() - ((float) y));
        startDrag(x, y);
        return true;
    }

    public boolean onTouch(View view, MotionEvent event) {
        if (this.mDragPosition == -1 || this.mDragImageView == null) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSUNICODE /*3*/:
                onDrop();
                resetLongClickTransition();
                getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mDragOffsetX = (int) (event.getRawX() - ((float) x));
                this.mDragOffsetY = (int) (event.getRawY() - ((float) y));
                onDrag(x, y);
                break;
        }
        return true;
    }

    public void fullScroll(View container) {
        if (container != null) {
            post(new C11512(container));
        }
    }
}
