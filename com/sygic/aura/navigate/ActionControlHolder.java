package com.sygic.aura.navigate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import com.sygic.aura.map.view.ModernViewSwitcher;
import com.sygic.aura.views.CircleProgressBar;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;

public abstract class ActionControlHolder {
    protected SImageView mIconTextView;
    private final int mIndexInViewSwitcher;
    protected CircleProgressBar mProgressBar;
    protected ModernViewSwitcher mSwitcher;
    protected STextView mTitleTextView;
    protected View mView;

    /* renamed from: com.sygic.aura.navigate.ActionControlHolder.1 */
    class C13781 implements OnClickListener {
        C13781() {
        }

        public void onClick(View v) {
            ActionControlHolder.this.onClickPositive(v);
        }
    }

    /* renamed from: com.sygic.aura.navigate.ActionControlHolder.2 */
    class C13792 implements OnClickListener {
        C13792() {
        }

        public void onClick(View v) {
            ActionControlHolder.this.onClickNegative(v);
        }
    }

    /* renamed from: com.sygic.aura.navigate.ActionControlHolder.3 */
    class C13803 extends AnimatorListenerAdapter {
        public boolean canceled;

        C13803() {
            this.canceled = false;
        }

        public void onAnimationCancel(Animator animation) {
            this.canceled = true;
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.canceled) {
                ActionControlHolder.this.onTimedOut();
            }
        }
    }

    protected abstract int getTitleForBaseLayout();

    public ActionControlHolder(ModernViewSwitcher switcher, View view) {
        view.setClickable(true);
        this.mIndexInViewSwitcher = switcher.indexOfChild(view);
        this.mSwitcher = switcher;
        this.mView = view.findViewById(2131624111);
        findViews();
    }

    public void show() {
        showInternal();
    }

    protected void findViews() {
        this.mView.setOnClickListener(new C13781());
        this.mView.findViewById(2131624114).setOnClickListener(new C13792());
        this.mProgressBar = (CircleProgressBar) this.mView.findViewById(2131624116);
        this.mProgressBar.setMax(15000);
        this.mTitleTextView = (STextView) this.mView.findViewById(2131624113);
        int titleRes = getTitleForBaseLayout();
        if (!(titleRes == -1 || this.mTitleTextView == null)) {
            this.mTitleTextView.setCoreText(titleRes);
        }
        this.mIconTextView = (SImageView) this.mView.findViewById(2131624112);
        int iconRes = getIconForBaseLayout();
        if (iconRes != -1 && this.mIconTextView != null) {
            this.mIconTextView.setFontDrawableResource(iconRes);
        }
    }

    protected int getIconForBaseLayout() {
        return -1;
    }

    protected void onClickPositive(View v) {
    }

    protected void onClickNegative(View v) {
        hideInternal();
    }

    protected void onTimedOut() {
        hideInternal();
    }

    protected void showInternal() {
        if (this.mSwitcher != null) {
            this.mSwitcher.switchTo(this.mIndexInViewSwitcher);
        }
        startCountDown(this.mProgressBar.getMax());
    }

    protected void hideInternal() {
        if (this.mSwitcher != null) {
            this.mProgressBar.cancelRunningAnimation();
            this.mSwitcher.switchTo(0);
        }
    }

    protected void startCountDown(int duration) {
        this.mProgressBar.setCircleProgress((float) duration);
        this.mProgressBar.setProgressWithAnimation(0.0f, duration, new C13803());
    }

    public void onConfigurationChanged() {
        Resources resources = this.mView.getContext().getResources();
        int marginSides = (int) resources.getDimension(2131230728);
        ((LayoutParams) this.mView.getLayoutParams()).setMargins(marginSides, 0, marginSides, (int) resources.getDimension(2131230727));
    }

    public void onLanguageChanged() {
        if (this.mTitleTextView != null) {
            int titleRes = getTitleForBaseLayout();
            if (titleRes != -1) {
                this.mTitleTextView.setCoreText(titleRes);
            }
        }
    }
}
