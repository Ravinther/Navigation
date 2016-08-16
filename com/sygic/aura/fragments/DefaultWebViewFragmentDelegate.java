package com.sygic.aura.fragments;

import android.text.TextUtils;

public class DefaultWebViewFragmentDelegate extends WebviewFragmentDelegate {
    public DefaultWebViewFragmentDelegate(WebViewFragment fragment) {
        super(fragment);
    }

    protected boolean shouldForcePortraitMode() {
        return false;
    }

    protected void onClientPageFinished(String title) {
        super.onClientPageFinished(title);
        if (!TextUtils.isEmpty(title)) {
            this.mF.mToolbar.setTitle((CharSequence) title);
        }
    }
}
