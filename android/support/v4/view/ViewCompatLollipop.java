package android.support.v4.view;

import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;

class ViewCompatLollipop {

    /* renamed from: android.support.v4.view.ViewCompatLollipop.1 */
    static class C00691 implements OnApplyWindowInsetsListener {
        final /* synthetic */ OnApplyWindowInsetsListener val$listener;

        C00691(OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            this.val$listener = onApplyWindowInsetsListener;
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            return ((WindowInsetsCompatApi21) this.val$listener.onApplyWindowInsets(view, new WindowInsetsCompatApi21(windowInsets))).unwrap();
        }
    }

    public static void requestApplyInsets(View view) {
        view.requestApplyInsets();
    }

    public static void setElevation(View view, float elevation) {
        view.setElevation(elevation);
    }

    public static float getElevation(View view) {
        return view.getElevation();
    }

    public static float getTranslationZ(View view) {
        return view.getTranslationZ();
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener listener) {
        view.setOnApplyWindowInsetsListener(new C00691(listener));
    }

    public static WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        if (!(insets instanceof WindowInsetsCompatApi21)) {
            return insets;
        }
        WindowInsets unwrapped = ((WindowInsetsCompatApi21) insets).unwrap();
        WindowInsets result = v.onApplyWindowInsets(unwrapped);
        if (result != unwrapped) {
            return new WindowInsetsCompatApi21(result);
        }
        return insets;
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View v, WindowInsetsCompat insets) {
        if (!(insets instanceof WindowInsetsCompatApi21)) {
            return insets;
        }
        WindowInsets unwrapped = ((WindowInsetsCompatApi21) insets).unwrap();
        WindowInsets result = v.dispatchApplyWindowInsets(unwrapped);
        if (result != unwrapped) {
            return new WindowInsetsCompatApi21(result);
        }
        return insets;
    }

    public static void stopNestedScroll(View view) {
        view.stopNestedScroll();
    }

    public static float getZ(View view) {
        return view.getZ();
    }
}
