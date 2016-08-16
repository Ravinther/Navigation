package com.sygic.aura.settings.data;

public class TwoListItemData {
    private String mStrLeft;
    private String mStrRight;

    public TwoListItemData(String strLeft, String strRight) {
        this.mStrRight = strRight;
        this.mStrLeft = strLeft;
    }

    public String getRight() {
        return this.mStrRight;
    }

    public String getLeft() {
        return this.mStrLeft;
    }
}
