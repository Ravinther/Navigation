package com.sygic.sdk.api.model;

import android.os.Bundle;

public class TmcEvent {
    private boolean bUserAvoid;
    private int lX;
    private int lY;
    private int nEventCode;
    private byte nValidityDay;
    private byte nValidityHour;
    private byte nValidityMinute;
    private byte nValidityMonth;
    private int nValidityYear;
    private int wID;

    public TmcEvent() {
        this.wID = 0;
        this.lX = 0;
        this.lY = 0;
        this.nEventCode = -1;
        this.bUserAvoid = false;
        this.nValidityYear = 0;
        this.nValidityMonth = (byte) 0;
        this.nValidityDay = (byte) 0;
        this.nValidityHour = (byte) 0;
        this.nValidityMinute = (byte) 0;
    }

    public TmcEvent(int id, int x, int y, int eventCode, boolean userAvoid, int validityYear, byte validityMonth, byte validityDay, byte validityHour, byte validityMinute) {
        this.wID = 0;
        this.lX = 0;
        this.lY = 0;
        this.nEventCode = -1;
        this.bUserAvoid = false;
        this.nValidityYear = 0;
        this.nValidityMonth = (byte) 0;
        this.nValidityDay = (byte) 0;
        this.nValidityHour = (byte) 0;
        this.nValidityMinute = (byte) 0;
        this.wID = id;
        this.lX = x;
        this.lY = y;
        this.nEventCode = eventCode;
        this.bUserAvoid = userAvoid;
        this.nValidityYear = validityYear;
        this.nValidityMonth = validityMonth;
        this.nValidityDay = validityDay;
        this.nValidityHour = validityHour;
        this.nValidityMinute = validityMinute;
    }

    public static Bundle writeBundle(TmcEvent event) {
        if (event == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("id", event.getId());
        b.putInt("x", event.getX());
        b.putInt("y", event.getY());
        b.putInt("eventCode", event.getEventCode());
        b.putBoolean("userAvoid", event.isUserAvoid());
        b.putInt("validityYear", event.getValidityYear());
        b.putByteArray("validity", new byte[]{event.getValidityMonth(), event.getValidityDay(), event.getValidityHour(), event.getValidityMinute()});
        return b;
    }

    public static TmcEvent readBundle(Bundle b) {
        if (b == null) {
            return null;
        }
        byte[] val = b.getByteArray("validity");
        return new TmcEvent(b.getInt("id"), b.getInt("x"), b.getInt("y"), b.getInt("eventCode"), b.getBoolean("userAvoid"), b.getInt("validityYear"), val[0], val[1], val[2], val[3]);
    }

    public int getId() {
        return this.wID;
    }

    public int getX() {
        return this.lX;
    }

    public int getY() {
        return this.lY;
    }

    public int getEventCode() {
        return this.nEventCode;
    }

    public boolean isUserAvoid() {
        return this.bUserAvoid;
    }

    public int getValidityYear() {
        return this.nValidityYear;
    }

    public byte getValidityMonth() {
        return this.nValidityMonth;
    }

    public byte getValidityDay() {
        return this.nValidityDay;
    }

    public byte getValidityHour() {
        return this.nValidityHour;
    }

    public byte getValidityMinute() {
        return this.nValidityMinute;
    }

    public String toString() {
        return "TmcEvent [Id=" + this.wID + ", X=" + this.lX + ", Y=" + this.lY + ", EventCode=" + this.nEventCode + ", UserAvoid=" + this.bUserAvoid + ", ValidityYear=" + this.nValidityYear + ", ValidityMonth=" + this.nValidityMonth + ", ValidityDay=" + this.nValidityDay + ", ValidityHour=" + this.nValidityHour + ", ValidityMinute=" + this.nValidityMinute + "]";
    }
}
