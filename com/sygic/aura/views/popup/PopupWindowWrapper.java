package com.sygic.aura.views.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class PopupWindowWrapper implements OnDismissListener {
    protected Context mContext;
    private OnDismissListener mDismissListener;
    protected View mRootView;
    public PopupWindow mWindow;
    protected WindowManager mWindowManager;

    /* renamed from: com.sygic.aura.views.popup.PopupWindowWrapper.1 */
    class C17981 implements OnTouchListener {
        C17981() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() != 4) {
                return false;
            }
            PopupWindowWrapper.this.mWindow.dismiss();
            return true;
        }
    }

    public PopupWindowWrapper(Context context) {
        this.mContext = context;
        this.mWindow = new PopupWindow(context);
        this.mWindow.setBackgroundDrawable(new BitmapDrawable());
        this.mWindow.setTouchInterceptor(new C17981());
        this.mWindow.setOnDismissListener(this);
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }

    public void onDismiss() {
        if (this.mDismissListener != null) {
            this.mDismissListener.onDismiss();
        }
    }

    protected void onShow() {
    }

    protected void preShow() {
        if (this.mRootView == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }
        onShow();
        this.mWindow.setWidth(-2);
        this.mWindow.setHeight(-2);
        this.mWindow.setOutsideTouchable(true);
        this.mWindow.setContentView(this.mRootView);
    }

    public void setContentView(View root) {
        this.mRootView = root;
        this.mWindow.setContentView(root);
    }

    public void dismiss() {
        this.mWindow.dismiss();
    }
}
