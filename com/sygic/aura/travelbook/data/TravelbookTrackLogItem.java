package com.sygic.aura.travelbook.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.route.RouteManager.RouteComputeMode;

public class TravelbookTrackLogItem implements Parcelable {
    public static final Creator<TravelbookTrackLogItem> CREATOR;
    private int mIndex;
    private RouteComputeMode mItemType;
    private String mStrDate;
    private String mStrDistance;
    private String mStrDownHill;
    private String mStrDuration;
    private String mStrEndAddress;
    private String mStrEndTime;
    private String mStrPace;
    private String mStrSpeed;
    private String mStrStartAddress;
    private String mStrStartTime;
    private String mStrUpHill;
    private int mTimeStamp;
    private boolean mbFavourite;

    /* renamed from: com.sygic.aura.travelbook.data.TravelbookTrackLogItem.1 */
    static class C17621 implements Creator<TravelbookTrackLogItem> {
        C17621() {
        }

        public TravelbookTrackLogItem createFromParcel(Parcel source) {
            return new TravelbookTrackLogItem(source);
        }

        public TravelbookTrackLogItem[] newArray(int size) {
            return new TravelbookTrackLogItem[size];
        }
    }

    public enum ETravelLogDataType {
        TYPE_HEADER(0),
        TYPE_ALTITUDE_DISTANCE(1),
        TYPE_ALTITUDE_TIME(2),
        TYPE_SPEED_DISTANCE(3),
        TYPE_SPEED_TIME(4),
        TYPE_ACCELERATION_DISTANCE(5),
        TYPE_ACCELERATION_TIME(6),
        TYPE_PACE_DISTANCE(7),
        TYPE_NONE(8);
        
        private final int id;

        private ETravelLogDataType(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }
    }

    public TravelbookTrackLogItem(int nIndex, int nTimeStamp, String strStartAddress, String strEndAddress, String strStartTime, String strEndTime, String strDate, String strDuration, String strDistance, String strSpeed, String strPace, String strUpHill, String strDownHill, boolean bFavourite, int m_ItemType) {
        this.mIndex = nIndex;
        this.mTimeStamp = nTimeStamp;
        this.mStrStartAddress = strStartAddress;
        this.mStrEndAddress = strEndAddress;
        this.mStrStartTime = strStartTime;
        this.mStrEndTime = strEndTime;
        this.mStrDate = strDate;
        this.mStrDuration = strDuration;
        this.mStrDistance = strDistance;
        this.mStrSpeed = strSpeed;
        this.mStrPace = strPace;
        this.mStrUpHill = strUpHill;
        this.mStrDownHill = strDownHill;
        this.mbFavourite = bFavourite;
        this.mItemType = RouteComputeMode.values()[m_ItemType];
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getTimeStamp() {
        return this.mTimeStamp;
    }

    public String getStartAddress() {
        return this.mStrStartAddress;
    }

    public String getEndAddress() {
        return this.mStrEndAddress;
    }

    public String getStartTime() {
        return this.mStrStartTime;
    }

    public String getEndTime() {
        return this.mStrEndTime;
    }

    public String getDate() {
        return this.mStrDate;
    }

    public String getDuration() {
        return this.mStrDuration;
    }

    public String getDistance() {
        return this.mStrDistance;
    }

    public String getSpeed() {
        return this.mStrSpeed;
    }

    public String getPace() {
        return this.mStrPace;
    }

    public String getUpHill() {
        return this.mStrUpHill;
    }

    public String getDownHil() {
        return this.mStrDownHill;
    }

    public boolean isFavourite() {
        return this.mbFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.mbFavourite = isFavourite;
    }

    static {
        CREATOR = new C17621();
    }

    public TravelbookTrackLogItem(Parcel source) {
        boolean z = true;
        this.mIndex = source.readInt();
        this.mTimeStamp = source.readInt();
        this.mStrStartAddress = source.readString();
        this.mStrEndAddress = source.readString();
        this.mStrStartTime = source.readString();
        this.mStrEndTime = source.readString();
        this.mStrDate = source.readString();
        this.mStrDuration = source.readString();
        this.mStrDistance = source.readString();
        this.mStrSpeed = source.readString();
        this.mStrPace = source.readString();
        this.mStrUpHill = source.readString();
        this.mStrDownHill = source.readString();
        if (source.readInt() != 1) {
            z = false;
        }
        this.mbFavourite = z;
        this.mItemType = RouteComputeMode.values()[source.readInt()];
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mIndex);
        dest.writeInt(this.mTimeStamp);
        dest.writeString(this.mStrStartAddress);
        dest.writeString(this.mStrEndAddress);
        dest.writeString(this.mStrStartTime);
        dest.writeString(this.mStrEndTime);
        dest.writeString(this.mStrDate);
        dest.writeString(this.mStrDuration);
        dest.writeString(this.mStrDistance);
        dest.writeString(this.mStrSpeed);
        dest.writeString(this.mStrPace);
        dest.writeString(this.mStrUpHill);
        dest.writeString(this.mStrDownHill);
        dest.writeInt(this.mbFavourite ? 1 : 0);
        dest.writeInt(this.mItemType.ordinal());
    }

    public int describeContents() {
        return 0;
    }
}
