package com.sygic.sdk.api.model;

import android.os.Bundle;

public class RoadInfo {
    private int lRoadFlags;
    private int lRoadOffset;
    private String strIsoCode;

    public static Bundle writeBundle(RoadInfo info) {
        if (info == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("offset", info.getRoadOffset());
        b.putInt("roadFlags", info.getRoadFlags());
        b.putString("iso", info.getIsoCode());
        return b;
    }

    public String getIsoCode() {
        return this.strIsoCode;
    }

    public int getRoadOffset() {
        return this.lRoadOffset;
    }

    private int getRoadFlags() {
        return this.lRoadFlags;
    }

    public String toString() {
        return "RoadInfo [RoadOffset=" + this.lRoadOffset + ", RoadFlags=" + this.lRoadFlags + ", IsoCode=" + this.strIsoCode + "]";
    }
}
