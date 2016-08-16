package android.support.v4.view;

import android.view.View;

class ViewCompatKitKat {
    public static boolean isLaidOut(View view) {
        return view.isLaidOut();
    }

    public static boolean isAttachedToWindow(View view) {
        return view.isAttachedToWindow();
    }
}
