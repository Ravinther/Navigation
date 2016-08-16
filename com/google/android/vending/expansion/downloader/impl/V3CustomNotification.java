package com.google.android.vending.expansion.downloader.impl;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.widget.RemoteViews;
import com.android.vending.expansion.downloader.C0154R;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.impl.DownloadNotification.ICustomNotification;

public class V3CustomNotification implements ICustomNotification {
    long mCurrentBytes;
    int mIcon;
    Notification mNotification;
    PendingIntent mPendingIntent;
    CharSequence mTicker;
    long mTimeRemaining;
    CharSequence mTitle;
    long mTotalBytes;

    public V3CustomNotification() {
        this.mTotalBytes = -1;
        this.mCurrentBytes = -1;
        this.mNotification = new Notification();
    }

    public void setIcon(int icon) {
        this.mIcon = icon;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setTotalBytes(long totalBytes) {
        this.mTotalBytes = totalBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.mCurrentBytes = currentBytes;
    }

    public Notification updateNotification(Context c) {
        Notification n = this.mNotification;
        n.icon = this.mIcon;
        n.flags |= 2;
        if (VERSION.SDK_INT > 10) {
            n.flags |= 8;
        }
        RemoteViews expandedView = new RemoteViews(c.getPackageName(), C0154R.layout.status_bar_ongoing_event_progress_bar);
        expandedView.setTextViewText(C0154R.id.title, this.mTitle);
        expandedView.setViewVisibility(C0154R.id.description, 0);
        expandedView.setTextViewText(C0154R.id.description, Helpers.getDownloadProgressString(this.mCurrentBytes, this.mTotalBytes));
        expandedView.setViewVisibility(C0154R.id.progress_bar_frame, 0);
        expandedView.setProgressBar(C0154R.id.progress_bar, (int) (this.mTotalBytes >> 8), (int) (this.mCurrentBytes >> 8), this.mTotalBytes <= 0);
        expandedView.setViewVisibility(C0154R.id.time_remaining, 0);
        expandedView.setTextViewText(C0154R.id.time_remaining, c.getString(C0154R.string.time_remaining_notification, new Object[]{Helpers.getTimeRemaining(this.mTimeRemaining)}));
        expandedView.setTextViewText(C0154R.id.progress_text, Helpers.getDownloadProgressPercent(this.mCurrentBytes, this.mTotalBytes));
        expandedView.setImageViewResource(C0154R.id.appIcon, this.mIcon);
        n.contentView = expandedView;
        n.contentIntent = this.mPendingIntent;
        return n;
    }

    public void setPendingIntent(PendingIntent contentIntent) {
        this.mPendingIntent = contentIntent;
    }

    public void setTicker(CharSequence ticker) {
        this.mTicker = ticker;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.mTimeRemaining = timeRemaining;
    }
}
