package com.google.android.vending.expansion.downloader;

import android.os.Messenger;

public interface IDownloaderClient {
    void onDownloadProgress(DownloadProgressInfo downloadProgressInfo);

    void onDownloadStateChanged(int i);

    void onServiceConnected(Messenger messenger);
}
