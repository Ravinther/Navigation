package com.sygic.aura.settings.data;

public class PoiGroupEntry extends PoiEntry {
    protected String m_strBitmapDis;

    public PoiGroupEntry(int nID, String resName, String strName, String strBitmap, String strIcon, String strBitmapDis, boolean bEnabled) {
        super(nID, resName, strName, strBitmap, strIcon, bEnabled);
        this.m_strBitmapDis = strBitmapDis;
    }
}
