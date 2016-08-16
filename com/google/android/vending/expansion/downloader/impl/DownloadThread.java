package com.google.android.vending.expansion.downloader.impl;

import android.content.Context;
import android.net.Proxy;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.util.Log;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.impl.DownloaderService.GenerateSaveFileError;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SyncFailedException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import loquendo.tts.engine.TTSConst;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;

public class DownloadThread {
    private Context mContext;
    private final DownloadsDB mDB;
    private DownloadInfo mInfo;
    private final DownloadNotification mNotification;
    private DownloaderService mService;
    private String mUserAgent;

    private static class InnerState {
        public int mBytesNotified;
        public int mBytesSoFar;
        public int mBytesThisSession;
        public boolean mContinuingDownload;
        public String mHeaderContentDisposition;
        public String mHeaderContentLength;
        public String mHeaderContentLocation;
        public String mHeaderETag;
        public long mTimeLastNotification;

        private InnerState() {
            this.mBytesSoFar = 0;
            this.mBytesThisSession = 0;
            this.mContinuingDownload = false;
            this.mBytesNotified = 0;
            this.mTimeLastNotification = 0;
        }
    }

    private class RetryDownload extends Throwable {
        private RetryDownload() {
        }
    }

    private static class State {
        public boolean mCountRetry;
        public String mFilename;
        public boolean mGotData;
        public String mNewUri;
        public int mRedirectCount;
        public String mRequestUri;
        public int mRetryAfter;
        public FileOutputStream mStream;

        public State(DownloadInfo info, DownloaderService service) {
            this.mCountRetry = false;
            this.mRetryAfter = 0;
            this.mRedirectCount = 0;
            this.mGotData = false;
            this.mRedirectCount = info.mRedirectCount;
            this.mRequestUri = info.mUri;
            this.mFilename = service.generateTempSaveFileName(info.mFileName);
        }
    }

    private class StopRequest extends Throwable {
        public int mFinalStatus;

        public StopRequest(int finalStatus, String message) {
            super(message);
            this.mFinalStatus = finalStatus;
        }

        public StopRequest(int finalStatus, String message, Throwable throwable) {
            super(message, throwable);
            this.mFinalStatus = finalStatus;
        }
    }

    public DownloadThread(DownloadInfo info, DownloaderService service, DownloadNotification notification) {
        this.mContext = service;
        this.mInfo = info;
        this.mService = service;
        this.mNotification = notification;
        this.mDB = DownloadsDB.getDB(service);
        this.mUserAgent = "APKXDL (Linux; U; Android " + VERSION.RELEASE + ";" + Locale.getDefault().toString() + "; " + Build.DEVICE + "/" + Build.ID + ")" + service.getPackageName();
    }

    private String userAgent() {
        return this.mUserAgent;
    }

    public HttpHost getPreferredHttpHost(Context context, String url) {
        if (!(isLocalHost(url) || this.mService.isWiFi())) {
            String proxyHost = Proxy.getHost(context);
            if (proxyHost != null) {
                return new HttpHost(proxyHost, Proxy.getPort(context), "http");
            }
        }
        return null;
    }

    private static final boolean isLocalHost(String url) {
        if (url == null) {
            return false;
        }
        try {
            String host = URI.create(url).getHost();
            if (host == null) {
                return false;
            }
            if (host.equalsIgnoreCase("localhost") || host.equals("127.0.0.1") || host.equals("[::1]")) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void run() {
        HttpGet request;
        Process.setThreadPriority(10);
        State state = new State(this.mInfo, this.mService);
        AndroidHttpClient client = null;
        WakeLock wakeLock = null;
        int finalStatus = 491;
        try {
            wakeLock = ((PowerManager) this.mContext.getSystemService("power")).newWakeLock(1, "LVLDL");
            wakeLock.acquire();
            client = AndroidHttpClient.newInstance(userAgent(), this.mContext);
            boolean finished = false;
            while (!finished) {
                ConnRouteParams.setDefaultProxy(client.getParams(), getPreferredHttpHost(this.mContext, state.mRequestUri));
                request = new HttpGet(state.mRequestUri);
                executeDownload(state, client, request);
                finished = true;
                request.abort();
            }
            finalizeDestinationFile(state);
            if (wakeLock != null) {
                wakeLock.release();
            }
            if (client != null) {
                client.close();
            }
            cleanupDestination(state, 200);
            notifyDownloadCompleted(200, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
        } catch (RetryDownload e) {
            request.abort();
        } catch (StopRequest error) {
            try {
                Log.w("LVLDL", "Aborting request for download " + this.mInfo.mFileName + ": " + error.getMessage());
                error.printStackTrace();
                finalStatus = error.mFinalStatus;
                if (wakeLock != null) {
                    wakeLock.release();
                }
                if (client != null) {
                    client.close();
                }
                cleanupDestination(state, finalStatus);
                notifyDownloadCompleted(finalStatus, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
            } catch (Throwable th) {
                Throwable th2 = th;
                if (wakeLock != null) {
                    wakeLock.release();
                }
                if (client != null) {
                    client.close();
                }
                cleanupDestination(state, finalStatus);
                notifyDownloadCompleted(finalStatus, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
            }
        } catch (Throwable ex) {
            Log.w("LVLDL", "Exception for " + this.mInfo.mFileName + ": " + ex);
            if (wakeLock != null) {
                wakeLock.release();
            }
            if (client != null) {
                client.close();
            }
            cleanupDestination(state, 491);
            notifyDownloadCompleted(491, state.mCountRetry, state.mRetryAfter, state.mRedirectCount, state.mGotData, state.mFilename);
        }
    }

    private void executeDownload(State state, AndroidHttpClient client, HttpGet request) throws StopRequest, RetryDownload {
        InnerState innerState = new InnerState();
        byte[] data = new byte[4096];
        checkPausedOrCanceled(state);
        setupDestinationFile(state, innerState);
        addRequestHeaders(innerState, request);
        checkConnectivity(state);
        this.mNotification.onDownloadStateChanged(3);
        HttpResponse response = sendRequest(state, client, request);
        handleExceptionalStatus(state, innerState, response);
        processResponseHeaders(state, innerState, response);
        InputStream entityStream = openResponseEntity(state, response);
        this.mNotification.onDownloadStateChanged(4);
        transferData(state, innerState, data, entityStream);
    }

    private void checkConnectivity(State state) throws StopRequest {
        switch (this.mService.getNetworkAvailabilityState(this.mDB)) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                throw new StopRequest(195, "waiting for network to return");
            case TTSConst.TTSUNICODE /*3*/:
                throw new StopRequest(197, "waiting for wifi");
            case TTSConst.TTSEVT_TEXT /*5*/:
                throw new StopRequest(195, "roaming is not allowed");
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                throw new StopRequest(196, "waiting for wifi or for download over cellular to be authorized");
            default:
        }
    }

    private void transferData(State state, InnerState innerState, byte[] data, InputStream entityStream) throws StopRequest {
        while (true) {
            int bytesRead = readFromResponse(state, innerState, data, entityStream);
            if (bytesRead == -1) {
                handleEndOfStream(state, innerState);
                return;
            }
            state.mGotData = true;
            writeDataToDestination(state, data, bytesRead);
            innerState.mBytesSoFar += bytesRead;
            innerState.mBytesThisSession += bytesRead;
            reportProgress(state, innerState);
            checkPausedOrCanceled(state);
        }
    }

    private void finalizeDestinationFile(State state) throws StopRequest {
        syncDestination(state);
        String tempFilename = state.mFilename;
        String finalFilename = Helpers.generateSaveFileName(this.mService, this.mInfo.mFileName);
        if (!state.mFilename.equals(finalFilename)) {
            File startFile = new File(tempFilename);
            File destFile = new File(finalFilename);
            if (this.mInfo.mTotalBytes == -1 || this.mInfo.mCurrentBytes != this.mInfo.mTotalBytes) {
                throw new StopRequest(487, "file delivered with incorrect size. probably due to network not browser configured");
            } else if (!startFile.renameTo(destFile)) {
                throw new StopRequest(492, "unable to finalize destination file");
            }
        }
    }

    private void cleanupDestination(State state, int finalStatus) {
        closeDestination(state);
        if (state.mFilename != null && DownloaderService.isStatusError(finalStatus)) {
            new File(state.mFilename).delete();
            state.mFilename = null;
        }
    }

    private void syncDestination(State state) {
        IOException ex;
        RuntimeException ex2;
        FileNotFoundException ex3;
        Throwable th;
        SyncFailedException ex4;
        FileOutputStream downloadedFileStream = null;
        try {
            FileOutputStream downloadedFileStream2 = new FileOutputStream(state.mFilename, true);
            try {
                downloadedFileStream2.getFD().sync();
                if (downloadedFileStream2 != null) {
                    try {
                        downloadedFileStream2.close();
                        downloadedFileStream = downloadedFileStream2;
                        return;
                    } catch (IOException ex5) {
                        Log.w("LVLDL", "IOException while closing synced file: ", ex5);
                        downloadedFileStream = downloadedFileStream2;
                        return;
                    } catch (RuntimeException ex22) {
                        Log.w("LVLDL", "exception while closing file: ", ex22);
                        downloadedFileStream = downloadedFileStream2;
                        return;
                    }
                }
            } catch (FileNotFoundException e) {
                ex3 = e;
                downloadedFileStream = downloadedFileStream2;
                try {
                    Log.w("LVLDL", "file " + state.mFilename + " not found: " + ex3);
                    if (downloadedFileStream != null) {
                        try {
                            downloadedFileStream.close();
                        } catch (IOException ex52) {
                            Log.w("LVLDL", "IOException while closing synced file: ", ex52);
                        } catch (RuntimeException ex222) {
                            Log.w("LVLDL", "exception while closing file: ", ex222);
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (downloadedFileStream != null) {
                        try {
                            downloadedFileStream.close();
                        } catch (IOException ex522) {
                            Log.w("LVLDL", "IOException while closing synced file: ", ex522);
                        } catch (RuntimeException ex2222) {
                            Log.w("LVLDL", "exception while closing file: ", ex2222);
                        }
                    }
                    throw th;
                }
            } catch (SyncFailedException e2) {
                ex4 = e2;
                downloadedFileStream = downloadedFileStream2;
                Log.w("LVLDL", "file " + state.mFilename + " sync failed: " + ex4);
                if (downloadedFileStream != null) {
                    try {
                        downloadedFileStream.close();
                    } catch (IOException ex5222) {
                        Log.w("LVLDL", "IOException while closing synced file: ", ex5222);
                    } catch (RuntimeException ex22222) {
                        Log.w("LVLDL", "exception while closing file: ", ex22222);
                    }
                }
            } catch (IOException e3) {
                ex5222 = e3;
                downloadedFileStream = downloadedFileStream2;
                Log.w("LVLDL", "IOException trying to sync " + state.mFilename + ": " + ex5222);
                if (downloadedFileStream != null) {
                    try {
                        downloadedFileStream.close();
                    } catch (IOException ex52222) {
                        Log.w("LVLDL", "IOException while closing synced file: ", ex52222);
                    } catch (RuntimeException ex222222) {
                        Log.w("LVLDL", "exception while closing file: ", ex222222);
                    }
                }
            } catch (RuntimeException e4) {
                ex222222 = e4;
                downloadedFileStream = downloadedFileStream2;
                Log.w("LVLDL", "exception while syncing file: ", ex222222);
                if (downloadedFileStream != null) {
                    try {
                        downloadedFileStream.close();
                    } catch (IOException ex522222) {
                        Log.w("LVLDL", "IOException while closing synced file: ", ex522222);
                    } catch (RuntimeException ex2222222) {
                        Log.w("LVLDL", "exception while closing file: ", ex2222222);
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                downloadedFileStream = downloadedFileStream2;
                if (downloadedFileStream != null) {
                    downloadedFileStream.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            ex3 = e5;
            Log.w("LVLDL", "file " + state.mFilename + " not found: " + ex3);
            if (downloadedFileStream != null) {
                downloadedFileStream.close();
            }
        } catch (SyncFailedException e6) {
            ex4 = e6;
            Log.w("LVLDL", "file " + state.mFilename + " sync failed: " + ex4);
            if (downloadedFileStream != null) {
                downloadedFileStream.close();
            }
        } catch (IOException e7) {
            ex522222 = e7;
            Log.w("LVLDL", "IOException trying to sync " + state.mFilename + ": " + ex522222);
            if (downloadedFileStream != null) {
                downloadedFileStream.close();
            }
        } catch (RuntimeException e8) {
            ex2222222 = e8;
            Log.w("LVLDL", "exception while syncing file: ", ex2222222);
            if (downloadedFileStream != null) {
                downloadedFileStream.close();
            }
        }
    }

    private void closeDestination(State state) {
        try {
            if (state.mStream != null) {
                state.mStream.close();
                state.mStream = null;
            }
        } catch (IOException e) {
        }
    }

    private void checkPausedOrCanceled(State state) throws StopRequest {
        if (this.mService.getControl() == 1) {
            switch (this.mService.getStatus()) {
                case 193:
                    throw new StopRequest(this.mService.getStatus(), "download paused");
                default:
            }
        }
    }

    private void reportProgress(State state, InnerState innerState) {
        long now = System.currentTimeMillis();
        if (innerState.mBytesSoFar - innerState.mBytesNotified > 4096 && now - innerState.mTimeLastNotification > 1000) {
            this.mInfo.mCurrentBytes = (long) innerState.mBytesSoFar;
            this.mDB.updateDownloadCurrentBytes(this.mInfo);
            innerState.mBytesNotified = innerState.mBytesSoFar;
            innerState.mTimeLastNotification = now;
            this.mService.notifyUpdateBytes(((long) innerState.mBytesThisSession) + this.mService.mBytesSoFar);
        }
    }

    private void writeDataToDestination(State state, byte[] data, int bytesRead) throws StopRequest {
        try {
            if (state.mStream == null) {
                state.mStream = new FileOutputStream(state.mFilename, true);
            }
            state.mStream.write(data, 0, bytesRead);
            closeDestination(state);
        } catch (IOException ex) {
            if (!Helpers.isExternalMediaMounted()) {
                throw new StopRequest(499, "external media not mounted while writing destination file");
            } else if (Helpers.getAvailableBytes(Helpers.getFilesystemRoot(state.mFilename)) < ((long) bytesRead)) {
                throw new StopRequest(498, "insufficient space while writing destination file", ex);
            } else {
                throw new StopRequest(492, "while writing destination file: " + ex.toString(), ex);
            }
        }
    }

    private void handleEndOfStream(State state, InnerState innerState) throws StopRequest {
        this.mInfo.mCurrentBytes = (long) innerState.mBytesSoFar;
        this.mDB.updateDownload(this.mInfo);
        boolean lengthMismatched = (innerState.mHeaderContentLength == null || innerState.mBytesSoFar == Integer.parseInt(innerState.mHeaderContentLength)) ? false : true;
        if (!lengthMismatched) {
            return;
        }
        if (cannotResume(innerState)) {
            throw new StopRequest(489, "mismatched content length");
        }
        throw new StopRequest(getFinalStatusForHttpError(state), "closed socket before end of file");
    }

    private boolean cannotResume(InnerState innerState) {
        return innerState.mBytesSoFar > 0 && innerState.mHeaderETag == null;
    }

    private int readFromResponse(State state, InnerState innerState, byte[] data, InputStream entityStream) throws StopRequest {
        try {
            return entityStream.read(data);
        } catch (IOException ex) {
            logNetworkState();
            this.mInfo.mCurrentBytes = (long) innerState.mBytesSoFar;
            this.mDB.updateDownload(this.mInfo);
            if (cannotResume(innerState)) {
                throw new StopRequest(489, "while reading response: " + ex.toString() + ", can't resume interrupted download with no ETag", ex);
            }
            throw new StopRequest(getFinalStatusForHttpError(state), "while reading response: " + ex.toString(), ex);
        }
    }

    private InputStream openResponseEntity(State state, HttpResponse response) throws StopRequest {
        try {
            return response.getEntity().getContent();
        } catch (IOException ex) {
            logNetworkState();
            throw new StopRequest(getFinalStatusForHttpError(state), "while getting entity: " + ex.toString(), ex);
        }
    }

    private void logNetworkState() {
        Log.i("LVLDL", "Net " + (this.mService.getNetworkAvailabilityState(this.mDB) == 1 ? "Up" : "Down"));
    }

    private void processResponseHeaders(State state, InnerState innerState, HttpResponse response) throws StopRequest {
        if (!innerState.mContinuingDownload) {
            readResponseHeaders(state, innerState, response);
            try {
                state.mFilename = this.mService.generateSaveFile(this.mInfo.mFileName, this.mInfo.mTotalBytes);
                try {
                    state.mStream = new FileOutputStream(state.mFilename);
                } catch (FileNotFoundException exc) {
                    try {
                        if (new File(Helpers.getSaveFilePath(this.mService)).mkdirs()) {
                            state.mStream = new FileOutputStream(state.mFilename);
                        }
                    } catch (Exception e) {
                        throw new StopRequest(492, "while opening destination file: " + exc.toString(), exc);
                    }
                }
                updateDatabaseFromHeaders(state, innerState);
                checkConnectivity(state);
            } catch (GenerateSaveFileError exc2) {
                throw new StopRequest(exc2.mStatus, exc2.mMessage);
            }
        }
    }

    private void updateDatabaseFromHeaders(State state, InnerState innerState) {
        this.mInfo.mETag = innerState.mHeaderETag;
        this.mDB.updateDownload(this.mInfo);
    }

    private void readResponseHeaders(State state, InnerState innerState, HttpResponse response) throws StopRequest {
        Header header = response.getFirstHeader("Content-Disposition");
        if (header != null) {
            innerState.mHeaderContentDisposition = header.getValue();
        }
        header = response.getFirstHeader("Content-Location");
        if (header != null) {
            innerState.mHeaderContentLocation = header.getValue();
        }
        header = response.getFirstHeader("ETag");
        if (header != null) {
            innerState.mHeaderETag = header.getValue();
        }
        String headerTransferEncoding = null;
        header = response.getFirstHeader("Transfer-Encoding");
        if (header != null) {
            headerTransferEncoding = header.getValue();
        }
        header = response.getFirstHeader("Content-Type");
        if (header == null || header.getValue().equals("application/vnd.android.obb")) {
            if (headerTransferEncoding == null) {
                header = response.getFirstHeader("Content-Length");
                if (header != null) {
                    innerState.mHeaderContentLength = header.getValue();
                    long contentLength = Long.parseLong(innerState.mHeaderContentLength);
                    if (!(contentLength == -1 || contentLength == this.mInfo.mTotalBytes)) {
                        Log.e("LVLDL", "Incorrect file size delivered.");
                    }
                }
            }
            boolean noSizeInfo = innerState.mHeaderContentLength == null && (headerTransferEncoding == null || !headerTransferEncoding.equalsIgnoreCase("chunked"));
            if (noSizeInfo) {
                throw new StopRequest(495, "can't know size of download, giving up");
            }
            return;
        }
        throw new StopRequest(487, "file delivered with incorrect Mime type");
    }

    private void handleExceptionalStatus(State state, InnerState innerState, HttpResponse response) throws StopRequest, RetryDownload {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 503 && this.mInfo.mNumFailed < 5) {
            handleServiceUnavailable(state, response);
        }
        if (statusCode == 301 || statusCode == 302 || statusCode == 303 || statusCode == 307) {
            handleRedirect(state, response, statusCode);
        }
        if (statusCode != (innerState.mContinuingDownload ? 206 : 200)) {
            handleOtherStatus(state, innerState, statusCode);
        } else {
            state.mRedirectCount = 0;
        }
    }

    private void handleOtherStatus(State state, InnerState innerState, int statusCode) throws StopRequest {
        int finalStatus;
        if (DownloaderService.isStatusError(statusCode)) {
            finalStatus = statusCode;
        } else if (statusCode >= 300 && statusCode < 400) {
            finalStatus = 493;
        } else if (innerState.mContinuingDownload && statusCode == 200) {
            finalStatus = 489;
        } else {
            finalStatus = 494;
        }
        throw new StopRequest(finalStatus, "http error " + statusCode);
    }

    private void handleRedirect(State state, HttpResponse response, int statusCode) throws StopRequest, RetryDownload {
        if (state.mRedirectCount >= 5) {
            throw new StopRequest(497, "too many redirects");
        }
        Header header = response.getFirstHeader("Location");
        if (header != null) {
            try {
                String newUri = new URI(this.mInfo.mUri).resolve(new URI(header.getValue())).toString();
                state.mRedirectCount++;
                state.mRequestUri = newUri;
                if (statusCode == 301 || statusCode == 303) {
                    state.mNewUri = newUri;
                }
                throw new RetryDownload();
            } catch (URISyntaxException e) {
                throw new StopRequest(495, "Couldn't resolve redirect URI");
            }
        }
    }

    private void addRequestHeaders(InnerState innerState, HttpGet request) {
        if (innerState.mContinuingDownload) {
            if (innerState.mHeaderETag != null) {
                request.addHeader("If-Match", innerState.mHeaderETag);
            }
            request.addHeader("Range", "bytes=" + innerState.mBytesSoFar + "-");
        }
    }

    private void handleServiceUnavailable(State state, HttpResponse response) throws StopRequest {
        state.mCountRetry = true;
        Header header = response.getFirstHeader("Retry-After");
        if (header != null) {
            try {
                state.mRetryAfter = Integer.parseInt(header.getValue());
                if (state.mRetryAfter < 0) {
                    state.mRetryAfter = 0;
                } else {
                    if (state.mRetryAfter < 30) {
                        state.mRetryAfter = 30;
                    } else if (state.mRetryAfter > 86400) {
                        state.mRetryAfter = 86400;
                    }
                    state.mRetryAfter += Helpers.sRandom.nextInt(31);
                    state.mRetryAfter *= 1000;
                }
            } catch (NumberFormatException e) {
            }
        }
        throw new StopRequest(194, "got 503 Service Unavailable, will retry later");
    }

    private HttpResponse sendRequest(State state, AndroidHttpClient client, HttpGet request) throws StopRequest {
        try {
            return client.execute(request);
        } catch (IllegalArgumentException ex) {
            throw new StopRequest(495, "while trying to execute request: " + ex.toString(), ex);
        } catch (IOException ex2) {
            logNetworkState();
            throw new StopRequest(getFinalStatusForHttpError(state), "while trying to execute request: " + ex2.toString(), ex2);
        }
    }

    private int getFinalStatusForHttpError(State state) {
        if (this.mService.getNetworkAvailabilityState(this.mDB) != 1) {
            return 195;
        }
        if (this.mInfo.mNumFailed < 5) {
            state.mCountRetry = true;
            return 194;
        }
        Log.w("LVLDL", "reached max retries for " + this.mInfo.mNumFailed);
        return 495;
    }

    private void setupDestinationFile(State state, InnerState innerState) throws StopRequest {
        if (state.mFilename != null) {
            if (Helpers.isFilenameValid(state.mFilename)) {
                File f = new File(state.mFilename);
                if (f.exists()) {
                    long fileLength = f.length();
                    if (fileLength == 0) {
                        f.delete();
                        state.mFilename = null;
                    } else if (this.mInfo.mETag == null) {
                        f.delete();
                        throw new StopRequest(489, "Trying to resume a download that can't be resumed");
                    } else {
                        try {
                            state.mStream = new FileOutputStream(state.mFilename, true);
                            innerState.mBytesSoFar = (int) fileLength;
                            if (this.mInfo.mTotalBytes != -1) {
                                innerState.mHeaderContentLength = Long.toString(this.mInfo.mTotalBytes);
                            }
                            innerState.mHeaderETag = this.mInfo.mETag;
                            innerState.mContinuingDownload = true;
                        } catch (FileNotFoundException exc) {
                            throw new StopRequest(492, "while opening destination for resuming: " + exc.toString(), exc);
                        }
                    }
                }
            }
            throw new StopRequest(492, "found invalid internal destination filename");
        }
        if (state.mStream != null) {
            closeDestination(state);
        }
    }

    private void notifyDownloadCompleted(int status, boolean countRetry, int retryAfter, int redirectCount, boolean gotData, String filename) {
        updateDownloadDatabase(status, countRetry, retryAfter, redirectCount, gotData, filename);
        if (!DownloaderService.isStatusCompleted(status)) {
        }
    }

    private void updateDownloadDatabase(int status, boolean countRetry, int retryAfter, int redirectCount, boolean gotData, String filename) {
        this.mInfo.mStatus = status;
        this.mInfo.mRetryAfter = retryAfter;
        this.mInfo.mRedirectCount = redirectCount;
        this.mInfo.mLastMod = System.currentTimeMillis();
        if (!countRetry) {
            this.mInfo.mNumFailed = 0;
        } else if (gotData) {
            this.mInfo.mNumFailed = 1;
        } else {
            DownloadInfo downloadInfo = this.mInfo;
            downloadInfo.mNumFailed++;
        }
        this.mDB.updateDownload(this.mInfo);
    }
}
