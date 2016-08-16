package com.sygic.aura.map.notification.data;

import android.content.Context;
import android.view.View;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.map.notification.NotificationCenterView;
import com.sygic.aura.map.notification.NotificationManager;

public abstract class NotificationCenterItem {
    protected final int mId;
    protected final ENITypes mItemType;
    protected final int mNotificationType;
    protected final String mPrimaryText;
    protected final String mSecondaryText;

    enum ENITypes {
        NIButton(0),
        NIIndicator(1),
        NIStatic(2);
        
        private final int mIntValue;

        private ENITypes(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        public static ENITypes createFromInt(int value) {
            for (ENITypes eStatus : values()) {
                if (eStatus.getValue() == value) {
                    return eStatus;
                }
            }
            return NIStatic;
        }
    }

    protected NotificationCenterItem(int id, int notificationType, int type, String primaryText, String secondaryText) {
        this.mId = id;
        this.mNotificationType = notificationType;
        this.mItemType = ENITypes.createFromInt(type);
        this.mPrimaryText = primaryText;
        this.mSecondaryText = secondaryText;
    }

    public int getId() {
        return this.mId;
    }

    public int getNotificationType() {
        return this.mNotificationType;
    }

    public String getPrimaryText() {
        return this.mPrimaryText;
    }

    public String getSecondaryText() {
        return this.mSecondaryText;
    }

    public boolean isClickable() {
        return this.mItemType.equals(ENITypes.NIButton);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationCenterItem item = (NotificationCenterItem) o;
        if (this.mId != item.mId) {
            return false;
        }
        if (this.mNotificationType != item.mNotificationType) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.mId * 31) + this.mNotificationType;
    }

    public void onClick(Context context) {
        NotificationManager.nativePerformClick(this.mId);
        SygicAnalyticsLogger.getAnalyticsEvent(context, EventType.NOTIFICATION_CENTER).setName("notification_centre").setValue("source", getClass().getSimpleName()).logAndRecycle();
    }

    public boolean addViewToHierarchy(NotificationCenterView notificationCenterView, View notificationItemView) {
        notificationCenterView.addView(notificationItemView, 0);
        int childCount = notificationCenterView.getChildCount();
        if (childCount > notificationCenterView.getMaxItemsCount()) {
            notificationCenterView.removeNotificationItem(childCount - 1);
        }
        return true;
    }
}
