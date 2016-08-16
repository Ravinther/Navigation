package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class FloatingActionButtonHoneycombMr1 extends FloatingActionButtonEclairMr1 {
    private boolean mIsHiding;

    /* renamed from: android.support.design.widget.FloatingActionButtonHoneycombMr1.1 */
    class C00111 extends AnimatorListenerAdapter {
        C00111() {
        }

        public void onAnimationStart(Animator animation) {
            FloatingActionButtonHoneycombMr1.this.mIsHiding = true;
        }

        public void onAnimationCancel(Animator animation) {
            FloatingActionButtonHoneycombMr1.this.mIsHiding = false;
        }

        public void onAnimationEnd(Animator animation) {
            FloatingActionButtonHoneycombMr1.this.mIsHiding = false;
            FloatingActionButtonHoneycombMr1.this.mView.setVisibility(8);
        }
    }

    FloatingActionButtonHoneycombMr1(View view, ShadowViewDelegate shadowViewDelegate) {
        super(view, shadowViewDelegate);
    }

    void hide() {
        if (!this.mIsHiding) {
            this.mView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(new C00111());
        }
    }

    void show() {
        this.mView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(null);
    }
}
