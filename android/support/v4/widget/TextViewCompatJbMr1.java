package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

class TextViewCompatJbMr1 {
    public static void setCompoundDrawablesRelative(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
        Drawable drawable;
        boolean rtl = true;
        if (textView.getLayoutDirection() != 1) {
            rtl = false;
        }
        if (rtl) {
            drawable = end;
        } else {
            drawable = start;
        }
        if (!rtl) {
            start = end;
        }
        textView.setCompoundDrawables(drawable, top, start, bottom);
    }
}
