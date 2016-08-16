package com.sygic.aura.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;

public class SmartProgressBar extends FrameLayout {
    private SImageView mEmptyImageView;
    private STextView mEmptyTextView;
    private View mEmptyView;
    private View mErrorView;
    private int mImageColor;
    private boolean mIsRunning;
    private int mMessageTextColor;
    private View mProgressView;
    public OnRetryCallback mRetryCallback;

    public interface OnRetryCallback {
        void onRetry();
    }

    /* renamed from: com.sygic.aura.views.SmartProgressBar.1 */
    class C17841 implements OnClickListener {
        C17841() {
        }

        public void onClick(View v) {
            if (SmartProgressBar.this.mRetryCallback != null) {
                SmartProgressBar.this.mRetryCallback.onRetry();
            }
        }
    }

    /* renamed from: com.sygic.aura.views.SmartProgressBar.2 */
    class C17852 implements Runnable {
        final /* synthetic */ Runnable val$endAction;

        C17852(Runnable runnable) {
            this.val$endAction = runnable;
        }

        public void run() {
            if (this.val$endAction != null) {
                this.val$endAction.run();
            }
        }
    }

    /* renamed from: com.sygic.aura.views.SmartProgressBar.3 */
    class C17863 extends AnimatorListenerAdapter {
        final /* synthetic */ Runnable val$endAction;
        final /* synthetic */ View val$view;

        C17863(View view, Runnable runnable) {
            this.val$view = view;
            this.val$endAction = runnable;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$view.setVisibility(8);
            if (this.val$endAction != null) {
                this.val$endAction.run();
            }
        }
    }

    /* renamed from: com.sygic.aura.views.SmartProgressBar.4 */
    class C17874 implements AnimatorListener {
        final /* synthetic */ Runnable val$endAction;
        final /* synthetic */ View val$view;

        C17874(View view, Runnable runnable) {
            this.val$view = view;
            this.val$endAction = runnable;
        }

        public void onAnimationStart(com.nineoldandroids.animation.Animator animator) {
        }

        public void onAnimationEnd(com.nineoldandroids.animation.Animator animator) {
            this.val$view.setVisibility(8);
            if (this.val$endAction != null) {
                this.val$endAction.run();
            }
        }

        public void onAnimationCancel(com.nineoldandroids.animation.Animator animator) {
        }

        public void onAnimationRepeat(com.nineoldandroids.animation.Animator animator) {
        }
    }

    public SmartProgressBar(Context context) {
        super(context);
        this.mIsRunning = false;
        init(context, null);
    }

    public SmartProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsRunning = false;
        init(context, attrs);
    }

    public SmartProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsRunning = false;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.SmartProgressBar);
        this.mImageColor = a.getColor(6, getResources().getColor(2131558703));
        this.mMessageTextColor = a.getColor(2, getResources().getColor(2131558704));
        if (!isInEditMode()) {
            initErrorView(inflater, a);
            initProgressView(inflater, a);
            initEmptyView(inflater, a);
        }
        a.recycle();
    }

    private void initEmptyView(LayoutInflater inflater, TypedArray a) {
        this.mEmptyView = inflater.inflate(2130903282, this, false);
        this.mEmptyImageView = (SImageView) this.mEmptyView.findViewById(2131624596);
        this.mEmptyTextView = (STextView) this.mEmptyView.findViewById(2131624165);
        this.mEmptyTextView.setTextColor(this.mMessageTextColor);
        this.mEmptyView.setVisibility(8);
        addView(this.mEmptyView);
    }

    private void initProgressView(LayoutInflater inflater, TypedArray a) {
        this.mProgressView = inflater.inflate(2130903284, this, false);
        this.mProgressView.setVisibility(8);
        addView(this.mProgressView);
    }

    private void initErrorView(LayoutInflater inflater, TypedArray a) {
        this.mErrorView = inflater.inflate(2130903283, this, false);
        SImageView errorImageView = (SImageView) this.mErrorView.findViewById(2131624208);
        STextView messageTextView = (STextView) this.mErrorView.findViewById(2131624597);
        STextView retryTextView = (STextView) this.mErrorView.findViewById(2131624598);
        retryTextView.setOnClickListener(new C17841());
        Drawable errorDrawable = FontDrawable.inflate(getResources(), a.getResourceId(5, 2131034305));
        if (errorDrawable instanceof FontDrawable) {
            ((FontDrawable) errorDrawable).setColor(this.mImageColor);
        }
        errorImageView.setImageDrawable(errorDrawable);
        messageTextView.setCoreText(a.getResourceId(0, 2131165914));
        messageTextView.setTextColor(this.mMessageTextColor);
        int retryStringRes = a.getResourceId(1, 2131165913);
        int retryTextColor = a.getColor(3, getResources().getColor(2131558702));
        int selectorRes = a.getResourceId(4, 2130838060);
        retryTextView.setCoreText(retryStringRes);
        retryTextView.setTextColor(retryTextColor);
        retryTextView.setBackgroundResource(selectorRes);
        this.mErrorView.setVisibility(8);
        addView(this.mErrorView);
    }

    public void setRetryCallback(OnRetryCallback retryCallback) {
        this.mRetryCallback = retryCallback;
    }

    public void setEmptyTextAndImageRes(int textRes, int imageRes) {
        this.mEmptyTextView.setCoreText(textRes);
        this.mEmptyImageView.setImageResource(imageRes);
        this.mEmptyImageView.setFontDrawableColor(this.mImageColor);
    }

    public void start() {
        this.mProgressView.setVisibility(0);
        this.mErrorView.setVisibility(8);
        this.mEmptyView.setVisibility(8);
    }

    public void stop() {
        this.mProgressView.setVisibility(8);
        this.mErrorView.setVisibility(8);
        this.mEmptyView.setVisibility(8);
    }

    public void showEmpty() {
        this.mErrorView.setVisibility(8);
        this.mProgressView.setVisibility(8);
        this.mEmptyView.setVisibility(0);
    }

    public void startWithFadeIn() {
        crossfade(null, this.mProgressView, null);
    }

    public void startWithCrossfade() {
        crossfade(this.mErrorView, this.mProgressView, null);
    }

    public void stopWithFadeOut() {
        crossfade(this.mProgressView, null, null);
    }

    public void stopAndShowEmpty() {
        crossfade(this.mProgressView, this.mEmptyView, null);
    }

    public void stopAndShowError() {
        crossfade(this.mProgressView, this.mErrorView, null);
    }

    public void stopAndCrossfadeWith(View view) {
        stopAndCrossfadeWith(view, null);
    }

    public void stopAndCrossfadeWith(View view, Runnable endAction) {
        crossfade(this.mProgressView, view, new C17852(endAction));
    }

    public boolean isRunning() {
        return this.mProgressView.getVisibility() == 0;
    }

    private void crossfade(View viewToFadeOut, View viewToFadeIn, Runnable endAction) {
        if (viewToFadeIn != null) {
            viewToFadeIn.setVisibility(0);
            if (VERSION.SDK_INT >= 12) {
                fadeIn(viewToFadeIn);
            } else {
                fadeInBackport(viewToFadeIn);
            }
        }
        if (viewToFadeOut != null) {
            viewToFadeOut.setVisibility(0);
            if (VERSION.SDK_INT >= 12) {
                fadeOut(viewToFadeOut, endAction);
            } else {
                fadeOutBackport(viewToFadeOut, endAction);
            }
        }
    }

    @TargetApi(12)
    private void fadeIn(View view) {
        view.setAlpha(0.0f);
        view.animate().alpha(1.0f).setDuration(250).setListener(null);
    }

    private void fadeInBackport(View view) {
        ViewHelper.setAlpha(view, 0.0f);
        ViewPropertyAnimator.animate(view).alpha(1.0f).setDuration(250).setListener(null);
    }

    @TargetApi(12)
    private void fadeOut(View view, Runnable endAction) {
        view.setAlpha(1.0f);
        view.animate().alpha(0.0f).setDuration(250).setListener(new C17863(view, endAction));
    }

    private void fadeOutBackport(View view, Runnable endAction) {
        ViewHelper.setAlpha(view, 1.0f);
        ViewPropertyAnimator.animate(view).alpha(0.0f).setDuration(250).setListener(new C17874(view, endAction));
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        int emptyVisibility = this.mEmptyView.getVisibility();
        String emptyText = ((STextView) this.mEmptyView.findViewById(2131624165)).getText().toString();
        Drawable emptyDrawable = ((SImageView) this.mEmptyView.findViewById(2131624596)).getDrawable();
        removeView(this.mEmptyView);
        this.mEmptyView = inflater.inflate(2130903282, this, false);
        this.mEmptyView.setVisibility(emptyVisibility);
        ((STextView) this.mEmptyView.findViewById(2131624165)).setCoreText(emptyText);
        ((SImageView) this.mEmptyView.findViewById(2131624596)).setImageDrawable(emptyDrawable);
        addView(this.mEmptyView);
    }
}
