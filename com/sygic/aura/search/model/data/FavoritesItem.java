package com.sygic.aura.search.model.data;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.resources.FontDrawable;

public abstract class FavoritesItem extends ListItem {
    protected long mMemoId;
    protected int mRequestedAddressIndex;

    protected abstract int getIconResId();

    protected FavoritesItem(String strName, String strExtName, MapSelection mapSel, long memoId, int iconType) {
        super(strName, strExtName, mapSel, iconType);
        this.mMemoId = memoId;
        this.mRequestedAddressIndex = 0;
    }

    public long getMemoId() {
        return this.mMemoId;
    }

    public Drawable getIcon(Resources resources) {
        return getIconResId() == 0 ? null : FontDrawable.inflate(resources, getIconResId());
    }

    public boolean hasOnlyOneAddress() {
        String[] addressDescriptions = getAddressDescriptions();
        if (addressDescriptions == null || addressDescriptions.length == 1) {
            return true;
        }
        return false;
    }

    public String getAddressSummary() {
        return null;
    }

    public String[] getAddressDescriptions() {
        return null;
    }

    protected long[] getAddressLocations() {
        return null;
    }

    public boolean hasSummary() {
        long[] addressLocations = getAddressLocations();
        return addressLocations != null && addressLocations.length > 0;
    }

    public void setRequestedAddress(int which) {
        this.mRequestedAddressIndex = which;
    }
}
