package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;

class ViewPropertyAnimatorCompatICS {

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompatICS.1 */
    static class C00751 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimatorListener val$listener;
        final /* synthetic */ View val$view;

        C00751(ViewPropertyAnimatorListener viewPropertyAnimatorListener, View view) {
            this.val$listener = viewPropertyAnimatorListener;
            this.val$view = view;
        }

        public void onAnimationCancel(Animator animation) {
            this.val$listener.onAnimationCancel(this.val$view);
        }

        public void onAnimationEnd(Animator animation) {
            this.val$listener.onAnimationEnd(this.val$view);
        }

        public void onAnimationStart(Animator animation) {
            this.val$listener.onAnimationStart(this.val$view);
        }
    }

    public static void setDuration(View view, long value) {
        view.animate().setDuration(value);
    }

    public static void alpha(View view, float value) {
        view.animate().alpha(value);
    }

    public static void translationX(View view, float value) {
        view.animate().translationX(value);
    }

    public static void translationY(View view, float value) {
        view.animate().translationY(value);
    }

    public static void setInterpolator(View view, Interpolator value) {
        view.animate().setInterpolator(value);
    }

    public static void setStartDelay(View view, long value) {
        view.animate().setStartDelay(value);
    }

    public static void scaleY(View view, float value) {
        view.animate().scaleY(value);
    }

    public static void cancel(View view) {
        view.animate().cancel();
    }

    public static void start(View view) {
        view.animate().start();
    }

    public static void setListener(View view, ViewPropertyAnimatorListener listener) {
        if (listener != null) {
            view.animate().setListener(new C00751(listener, view));
        } else {
            view.animate().setListener(null);
        }
    }
}
