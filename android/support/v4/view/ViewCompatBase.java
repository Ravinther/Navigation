package android.support.v4.view;

import android.view.View;
import java.lang.reflect.Field;

class ViewCompatBase {
    private static Field sMinHeightField;
    private static boolean sMinHeightFieldFetched;

    static boolean isLaidOut(View view) {
        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    static int getMinimumHeight(View view) {
        if (!sMinHeightFieldFetched) {
            try {
                sMinHeightField = View.class.getDeclaredField("mMinHeight");
                sMinHeightField.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            sMinHeightFieldFetched = true;
        }
        if (sMinHeightField != null) {
            try {
                return ((Integer) sMinHeightField.get(view)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    static boolean isAttachedToWindow(View view) {
        return view.getWindowToken() != null;
    }
}
