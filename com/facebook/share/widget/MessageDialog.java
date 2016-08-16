package com.facebook.share.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.DialogPresenter.ParameterProvider;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FacebookDialogBase$com.facebook.internal.FacebookDialogBase.ModeHandler;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.MessageDialogFeature;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphMessageDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;

public final class MessageDialog extends FacebookDialogBase<ShareContent, Object> {
    private static final int DEFAULT_REQUEST_CODE;
    private boolean shouldFailOnDataError;

    private class NativeHandler extends ModeHandler {

        /* renamed from: com.facebook.share.widget.MessageDialog.NativeHandler.1 */
        class C04021 implements ParameterProvider {
            final /* synthetic */ AppCall val$appCall;
            final /* synthetic */ ShareContent val$content;
            final /* synthetic */ boolean val$shouldFailOnDataError;

            C04021(AppCall appCall, ShareContent shareContent, boolean z) {
                this.val$appCall = appCall;
                this.val$content = shareContent;
                this.val$shouldFailOnDataError = z;
            }

            public Bundle getParameters() {
                return NativeDialogParameters.create(this.val$appCall.getCallId(), this.val$content, this.val$shouldFailOnDataError);
            }

            public Bundle getLegacyParameters() {
                return LegacyNativeDialogParameters.create(this.val$appCall.getCallId(), this.val$content, this.val$shouldFailOnDataError);
            }
        }

        private NativeHandler() {
            super();
        }

        public boolean canShow(ShareContent shareContent) {
            return shareContent != null && MessageDialog.canShow(shareContent.getClass());
        }

        public AppCall createAppCall(ShareContent content) {
            ShareContentValidation.validateForMessage(content);
            AppCall appCall = MessageDialog.this.createBaseAppCall();
            boolean shouldFailOnDataError = MessageDialog.this.getShouldFailOnDataError();
            Activity activity = MessageDialog.this.getActivityContext();
            DialogPresenter.setupAppCallForNativeDialog(appCall, new C04021(appCall, content, shouldFailOnDataError), MessageDialog.getFeature(content.getClass()));
            return appCall;
        }
    }

    static {
        DEFAULT_REQUEST_CODE = RequestCodeOffset.Message.toRequestCode();
    }

    public static boolean canShow(Class<? extends ShareContent> contentType) {
        DialogFeature feature = getFeature(contentType);
        return feature != null && DialogPresenter.canPresentNativeDialogWithFeature(feature);
    }

    MessageDialog(Activity activity, int requestCode) {
        super(activity, requestCode);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(requestCode);
    }

    MessageDialog(Fragment fragment, int requestCode) {
        super(fragment, requestCode);
        this.shouldFailOnDataError = false;
        ShareInternalUtility.registerStaticShareCallback(requestCode);
    }

    public boolean getShouldFailOnDataError() {
        return this.shouldFailOnDataError;
    }

    protected AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    protected List<ModeHandler> getOrderedModeHandlers() {
        ArrayList handlers = new ArrayList();
        handlers.add(new NativeHandler());
        return handlers;
    }

    private static DialogFeature getFeature(Class<? extends ShareContent> contentType) {
        if (ShareLinkContent.class.isAssignableFrom(contentType)) {
            return MessageDialogFeature.MESSAGE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom(contentType)) {
            return MessageDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom(contentType)) {
            return MessageDialogFeature.VIDEO;
        }
        if (ShareOpenGraphContent.class.isAssignableFrom(contentType)) {
            return OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG;
        }
        return null;
    }
}
