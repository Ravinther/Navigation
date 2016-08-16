package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import com.sygic.aura.resources.ResourceManager;

public class CoreDialogPreference extends DialogPreference {
    public CoreDialogPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTitle();
    }

    public CoreDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitle();
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
