package com.sygic.aura.map.data;

public class SignpostItem {
    private final int mBackgroundColor;
    private final int mBorderColor;
    private final ESignPostsType mItemType;
    private final String mStrText;
    private final int mSymbol;
    private final int mTextColor;

    public enum ESignPostsType {
        SIGNPOST_TYPE_PLATE(0),
        SIGNPOST_TYPE_LABEL(1),
        SIGNPOST_TYPE_ROAD_SIGN(2),
        SIGNPOST_TYPE_PICTOGRAM(3),
        SIGNPOST_TYPE_BREAK(4);
        
        private final int id;

        private ESignPostsType(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }
    }

    public SignpostItem(int itemType, String strText, int textColor, int backgroundColor, int borderColor, int symbol) {
        this.mItemType = ESignPostsType.values()[itemType];
        this.mStrText = strText;
        this.mTextColor = textColor;
        this.mBackgroundColor = backgroundColor;
        this.mBorderColor = borderColor;
        this.mSymbol = symbol;
        if (this.mItemType.getValue() != itemType) {
            throw new RuntimeException("Invalid enum value");
        }
    }

    public String getText() {
        return this.mStrText;
    }

    public int getColor() {
        return this.mTextColor;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public int getSymbol() {
        return this.mSymbol;
    }

    public ESignPostsType getType() {
        return this.mItemType;
    }
}
