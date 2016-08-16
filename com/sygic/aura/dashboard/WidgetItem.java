package com.sygic.aura.dashboard;

public class WidgetItem implements Comparable<WidgetItem> {
    private int mId;
    private long mMemoId;
    private int mPositionX;
    private int mPositionY;
    private EWidgetSize mSize;
    private String mStrExtName;
    private String mStrName;
    private EWidgetType mType;

    public enum EWidgetSize {
        widgetSizeHalfRow(1),
        widgetSizeOneRow(2),
        widgetSizeDoubleRow(3),
        widgetSizeEmpty(-1);
        
        private final int mIntValue;

        private EWidgetSize(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        static EWidgetSize createFromInt(int value) {
            for (EWidgetSize eSize : values()) {
                if (eSize.getValue() == value) {
                    return eSize;
                }
            }
            return widgetSizeEmpty;
        }
    }

    public enum EWidgetType {
        widgetTypeNone(0),
        widgetTypeFavorite(1),
        widgetTypeHome(2),
        widgetTypeTravelBook(3),
        widgetTypeSOS(4),
        widgetTypeContact(5),
        widgetTypeRecent(6),
        widgetTypeHUD(7),
        widgetTypeBlackBox(8),
        widgetTypeGmeter(9),
        widgetTypeFavoriteRoute(10),
        widgetTypeMeccaFinder(11),
        widgetTypeNavigateToPhoto(12),
        widgetTypeWork(13),
        widgetTypeAddNew(14),
        widgetTypeParking(16),
        widgetTypeAll(99);
        
        private final int mIntValue;

        private EWidgetType(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        static EWidgetType createFromInt(int value) {
            for (EWidgetType eType : values()) {
                if (eType.getValue() == value) {
                    return eType;
                }
            }
            return widgetTypeNone;
        }
    }

    WidgetItem() {
        this.mId = 0;
        this.mMemoId = -1;
        this.mPositionX = -1;
        this.mPositionY = -1;
        this.mStrName = "";
        this.mStrExtName = "";
        this.mSize = EWidgetSize.widgetSizeEmpty;
        this.mType = EWidgetType.widgetTypeNone;
    }

    public WidgetItem(EWidgetType type, EWidgetSize size) {
        this();
        this.mType = type;
        this.mSize = size;
    }

    private WidgetItem(int id, String strName, String strExtName, int memoId, int positionX, int positionY, EWidgetSize size, EWidgetType type) {
        this.mId = id;
        this.mStrName = strName;
        this.mStrExtName = strExtName;
        this.mMemoId = (long) memoId;
        this.mPositionX = positionX;
        this.mPositionY = positionY;
        this.mSize = size;
        this.mType = type;
    }

    public WidgetItem(int id, String strName, String strExtName, int memoId, int positionX, int positionY, int size, int type) {
        this(id, strName, strExtName, memoId, positionX, positionY, EWidgetSize.createFromInt(size), EWidgetType.createFromInt(type));
    }

    public int getItemId() {
        return this.mId;
    }

    public long getMemoId() {
        return this.mMemoId;
    }

    public int getPositionX() {
        return this.mPositionX;
    }

    public int getPositionY() {
        return this.mPositionY;
    }

    public String getName() {
        return this.mStrName;
    }

    public String getExtName() {
        return this.mStrExtName;
    }

    public EWidgetType getType() {
        return this.mType;
    }

    public EWidgetSize getSize() {
        return this.mSize;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setMemoId(long memoId) {
        this.mMemoId = memoId;
    }

    public void setPositionX(int positionX) {
        this.mPositionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.mPositionY = positionY;
    }

    public void setName(String strName) {
        this.mStrName = strName;
    }

    public void setExtName(String strExtName) {
        this.mStrExtName = strExtName;
    }

    public int compareTo(WidgetItem another) {
        if (another == null) {
            return -100;
        }
        int rowDiff = this.mPositionY - another.mPositionY;
        return rowDiff == 0 ? this.mPositionX - another.mPositionX : rowDiff;
    }
}
