package com.sygic.aura.map.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

public class ModernViewSwitcher extends FrameLayout {
    private SwitchAnimator mAnimator;
    private int mCurrentIndex;

    public interface SwitchAnimator {
        void animate(View view, View view2);
    }

    public static class AlphaSwitchAnimator implements SwitchAnimator {
        private static Interpolator sInterpolator;

        /* renamed from: com.sygic.aura.map.view.ModernViewSwitcher.AlphaSwitchAnimator.1 */
        class C13701 extends AnimatorListenerAdapter {
            final /* synthetic */ View val$goingOutView;

            C13701(View view) {
                this.val$goingOutView = view;
            }

            public void onAnimationEnd(Animator animation) {
                this.val$goingOutView.setVisibility(8);
            }
        }

        static {
            sInterpolator = new DecelerateInterpolator();
        }

        public void animate(View comingInView, View goingOutView) {
            comingInView.setVisibility(0);
            comingInView.setAlpha(0.0f);
            comingInView.animate().alpha(1.0f).setDuration(500).setInterpolator(sInterpolator).setListener(null);
            goingOutView.setVisibility(0);
            goingOutView.setAlpha(1.0f);
            goingOutView.animate().alpha(0.0f).setDuration(500).setInterpolator(sInterpolator).setListener(new C13701(goingOutView));
        }
    }

    public ModernViewSwitcher(Context context) {
        super(context);
        this.mAnimator = new AlphaSwitchAnimator();
    }

    public ModernViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAnimator = new AlphaSwitchAnimator();
    }

    public ModernViewSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAnimator = new AlphaSwitchAnimator();
    }

    @TargetApi(21)
    public ModernViewSwitcher(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mAnimator = new AlphaSwitchAnimator();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int i = 0;
        while (i < getChildCount()) {
            getChildAt(i).setVisibility(i == this.mCurrentIndex ? 0 : 8);
            i++;
        }
    }

    public void setSwitchAnimator(SwitchAnimator animator) {
        this.mAnimator = animator;
    }

    public int getCurrentIndex() {
        return this.mCurrentIndex;
    }

    public void switchTo(int childIndex) {
        if (childIndex != this.mCurrentIndex) {
            View nextChildView = getChildAt(childIndex);
            View currentChildView = getChildAt(this.mCurrentIndex);
            if (nextChildView != null && currentChildView != null) {
                this.mAnimator.animate(nextChildView, currentChildView);
                this.mCurrentIndex = childIndex;
            }
        }
    }
}
