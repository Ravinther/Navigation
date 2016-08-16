package com.facebook.internal;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import java.util.List;

public abstract class FacebookDialogBase<CONTENT, RESULT> {
    protected static final Object BASE_AUTOMATIC_MODE;
    private final Activity activity;
    private final Fragment fragment;
    private List<com.facebook.internal.FacebookDialogBase$com.facebook.internal.FacebookDialogBase.ModeHandler> modeHandlers;
    private int requestCode;

    protected abstract class ModeHandler {
        public abstract boolean canShow(CONTENT content);

        public abstract AppCall createAppCall(CONTENT content);

        protected ModeHandler() {
        }

        public Object getMode() {
            return FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
    }

    protected abstract AppCall createBaseAppCall();

    protected abstract List<com.facebook.internal.FacebookDialogBase$com.facebook.internal.FacebookDialogBase$com.facebook.internal.FacebookDialogBase.ModeHandler> getOrderedModeHandlers();

    static {
        BASE_AUTOMATIC_MODE = new Object();
    }

    protected FacebookDialogBase(Activity activity, int requestCode) {
        Validate.notNull(activity, "activity");
        this.activity = activity;
        this.fragment = null;
        this.requestCode = requestCode;
    }

    protected FacebookDialogBase(Fragment fragment, int requestCode) {
        Validate.notNull(fragment, "fragment");
        this.fragment = fragment;
        this.activity = null;
        this.requestCode = requestCode;
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("Cannot use a fragment that is not attached to an activity");
        }
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void show(CONTENT content) {
        showImpl(content, BASE_AUTOMATIC_MODE);
    }

    protected void showImpl(CONTENT content, Object mode) {
        AppCall appCall = createAppCallForMode(content, mode);
        if (appCall == null) {
            String errorMessage = "No code path should ever result in a null appCall";
            Log.e("FacebookDialog", errorMessage);
            if (FacebookSdk.isDebugEnabled()) {
                throw new IllegalStateException(errorMessage);
            }
        } else if (this.fragment != null) {
            DialogPresenter.present(appCall, this.fragment);
        } else {
            DialogPresenter.present(appCall, this.activity);
        }
    }

    protected Activity getActivityContext() {
        if (this.activity != null) {
            return this.activity;
        }
        if (this.fragment != null) {
            return this.fragment.getActivity();
        }
        return null;
    }

    private AppCall createAppCallForMode(CONTENT content, Object mode) {
        boolean anyModeAllowed = mode == BASE_AUTOMATIC_MODE;
        AppCall appCall = null;
        for (ModeHandler handler : cachedModeHandlers()) {
            if ((anyModeAllowed || Utility.areObjectsEqual(handler.getMode(), mode)) && handler.canShow(content)) {
                try {
                    appCall = handler.createAppCall(content);
                    break;
                } catch (FacebookException e) {
                    appCall = createBaseAppCall();
                    DialogPresenter.setupAppCallForValidationError(appCall, e);
                }
            }
        }
        if (appCall != null) {
            return appCall;
        }
        appCall = createBaseAppCall();
        DialogPresenter.setupAppCallForCannotShowError(appCall);
        return appCall;
    }

    private List<com.facebook.internal.FacebookDialogBase$com.facebook.internal.FacebookDialogBase$com.facebook.internal.FacebookDialogBase.ModeHandler> cachedModeHandlers() {
        if (this.modeHandlers == null) {
            this.modeHandlers = getOrderedModeHandlers();
        }
        return this.modeHandlers;
    }
}
