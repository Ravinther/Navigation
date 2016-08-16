package com.sygic.aura.helper.interfaces;

public interface ComponentsListener extends StoreBaseListener {
    void onDownloadProgressUpdated(String str, Short sh, Long l, Long l2);

    void onInstallFinished(String str);

    void onInstallWaiting(String str);

    void onUninstallFinished(String str);
}
