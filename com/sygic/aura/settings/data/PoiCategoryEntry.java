package com.sygic.aura.settings.data;

public class PoiCategoryEntry extends PoiEntry {
    protected boolean m_bIsCustom;
    protected int m_nBckgIndex;
    protected int m_nConvert;
    protected int m_nParentGroupID;

    public PoiCategoryEntry(int nParentID, int nID, int nBckgIndex, int nConvert, String strResName, String strName, String strBitmap, String strIcon, boolean bIsCustom, boolean bEnabled) {
        super(nID, strResName, strName, strBitmap, strIcon, bEnabled);
        this.m_nParentGroupID = nParentID;
        this.m_nBckgIndex = nBckgIndex;
        this.m_nConvert = nConvert;
        this.m_bIsCustom = bIsCustom;
    }

    public boolean isCustom() {
        return this.m_bIsCustom;
    }
}
