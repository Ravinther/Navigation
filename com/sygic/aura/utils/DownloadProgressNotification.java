package com.sygic.aura.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.util.SimpleArrayMap;
import com.sygic.aura.helper.interfaces.ComponentsListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import java.util.HashSet;

public class DownloadProgressNotification implements ComponentsListener {
    private Builder mBuilder;
    private String mDownloadCompletedMsg;
    private String mDownloadProgressdMsg;
    private SimpleArrayMap<String, Short> mInstallProgress;
    private int mInstalled;
    private HashSet<String> mInstalledIds;
    private NotificationManager mNotificationManager;

    public DownloadProgressNotification(Context context, String[] installIds, String strTitle) {
        this.mInstallProgress = new SimpleArrayMap();
        this.mInstalledIds = new HashSet();
        this.mInstalled = 0;
        this.mDownloadCompletedMsg = "";
        this.mDownloadProgressdMsg = "";
        if (installIds != null) {
            for (String id : installIds) {
                this.mInstallProgress.put(id, Short.valueOf((short) 0));
            }
        }
        PendingIntent contentIntent = GuiUtils.getPackageLaunchPendingIntentForNotification(context, 1, null, null, null);
        if (contentIntent != null) {
            this.mDownloadCompletedMsg = ResourceManager.getCoreString(context, 2131165784);
            this.mDownloadProgressdMsg = ResourceManager.getCoreString(context, 2131165785);
            this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
            this.mBuilder = new Builder(context);
            this.mBuilder.setContentTitle(strTitle).setContentText(this.mDownloadProgressdMsg).setColor(context.getResources().getColor(C1799R.color.azure_radiance)).setContentIntent(contentIntent).setAutoCancel(false).setSmallIcon(C1799R.drawable.sygic_bar);
        }
    }

    public void onInstallFinished(String id) {
        this.mInstalledIds.add(id);
        int i = this.mInstalled + 1;
        this.mInstalled = i;
        if (i == this.mInstallProgress.size()) {
            donwnloadFinished();
        }
    }

    public void onDownloadProgressUpdated(String id, Short progress, Long total, Long downloaded) {
        if (!progress.equals(this.mInstallProgress.put(id, progress))) {
            double actualProgressSize = 0.0d;
            for (int i = 0; i < this.mInstallProgress.size(); i++) {
                actualProgressSize += (double) ((Short) this.mInstallProgress.valueAt(i)).shortValue();
            }
            this.mBuilder.setProgress(100, (int) Math.round((actualProgressSize / ((double) (this.mInstallProgress.size() * 100))) * 100.0d), false);
            this.mNotificationManager.notify(1, this.mBuilder.build());
        }
    }

    public void onInstallWaiting(String id) {
    }

    public void onUninstallFinished(String id) {
        if (this.mInstallProgress.containsKey(id)) {
            this.mInstallProgress.remove(id);
            if (this.mInstalledIds.contains(id)) {
                this.mInstalled--;
            } else if (this.mInstalled == this.mInstallProgress.size()) {
                donwnloadFinished();
            }
        }
    }

    public void onListLoaded(ArrayList<StoreEntry> arrayList, Boolean isUpdateRequired) {
    }

    public void onStoreMessage(String message) {
    }

    public void onConnectionChanged(Boolean connected) {
    }

    public void clearNotification() {
        if (this.mNotificationManager != null) {
            this.mNotificationManager.cancel(1);
        }
    }

    public void add(String[] installIds) {
        if (this.mInstallProgress.size() == 0) {
            this.mBuilder.setAutoCancel(false).setContentText(this.mDownloadProgressdMsg);
        }
        for (String id : installIds) {
            this.mInstallProgress.put(id, Short.valueOf((short) 0));
        }
    }

    private void donwnloadFinished() {
        this.mBuilder.setContentText(this.mDownloadCompletedMsg).setProgress(0, 0, false).setAutoCancel(true);
        this.mNotificationManager.notify(1, this.mBuilder.build());
        this.mInstallProgress.clear();
        this.mInstalledIds.clear();
        this.mInstalled = 0;
    }
}
