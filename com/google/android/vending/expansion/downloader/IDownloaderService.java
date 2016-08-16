package com.google.android.vending.expansion.downloader;

import android.os.Messenger;

public interface IDownloaderService {
    void onClientUpdated(Messenger messenger);

    void requestAbortDownload();

    void requestContinueDownload();

    void requestDownloadStatus();

    void requestPauseDownload();

    void setDownloadFlags(int i);
}
