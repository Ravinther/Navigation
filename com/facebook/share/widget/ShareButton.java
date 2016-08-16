package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.C0322R;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;

public final class ShareButton extends ShareButtonBase {

    /* renamed from: com.facebook.share.widget.ShareButton.1 */
    class C04041 implements OnClickListener {
        C04041() {
        }

        public void onClick(View v) {
            ShareDialog dialog;
            ShareButton.this.callExternalOnClickListener(v);
            if (ShareButton.this.getFragment() != null) {
                dialog = new ShareDialog(ShareButton.this.getFragment(), ShareButton.this.getRequestCode());
            } else {
                dialog = new ShareDialog(ShareButton.this.getActivity(), ShareButton.this.getRequestCode());
            }
            dialog.show(ShareButton.this.getShareContent());
        }
    }

    public ShareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, "fb_share_button_create", "fb_share_button_did_tap");
    }

    protected int getDefaultStyleResource() {
        return C0322R.style.com_facebook_button_share;
    }

    protected OnClickListener getShareOnClickListener() {
        return new C04041();
    }

    protected int getDefaultRequestCode() {
        return RequestCodeOffset.Share.toRequestCode();
    }
}
