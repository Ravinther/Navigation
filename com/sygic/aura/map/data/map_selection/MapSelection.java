package com.sygic.aura.map.data.map_selection;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.data.LongPosition;

public class MapSelection implements Parcelable {
    public static final Creator<MapSelection> CREATOR;
    public static final MapSelection Empty;
    private long mData;
    private ENavSelType mNavSelType;
    private LongPosition mPos;

    /* renamed from: com.sygic.aura.map.data.map_selection.MapSelection.1 */
    static class C13251 implements Creator<MapSelection> {
        C13251() {
        }

        public MapSelection createFromParcel(Parcel in) {
            return new MapSelection(null);
        }

        public MapSelection[] newArray(int size) {
            return new MapSelection[size];
        }
    }

    public enum ENavSelType {
        NTUnspecified(0),
        NTCountry(1),
        NTCity(2),
        NTStreet(3),
        NTPoiTree(4),
        NTMemo(5),
        NTHome(6),
        NTParking(7),
        NTRupi(8),
        NTFriend(9),
        NTSearch(10),
        NTVehicle(11);
        
        private final int mIntValue;

        private ENavSelType(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        public static ENavSelType createFromInt(int value) {
            for (ENavSelType navSelType : values()) {
                if (navSelType.getValue() == value) {
                    return navSelType;
                }
            }
            return NTUnspecified;
        }
    }

    static {
        Empty = new MapSelection(LongPosition.InvalidNativeLong, 0, 0);
        CREATOR = new C13251();
    }

    public MapSelection(long pos, int navSelType, long data) {
        this.mPos = new LongPosition(pos);
        this.mData = data;
        this.mNavSelType = ENavSelType.createFromInt(navSelType);
    }

    private MapSelection(Parcel in) {
        readFromParcel(in);
    }

    public LongPosition getPosition() {
        return this.mPos;
    }

    public void setPosition(LongPosition position) {
        this.mPos = position;
    }

    public ENavSelType getNavSelType() {
        return this.mNavSelType;
    }

    public long getData() {
        return this.mData;
    }

    public boolean isVehicle() {
        return this.mNavSelType == ENavSelType.NTVehicle;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.mPos, flags);
        parcel.writeInt(this.mNavSelType.getValue());
        parcel.writeLong(this.mData);
    }

    protected void readFromParcel(Parcel parcel) {
        this.mPos = (LongPosition) parcel.readParcelable(LongPosition.class.getClassLoader());
        this.mNavSelType = ENavSelType.createFromInt(parcel.readInt());
        this.mData = parcel.readLong();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MapSelection that = (MapSelection) o;
        if (this.mData != that.mData) {
            return false;
        }
        if (this.mNavSelType != that.mNavSelType) {
            return false;
        }
        if (this.mPos != null) {
            if (this.mPos.equals(that.mPos)) {
                return true;
            }
        } else if (that.mPos == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mPos != null ? this.mPos.hashCode() : 0) * 31) + ((int) (this.mData ^ (this.mData >>> 32)))) * 31) + this.mNavSelType.getValue();
    }
}
