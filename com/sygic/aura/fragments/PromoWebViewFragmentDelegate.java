package com.sygic.aura.fragments;

import android.view.View;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.views.font_specials.SToolbar;

public class PromoWebViewFragmentDelegate extends WebviewFragmentDelegate {
    private String mOriginUrl;

    public PromoWebViewFragmentDelegate(WebViewFragment fragment) {
        super(fragment);
    }

    public void onSetupToolbar(SToolbar toolbar) {
    }

    protected void onChromeTitleReceived(String title) {
    }

    public void onViewCreated(View view) {
        super.onViewCreated(view);
        this.mOriginUrl = this.mF.getArguments().getString("uri");
        InfinarioAnalyticsLogger.getInstance(this.mF.getActivity()).trackOnlinePromoViewed(this.mOriginUrl);
    }

    protected int getLayoutRes() {
        return 2130903141;
    }

    protected boolean shouldForcePortraitMode() {
        return true;
    }

    protected void handleSygicCustomScheme(String url) {
        if (url.equals("com.sygic.aura://close")) {
            InfinarioAnalyticsLogger.getInstance(this.mF.getActivity()).trackOnlinePromoActionClose(this.mOriginUrl);
        } else {
            InfinarioAnalyticsLogger.getInstance(this.mF.getActivity()).trackOnlinePromoActionBuy(this.mOriginUrl);
        }
        super.handleSygicCustomScheme(url);
    }
}
