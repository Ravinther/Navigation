package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.network.AccountManager;
import com.sygic.aura.resources.FontDrawable;
import loquendo.tts.engine.TTSConst;

public class UserPreference extends Preference {
    public UserPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public UserPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserPreference(Context context) {
        super(context);
    }

    protected void onBindView(View view) {
        int drawableResId;
        super.onBindView(view);
        View userName = view.findViewById(2131624109);
        ((TextView) userName).setText(AccountManager.nativeGetUserName());
        switch (AccountManager.nativeGetServiceLogged()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                drawableResId = 2131166808;
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                drawableResId = 2131166094;
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                drawableResId = 2131166108;
                break;
            default:
                drawableResId = 0;
                break;
        }
        ((TextView) userName).setCompoundDrawables(null, null, FontDrawable.inflate(getContext().getResources(), 2131034115, getContext().getString(drawableResId)), null);
    }

    protected void onPrepareForRemoval() {
        super.onPrepareForRemoval();
        AccountManager.cancelAvatarDownload();
    }
}
