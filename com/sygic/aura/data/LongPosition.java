package com.sygic.aura.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Comparator;

public class LongPosition implements Parcelable {
    public static final Creator<LongPosition> CREATOR;
    public static final LongPosition Invalid;
    public static final long InvalidNativeLong;
    protected int mX;
    protected int mY;

    /* renamed from: com.sygic.aura.data.LongPosition.1 */
    static class C11761 implements Comparator<LongPosition> {
        public int compare(LongPosition leftPosition, LongPosition rightPosition) {
            return (leftPosition.mX - rightPosition.mX) - (leftPosition.mY - rightPosition.mY);
        }
    }

    /* renamed from: com.sygic.aura.data.LongPosition.2 */
    static class C11772 implements Creator<LongPosition> {
        C11772() {
        }

        public LongPosition createFromParcel(Parcel in) {
            return new LongPosition(null);
        }

        public LongPosition[] newArray(int size) {
            return new LongPosition[size];
        }
    }

    static {
        Invalid = new LongPosition();
        InvalidNativeLong = Invalid.toNativeLong();
        CREATOR = new C11772();
    }

    public LongPosition() {
        this.mX = -999999999;
        this.mY = -999999999;
    }

    public LongPosition(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public LongPosition(long lXY) {
        if (lXY == InvalidNativeLong) {
            invalidate();
            return;
        }
        this.mX = (int) (lXY >> 32);
        this.mY = (int) lXY;
    }

    public long toNativeLong() {
        return (((long) this.mY) & 4294967295L) | (((long) this.mX) << 32);
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public double getDoubleX() {
        return ((double) this.mX) / 100000.0d;
    }

    public double getDoubleY() {
        return ((double) this.mY) / 100000.0d;
    }

    public boolean isValid() {
        return (this.mX == -999999999 || this.mY == -999999999) ? false : true;
    }

    public void invalidate() {
        this.mX = -999999999;
        this.mY = -999999999;
    }

    public String toString() {
        return String.format("%d:%d", new Object[]{Integer.valueOf(this.mX), Integer.valueOf(this.mY)});
    }

    private LongPosition(Parcel in) {
        readFromParcel(in);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongPosition that = (LongPosition) o;
        if (this.mX == that.mX && this.mY == that.mY) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.mX * 31) + this.mY;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mX);
        parcel.writeInt(this.mY);
    }

    private void readFromParcel(Parcel parcel) {
        this.mX = parcel.readInt();
        this.mY = parcel.readInt();
    }
}
