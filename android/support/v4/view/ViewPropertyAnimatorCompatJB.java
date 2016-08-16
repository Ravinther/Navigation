package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class ViewPropertyAnimatorCompatJB {

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompatJB.1 */
    static class C00761 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimatorListener val$listener;
        final /* synthetic */ View val$view;

        C00761(ViewPropertyAnimatorListener viewPropertyAnimatorListener, View view) {
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

    public static void setListener(View view, ViewPropertyAnimatorListener listener) {
        if (listener != null) {
            view.animate().setListener(new C00761(listener, view));
        } else {
            view.animate().setListener(null);
        }
    }
}
