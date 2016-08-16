package com.sygic.aura.views.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;

public class VerticalExpandingAnimator extends ExpandingAnimator {
    private final int mTargetHeight;

    public /* bridge */ /* synthetic */ void collapse() {
        super.collapse();
    }

    public /* bridge */ /* synthetic */ void expand() {
        super.expand();
    }

    public /* bridge */ /* synthetic */ void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
    }

    public /* bridge */ /* synthetic */ void onAnimationRepeat(Animation animation) {
        super.onAnimationRepeat(animation);
    }

    public /* bridge */ /* synthetic */ void onAnimationStart(Animation animation) {
        super.onAnimationStart(animation);
    }

    public /* bridge */ /* synthetic */ boolean willChangeBounds() {
        return super.willChangeBounds();
    }

    public VerticalExpandingAnimator(View view) {
        super(view);
        if (this.mLayoutParams.height < 0) {
            view.measure(this.mLayoutParams.width < 0 ? this.mLayoutParams.width : -1, this.mLayoutParams.height);
            this.mTargetHeight = view.getMeasuredHeight();
            return;
        }
        this.mTargetHeight = this.mLayoutParams.height;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;
        if (this.mExpand.booleanValue()) {
            if (interpolatedTime == 0.0f) {
                this.mView.setVisibility(0);
            }
            newHeight = interpolatedTime == 1.0f ? this.mTargetHeight : (int) (((float) this.mTargetHeight) * interpolatedTime);
        } else {
            newHeight = this.mTargetHeight - ((int) (((float) this.mTargetHeight) * interpolatedTime));
            if (interpolatedTime == 1.0f) {
                this.mView.setVisibility(8);
            }
        }
        this.mLayoutParams.height = newHeight;
        this.mView.requestLayout();
    }

    public static void animateView(View view, boolean show) {
        animateView(view, show, null);
    }

    public static void animateView(View view, boolean show, AnimationListener listener) {
        if (view != null) {
            ExpandingAnimator animator = (ExpandingAnimator) view.getTag();
            if (animator == null) {
                animator = new VerticalExpandingAnimator(view);
                view.setTag(animator);
            }
            animator.setAnimationListener(listener);
            if (show) {
                animator.expand();
            } else {
                animator.collapse();
            }
        }
    }
}
