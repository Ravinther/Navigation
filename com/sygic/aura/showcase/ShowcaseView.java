package com.sygic.aura.showcase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.sygic.aura.C1090R;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.showcase.targets.PointTarget;
import com.sygic.aura.showcase.targets.Target;

public class ShowcaseView extends FrameLayout implements OnTouchListener, BackPressedListener {
    private long FADE_DURATION;
    private Activity mActivity;
    private boolean mHasAlteredText;
    private final String mId;
    private OnProceedListener mProceedListener;
    private ShowcaseAreaCalculator mShowcaseAreaCalculator;
    private ShowcaseDisplayer mShowcaseDisplayer;
    private int mShowcaseX;
    private int mShowcaseY;
    private boolean mShowing;
    private Target mTarget;
    private TextDisplayer mTextDisplayer;

    public interface OnProceedListener {
        void onProceed();
    }

    /* renamed from: com.sygic.aura.showcase.ShowcaseView.1 */
    class C16781 implements OnClickListener {
        C16781() {
        }

        public void onClick(View v) {
            ShowcaseView.this.hide();
            if (ShowcaseView.this.mProceedListener != null) {
                ShowcaseView.this.mProceedListener.onProceed();
            }
        }
    }

    /* renamed from: com.sygic.aura.showcase.ShowcaseView.2 */
    class C16792 implements Runnable {
        final /* synthetic */ Target val$target;

        C16792(Target target) {
            this.val$target = target;
        }

        public void run() {
            ShowcaseView.this.mShowcaseDisplayer.prepare();
            Point targetPoint = this.val$target.getPoint();
            if (targetPoint != null) {
                ShowcaseView.this.setShowcasePosition(targetPoint);
            }
            ShowcaseView.this.invalidate();
        }
    }

    /* renamed from: com.sygic.aura.showcase.ShowcaseView.3 */
    class C16803 extends AnimatorListenerAdapter {
        C16803() {
        }

        public void onAnimationEnd(Animator animation) {
            ((ViewGroup) ShowcaseView.this.mActivity.getWindow().getDecorView()).removeView(ShowcaseView.this);
        }
    }

    private class CalculateTextOnPreDraw implements OnPreDrawListener {
        private CalculateTextOnPreDraw() {
        }

        public boolean onPreDraw() {
            ShowcaseView.this.recalculateText();
            return true;
        }
    }

    public static boolean shouldShow(Context context, String id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (!prefs.getBoolean(context.getString(2131166715), true) || prefs.getBoolean(id, false)) {
            return false;
        }
        return true;
    }

    public ShowcaseView(Activity activity) {
        this(activity, null);
    }

    public ShowcaseView(Activity activity, String id) {
        super(activity);
        this.FADE_DURATION = 300;
        this.mShowcaseX = -1;
        this.mShowcaseY = -1;
        this.mHasAlteredText = false;
        this.mActivity = activity;
        this.mId = id;
        init(activity);
    }

    private void init(Context context) {
        this.mShowcaseAreaCalculator = new ShowcaseAreaCalculator();
        ((NaviNativeActivity) this.mActivity).registerBackPressedListener(this);
        getViewTreeObserver().addOnPreDrawListener(new CalculateTextOnPreDraw());
        TypedArray ta = context.getTheme().obtainStyledAttributes(null, C1090R.styleable.ShowcaseView, 0, 2131296527);
        int backgroundColor = ta.getColor(0, Color.argb(128, 80, 80, 80));
        int titleTextAppearance = ta.getResourceId(2, 2131296529);
        int detailTextAppearance = ta.getResourceId(1, 2131296528);
        ta.recycle();
        this.mShowcaseDisplayer = new ShowcaseDisplayer(getResources(), backgroundColor);
        this.mTextDisplayer = new TextDisplayer(getResources(), this.mShowcaseAreaCalculator, getContext());
        this.mTextDisplayer.setTitleStyling(titleTextAppearance);
        this.mTextDisplayer.setDetailStyling(detailTextAppearance);
        this.mHasAlteredText = true;
        Button okButton = (Button) LayoutInflater.from(context).inflate(2130903281, this, false);
        ((LayoutParams) okButton.getLayoutParams()).gravity = 8388693;
        okButton.setOnClickListener(new C16781());
        addView(okButton);
        setOnTouchListener(this);
        adjustPaddingIfNecessary();
    }

    private void adjustPaddingIfNecessary() {
        if (VERSION.SDK_INT >= 21) {
            Display display = this.mActivity.getWindowManager().getDefaultDisplay();
            Point sizeWithBars = new Point();
            display.getSize(sizeWithBars);
            Point sizeWithoutBars = new Point();
            display.getRealSize(sizeWithoutBars);
            if (!sizeWithoutBars.equals(sizeWithBars)) {
                Resources res = this.mActivity.getResources();
                int dimenId;
                if (sizeWithoutBars.y > sizeWithBars.y) {
                    dimenId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (dimenId > 0) {
                        setPadding(0, 0, 0, (int) res.getDimension(dimenId));
                    }
                } else if (sizeWithoutBars.x > sizeWithBars.x) {
                    dimenId = res.getIdentifier("navigation_bar_width", "dimen", "android");
                    if (dimenId > 0) {
                        setPadding(0, 0, (int) res.getDimension(dimenId), 0);
                    }
                }
            }
        }
    }

    private void recalculateText() {
        boolean recalculateText;
        if (this.mShowcaseAreaCalculator.calculateShowcaseRect((float) this.mShowcaseX, (float) this.mShowcaseY, this.mShowcaseDisplayer) || this.mHasAlteredText) {
            recalculateText = true;
        } else {
            recalculateText = false;
        }
        if (recalculateText) {
            this.mTextDisplayer.calculateTextPosition(getMeasuredWidth(), getMeasuredHeight(), this);
        }
        this.mHasAlteredText = false;
    }

    protected boolean hasTarget() {
        return this.mTarget != null;
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    private ShowcaseView setShowcasePosition(Point point) {
        setShowcasePosition(point.x, point.y);
        return this;
    }

    private ShowcaseView setShowcasePosition(int x, int y) {
        this.mShowcaseX = x;
        this.mShowcaseY = y;
        return this;
    }

    public ShowcaseView setTarget(Target target) {
        setShowcase(target);
        return this;
    }

    public ShowcaseView setContentTitle(int textRes) {
        this.mTextDisplayer.setContentTitle(ResourceManager.getResourceOrCoreString(getContext(), this, textRes));
        return this;
    }

    public ShowcaseView setContentText(int textRes) {
        this.mTextDisplayer.setContentText(ResourceManager.getResourceOrCoreString(getContext(), this, textRes));
        return this;
    }

    public ShowcaseView setOnProceedListener(OnProceedListener listener) {
        this.mProceedListener = listener;
        return this;
    }

    public ShowcaseView setScale(float scale) {
        this.mShowcaseDisplayer.setScale(scale);
        return this;
    }

    private void setShowcase(Target target) {
        post(new C16792(target));
        this.mTarget = target;
    }

    public ShowcaseView show() {
        if (!(this.mShowing || this.mActivity == null)) {
            ((ViewGroup) this.mActivity.getWindow().getDecorView()).addView(this);
            if (VERSION.SDK_INT >= 16) {
                setAlpha(0.0f);
                animate().alpha(1.0f).setDuration(this.FADE_DURATION).setListener(null);
            }
            this.mShowing = true;
        }
        return this;
    }

    public void hide() {
        if (this.mShowing && this.mActivity != null) {
            ((NaviNativeActivity) this.mActivity).unregisterBackPressedListener(this);
            save();
            this.mShowcaseDisplayer.release();
            if (VERSION.SDK_INT >= 16) {
                animate().alpha(0.0f).setDuration(this.FADE_DURATION).setListener(new C16803());
            } else {
                ((ViewGroup) this.mActivity.getWindow().getDecorView()).removeView(this);
            }
            this.mShowing = false;
        }
    }

    private void save() {
        if (this.mActivity != null && this.mId != null) {
            PreferenceManager.getDefaultSharedPreferences(this.mActivity).edit().putBoolean(this.mId, true).apply();
        }
    }

    public static void dismiss(ShowcaseView showcase) {
        if (showcase != null && showcase.isShowing()) {
            showcase.hide();
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            hide();
            if (this.mProceedListener != null) {
                this.mProceedListener.onProceed();
            }
        }
        return true;
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mTarget != null) {
            if (this.mTarget instanceof PointTarget) {
                ((PointTarget) this.mTarget).swapCoordinates();
            }
            setShowcase(this.mTarget);
        }
        adjustPaddingIfNecessary();
    }

    protected void dispatchDraw(Canvas canvas) {
        this.mShowcaseDisplayer.draw(canvas, this.mShowcaseX, this.mShowcaseY);
        this.mTextDisplayer.draw(canvas);
        super.dispatchDraw(canvas);
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }
}
