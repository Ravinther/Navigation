package com.nineoldandroids.view;

import android.animation.Animator.AnimatorListener;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import java.lang.ref.WeakReference;

class ViewPropertyAnimatorICS extends ViewPropertyAnimator {
    private final WeakReference<ViewPropertyAnimator> mNative;

    /* renamed from: com.nineoldandroids.view.ViewPropertyAnimatorICS.1 */
    class C10741 implements AnimatorListener {
        final /* synthetic */ Animator.AnimatorListener val$listener;

        C10741(Animator.AnimatorListener animatorListener) {
            this.val$listener = animatorListener;
        }

        public void onAnimationStart(android.animation.Animator animation) {
            this.val$listener.onAnimationStart(null);
        }

        public void onAnimationRepeat(android.animation.Animator animation) {
            this.val$listener.onAnimationRepeat(null);
        }

        public void onAnimationEnd(android.animation.Animator animation) {
            this.val$listener.onAnimationEnd(null);
        }

        public void onAnimationCancel(android.animation.Animator animation) {
            this.val$listener.onAnimationCancel(null);
        }
    }

    ViewPropertyAnimatorICS(View view) {
        this.mNative = new WeakReference(view.animate());
    }

    public ViewPropertyAnimator setDuration(long duration) {
        ViewPropertyAnimator n = (ViewPropertyAnimator) this.mNative.get();
        if (n != null) {
            n.setDuration(duration);
        }
        return this;
    }

    public ViewPropertyAnimator setStartDelay(long startDelay) {
        ViewPropertyAnimator n = (ViewPropertyAnimator) this.mNative.get();
        if (n != null) {
            n.setStartDelay(startDelay);
        }
        return this;
    }

    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        ViewPropertyAnimator n = (ViewPropertyAnimator) this.mNative.get();
        if (n != null) {
            n.setInterpolator(interpolator);
        }
        return this;
    }

    public ViewPropertyAnimator setListener(Animator.AnimatorListener listener) {
        ViewPropertyAnimator n = (ViewPropertyAnimator) this.mNative.get();
        if (n != null) {
            if (listener == null) {
                n.setListener(null);
            } else {
                n.setListener(new C10741(listener));
            }
        }
        return this;
    }

    public void cancel() {
        ViewPropertyAnimator n = (ViewPropertyAnimator) this.mNative.get();
        if (n != null) {
            n.cancel();
        }
    }

    public ViewPropertyAnimator translationY(float value) {
        ViewPropertyAnimator n = (ViewPropertyAnimator) this.mNative.get();
        if (n != null) {
            n.translationY(value);
        }
        return this;
    }

    public ViewPropertyAnimator alpha(float value) {
        ViewPropertyAnimator n = (ViewPropertyAnimator) this.mNative.get();
        if (n != null) {
            n.alpha(value);
        }
        return this;
    }
}
