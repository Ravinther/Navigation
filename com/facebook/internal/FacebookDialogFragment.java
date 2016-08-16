package com.facebook.internal;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.WebDialog.Builder;
import com.facebook.internal.WebDialog.OnCompleteListener;

public class FacebookDialogFragment extends DialogFragment {
    private Dialog dialog;

    /* renamed from: com.facebook.internal.FacebookDialogFragment.1 */
    class C03411 implements OnCompleteListener {
        C03411() {
        }

        public void onComplete(Bundle values, FacebookException error) {
            FacebookDialogFragment.this.onCompleteWebDialog(values, error);
        }
    }

    /* renamed from: com.facebook.internal.FacebookDialogFragment.2 */
    class C03422 implements OnCompleteListener {
        C03422() {
        }

        public void onComplete(Bundle values, FacebookException error) {
            FacebookDialogFragment.this.onCompleteWebFallbackDialog(values);
        }
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.dialog == null) {
            WebDialog webDialog;
            FragmentActivity activity = getActivity();
            Bundle params = NativeProtocol.getMethodArgumentsFromIntent(activity.getIntent());
            if (params.getBoolean("is_fallback", false)) {
                String url = params.getString("url");
                if (Utility.isNullOrEmpty(url)) {
                    Utility.logd("FacebookDialogFragment", "Cannot start a fallback WebDialog with an empty/missing 'url'");
                    activity.finish();
                    return;
                }
                webDialog = new FacebookWebFallbackDialog(activity, url, String.format("fb%s://bridge/", new Object[]{FacebookSdk.getApplicationId()}));
                webDialog.setOnCompleteListener(new C03422());
            } else {
                String actionName = params.getString("action");
                Bundle webParams = params.getBundle("params");
                if (Utility.isNullOrEmpty(actionName)) {
                    Utility.logd("FacebookDialogFragment", "Cannot start a WebDialog with an empty/missing 'actionName'");
                    activity.finish();
                    return;
                }
                webDialog = new Builder(activity, actionName, webParams).setOnCompleteListener(new C03411()).build();
            }
            this.dialog = webDialog;
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.dialog == null) {
            onCompleteWebDialog(null, null);
            setShowsDialog(false);
        }
        return this.dialog;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.dialog instanceof WebDialog) {
            ((WebDialog) this.dialog).resize();
        }
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    private void onCompleteWebDialog(Bundle values, FacebookException error) {
        FragmentActivity fragmentActivity = getActivity();
        fragmentActivity.setResult(error == null ? -1 : 0, NativeProtocol.createProtocolResultIntent(fragmentActivity.getIntent(), values, error));
        fragmentActivity.finish();
    }

    private void onCompleteWebFallbackDialog(Bundle values) {
        FragmentActivity fragmentActivity = getActivity();
        Intent resultIntent = new Intent();
        if (values == null) {
            values = new Bundle();
        }
        resultIntent.putExtras(values);
        fragmentActivity.setResult(-1, resultIntent);
        fragmentActivity.finish();
    }
}
