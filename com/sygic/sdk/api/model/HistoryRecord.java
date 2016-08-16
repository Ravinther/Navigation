package com.sygic.sdk.api.model;

import android.os.Bundle;

public class HistoryRecord {
    private int HistoryType;
    private Position Location;
    private String strAddress;
    private String strHistoryCaption;

    public HistoryRecord() {
        this.HistoryType = 0;
        this.Location = new Position();
    }

    public static Bundle writeBundle(HistoryRecord rec) {
        if (rec == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("historyType", rec.getHistoryType());
        b.putIntArray("location", rec.getLocation().toArray());
        b.putString("historyCaption", rec.getCaption());
        b.putString("address", rec.getAddress());
        return b;
    }

    public String getCaption() {
        return this.strHistoryCaption;
    }

    public String getAddress() {
        return this.strAddress;
    }

    public int getHistoryType() {
        return this.HistoryType;
    }

    public Position getLocation() {
        return this.Location;
    }

    public String toString() {
        return "HistoryRecord [HistoryType=" + this.HistoryType + ", Location=" + this.Location + ", HistoryCaption=" + this.strHistoryCaption + ", Address=" + this.strAddress + "]";
    }
}
