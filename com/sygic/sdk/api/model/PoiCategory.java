package com.sygic.sdk.api.model;

import android.os.Bundle;

public class PoiCategory {
    public boolean bNotifyOnlyWhenOnRoad;
    public int lNotificationDistance;
    public String strName;
    public String strSound;

    public PoiCategory() {
        this.lNotificationDistance = 0;
        this.bNotifyOnlyWhenOnRoad = false;
    }

    public PoiCategory(int notifDist, boolean notifOnRoad, String name, String sound) {
        this.lNotificationDistance = notifDist;
        this.bNotifyOnlyWhenOnRoad = notifOnRoad;
        this.strName = name;
        this.strSound = sound;
    }

    public static Bundle writeBundle(PoiCategory poiCat) {
        if (poiCat == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("notifDistance", poiCat.getNotificationDistance());
        b.putBoolean("notifyOnRoad", poiCat.isNotifyOnlyWhenOnRoad());
        b.putString("sound", poiCat.getSound());
        b.putString("name", poiCat.getName());
        return b;
    }

    public static PoiCategory readBundle(Bundle b) {
        if (b == null) {
            return null;
        }
        return new PoiCategory(b.getInt("notifDistance"), b.getBoolean("notifyOnRoad"), b.getString("name"), b.getString("sound"));
    }

    public String getName() {
        return this.strName;
    }

    public String getSound() {
        return this.strSound;
    }

    public int getNotificationDistance() {
        return this.lNotificationDistance;
    }

    public boolean isNotifyOnlyWhenOnRoad() {
        return this.bNotifyOnlyWhenOnRoad;
    }

    public String toString() {
        return "PoiCategory [NotificationDistance=" + this.lNotificationDistance + ", NotifyOnlyWhenOnRoad=" + this.bNotifyOnlyWhenOnRoad + ", Name=" + this.strName + ", Sound=" + this.strSound + "]";
    }
}
