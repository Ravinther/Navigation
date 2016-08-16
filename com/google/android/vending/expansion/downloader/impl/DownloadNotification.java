package com.google.android.vending.expansion.downloader.impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Messenger;
import com.android.vending.expansion.downloader.C0154R;
import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderClient;
import loquendo.tts.engine.TTSConst;

public class DownloadNotification implements IDownloaderClient {
    static final int NOTIFICATION_ID;
    private IDownloaderClient mClientProxy;
    private PendingIntent mContentIntent;
    private final Context mContext;
    private Notification mCurrentNotification;
    private String mCurrentText;
    private String mCurrentTitle;
    final ICustomNotification mCustomNotification;
    private CharSequence mLabel;
    private Notification mNotification;
    private final NotificationManager mNotificationManager;
    private DownloadProgressInfo mProgressInfo;
    private int mState;

    public interface ICustomNotification {
        void setCurrentBytes(long j);

        void setIcon(int i);

        void setPendingIntent(PendingIntent pendingIntent);

        void setTicker(CharSequence charSequence);

        void setTimeRemaining(long j);

        void setTitle(CharSequence charSequence);

        void setTotalBytes(long j);

        Notification updateNotification(Context context);
    }

    static {
        NOTIFICATION_ID = "DownloadNotification".hashCode();
    }

    public void setClientIntent(PendingIntent mClientIntent) {
        this.mContentIntent = mClientIntent;
    }

    public void resendState() {
        if (this.mClientProxy != null) {
            this.mClientProxy.onDownloadStateChanged(this.mState);
        }
    }

    public void onDownloadStateChanged(int newState) {
        if (this.mClientProxy != null) {
            this.mClientProxy.onDownloadStateChanged(newState);
        }
        if (newState != this.mState) {
            this.mState = newState;
            if (newState != 1 && this.mContentIntent != null) {
                int iconResource;
                int stringDownloadID;
                boolean ongoingEvent;
                switch (newState) {
                    case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                        iconResource = 17301642;
                        stringDownloadID = C0154R.string.state_unknown;
                        ongoingEvent = false;
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                    case TTSConst.TTSUNICODE /*3*/:
                        iconResource = 17301634;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = true;
                        break;
                    case TTSConst.TTSXML /*4*/:
                        iconResource = 17301633;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = true;
                        break;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                    case TTSConst.TTSEVT_BOOKMARK /*7*/:
                        iconResource = 17301634;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = false;
                        break;
                    case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                    case TTSConst.TTSEVT_ERROR /*16*/:
                    case TTSConst.TTSEVT_JUMP /*17*/:
                    case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                    case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                        iconResource = 17301642;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = false;
                        break;
                    default:
                        iconResource = 17301642;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = true;
                        break;
                }
                this.mCurrentText = this.mContext.getString(stringDownloadID);
                this.mCurrentTitle = this.mLabel.toString();
                this.mCurrentNotification.tickerText = this.mLabel + ": " + this.mCurrentText;
                this.mCurrentNotification.icon = iconResource;
                this.mCurrentNotification.setLatestEventInfo(this.mContext, this.mCurrentTitle, this.mCurrentText, this.mContentIntent);
                Notification notification;
                if (ongoingEvent) {
                    notification = this.mCurrentNotification;
                    notification.flags |= 2;
                } else {
                    notification = this.mCurrentNotification;
                    notification.flags &= -3;
                    notification = this.mCurrentNotification;
                    notification.flags |= 16;
                }
                this.mNotificationManager.notify(NOTIFICATION_ID, this.mCurrentNotification);
            }
        }
    }

    public void onDownloadProgress(DownloadProgressInfo progress) {
        this.mProgressInfo = progress;
        if (this.mClientProxy != null) {
            this.mClientProxy.onDownloadProgress(progress);
        }
        if (progress.mOverallTotal <= 0) {
            this.mNotification.tickerText = this.mCurrentTitle;
            this.mNotification.icon = 17301633;
            this.mNotification.setLatestEventInfo(this.mContext, this.mLabel, this.mCurrentText, this.mContentIntent);
            this.mCurrentNotification = this.mNotification;
        } else {
            this.mCustomNotification.setCurrentBytes(progress.mOverallProgress);
            this.mCustomNotification.setTotalBytes(progress.mOverallTotal);
            this.mCustomNotification.setIcon(17301633);
            this.mCustomNotification.setPendingIntent(this.mContentIntent);
            this.mCustomNotification.setTicker(this.mLabel + ": " + this.mCurrentText);
            this.mCustomNotification.setTitle(this.mLabel);
            this.mCustomNotification.setTimeRemaining(progress.mTimeRemaining);
            this.mCurrentNotification = this.mCustomNotification.updateNotification(this.mContext);
        }
        this.mNotificationManager.notify(NOTIFICATION_ID, this.mCurrentNotification);
    }

    public void setMessenger(Messenger msg) {
        this.mClientProxy = DownloaderClientMarshaller.CreateProxy(msg);
        if (this.mProgressInfo != null) {
            this.mClientProxy.onDownloadProgress(this.mProgressInfo);
        }
        if (this.mState != -1) {
            this.mClientProxy.onDownloadStateChanged(this.mState);
        }
    }

    DownloadNotification(Context ctx, CharSequence applicationLabel) {
        this.mState = -1;
        this.mContext = ctx;
        this.mLabel = applicationLabel;
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        this.mCustomNotification = CustomNotificationFactory.createCustomNotification();
        this.mNotification = new Notification();
        this.mCurrentNotification = this.mNotification;
    }

    public void onServiceConnected(Messenger m) {
    }
}
