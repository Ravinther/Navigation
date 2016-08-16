package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

class ViewCompatJB {
    public static void postInvalidateOnAnimation(View view) {
        view.postInvalidateOnAnimation();
    }

    public static void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
        view.postInvalidate(left, top, right, bottom);
    }

    public static void postOnAnimation(View view, Runnable action) {
        view.postOnAnimation(action);
    }

    public static void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
        view.postOnAnimationDelayed(action, delayMillis);
    }

    public static int getImportantForAccessibility(View view) {
        return view.getImportantForAccessibility();
    }

    public static void setImportantForAccessibility(View view, int mode) {
        view.setImportantForAccessibility(mode);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return view.getParentForAccessibility();
    }

    public static int getMinimumHeight(View view) {
        return view.getMinimumHeight();
    }

    public static void requestApplyInsets(View view) {
        view.requestFitSystemWindows();
    }

    public static boolean getFitsSystemWindows(View view) {
        return view.getFitsSystemWindows();
    }
}
