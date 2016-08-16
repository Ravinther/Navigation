package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import loquendo.tts.engine.TTSConst;

public class SwipeDismissBehavior<V extends View> extends Behavior<V> {
    private float mAlphaEndSwipeDistance;
    private float mAlphaStartSwipeDistance;
    private final Callback mDragCallback;
    private float mDragDismissThreshold;
    private boolean mIgnoreEvents;
    private OnDismissListener mListener;
    private float mSensitivity;
    private boolean mSensitivitySet;
    private int mSwipeDirection;
    private ViewDragHelper mViewDragHelper;

    public interface OnDismissListener {
        void onDismiss(View view);

        void onDragStateChanged(int i);
    }

    /* renamed from: android.support.design.widget.SwipeDismissBehavior.1 */
    class C00231 extends Callback {
        private int mOriginalCapturedViewLeft;

        C00231() {
        }

        public boolean tryCaptureView(View child, int pointerId) {
            this.mOriginalCapturedViewLeft = child.getLeft();
            return true;
        }

        public void onViewDragStateChanged(int state) {
            if (SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDragStateChanged(state);
            }
        }

        public void onViewReleased(View child, float xvel, float yvel) {
            int targetLeft;
            int childWidth = child.getWidth();
            boolean dismiss = false;
            if (shouldDismiss(child, xvel)) {
                targetLeft = child.getLeft() < this.mOriginalCapturedViewLeft ? this.mOriginalCapturedViewLeft - childWidth : this.mOriginalCapturedViewLeft + childWidth;
                dismiss = true;
            } else {
                targetLeft = this.mOriginalCapturedViewLeft;
            }
            if (SwipeDismissBehavior.this.mViewDragHelper.settleCapturedViewAt(targetLeft, child.getTop())) {
                ViewCompat.postOnAnimation(child, new SettleRunnable(child, dismiss));
            } else if (dismiss && SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDismiss(child);
            }
        }

        private boolean shouldDismiss(View child, float xvel) {
            if (xvel != 0.0f) {
                boolean isRtl = ViewCompat.getLayoutDirection(child) == 1;
                if (SwipeDismissBehavior.this.mSwipeDirection == 2) {
                    return true;
                }
                if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                    if (isRtl) {
                        if (xvel >= 0.0f) {
                            return false;
                        }
                        return true;
                    } else if (xvel <= 0.0f) {
                        return false;
                    } else {
                        return true;
                    }
                } else if (SwipeDismissBehavior.this.mSwipeDirection != 1) {
                    return false;
                } else {
                    if (isRtl) {
                        if (xvel <= 0.0f) {
                            return false;
                        }
                        return true;
                    } else if (xvel >= 0.0f) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            if (Math.abs(child.getLeft() - this.mOriginalCapturedViewLeft) < Math.round(((float) child.getWidth()) * SwipeDismissBehavior.this.mDragDismissThreshold)) {
                return false;
            }
            return true;
        }

        public int getViewHorizontalDragRange(View child) {
            return child.getWidth();
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int min;
            int max;
            boolean isRtl = ViewCompat.getLayoutDirection(child) == 1;
            if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                if (isRtl) {
                    min = this.mOriginalCapturedViewLeft - child.getWidth();
                    max = this.mOriginalCapturedViewLeft;
                } else {
                    min = this.mOriginalCapturedViewLeft;
                    max = this.mOriginalCapturedViewLeft + child.getWidth();
                }
            } else if (SwipeDismissBehavior.this.mSwipeDirection != 1) {
                min = this.mOriginalCapturedViewLeft - child.getWidth();
                max = this.mOriginalCapturedViewLeft + child.getWidth();
            } else if (isRtl) {
                min = this.mOriginalCapturedViewLeft;
                max = this.mOriginalCapturedViewLeft + child.getWidth();
            } else {
                min = this.mOriginalCapturedViewLeft - child.getWidth();
                max = this.mOriginalCapturedViewLeft;
            }
            return SwipeDismissBehavior.clamp(min, left, max);
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return child.getTop();
        }

        public void onViewPositionChanged(View child, int left, int top, int dx, int dy) {
            float startAlphaDistance = ((float) child.getWidth()) * SwipeDismissBehavior.this.mAlphaStartSwipeDistance;
            float endAlphaDistance = ((float) child.getWidth()) * SwipeDismissBehavior.this.mAlphaEndSwipeDistance;
            if (((float) left) <= startAlphaDistance) {
                ViewCompat.setAlpha(child, 1.0f);
            } else if (((float) left) >= endAlphaDistance) {
                ViewCompat.setAlpha(child, 0.0f);
            } else {
                ViewCompat.setAlpha(child, SwipeDismissBehavior.clamp(0.0f, 1.0f - SwipeDismissBehavior.fraction(startAlphaDistance, endAlphaDistance, (float) left), 1.0f));
            }
        }
    }

    private class SettleRunnable implements Runnable {
        private final boolean mDismiss;
        private final View mView;

        SettleRunnable(View view, boolean dismiss) {
            this.mView = view;
            this.mDismiss = dismiss;
        }

        public void run() {
            if (SwipeDismissBehavior.this.mViewDragHelper != null && SwipeDismissBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
            } else if (this.mDismiss && SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDismiss(this.mView);
            }
        }
    }

    public SwipeDismissBehavior() {
        this.mSensitivity = 0.0f;
        this.mSwipeDirection = 2;
        this.mDragDismissThreshold = 0.5f;
        this.mAlphaStartSwipeDistance = 0.0f;
        this.mAlphaEndSwipeDistance = 0.5f;
        this.mDragCallback = new C00231();
    }

    public void setListener(OnDismissListener listener) {
        this.mListener = listener;
    }

    public void setSwipeDirection(int direction) {
        this.mSwipeDirection = direction;
    }

    public void setStartAlphaSwipeDistance(float fraction) {
        this.mAlphaStartSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float fraction) {
        this.mAlphaEndSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSUNICODE /*3*/:
                if (this.mIgnoreEvents) {
                    this.mIgnoreEvents = false;
                    return false;
                }
                break;
            default:
                boolean z;
                if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                    z = false;
                } else {
                    z = true;
                }
                this.mIgnoreEvents = z;
                break;
        }
        if (this.mIgnoreEvents) {
            return false;
        }
        ensureViewDragHelper(parent);
        return this.mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V v, MotionEvent event) {
        if (this.mViewDragHelper == null) {
            return false;
        }
        this.mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private void ensureViewDragHelper(ViewGroup parent) {
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = this.mSensitivitySet ? ViewDragHelper.create(parent, this.mSensitivity, this.mDragCallback) : ViewDragHelper.create(parent, this.mDragCallback);
        }
    }

    private static float clamp(float min, float value, float max) {
        return Math.min(Math.max(min, value), max);
    }

    private static int clamp(int min, int value, int max) {
        return Math.min(Math.max(min, value), max);
    }

    public int getDragState() {
        return this.mViewDragHelper != null ? this.mViewDragHelper.getViewDragState() : 0;
    }

    static float fraction(float startValue, float endValue, float value) {
        return (value - startValue) / (endValue - startValue);
    }
}
