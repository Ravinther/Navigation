package com.sygic.sdk.api.model;

import android.os.Bundle;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

public class RouteInfo {
    private Rectangle mBoundaryRectangle;
    private int[] mDistToWp;
    private int mEstimatedTimeArrival;
    private int mRemainingDistance;
    private int mRemainingTime;
    private int mStatus;
    private int[] mTimeToWp;
    private int mTotalDistance;
    private int mTotalTime;

    public class RouteInfoTime {
        private int mDay;
        private int mHour;
        private int mMinute;
        private int mMonth;
        private int mSecond;
        private int mYear;
        private final long ms_2001;

        private RouteInfoTime(long lTime) {
            this.mYear = 0;
            this.mMonth = 0;
            this.mDay = 0;
            this.mHour = 0;
            this.mMinute = 0;
            this.mSecond = 0;
            this.ms_2001 = new GregorianCalendar(2001, 1, 0).getTimeInMillis();
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(new Date((1000 * lTime) + this.ms_2001));
            this.mYear = c.get(1);
            this.mMonth = c.get(2);
            this.mDay = c.get(5);
            this.mHour = c.get(11);
            this.mMinute = c.get(12);
            this.mSecond = c.get(13);
        }

        public String toString() {
            return "[Year=" + this.mYear + ", Month=" + this.mMonth + ", Day=" + this.mDay + ", Hour=" + this.mHour + ", Minute=" + this.mMinute + ", Second=" + this.mSecond + "]";
        }
    }

    public static Bundle writeBundle(RouteInfo info) {
        if (info == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("totalDistance", info.getTotalDistance());
        b.putInt("remianDist", info.getRemainingDistance());
        b.putIntArray("boundary", info.getBoundaryRectangle().toArray());
        b.putInt("status", info.getStatus());
        b.putInt("eta", info.getEta());
        b.putInt("remTime", info.getRemainingTime());
        b.putInt("totalTime", info.getTotalTime());
        b.putIntArray("timeToWp", info.getTimeToWp());
        b.putIntArray("distToWp", info.getDistanceToWp());
        return b;
    }

    private int getEta() {
        return this.mEstimatedTimeArrival;
    }

    public int getTotalDistance() {
        return this.mTotalDistance;
    }

    public int getRemainingDistance() {
        return this.mRemainingDistance;
    }

    public Rectangle getBoundaryRectangle() {
        return this.mBoundaryRectangle;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getRemainingTime() {
        return this.mRemainingTime;
    }

    public int getTotalTime() {
        return this.mTotalTime;
    }

    public int[] getTimeToWp() {
        return this.mTimeToWp;
    }

    public int[] getDistanceToWp() {
        return this.mDistToWp;
    }

    public RouteInfoTime getEstimatedTimeArrival() {
        return new RouteInfoTime((long) this.mEstimatedTimeArrival, null);
    }

    public String toString() {
        return "RouteInfo [TotalDistance=" + this.mTotalDistance + ", RemainingDistance=" + this.mRemainingDistance + ", BoundaryRectangle=" + this.mBoundaryRectangle + ", Status=" + this.mStatus + ", EstimatedTimeArrival=" + getEstimatedTimeArrival().toString() + ", RemainingTime=" + this.mRemainingTime + ", TotalTime=" + this.mTotalTime + ", TimeToWp=" + Arrays.toString(this.mTimeToWp) + ", DistanceToWp=" + Arrays.toString(this.mDistToWp) + "]";
    }
}
