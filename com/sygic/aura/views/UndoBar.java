package com.sygic.aura.views;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import com.sygic.aura.views.font_specials.STextView;

public class UndoBar extends PopupWindow {
    private final Activity mActivity;
    private final Handler mHandler;
    private OnTimeOutListener mOnTimeOutListener;
    private OnUndoListener mOnUndoListener;

    public interface OnUndoListener {
        void onUndo(Context context);
    }

    /* renamed from: com.sygic.aura.views.UndoBar.1 */
    class C17901 implements OnClickListener {
        C17901() {
        }

        public void onClick(View v) {
            UndoBar.this.mHandler.removeCallbacksAndMessages(null);
            UndoBar.this.dismiss();
            if (UndoBar.this.mOnUndoListener != null) {
                UndoBar.this.mOnUndoListener.onUndo(UndoBar.this.mActivity);
            }
        }
    }

    /* renamed from: com.sygic.aura.views.UndoBar.2 */
    class C17912 implements Runnable {
        C17912() {
        }

        public void run() {
            UndoBar.this.dismiss();
            if (UndoBar.this.mOnTimeOutListener != null) {
                UndoBar.this.mOnTimeOutListener.onTimeOut();
            }
        }
    }

    public interface OnTimeOutListener {
        void onTimeOut();
    }

    public static UndoBar newInstance(Activity activity, int messageId) {
        return new UndoBar(activity, LayoutInflater.from(activity).inflate(2130903222, null), messageId);
    }

    private UndoBar(Activity activity, View contentView, int messageId) {
        super(contentView, -2, -2);
        this.mActivity = activity;
        this.mHandler = new Handler();
        ((STextView) contentView.findViewById(2131624502)).setCoreText(messageId);
        ((STextView) contentView.findViewById(2131624503)).setOnClickListener(new C17901());
    }

    public void setOnUndoListener(OnUndoListener listener) {
        this.mOnUndoListener = listener;
    }

    public void show() {
        show(this.mActivity.getWindow().getDecorView(), 81, 0, (int) this.mActivity.getResources().getDimension(2131231078));
    }

    public void show(View view, int gravity, int xOffset, int yOffset) {
        if (!isShowing()) {
            showAtLocation(view, gravity, xOffset, yOffset);
            this.mHandler.postDelayed(new C17912(), 5000);
        }
    }
}
