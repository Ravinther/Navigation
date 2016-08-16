package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface InvokeCommandListener extends CoreEventListener {
    void onCloseFragments();

    void onDownloadMap(String str);

    void onStoreInvoked(Integer num, String str);
}
