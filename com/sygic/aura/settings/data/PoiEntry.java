package com.sygic.aura.settings.data;

import com.sygic.aura.helper.LocalizedStringComparator;
import java.util.Comparator;

public class PoiEntry {
    private static final int EXTENSIONS_ID = -9006;
    private static final int IMPORTED_POIS_ID = -9002;
    protected boolean m_bEnabled;
    protected int m_nID;
    protected String m_strBitmap;
    protected String m_strIcon;
    protected String m_strName;
    private String m_strResName;

    /* renamed from: com.sygic.aura.settings.data.PoiEntry.1 */
    static class C16081 implements Comparator<PoiEntry> {
        final /* synthetic */ LocalizedStringComparator val$comparator;

        C16081(LocalizedStringComparator localizedStringComparator) {
            this.val$comparator = localizedStringComparator;
        }

        public int compare(PoiEntry lhs, PoiEntry rhs) {
            if (rhs == null) {
                return 1;
            }
            if (lhs == null) {
                return -1;
            }
            if (lhs.m_nID == PoiEntry.EXTENSIONS_ID || lhs.m_nID == PoiEntry.IMPORTED_POIS_ID) {
                return 1;
            }
            if (rhs.m_nID == PoiEntry.EXTENSIONS_ID || rhs.m_nID == PoiEntry.IMPORTED_POIS_ID) {
                return -1;
            }
            return this.val$comparator.compare(lhs.m_strName, rhs.m_strName);
        }
    }

    public PoiEntry(int nID, String strResName, String strName, String strBitmap, String strIcon, boolean bEnabled) {
        this.m_nID = nID;
        this.m_strResName = strResName;
        this.m_strName = strName;
        this.m_strBitmap = strBitmap;
        this.m_strIcon = strIcon;
        this.m_bEnabled = bEnabled;
    }

    public PoiEntry(PoiEntry entry) {
        this(entry.m_nID, entry.m_strResName, entry.m_strName, entry.m_strBitmap, entry.m_strIcon, entry.m_bEnabled);
    }

    public int getID() {
        return this.m_nID;
    }

    public String getResName() {
        return this.m_strResName;
    }

    public String getName() {
        return this.m_strName;
    }

    public boolean getShowStatus() {
        return this.m_bEnabled;
    }

    public void setShowStatus(boolean bEnabled) {
        this.m_bEnabled = bEnabled;
    }

    public static Comparator<PoiEntry> getComparator(LocalizedStringComparator comparator) {
        return new C16081(comparator);
    }
}
