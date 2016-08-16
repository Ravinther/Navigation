package com.sygic.aura.fragments;

import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.views.font_specials.SToolbar;

public class PanoramaWebViewFragmentDelegate extends WebviewFragmentDelegate {

    /* renamed from: com.sygic.aura.fragments.PanoramaWebViewFragmentDelegate.1 */
    class C12491 implements OnClickListener {
        C12491() {
        }

        public void onClick(View v) {
            PanoramaWebViewFragmentDelegate.this.handleUpPressed();
        }
    }

    public PanoramaWebViewFragmentDelegate(WebViewFragment fragment) {
        super(fragment);
    }

    public void onSetupToolbar(SToolbar toolbar) {
        toolbar.setNavigationIconAsUp();
        toolbar.setNavigationOnClickListener(new C12491());
    }

    protected boolean shouldForcePortraitMode() {
        return false;
    }
}
