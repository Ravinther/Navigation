package com.sygic.aura.settings.preferences;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.base.C1799R;

@TargetApi(14)
public abstract class DialogSwitchPreference extends StyleableSwitchPreference {
    protected Context mContext;
    protected String mDialogMessage;
    protected String mDialogTitle;
    protected String mNegativeButtonText;
    private OnCancelListener mOnCancelListener;
    private OnClickListener mOnClickListener;
    protected String mPositiveButtonText;

    public DialogSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DialogSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.DialogSwitchPreference, 0, 0);
        this.mDialogTitle = ResourceManager.getCoreString(a.getString(1));
        this.mDialogMessage = ResourceManager.getCoreString(a.getString(0));
        this.mPositiveButtonText = ResourceManager.getCoreString(a.getString(3));
        this.mNegativeButtonText = ResourceManager.getCoreString(a.getString(2));
        if (this.mDialogTitle == null) {
            this.mDialogTitle = getTitle().toString();
        }
        if (!TextUtils.isEmpty(this.mDialogMessage)) {
            this.mDialogMessage = this.mDialogMessage.replace("%app_name%", context.getString(C1799R.string.app_name));
        }
        a.recycle();
        setTitle(ResourceManager.getCoreString(String.valueOf(getTitle())));
    }

    public DialogSwitchPreference(Context context) {
        super(context);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public void setOnCancelListener(OnCancelListener listener) {
        this.mOnCancelListener = listener;
    }

    protected void showDialog() {
        Builder builder = new Builder(this.mContext).setTitle(this.mDialogTitle).setPositiveButton(this.mPositiveButtonText, this.mOnClickListener).setNegativeButton(this.mNegativeButtonText, this.mOnClickListener).setOnCancelListener(this.mOnCancelListener);
        builder.setMessage(this.mDialogMessage);
        builder.create().show();
    }
}
