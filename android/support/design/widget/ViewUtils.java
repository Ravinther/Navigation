package android.support.design.widget;

import android.os.Build.VERSION;

class ViewUtils {
    static final Creator DEFAULT_ANIMATOR_CREATOR;
    private static final ViewUtilsImpl IMPL;

    /* renamed from: android.support.design.widget.ViewUtils.1 */
    static class C00341 implements Creator {
        C00341() {
        }

        public ValueAnimatorCompat createAnimator() {
            return new ValueAnimatorCompat(VERSION.SDK_INT >= 12 ? new ValueAnimatorCompatImplHoneycombMr1() : new ValueAnimatorCompatImplEclairMr1());
        }
    }

    private interface ViewUtilsImpl {
    }

    private static class ViewUtilsImplBase implements ViewUtilsImpl {
        private ViewUtilsImplBase() {
        }
    }

    private static class ViewUtilsImplLollipop implements ViewUtilsImpl {
        private ViewUtilsImplLollipop() {
        }
    }

    static {
        DEFAULT_ANIMATOR_CREATOR = new C00341();
        if (VERSION.SDK_INT >= 21) {
            IMPL = new ViewUtilsImplLollipop();
        } else {
            IMPL = new ViewUtilsImplBase();
        }
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }
}
