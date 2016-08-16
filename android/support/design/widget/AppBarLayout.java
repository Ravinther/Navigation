package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.design.C0001R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

@DefaultBehavior(Behavior.class)
public class AppBarLayout extends LinearLayout {
    private int mDownPreScrollRange;
    private int mDownScrollRange;
    boolean mHaveChildWithInterpolator;
    private WindowInsetsCompat mLastInsets;
    private final List<WeakReference<OnOffsetChangedListener>> mListeners;
    private float mTargetElevation;
    private int mTotalScrollRange;

    public static class Behavior extends ViewOffsetBehavior<AppBarLayout> {
        private ValueAnimatorCompat mAnimator;
        private Runnable mFlingRunnable;
        private int mOffsetDelta;
        private int mOffsetToChildIndexOnLayout;
        private boolean mOffsetToChildIndexOnLayoutIsMinHeight;
        private float mOffsetToChildIndexOnLayoutPerc;
        private ScrollerCompat mScroller;
        private boolean mSkipNestedPreScroll;

        /* renamed from: android.support.design.widget.AppBarLayout.Behavior.1 */
        class C00031 implements AnimatorUpdateListener {
            final /* synthetic */ AppBarLayout val$child;
            final /* synthetic */ CoordinatorLayout val$coordinatorLayout;

            C00031(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
                this.val$coordinatorLayout = coordinatorLayout;
                this.val$child = appBarLayout;
            }

            public void onAnimationUpdate(ValueAnimatorCompat animator) {
                Behavior.this.setAppBarTopBottomOffset(this.val$coordinatorLayout, this.val$child, animator.getAnimatedIntValue());
            }
        }

        private class FlingRunnable implements Runnable {
            private final AppBarLayout mLayout;
            private final CoordinatorLayout mParent;

            FlingRunnable(CoordinatorLayout parent, AppBarLayout layout) {
                this.mParent = parent;
                this.mLayout = layout;
            }

            public void run() {
                if (this.mLayout != null && Behavior.this.mScroller != null && Behavior.this.mScroller.computeScrollOffset()) {
                    Behavior.this.setAppBarTopBottomOffset(this.mParent, this.mLayout, Behavior.this.mScroller.getCurrY());
                    ViewCompat.postOnAnimation(this.mLayout, this);
                }
            }
        }

        protected static class SavedState extends BaseSavedState {
            public static final Creator<SavedState> CREATOR;
            boolean firstVisibileChildAtMinimumHeight;
            float firstVisibileChildPercentageShown;
            int firstVisibleChildIndex;

            /* renamed from: android.support.design.widget.AppBarLayout.Behavior.SavedState.1 */
            static class C00041 implements Creator<SavedState> {
                C00041() {
                }

                public SavedState createFromParcel(Parcel source) {
                    return new SavedState(source);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            }

            public SavedState(Parcel source) {
                super(source);
                this.firstVisibleChildIndex = source.readInt();
                this.firstVisibileChildPercentageShown = source.readFloat();
                this.firstVisibileChildAtMinimumHeight = source.readByte() != null;
            }

            public SavedState(Parcelable superState) {
                super(superState);
            }

            public void writeToParcel(Parcel dest, int flags) {
                super.writeToParcel(dest, flags);
                dest.writeInt(this.firstVisibleChildIndex);
                dest.writeFloat(this.firstVisibileChildPercentageShown);
                dest.writeByte((byte) (this.firstVisibileChildAtMinimumHeight ? 1 : 0));
            }

            static {
                CREATOR = new C00041();
            }
        }

        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int x0) {
            return super.setTopAndBottomOffset(x0);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
            boolean started = (nestedScrollAxes & 2) != 0 && child.hasScrollableChildren() && coordinatorLayout.getHeight() - target.getHeight() <= child.getHeight();
            if (started && this.mAnimator != null) {
                this.mAnimator.cancel();
            }
            return started;
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
            if (dy != 0 && !this.mSkipNestedPreScroll) {
                int min;
                int max;
                if (dy < 0) {
                    min = -child.getTotalScrollRange();
                    max = min + child.getDownNestedPreScrollRange();
                } else {
                    min = -child.getUpNestedPreScrollRange();
                    max = 0;
                }
                consumed[1] = scroll(coordinatorLayout, child, dy, min, max);
            }
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
            if (dyUnconsumed < 0) {
                scroll(coordinatorLayout, child, dyUnconsumed, -child.getDownNestedScrollRange(), 0);
                this.mSkipNestedPreScroll = true;
                return;
            }
            this.mSkipNestedPreScroll = false;
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target) {
            this.mSkipNestedPreScroll = false;
        }

        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
            if (consumed) {
                int targetScroll;
                if (velocityY < 0.0f) {
                    targetScroll = (-child.getTotalScrollRange()) + child.getDownNestedPreScrollRange();
                    if (getTopBottomOffsetForScrollingSibling() > targetScroll) {
                        return false;
                    }
                }
                targetScroll = -child.getUpNestedPreScrollRange();
                if (getTopBottomOffsetForScrollingSibling() < targetScroll) {
                    return false;
                }
                if (getTopBottomOffsetForScrollingSibling() == targetScroll) {
                    return false;
                }
                animateOffsetTo(coordinatorLayout, child, targetScroll);
                return true;
            }
            return fling(coordinatorLayout, child, -child.getTotalScrollRange(), 0, -velocityY);
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, AppBarLayout child, int offset) {
            if (this.mAnimator == null) {
                this.mAnimator = ViewUtils.createAnimator();
                this.mAnimator.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.mAnimator.setUpdateListener(new C00031(coordinatorLayout, child));
            } else {
                this.mAnimator.cancel();
            }
            this.mAnimator.setIntValues(getTopBottomOffsetForScrollingSibling(), offset);
            this.mAnimator.start();
        }

        private boolean fling(CoordinatorLayout coordinatorLayout, AppBarLayout layout, int minOffset, int maxOffset, float velocityY) {
            if (this.mFlingRunnable != null) {
                layout.removeCallbacks(this.mFlingRunnable);
            }
            if (this.mScroller == null) {
                this.mScroller = ScrollerCompat.create(layout.getContext());
            }
            this.mScroller.fling(0, getTopBottomOffsetForScrollingSibling(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
            if (this.mScroller.computeScrollOffset()) {
                this.mFlingRunnable = new FlingRunnable(coordinatorLayout, layout);
                ViewCompat.postOnAnimation(layout, this.mFlingRunnable);
                return true;
            }
            this.mFlingRunnable = null;
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout appBarLayout, int layoutDirection) {
            boolean handled = super.onLayoutChild(parent, appBarLayout, layoutDirection);
            if (this.mOffsetToChildIndexOnLayout >= 0) {
                View child = appBarLayout.getChildAt(this.mOffsetToChildIndexOnLayout);
                int offset = -child.getBottom();
                if (this.mOffsetToChildIndexOnLayoutIsMinHeight) {
                    offset += ViewCompat.getMinimumHeight(child);
                } else {
                    offset += Math.round(((float) child.getHeight()) * this.mOffsetToChildIndexOnLayoutPerc);
                }
                setTopAndBottomOffset(offset);
                this.mOffsetToChildIndexOnLayout = -1;
            }
            dispatchOffsetUpdates(appBarLayout);
            return handled;
        }

        private int scroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int dy, int minOffset, int maxOffset) {
            return setAppBarTopBottomOffset(coordinatorLayout, appBarLayout, getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
        }

        final int setAppBarTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int newOffset) {
            return setAppBarTopBottomOffset(coordinatorLayout, appBarLayout, newOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        final int setAppBarTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int newOffset, int minOffset, int maxOffset) {
            int curOffset = getTopBottomOffsetForScrollingSibling();
            int consumed = 0;
            if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
                newOffset = MathUtils.constrain(newOffset, minOffset, maxOffset);
                if (curOffset != newOffset) {
                    int interpolatedOffset = appBarLayout.hasChildWithInterpolator() ? interpolateOffset(appBarLayout, newOffset) : newOffset;
                    boolean offsetChanged = setTopAndBottomOffset(interpolatedOffset);
                    consumed = curOffset - newOffset;
                    this.mOffsetDelta = newOffset - interpolatedOffset;
                    if (!offsetChanged && appBarLayout.hasChildWithInterpolator()) {
                        coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                    }
                    dispatchOffsetUpdates(appBarLayout);
                }
            }
            return consumed;
        }

        private void dispatchOffsetUpdates(AppBarLayout layout) {
            List<WeakReference<OnOffsetChangedListener>> listeners = layout.mListeners;
            int z = listeners.size();
            for (int i = 0; i < z; i++) {
                WeakReference<OnOffsetChangedListener> ref = (WeakReference) listeners.get(i);
                OnOffsetChangedListener listener = ref != null ? (OnOffsetChangedListener) ref.get() : null;
                if (listener != null) {
                    listener.onOffsetChanged(layout, getTopAndBottomOffset());
                }
            }
        }

        private int interpolateOffset(AppBarLayout layout, int offset) {
            int absOffset = Math.abs(offset);
            int i = 0;
            int z = layout.getChildCount();
            while (i < z) {
                View child = layout.getChildAt(i);
                LayoutParams childLp = (LayoutParams) child.getLayoutParams();
                Interpolator interpolator = childLp.getScrollInterpolator();
                if (absOffset < child.getTop() || absOffset > child.getBottom()) {
                    i++;
                } else if (interpolator == null) {
                    return offset;
                } else {
                    int childScrollableHeight = 0;
                    int flags = childLp.getScrollFlags();
                    if ((flags & 1) != 0) {
                        childScrollableHeight = 0 + child.getHeight();
                        if ((flags & 2) != 0) {
                            childScrollableHeight -= ViewCompat.getMinimumHeight(child);
                        }
                    }
                    if (childScrollableHeight <= 0) {
                        return offset;
                    }
                    return Integer.signum(offset) * (child.getTop() + Math.round(((float) childScrollableHeight) * interpolator.getInterpolation(((float) (absOffset - child.getTop())) / ((float) childScrollableHeight))));
                }
            }
            return offset;
        }

        final int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.mOffsetDelta;
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout parent, AppBarLayout appBarLayout) {
            Parcelable superState = super.onSaveInstanceState(parent, appBarLayout);
            int offset = getTopAndBottomOffset();
            int i = 0;
            int count = appBarLayout.getChildCount();
            while (i < count) {
                View child = appBarLayout.getChildAt(i);
                int visBottom = child.getBottom() + offset;
                if (child.getTop() + offset > 0 || visBottom < 0) {
                    i++;
                } else {
                    SavedState ss = new SavedState(superState);
                    ss.firstVisibleChildIndex = i;
                    ss.firstVisibileChildAtMinimumHeight = visBottom == ViewCompat.getMinimumHeight(child);
                    ss.firstVisibileChildPercentageShown = ((float) visBottom) / ((float) child.getHeight());
                    return ss;
                }
            }
            return superState;
        }

        public void onRestoreInstanceState(CoordinatorLayout parent, AppBarLayout appBarLayout, Parcelable state) {
            if (state instanceof SavedState) {
                SavedState ss = (SavedState) state;
                super.onRestoreInstanceState(parent, appBarLayout, ss.getSuperState());
                this.mOffsetToChildIndexOnLayout = ss.firstVisibleChildIndex;
                this.mOffsetToChildIndexOnLayoutPerc = ss.firstVisibileChildPercentageShown;
                this.mOffsetToChildIndexOnLayoutIsMinHeight = ss.firstVisibileChildAtMinimumHeight;
                return;
            }
            super.onRestoreInstanceState(parent, appBarLayout, state);
            this.mOffsetToChildIndexOnLayout = -1;
        }
    }

    public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {
        int mScrollFlags;
        Interpolator mScrollInterpolator;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mScrollFlags = 1;
            TypedArray a = c.obtainStyledAttributes(attrs, C0001R.styleable.AppBarLayout_LayoutParams);
            this.mScrollFlags = a.getInt(C0001R.styleable.AppBarLayout_LayoutParams_layout_scrollFlags, 0);
            if (a.hasValue(C0001R.styleable.AppBarLayout_LayoutParams_layout_scrollInterpolator)) {
                this.mScrollInterpolator = AnimationUtils.loadInterpolator(c, a.getResourceId(C0001R.styleable.AppBarLayout_LayoutParams_layout_scrollInterpolator, 0));
            }
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.mScrollFlags = 1;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams p) {
            super(p);
            this.mScrollFlags = 1;
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
            this.mScrollFlags = 1;
        }

        public LayoutParams(android.widget.LinearLayout.LayoutParams source) {
            super(source);
            this.mScrollFlags = 1;
        }

        public int getScrollFlags() {
            return this.mScrollFlags;
        }

        public Interpolator getScrollInterpolator() {
            return this.mScrollInterpolator;
        }
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener listener) {
        int i = 0;
        int z = this.mListeners.size();
        while (i < z) {
            WeakReference<OnOffsetChangedListener> ref = (WeakReference) this.mListeners.get(i);
            if (ref == null || ref.get() != listener) {
                i++;
            } else {
                return;
            }
        }
        this.mListeners.add(new WeakReference(listener));
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener listener) {
        Iterator<WeakReference<OnOffsetChangedListener>> i = this.mListeners.iterator();
        while (i.hasNext()) {
            OnOffsetChangedListener item = (OnOffsetChangedListener) ((WeakReference) i.next()).get();
            if (item == listener || item == null) {
                i.remove();
            }
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mTotalScrollRange = -1;
        this.mDownPreScrollRange = -1;
        this.mDownPreScrollRange = -1;
        this.mHaveChildWithInterpolator = false;
        int z = getChildCount();
        for (int i = 0; i < z; i++) {
            if (((LayoutParams) getChildAt(i).getLayoutParams()).getScrollInterpolator() != null) {
                this.mHaveChildWithInterpolator = true;
                return;
            }
        }
    }

    public void setOrientation(int orientation) {
        if (orientation != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(orientation);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        if (p instanceof android.widget.LinearLayout.LayoutParams) {
            return new LayoutParams((android.widget.LinearLayout.LayoutParams) p);
        }
        if (p instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) p);
        }
        return new LayoutParams(p);
    }

    final boolean hasChildWithInterpolator() {
        return this.mHaveChildWithInterpolator;
    }

    public final int getTotalScrollRange() {
        if (this.mTotalScrollRange != -1) {
            return this.mTotalScrollRange;
        }
        int range = 0;
        int z = getChildCount();
        for (int i = 0; i < z; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childHeight = ViewCompat.isLaidOut(child) ? child.getHeight() : child.getMeasuredHeight();
            int flags = lp.mScrollFlags;
            if ((flags & 1) == 0) {
                break;
            }
            range += childHeight;
            if ((flags & 2) != 0) {
                range -= ViewCompat.getMinimumHeight(child);
                break;
            }
        }
        int systemWindowInsetTop = range - (this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0);
        this.mTotalScrollRange = systemWindowInsetTop;
        return systemWindowInsetTop;
    }

    final boolean hasScrollableChildren() {
        return getTotalScrollRange() != 0;
    }

    final int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    final int getDownNestedPreScrollRange() {
        if (this.mDownPreScrollRange != -1) {
            return this.mDownPreScrollRange;
        }
        int range = 0;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childHeight = ViewCompat.isLaidOut(child) ? child.getHeight() : child.getMeasuredHeight();
            int flags = lp.mScrollFlags;
            if ((flags & 5) == 5) {
                if ((flags & 8) != 0) {
                    range += ViewCompat.getMinimumHeight(child);
                } else {
                    range += childHeight;
                }
            } else if (range > 0) {
                break;
            }
        }
        this.mDownPreScrollRange = range;
        return range;
    }

    final int getDownNestedScrollRange() {
        if (this.mDownScrollRange != -1) {
            return this.mDownScrollRange;
        }
        int range = 0;
        int z = getChildCount();
        for (int i = 0; i < z; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childHeight = ViewCompat.isLaidOut(child) ? child.getHeight() : child.getMeasuredHeight();
            int flags = lp.mScrollFlags;
            if ((flags & 1) == 0) {
                break;
            }
            range += childHeight;
            if ((flags & 2) != 0) {
                return range - ViewCompat.getMinimumHeight(child);
            }
        }
        this.mDownScrollRange = range;
        return range;
    }

    final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset;
        if (this.mLastInsets != null) {
            topInset = this.mLastInsets.getSystemWindowInsetTop();
        } else {
            topInset = 0;
        }
        int minHeight = ViewCompat.getMinimumHeight(this);
        if (minHeight != 0) {
            return (minHeight * 2) + topInset;
        }
        int childCount = getChildCount();
        if (childCount >= 1) {
            return (ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) * 2) + topInset;
        }
        return 0;
    }

    public void setTargetElevation(float elevation) {
        this.mTargetElevation = elevation;
    }

    public float getTargetElevation() {
        return this.mTargetElevation;
    }

    private void setWindowInsets(WindowInsetsCompat insets) {
        this.mTotalScrollRange = -1;
        this.mLastInsets = insets;
        int i = 0;
        int z = getChildCount();
        while (i < z) {
            insets = ViewCompat.dispatchApplyWindowInsets(getChildAt(i), insets);
            if (!insets.isConsumed()) {
                i++;
            } else {
                return;
            }
        }
    }
}
