package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.design.C0001R;
import android.support.design.widget.SwipeDismissBehavior.OnDismissListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import loquendo.tts.engine.TTSConst;

public class Snackbar {
    private static final Handler sHandler;
    private final Callback mManagerCallback;
    private final ViewGroup mParent;
    private final SnackbarLayout mView;

    /* renamed from: android.support.design.widget.Snackbar.1 */
    static class C00141 implements Callback {
        C00141() {
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    ((Snackbar) message.obj).showView();
                    return true;
                case TTSConst.TTSMULTILINE /*1*/:
                    ((Snackbar) message.obj).hideView();
                    return true;
                default:
                    return false;
            }
        }
    }

    /* renamed from: android.support.design.widget.Snackbar.4 */
    class C00154 implements OnDismissListener {
        C00154() {
        }

        public void onDismiss(View view) {
            Snackbar.this.dismiss();
        }

        public void onDragStateChanged(int state) {
            switch (state) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    SnackbarManager.getInstance().restoreTimeout(Snackbar.this.mManagerCallback);
                case TTSConst.TTSMULTILINE /*1*/:
                case TTSConst.TTSPARAGRAPH /*2*/:
                    SnackbarManager.getInstance().cancelTimeout(Snackbar.this.mManagerCallback);
                default:
            }
        }
    }

    /* renamed from: android.support.design.widget.Snackbar.5 */
    class C00165 implements OnLayoutChangeListener {
        C00165() {
        }

        public void onLayoutChange(View view, int left, int top, int right, int bottom) {
            Snackbar.this.animateViewIn();
            Snackbar.this.mView.setOnLayoutChangeListener(null);
        }
    }

    /* renamed from: android.support.design.widget.Snackbar.6 */
    class C00176 extends ViewPropertyAnimatorListenerAdapter {
        C00176() {
        }

        public void onAnimationStart(View view) {
            Snackbar.this.mView.animateChildrenIn(70, 180);
        }

        public void onAnimationEnd(View view) {
            SnackbarManager.getInstance().onShown(Snackbar.this.mManagerCallback);
        }
    }

    /* renamed from: android.support.design.widget.Snackbar.7 */
    class C00187 implements AnimationListener {
        C00187() {
        }

        public void onAnimationEnd(Animation animation) {
            SnackbarManager.getInstance().onShown(Snackbar.this.mManagerCallback);
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /* renamed from: android.support.design.widget.Snackbar.8 */
    class C00198 extends ViewPropertyAnimatorListenerAdapter {
        C00198() {
        }

        public void onAnimationStart(View view) {
            Snackbar.this.mView.animateChildrenOut(0, 180);
        }

        public void onAnimationEnd(View view) {
            Snackbar.this.onViewHidden();
        }
    }

    /* renamed from: android.support.design.widget.Snackbar.9 */
    class C00209 implements AnimationListener {
        C00209() {
        }

        public void onAnimationEnd(Animation animation) {
            Snackbar.this.onViewHidden();
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    final class Behavior extends SwipeDismissBehavior<SnackbarLayout> {
        Behavior() {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout parent, SnackbarLayout child, MotionEvent event) {
            if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                switch (event.getActionMasked()) {
                    case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                        SnackbarManager.getInstance().cancelTimeout(Snackbar.this.mManagerCallback);
                        break;
                    case TTSConst.TTSMULTILINE /*1*/:
                    case TTSConst.TTSUNICODE /*3*/:
                        SnackbarManager.getInstance().restoreTimeout(Snackbar.this.mManagerCallback);
                        break;
                }
            }
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }

    public static class SnackbarLayout extends LinearLayout {
        private TextView mActionView;
        private int mMaxInlineActionWidth;
        private int mMaxWidth;
        private TextView mMessageView;
        private OnLayoutChangeListener mOnLayoutChangeListener;

        interface OnLayoutChangeListener {
            void onLayoutChange(View view, int i, int i2, int i3, int i4);
        }

        public SnackbarLayout(Context context) {
            this(context, null);
        }

        public SnackbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, C0001R.styleable.SnackbarLayout);
            this.mMaxWidth = a.getDimensionPixelSize(C0001R.styleable.SnackbarLayout_android_maxWidth, -1);
            this.mMaxInlineActionWidth = a.getDimensionPixelSize(C0001R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
            if (a.hasValue(C0001R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(C0001R.styleable.SnackbarLayout_elevation, 0));
            }
            a.recycle();
            setClickable(true);
            LayoutInflater.from(context).inflate(C0001R.layout.layout_snackbar_include, this);
        }

        protected void onFinishInflate() {
            super.onFinishInflate();
            this.mMessageView = (TextView) findViewById(C0001R.id.snackbar_text);
            this.mActionView = (TextView) findViewById(C0001R.id.snackbar_action);
        }

        TextView getMessageView() {
            return this.mMessageView;
        }

        TextView getActionView() {
            return this.mActionView;
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            boolean isMultiLine;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.mMaxWidth > 0 && getMeasuredWidth() > this.mMaxWidth) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
            int multiLineVPadding = getResources().getDimensionPixelSize(C0001R.dimen.snackbar_padding_vertical_2lines);
            int singleLineVPadding = getResources().getDimensionPixelSize(C0001R.dimen.snackbar_padding_vertical);
            if (this.mMessageView.getLayout().getLineCount() > 1) {
                isMultiLine = true;
            } else {
                isMultiLine = false;
            }
            boolean remeasure = false;
            if (!isMultiLine || this.mMaxInlineActionWidth <= 0 || this.mActionView.getMeasuredWidth() <= this.mMaxInlineActionWidth) {
                int messagePadding;
                if (isMultiLine) {
                    messagePadding = multiLineVPadding;
                } else {
                    messagePadding = singleLineVPadding;
                }
                if (updateViewsWithinLayout(0, messagePadding, messagePadding)) {
                    remeasure = true;
                }
            } else if (updateViewsWithinLayout(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                remeasure = true;
            }
            if (remeasure) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        void animateChildrenIn(int delay, int duration) {
            ViewCompat.setAlpha(this.mMessageView, 0.0f);
            ViewCompat.animate(this.mMessageView).alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            if (this.mActionView.getVisibility() == 0) {
                ViewCompat.setAlpha(this.mActionView, 0.0f);
                ViewCompat.animate(this.mActionView).alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            }
        }

        void animateChildrenOut(int delay, int duration) {
            ViewCompat.setAlpha(this.mMessageView, 1.0f);
            ViewCompat.animate(this.mMessageView).alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            if (this.mActionView.getVisibility() == 0) {
                ViewCompat.setAlpha(this.mActionView, 1.0f);
                ViewCompat.animate(this.mActionView).alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            }
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (changed && this.mOnLayoutChangeListener != null) {
                this.mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            this.mOnLayoutChangeListener = onLayoutChangeListener;
        }

        private boolean updateViewsWithinLayout(int orientation, int messagePadTop, int messagePadBottom) {
            boolean changed = false;
            if (orientation != getOrientation()) {
                setOrientation(orientation);
                changed = true;
            }
            if (this.mMessageView.getPaddingTop() == messagePadTop && this.mMessageView.getPaddingBottom() == messagePadBottom) {
                return changed;
            }
            updateTopBottomPadding(this.mMessageView, messagePadTop, messagePadBottom);
            return true;
        }

        private static void updateTopBottomPadding(View view, int topPadding, int bottomPadding) {
            if (ViewCompat.isPaddingRelative(view)) {
                ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding);
            } else {
                view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);
            }
        }
    }

    static {
        sHandler = new Handler(Looper.getMainLooper(), new C00141());
    }

    public void dismiss() {
        SnackbarManager.getInstance().dismiss(this.mManagerCallback);
    }

    final void showView() {
        if (this.mView.getParent() == null) {
            LayoutParams lp = this.mView.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                Behavior behavior = new Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(0);
                behavior.setListener(new C00154());
                ((CoordinatorLayout.LayoutParams) lp).setBehavior(behavior);
            }
            this.mParent.addView(this.mView);
        }
        if (ViewCompat.isLaidOut(this.mView)) {
            animateViewIn();
        } else {
            this.mView.setOnLayoutChangeListener(new C00165());
        }
    }

    private void animateViewIn() {
        if (VERSION.SDK_INT >= 14) {
            ViewCompat.setTranslationY(this.mView, (float) this.mView.getHeight());
            ViewCompat.animate(this.mView).translationY(0.0f).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250).setListener(new C00176()).start();
            return;
        }
        Animation anim = AnimationUtils.loadAnimation(this.mView.getContext(), C0001R.anim.snackbar_in);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250);
        anim.setAnimationListener(new C00187());
        this.mView.startAnimation(anim);
    }

    private void animateViewOut() {
        if (VERSION.SDK_INT >= 14) {
            ViewCompat.animate(this.mView).translationY((float) this.mView.getHeight()).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250).setListener(new C00198()).start();
            return;
        }
        Animation anim = AnimationUtils.loadAnimation(this.mView.getContext(), C0001R.anim.snackbar_out);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250);
        anim.setAnimationListener(new C00209());
        this.mView.startAnimation(anim);
    }

    final void hideView() {
        if (this.mView.getVisibility() != 0 || isBeingDragged()) {
            onViewHidden();
        } else {
            animateViewOut();
        }
    }

    private void onViewHidden() {
        this.mParent.removeView(this.mView);
        SnackbarManager.getInstance().onDismissed(this.mManagerCallback);
    }

    private boolean isBeingDragged() {
        LayoutParams lp = this.mView.getLayoutParams();
        if (!(lp instanceof CoordinatorLayout.LayoutParams)) {
            return false;
        }
        android.support.design.widget.CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) lp).getBehavior();
        if (!(behavior instanceof SwipeDismissBehavior) || ((SwipeDismissBehavior) behavior).getDragState() == 0) {
            return false;
        }
        return true;
    }
}
