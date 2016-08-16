package com.sygic.aura.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.sygic.aura.SygicMain;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.WebViewFragment.Mode;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.views.font_specials.SToolbar;
import loquendo.tts.engine.TTSConst;

public abstract class WebviewFragmentDelegate implements BackPressedListener {
    protected final WebViewFragment mF;
    private boolean mHwAccDisabled;
    private int mPreviousOrientation;
    private ProgressBar mProgressBar;
    private SharedPreferences mSharedPreferences;
    private WebView mWebView;

    /* renamed from: com.sygic.aura.fragments.WebviewFragmentDelegate.1 */
    class C12591 implements OnClickListener {
        C12591() {
        }

        public void onClick(View v) {
            WebviewFragmentDelegate.this.handleUpPressed();
        }
    }

    /* renamed from: com.sygic.aura.fragments.WebviewFragmentDelegate.2 */
    class C12602 implements OnMenuItemClickListener {
        C12602() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() != 2131624702) {
                return false;
            }
            WebviewFragmentDelegate.this.openInBrowser(WebviewFragmentDelegate.this.mWebView.getUrl());
            return true;
        }
    }

    /* renamed from: com.sygic.aura.fragments.WebviewFragmentDelegate.3 */
    class C12613 extends WebChromeClient {
        C12613() {
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            WebviewFragmentDelegate.this.onChromeTitleReceived(title);
        }

        public void onProgressChanged(WebView view, int progress) {
            WebviewFragmentDelegate.this.mProgressBar.setProgress(progress);
        }
    }

    /* renamed from: com.sygic.aura.fragments.WebviewFragmentDelegate.4 */
    static /* synthetic */ class C12624 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$fragments$WebViewFragment$Mode;

        static {
            $SwitchMap$com$sygic$aura$fragments$WebViewFragment$Mode = new int[Mode.values().length];
            try {
                $SwitchMap$com$sygic$aura$fragments$WebViewFragment$Mode[Mode.ESHOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$fragments$WebViewFragment$Mode[Mode.PROMO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$fragments$WebViewFragment$Mode[Mode.PANORAMA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            WebviewFragmentDelegate.this.onClientPageStarted();
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            WebviewFragmentDelegate.this.onClientPageFinished(view.getTitle());
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (errorCode != -2) {
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null) {
                return false;
            }
            return WebviewFragmentDelegate.this.onClientShouldOverrideUrlLoading(url);
        }
    }

    protected abstract boolean shouldForcePortraitMode();

    public static WebviewFragmentDelegate fromMode(Mode mode, WebViewFragment fragment) {
        switch (C12624.$SwitchMap$com$sygic$aura$fragments$WebViewFragment$Mode[mode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return new EshopWebviewFragmentDelegate(fragment);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return new PromoWebViewFragmentDelegate(fragment);
            case TTSConst.TTSUNICODE /*3*/:
                return new PanoramaWebViewFragmentDelegate(fragment);
            default:
                return new DefaultWebViewFragmentDelegate(fragment);
        }
    }

    protected WebviewFragmentDelegate(WebViewFragment webViewFragment) {
        this.mF = webViewFragment;
    }

    public void onAttach(Activity activity) {
        if (shouldForcePortraitMode()) {
            this.mPreviousOrientation = activity.getRequestedOrientation();
            activity.setRequestedOrientation(1);
        }
    }

    public void onSetupToolbar(SToolbar toolbar) {
        toolbar.setNavigationIconAsUp();
        toolbar.setNavigationOnClickListener(new C12591());
        toolbar.inflateMenu(2131755036);
        toolbar.setOnMenuItemClickListener(new C12602());
    }

    public void onCreate() {
        ((NaviNativeActivity) this.mF.getActivity()).registerBackPressedListener(this);
    }

    public void onDestroy() {
        ((NaviNativeActivity) this.mF.getActivity()).unregisterBackPressedListener(this);
        if (this.mSharedPreferences != null && !this.mHwAccDisabled) {
            this.mSharedPreferences.edit().putBoolean("com.sygic.aura.hw.acceleration", true).apply();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    protected int getLayoutRes() {
        return 2130903139;
    }

    public void onViewCreated(View view) {
        this.mProgressBar = (ProgressBar) view.findViewById(2131624110);
        this.mWebView = (WebView) view.findViewById(2131624351);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        this.mWebView.setWebChromeClient(new C12613());
        this.mWebView.setBackgroundColor(0);
        if (this.mSharedPreferences == null) {
            this.mSharedPreferences = this.mF.getActivity().getSharedPreferences("fragment_webview", 0);
        }
        if (this.mSharedPreferences != null) {
            if (!this.mSharedPreferences.getBoolean("com.sygic.aura.hw.acceleration", true)) {
                this.mHwAccDisabled = true;
                this.mWebView.setLayerType(1, null);
            }
            this.mSharedPreferences.edit().putBoolean("com.sygic.aura.hw.acceleration", false).apply();
        }
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        Bundle args = this.mF.getArguments();
        if (args == null || args.getString("uri") == null) {
            SToast.makeText(this.mF.getActivity(), 2131166004, 0).show();
        } else {
            this.mWebView.loadUrl(args.getString("uri"));
        }
    }

    public void onDestroyView() {
        View view = this.mF.getView();
        if (view != null) {
            NaviNativeActivity.hideKeyboard(view.getWindowToken());
        }
    }

    public void onDetach() {
        if (shouldForcePortraitMode()) {
            this.mF.getActivity().setRequestedOrientation(this.mPreviousOrientation);
        }
    }

    public boolean onBackPressed() {
        if (!this.mWebView.canGoBack()) {
            return false;
        }
        this.mWebView.goBack();
        return true;
    }

    protected boolean onUpPressed() {
        if (!this.mWebView.canGoBack()) {
            return false;
        }
        this.mWebView.goBack();
        return true;
    }

    protected void handleUpPressed() {
        if (!onUpPressed()) {
            this.mF.performHomeAction();
        }
    }

    protected void onChromeTitleReceived(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.mF.mToolbar.setTitle((CharSequence) title);
        }
    }

    protected boolean onClientShouldOverrideUrlLoading(String url) {
        if (url.startsWith("com.sygic.aura://") || url.startsWith("com.sygic.webcontrol://")) {
            handleSygicCustomScheme(url);
            return true;
        } else if (!url.contains("://") || url.startsWith("http")) {
            return false;
        } else {
            handleOthersCustomScheme(url);
            return true;
        }
    }

    protected void onClientPageStarted() {
        this.mProgressBar.setProgress(0);
    }

    protected void onClientPageFinished(String title) {
        this.mProgressBar.setProgress(0);
    }

    protected void handleSygicCustomScheme(String url) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        intent.setFlags(268435456);
        SygicMain.getInstance().getFeature().getCommonFeature().handleWebLink(intent);
        this.mF.performHomeAction();
    }

    protected void handleOthersCustomScheme(String url) {
        openInBrowser(url);
    }

    protected void openInBrowser(String url) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if (intent.resolveActivity(this.mF.getActivity().getPackageManager()) == null) {
            SToast.makeText(this.mF.getActivity(), 2131166008, 0).show();
            this.mProgressBar.setProgress(0);
            return;
        }
        this.mF.getActivity().startActivity(intent);
    }
}
