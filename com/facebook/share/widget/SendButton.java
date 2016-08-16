package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.C0322R;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;

public final class SendButton extends ShareButtonBase {

    /* renamed from: com.facebook.share.widget.SendButton.1 */
    class C04031 implements OnClickListener {
        C04031() {
        }

        public void onClick(View v) {
            MessageDialog dialog;
            SendButton.this.callExternalOnClickListener(v);
            if (SendButton.this.getFragment() != null) {
                dialog = new MessageDialog(SendButton.this.getFragment(), SendButton.this.getRequestCode());
            } else {
                dialog = new MessageDialog(SendButton.this.getActivity(), SendButton.this.getRequestCode());
            }
            dialog.show(SendButton.this.getShareContent());
        }
    }

    public SendButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, "fb_send_button_create", "fb_send_button_did_tap");
    }

    protected int getDefaultStyleResource() {
        return C0322R.style.com_facebook_button_send;
    }

    protected OnClickListener getShareOnClickListener() {
        return new C04031();
    }

    protected int getDefaultRequestCode() {
        return RequestCodeOffset.Message.toRequestCode();
    }
}
