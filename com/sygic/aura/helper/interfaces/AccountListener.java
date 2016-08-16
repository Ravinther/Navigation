package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.network.AccountManager.ELoginStatus;

public interface AccountListener extends CoreEventListener {
    void displayToast(String str);

    void onDownloadCompleted();

    void onLoginFinished(Boolean bool, ELoginStatus eLoginStatus);

    void showWaitingDialog();

    void showWaitingDialogMessage(Integer num);
}
