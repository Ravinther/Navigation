package android.support.v7.internal.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v7.appcompat.C0101R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AppCompatPopupWindow extends PopupWindow {
    private final boolean mOverlapAnchor;

    /* renamed from: android.support.v7.internal.widget.AppCompatPopupWindow.1 */
    static class C01201 implements OnScrollChangedListener {
        final /* synthetic */ Field val$fieldAnchor;
        final /* synthetic */ OnScrollChangedListener val$originalListener;
        final /* synthetic */ PopupWindow val$popup;

        C01201(Field field, PopupWindow popupWindow, OnScrollChangedListener onScrollChangedListener) {
            this.val$fieldAnchor = field;
            this.val$popup = popupWindow;
            this.val$originalListener = onScrollChangedListener;
        }

        public void onScrollChanged() {
            try {
                WeakReference<View> mAnchor = (WeakReference) this.val$fieldAnchor.get(this.val$popup);
                if (mAnchor != null && mAnchor.get() != null) {
                    this.val$originalListener.onScrollChanged();
                }
            } catch (IllegalAccessException e) {
            }
        }
    }

    public AppCompatPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C0101R.styleable.PopupWindow, defStyleAttr, 0);
        this.mOverlapAnchor = a.getBoolean(C0101R.styleable.PopupWindow_overlapAnchor, false);
        setBackgroundDrawable(a.getDrawable(C0101R.styleable.PopupWindow_android_popupBackground));
        a.recycle();
        if (VERSION.SDK_INT < 14) {
            wrapOnScrollChangedListener(this);
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (VERSION.SDK_INT < 21 && this.mOverlapAnchor) {
            yoff -= anchor.getHeight();
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @TargetApi(19)
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (VERSION.SDK_INT < 21 && this.mOverlapAnchor) {
            yoff -= anchor.getHeight();
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public void update(View anchor, int xoff, int yoff, int width, int height) {
        if (VERSION.SDK_INT < 21 && this.mOverlapAnchor) {
            yoff -= anchor.getHeight();
        }
        super.update(anchor, xoff, yoff, width, height);
    }

    private static void wrapOnScrollChangedListener(PopupWindow popup) {
        try {
            Field fieldAnchor = PopupWindow.class.getDeclaredField("mAnchor");
            fieldAnchor.setAccessible(true);
            Field fieldListener = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            fieldListener.setAccessible(true);
            fieldListener.set(popup, new C01201(fieldAnchor, popup, (OnScrollChangedListener) fieldListener.get(popup)));
        } catch (Exception e) {
            Log.d("AppCompatPopupWindow", "Exception while installing workaround OnScrollChangedListener", e);
        }
    }
}
