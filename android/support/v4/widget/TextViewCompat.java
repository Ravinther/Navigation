package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.TextView;

public class TextViewCompat {
    static final TextViewCompatImpl IMPL;

    interface TextViewCompatImpl {
        void setCompoundDrawablesRelative(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4);
    }

    static class BaseTextViewCompatImpl implements TextViewCompatImpl {
        BaseTextViewCompatImpl() {
        }

        public void setCompoundDrawablesRelative(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
            textView.setCompoundDrawables(start, top, end, bottom);
        }
    }

    static class JbMr1TextViewCompatImpl extends BaseTextViewCompatImpl {
        JbMr1TextViewCompatImpl() {
        }

        public void setCompoundDrawablesRelative(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
            TextViewCompatJbMr1.setCompoundDrawablesRelative(textView, start, top, end, bottom);
        }
    }

    static class JbMr2TextViewCompatImpl extends JbMr1TextViewCompatImpl {
        JbMr2TextViewCompatImpl() {
        }

        public void setCompoundDrawablesRelative(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
            TextViewCompatJbMr2.setCompoundDrawablesRelative(textView, start, top, end, bottom);
        }
    }

    static {
        int version = VERSION.SDK_INT;
        if (version >= 18) {
            IMPL = new JbMr2TextViewCompatImpl();
        } else if (version >= 17) {
            IMPL = new JbMr1TextViewCompatImpl();
        } else {
            IMPL = new BaseTextViewCompatImpl();
        }
    }

    public static void setCompoundDrawablesRelative(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
        IMPL.setCompoundDrawablesRelative(textView, start, top, end, bottom);
    }
}
