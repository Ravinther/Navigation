package com.sygic.aura.search.model.data;

import android.view.View;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.holder.ViewHolder;

public abstract class SearchItem extends ListItem {
    protected long mID;
    protected String mStrIso;
    protected long mTreeEntry;

    public enum SearchType {
        NONE,
        COUNTRY,
        CITY,
        STREET,
        STREET_NUM,
        HOUSE_NO,
        QUICK,
        SPECIAL
    }

    public abstract SearchType getSearchType();

    public abstract ViewHolder newViewHolderInstance(View view);

    protected SearchItem(String strIso, String strName) {
        super(strName);
        this.mStrIso = strIso;
        this.mTreeEntry = 0;
    }

    protected SearchItem(String strIso, String strName, String strExtName, long treeEntry, MapSelection mapSel, int iconType) {
        super(strName, strExtName, mapSel, iconType);
        this.mStrIso = strIso;
        this.mTreeEntry = treeEntry;
    }

    public long getTreeEntry() {
        return this.mTreeEntry;
    }

    public long getID() {
        return this.mID;
    }

    public String getIso() {
        return this.mStrIso;
    }

    public boolean checkMapSelection(LocationQuery locationQuery) {
        if (this.mMapSel != null) {
            return true;
        }
        this.mMapSel = locationQuery.getMapSelection(this.mTreeEntry);
        if (this.mMapSel != null) {
            return true;
        }
        return false;
    }
}
