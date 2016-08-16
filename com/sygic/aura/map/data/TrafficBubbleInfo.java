package com.sygic.aura.map.data;

import com.sygic.aura.map.data.map_selection.MapSelection;

public class TrafficBubbleInfo extends BubbleBaseInfo {
    int mDistance;
    int mTime;

    public TrafficBubbleInfo(int screenX, int screenY, MapSelection sel, int time, int distance) {
        super(screenX, screenY, 0, sel);
        this.mTime = time;
        this.mDistance = distance;
    }

    public int getTrafficTime() {
        return this.mTime;
    }

    public int getTrafficDistance() {
        return this.mDistance;
    }
}
