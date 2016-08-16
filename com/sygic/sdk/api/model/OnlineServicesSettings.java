package com.sygic.sdk.api.model;

public class OnlineServicesSettings {
    boolean bFriendsEnabled;
    boolean bTrafficAutoRecompute;
    boolean bTrafficEnabled;
    boolean bTrafficShowNotification;
    boolean bTrafficShowOnMap;
    boolean bTrapsEnabled;

    public boolean[] toArray() {
        return new boolean[]{this.bFriendsEnabled, this.bTrafficEnabled, this.bTrapsEnabled, this.bTrafficShowNotification, this.bTrafficAutoRecompute, this.bTrafficShowOnMap};
    }

    public String toString() {
        return "SOnlineServicesSettings [FriendsEnabled=" + this.bFriendsEnabled + ", TrafficEnabled=" + this.bTrafficEnabled + ", TrapsEnabled=" + this.bTrapsEnabled + ", TrafficShowNotification=" + this.bTrafficShowNotification + ", TrafficAutoRecompute=" + this.bTrafficAutoRecompute + ", TrafficShowOnMap=" + this.bTrafficShowOnMap + "]";
    }
}
