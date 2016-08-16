package com.sygic.aura.map.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.data.LongPosition;

public class MemoItem {
    private final long mId;
    private final LongPosition mLongPosition;
    private final int mPriority;
    private final ESelType mSelType;
    private final String mStrData;
    private final String mStrOrigText;
    private final EMemoType mType;

    public enum EMemoType implements Parcelable {
        memoUnknown(-1),
        memoHistory(10),
        memoFavorites(11),
        memoHome(12),
        memoRoute(13),
        memoWork(14),
        memoParking(15),
        memoBookmark(99);
        
        public static final Creator<EMemoType> CREATOR;
        private final int mIntValue;

        /* renamed from: com.sygic.aura.map.data.MemoItem.EMemoType.1 */
        static class C13241 implements Creator<EMemoType> {
            C13241() {
            }

            public EMemoType createFromParcel(Parcel source) {
                return EMemoType.createFromInt(source.readInt());
            }

            public EMemoType[] newArray(int size) {
                return new EMemoType[size];
            }
        }

        static {
            CREATOR = new C13241();
        }

        private EMemoType(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        public static EMemoType createFromInt(int value) {
            for (EMemoType eSize : values()) {
                if (eSize.getValue() == value) {
                    return eSize;
                }
            }
            return memoUnknown;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(getValue());
        }
    }

    public enum ENetItemType {
        typeNone(1),
        typeService(2),
        typeFourSquare(3),
        typeYelp(4),
        typeTripAdvisor(5),
        typeViator(6);
        
        private final int mIntValue;

        private ENetItemType(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        public static ENetItemType createFromInt(int value) {
            for (ENetItemType eSize : values()) {
                if (eSize.getValue() == value) {
                    return eSize;
                }
            }
            return typeNone;
        }
    }

    public enum ESelType {
        selUnknown(0),
        selCityGuide(1),
        selRupi(2),
        selPoi(3),
        selRoad(4),
        selVehicle(5),
        selFlag(6),
        selFriend(7),
        selPhoto(8),
        selMemo(9),
        selHome(10),
        selCityCenter(11),
        selSearch(12),
        selPoiTree(13),
        selWork(14),
        selParking(15);
        
        private final int mIntValue;

        private ESelType(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        public static ESelType createFromInt(int value) {
            for (ESelType eSize : values()) {
                if (eSize.getValue() == value) {
                    return eSize;
                }
            }
            return selUnknown;
        }
    }

    public MemoItem() {
        this.mId = 0;
        this.mStrData = null;
        this.mStrOrigText = null;
        this.mLongPosition = LongPosition.Invalid;
        this.mType = EMemoType.memoUnknown;
        this.mSelType = ESelType.selUnknown;
        this.mPriority = 0;
    }

    public MemoItem(long id, String strData, String strOrigText, LongPosition longPosition, EMemoType type, ESelType selType, int priority) {
        this.mId = id;
        this.mStrData = strData;
        this.mStrOrigText = strOrigText;
        this.mLongPosition = longPosition;
        this.mType = type;
        this.mSelType = selType;
        this.mPriority = priority;
    }

    public MemoItem(long id, String strData, String strOrigText, long longPosition, int type, int selType, int priority) {
        this(id, strData, strOrigText, new LongPosition(longPosition), EMemoType.createFromInt(type), ESelType.createFromInt(selType), priority);
    }

    public long getId() {
        return this.mId;
    }

    public String getStrData() {
        return this.mStrData;
    }

    public String getStrOrigText() {
        return this.mStrOrigText;
    }

    public LongPosition getLongPosition() {
        return this.mLongPosition;
    }

    public EMemoType getType() {
        return this.mType;
    }

    public ESelType getSelType() {
        return this.mSelType;
    }

    public int getPriority() {
        return this.mPriority;
    }
}
