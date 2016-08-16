package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.resources.ResourceManager;

public class CoreEditTextPreference extends EditTextPreference {
    private View mView;

    public CoreEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTitle();
    }

    public CoreEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitle();
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        this.mView = view;
    }

    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (this.mView != null) {
            NaviNativeActivity.hideKeyboard(this.mView.getWindowToken());
        }
    }

    private void initTitle() {
        CharSequence titleId = getTitle();
        if (titleId != null) {
            String title = ResourceManager.getCoreString(titleId.toString());
            setTitle(title);
            setDialogTitle(title);
        }
        CharSequence positiveBtnId = getPositiveButtonText();
        if (positiveBtnId != null) {
            setPositiveButtonText(ResourceManager.getCoreString(positiveBtnId.toString()));
        }
        CharSequence negativeBtnId = getNegativeButtonText();
        if (negativeBtnId != null) {
            setNegativeButtonText(ResourceManager.getCoreString(negativeBtnId.toString()));
        }
        CharSequence msgId = getDialogMessage();
        if (msgId != null) {
            setDialogMessage(ResourceManager.getCoreString(msgId.toString()));
        }
    }
}
