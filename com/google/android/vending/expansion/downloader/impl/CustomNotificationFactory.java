package com.google.android.vending.expansion.downloader.impl;

import android.os.Build.VERSION;
import com.google.android.vending.expansion.downloader.impl.DownloadNotification.ICustomNotification;

public class CustomNotificationFactory {
    public static ICustomNotification createCustomNotification() {
        if (VERSION.SDK_INT > 13) {
            return new V14CustomNotification();
        }
        return new V3CustomNotification();
    }
}
