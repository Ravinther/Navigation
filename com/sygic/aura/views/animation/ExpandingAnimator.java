package com.sygic.aura.views.animation;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

abstract class ExpandingAnimator extends Animation implements AnimationListener {
    protected Boolean mExpand;
    protected final LayoutParams mLayoutParams;
    protected final View mView;

    protected ExpandingAnimator(View view) {
        this.mExpand = null;
        this.mView = view;
        this.mLayoutParams = view.getLayoutParams();
        setDuration(500);
        setAnimationListener(this);
    }

    public boolean willChangeBounds() {
        return true;
    }

    public void expand() {
        if (this.mExpand == null || !this.mExpand.booleanValue()) {
            this.mExpand = Boolean.valueOf(true);
            this.mView.clearAnimation();
            this.mView.startAnimation(this);
        }
    }

    public void collapse() {
        if (this.mExpand == null || this.mExpand.booleanValue()) {
            this.mExpand = Boolean.valueOf(false);
            this.mView.clearAnimation();
            this.mView.startAnimation(this);
        }
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
