package com.sygic.aura.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.sygic.aura.SygicMain;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.feature.system.LowSystemFeature.EEventType;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.utils.AdWordsConversionUtils;
import com.sygic.aura.views.font_specials.SToolbar;
import java.util.UUID;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EshopWebviewFragmentDelegate extends WebviewFragmentDelegate {
    private static final Pattern sThankYouPageUrlPattern;
    private int mPreviousSoftMode;
    private boolean mWasFullscreen;

    /* renamed from: com.sygic.aura.fragments.EshopWebviewFragmentDelegate.1 */
    class C12461 implements OnClickListener {
        C12461() {
        }

        public void onClick(View v) {
            EshopWebviewFragmentDelegate.this.handleUpPressed();
        }
    }

    static {
        sThankYouPageUrlPattern = Pattern.compile("(.+)/[a-z]{2}/payment-result/([0-9]+)(\\?.*)?");
    }

    protected EshopWebviewFragmentDelegate(WebViewFragment webViewFragment) {
        super(webViewFragment);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Window window = activity.getWindow();
        LayoutParams attributes = window.getAttributes();
        this.mWasFullscreen = (attributes.flags & 1024) == 1024;
        if (this.mWasFullscreen) {
            window.clearFlags(1024);
        }
        this.mPreviousSoftMode = attributes.softInputMode;
        window.setSoftInputMode(16);
    }

    protected boolean shouldForcePortraitMode() {
        return true;
    }

    public void onSetupToolbar(SToolbar toolbar) {
        Bundle args = this.mF.getArguments();
        if (args != null) {
            toolbar.setTitle(args.getString(AbstractFragment.ARG_TITLE));
        }
        toolbar.setNavigationIconAsUp();
        toolbar.setNavigationOnClickListener(new C12461());
    }

    public void onCreate() {
        super.onCreate();
        Bundle params = new Bundle();
        params.putString("eventName", "entered");
        params.putString("category", "buy_webview");
        SygicAnalyticsLogger.logEvent(this.mF.getActivity(), EventType.WEBVIEW, params);
    }

    public void onDetach() {
        super.onDetach();
        Window window = this.mF.getActivity().getWindow();
        if (this.mWasFullscreen) {
            this.mF.getActivity().getWindow().addFlags(1024);
        }
        window.setSoftInputMode(this.mPreviousSoftMode);
    }

    protected int getLayoutRes() {
        return 2130903139;
    }

    public boolean onBackPressed() {
        boolean consumed = super.onBackPressed();
        if (!consumed) {
            Bundle params = new Bundle();
            params.putString("eventName", "exited");
            params.putString("category", "buy_webview");
            params.putString("label", "exit_by_back_button");
            SygicAnalyticsLogger.logEvent(this.mF.getActivity(), EventType.WEBVIEW, params);
        }
        return consumed;
    }

    protected boolean onUpPressed() {
        boolean consumed = super.onUpPressed();
        if (!consumed) {
            Bundle params = new Bundle();
            params.putString("eventName", "exited");
            params.putString("category", "buy_webview");
            params.putString("label", "exit_by_up_button");
            SygicAnalyticsLogger.logEvent(this.mF.getActivity(), EventType.WEBVIEW, params);
        }
        return consumed;
    }

    protected void onChromeTitleReceived(String title) {
    }

    protected boolean onClientShouldOverrideUrlLoading(String url) {
        if (sThankYouPageUrlPattern.matcher(url).matches()) {
            handleEshopPurchaseDone(url);
            return false;
        } else if (!url.startsWith("com.sygic.aura://done")) {
            return super.onClientShouldOverrideUrlLoading(url);
        } else {
            this.mF.performHomeAction();
            return true;
        }
    }

    private void handleEshopPurchaseDone(String url) {
        Exception e;
        Bundle logParams;
        try {
            String params = Uri.parse(url).getQueryParameter("params");
            if (!TextUtils.isEmpty(params)) {
                JSONArray jsonArray = new JSONArray(params);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productJson = jsonArray.getJSONObject(i);
                    String id = productJson.getString("gp_id");
                    String title = productJson.getString(AbstractFragment.ARG_TITLE);
                    String price = productJson.getString("price");
                    logPurchase(id, title, price, productJson.getString("currency"), productJson.getString("email"));
                    AdWordsConversionUtils.reportConversion(this.mF.getActivity(), price);
                }
                logParams = new Bundle();
                logParams.putString("eventName", "purchased");
                logParams.putString("category", "buy_webview");
                SygicAnalyticsLogger.logEvent(this.mF.getActivity(), EventType.WEBVIEW, logParams);
                LocalBroadcastManager.getInstance(this.mF.getActivity()).sendBroadcast(new Intent("com.sygic.aura.ACTION_PURCHASE_DONE"));
            }
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            CrashlyticsHelper.logException(getClass().getName(), "handleEshopPurchaseDone", e);
            logParams = new Bundle();
            logParams.putString("eventName", "purchased");
            logParams.putString("category", "buy_webview");
            SygicAnalyticsLogger.logEvent(this.mF.getActivity(), EventType.WEBVIEW, logParams);
            LocalBroadcastManager.getInstance(this.mF.getActivity()).sendBroadcast(new Intent("com.sygic.aura.ACTION_PURCHASE_DONE"));
        } catch (UnsupportedOperationException e3) {
            e = e3;
            e.printStackTrace();
            CrashlyticsHelper.logException(getClass().getName(), "handleEshopPurchaseDone", e);
            logParams = new Bundle();
            logParams.putString("eventName", "purchased");
            logParams.putString("category", "buy_webview");
            SygicAnalyticsLogger.logEvent(this.mF.getActivity(), EventType.WEBVIEW, logParams);
            LocalBroadcastManager.getInstance(this.mF.getActivity()).sendBroadcast(new Intent("com.sygic.aura.ACTION_PURCHASE_DONE"));
        }
    }

    private void logPurchase(String id, String title, String price, String currency, String email) {
        SygicMain.getInstance().LogEvent("fb_mobile_purchase", "productId" + ":" + id + ":" + AbstractFragment.ARG_TITLE + ":" + title + ":" + "fb_currency" + ":" + currency, EEventType.ETFBEvent.getValue(), price, true);
        InfinarioAnalyticsLogger.getInstance(this.mF.getActivity()).trackPurchaseWebView(id, price, currency, email);
        Bundle productParams = new Bundle();
        productParams.putString("transaction_id", UUID.randomUUID().toString());
        productParams.putString("affiliation", "WebView");
        productParams.putString("name", title);
        productParams.putString("sku", id);
        productParams.putFloat("price", Float.parseFloat(price));
        productParams.putString("currency", currency);
        SygicAnalyticsLogger.logEvent(this.mF.getActivity(), EventType.WEBVIEW, productParams);
    }
}
