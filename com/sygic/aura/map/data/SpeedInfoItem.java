package com.sygic.aura.map.data;

public class SpeedInfoItem {
    private boolean mAmerica;
    private boolean mExclamation;
    private int mSpeed;

    public boolean isAmerica() {
        return this.mAmerica;
    }

    public int getSpeed() {
        return this.mSpeed;
    }

    public boolean isExclamation() {
        return this.mExclamation;
    }

    public SpeedInfoItem(int speed, boolean exclamation, boolean america) {
        this.mSpeed = speed;
        this.mExclamation = exclamation;
        this.mAmerica = america;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpeedInfoItem that = (SpeedInfoItem) o;
        if (this.mAmerica == that.mAmerica && this.mExclamation == that.mExclamation && this.mSpeed == that.mSpeed) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2 = 1;
        int i3 = this.mSpeed * 31;
        if (this.mExclamation) {
            i = 1;
        } else {
            i = 0;
        }
        i = (i3 + i) * 31;
        if (!this.mAmerica) {
            i2 = 0;
        }
        return i + i2;
    }
}
