package com.google.android.vending.expansion.downloader;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DownloadProgressInfo implements Parcelable {
    public static final Creator<DownloadProgressInfo> CREATOR;
    public float mCurrentSpeed;
    public long mOverallProgress;
    public long mOverallTotal;
    public long mTimeRemaining;

    /* renamed from: com.google.android.vending.expansion.downloader.DownloadProgressInfo.1 */
    static class C10301 implements Creator<DownloadProgressInfo> {
        C10301() {
        }

        public DownloadProgressInfo createFromParcel(Parcel parcel) {
            return new DownloadProgressInfo(parcel);
        }

        public DownloadProgressInfo[] newArray(int i) {
            return new DownloadProgressInfo[i];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel p, int i) {
        p.writeLong(this.mOverallTotal);
        p.writeLong(this.mOverallProgress);
        p.writeLong(this.mTimeRemaining);
        p.writeFloat(this.mCurrentSpeed);
    }

    public DownloadProgressInfo(Parcel p) {
        this.mOverallTotal = p.readLong();
        this.mOverallProgress = p.readLong();
        this.mTimeRemaining = p.readLong();
        this.mCurrentSpeed = p.readFloat();
    }

    public DownloadProgressInfo(long overallTotal, long overallProgress, long timeRemaining, float currentSpeed) {
        this.mOverallTotal = overallTotal;
        this.mOverallProgress = overallProgress;
        this.mTimeRemaining = timeRemaining;
        this.mCurrentSpeed = currentSpeed;
    }

    static {
        CREATOR = new C10301();
    }
}
